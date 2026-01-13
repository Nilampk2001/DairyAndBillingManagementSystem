package com.example.dairy.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dairy.model.DudhSankalan;
import com.example.dairy.service.DudhSankalanService;

@Controller
@RequestMapping("/milk")
public class DudhSankalanController {

    @Autowired
    private DudhSankalanService service;

    // १. सुरुवातीला पेज लोड करताना
    @GetMapping("/form")
    public String showForm(Model model) {
        // आजची तारीख (Formatting नीट तपासा, DB मध्ये याच फॉरमॅटमध्ये सेव्ह होतोय का?)
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
        
        // फॉर्मसाठी नवीन ऑब्जेक्ट
        if (!model.containsAttribute("milkEntry")) {
            DudhSankalan newEntry = new DudhSankalan();
            newEntry.setCollectionDate(today);
            newEntry.setShift("S");
            model.addAttribute("milkEntry", newEntry);
        }
        
        // *** महत्त्वाचा बदल: जुना सर्व डेटा हवा असेल तर service.getAllCollections() वापरा ***
        // सध्या तुम्ही फक्त आजचा डेटा दाखवत आहात:
        List<DudhSankalan> todayList = service.getTodayCollections(today);
        model.addAttribute("collections", todayList);
        
        return "dudh-sankalan"; 
    }

    // २. डेटा सेव्ह करताना
    @PostMapping("/save")
    public String saveEntry(@ModelAttribute("milkEntry") DudhSankalan entry) {
        // डेटा सेव्ह करा
        service.saveCollection(entry);
        
        // सेव्ह झाल्यानंतर 'redirect' करा. यामुळे पेज रिफ्रेश होईल आणि 
        // @GetMapping पुन्हा कॉल होऊन डेटाबेसमधून ताजी लिस्ट येईल.
        return "redirect:/milk/form"; 
    }
    
    
 // १. Edit करण्यासाठी नोंद शोधणे आणि फॉर्ममध्ये भरणे
    @GetMapping("/edit/{id}")
    public String editEntry(@PathVariable("id") Long id, Model model) {
        // ID नुसार रेकॉर्ड शोधा
        DudhSankalan entry = service.getById(id); 
        model.addAttribute("milkEntry", entry); // हे फॉर्ममध्ये डेटा भरेल
        
        // टेबल दाखवण्यासाठी पुन्हा लिस्ट लोड करा
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
        List<DudhSankalan> todayList = service.getTodayCollections(today);
        model.addAttribute("collections", todayList);
        
        return "dudh-sankalan"; // त्याच पेजवर परत जा
    }

    // २. Delete करण्यासाठी नोंद काढून टाकणे
    @GetMapping("/delete/{id}")
    public String deleteEntry(@PathVariable("id") Long id) {
        service.deleteById(id); // सर्विसमधून रेकॉर्ड डिलीट करा
        return "redirect:/milk/form"; // पेज रिफ्रेश करा
    }
    // Button 5: Exit
    @GetMapping("/exit")
    public String exitSystem() {
        return "redirect:/dashboard"; // Redirect to your home screen
    }

}

   
