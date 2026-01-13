package com.example.dairy.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dairy.model.MilkCollection;
import com.example.dairy.model.Utpadak;
import com.example.dairy.repo.MilkCollectionRepository;
import com.example.dairy.repo.UtpadakRepository;
import com.example.dairy.service.MilkCollectionService;

@Controller
@RequestMapping("/milk-collection")
public class MilkController {

    @Autowired
    private UtpadakRepository producerRepo;

    @Autowired
    private MilkCollectionService collectionService;
    
    @Autowired
    private MilkCollectionRepository collectionRepository; // ही ओळ तपासा


    // १. मुख्य पेज दाखवणे
    @GetMapping("/entry")
    public String showPage() {
        return "milk_collection";
    }

    // २. उत्पादकाचा डेटा शोधणे (AJAX साठी)
    @GetMapping("/get-producer/{code}")
    @ResponseBody
    public ResponseEntity<Utpadak> getProducer(@PathVariable String code) {
        try {
            // String कोडला Long मध्ये रूपांतरित करा (कारण तुमची ID Long आहे)
            Long id = Long.parseLong(code);
            
            // findById वापरा
            Optional<Utpadak> utpadak = producerRepo.findById(id);
            
            return utpadak.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ३. सर्व डेटा जतन करणे
    @PostMapping("/save")
    @ResponseBody
    public String saveAll(@RequestBody List<MilkCollection> entries) {
        collectionService.saveAll(entries);
        return "डेटा यशस्वीरित्या जतन केला!";
    }
    
    
    @GetMapping("/report")
    public String showReportPage(Model model) {
        // सुरुवातीला रिकामी लिस्ट पाठवणे
        model.addAttribute("reportData", new ArrayList<MilkCollection>());
        return "milk_report"; 
    }
    

    @PostMapping("/generate-report")
    public String generateReport(@RequestParam(required = false) String prodCode, // String मध्ये घ्या
                                 @RequestParam String fromDate, 
                                 @RequestParam String toDate, 
                                 Model model) {
        
        LocalDate start = LocalDate.parse(fromDate);
        LocalDate end = LocalDate.parse(toDate);
        
        List<MilkCollection> list;
        
        // जर युजरने कोड टाकला असेल तर (रिकामे नसेल तर)
        if (prodCode != null && !prodCode.isEmpty()) {
            list = collectionRepository.findByGrahakCodeAndCollectionDateBetween(prodCode, start, end);
        } else {
            list = collectionRepository.findByCollectionDateBetween(start, end);
        }
        
        model.addAttribute("reportData", list);
        return "milk_report";
    }
    
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteEntry(@PathVariable Long id) {
        try {
            collectionRepository.deleteById(id);
            return ResponseEntity.ok("Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting record");
        }
    }
    
    @GetMapping("/")
    public String logout() {
        // इथे तुम्ही सेशन (Session) इनव्हॅलिडेट करू शकता
        return "index"; // किंवा तुमच्या होम पेजचा मार्ग द्या
    }
}