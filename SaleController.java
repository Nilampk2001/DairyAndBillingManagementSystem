package com.example.dairy.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dairy.model.Feed;
import com.example.dairy.model.Sale;
import com.example.dairy.model.Utpadak;
import com.example.dairy.repo.SaleRepository;
import com.example.dairy.service.SaleService;

@Controller
public class SaleController {
    @Autowired private SaleService saleService;
    @Autowired private SaleRepository saleRepo;

    @GetMapping("/sale-form")
    public String showForm(Model model) {
        model.addAttribute("nextPavati", saleService.getNextPavatiNo());
        model.addAttribute("today", LocalDate.now());
        return "sale_form";
    }
    

    @GetMapping("/api/get-utpadak/{code}")
    @ResponseBody
    public Utpadak getUtpadak(@PathVariable String code) {
        return saleService.getUtpadak(code);
    }

    @GetMapping("/api/get-feed/{code}")
    @ResponseBody
    public Feed getFeed(@PathVariable String code) {
        return saleService.getFeed(code);
    }
 // रिपोर्ट पेज उघडण्यासाठी
    @GetMapping("/sales-report")
    public String showReportPage(Model model) {
        // सुरुवातीला आजच्या तारखेच्या नोंदी दाखवण्यासाठी
        model.addAttribute("sales", saleRepo.findByDateBetween(LocalDate.now(), LocalDate.now()));
        return "sales_report";
    }
 // फिल्टर बटण क्लिक केल्यावर डेटा मिळवण्यासाठी (AJAX द्वारे)
    @GetMapping("/api/get-report")
    @ResponseBody
    public List<Sale> getFilteredReport(@RequestParam String fromDate, @RequestParam String toDate) {
        LocalDate start = LocalDate.parse(fromDate);
        LocalDate end = LocalDate.parse(toDate);
        return saleRepo.findByDateBetween(start, end);
    }

    
    @PostMapping("/api/save-sale")
    @ResponseBody
    public ResponseEntity<String> saveSale(@RequestBody Sale sale) {
        try {
            // जर तुमची तारीख String स्वरूपात येत असेल आणि Entity मध्ये LocalDate असेल
            // तर येथे योग्य कन्वर्जन करा किंवा साध्या पद्धतीने सेव्ह करा.
            saleService.saveSale(sale);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace(); // कंसोलमध्ये नेमका काय एरर आहे ते पाहण्यासाठी
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    
 // १. नोंद डिलीट करण्यासाठी
    @DeleteMapping("/api/delete-sale/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteSale(@PathVariable Long id) {
        saleRepo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    // २. नोंद एडिट करण्यासाठी (जुना डेटा फॉर्मवर लोड करणे)
    @GetMapping("/edit-sale/{id}")
    public String editSale(@PathVariable Long id, Model model) {
        Sale sale = saleRepo.findById(id).orElse(null);
        if (sale != null) {
            model.addAttribute("sale", sale);
            model.addAttribute("nextPavati", sale.getPavatiNo()); 
            model.addAttribute("today", sale.getDate());
            return "sale_form"; 
        }
        return "redirect:/sales_report"; // येथे 'sales_report' करा (अंडरस्कोर सह)
    }
}