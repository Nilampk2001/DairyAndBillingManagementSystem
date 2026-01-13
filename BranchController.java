package com.example.dairy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.dairy.model.Branch;
import com.example.dairy.repo.BranchRepository;

import java.util.List;

@Controller
public class BranchController {

    @Autowired
    private BranchRepository repo;

    // 🟢 1. नवीन शाखा नोंदणी फॉर्म
    @GetMapping("/branch")
    public String showForm(Model model) {
        model.addAttribute("branch", new Branch());
        return "branch_registration";
    }

    // 🟢 2. शाखा जतन करा (Save)
    @PostMapping("/branch")
    public String saveBranch(@ModelAttribute Branch branch) {
        repo.save(branch);
        return "redirect:/branch/list";
    }
    

 // ✅ 5. सर्व नोंदी दाखवा (List)
    @GetMapping("/branch/list")
    public String listBranches(Model model) {
        List<Branch> branches = repo.findAll();
        model.addAttribute("branches", branches);
        return "branch_list";
    }


// // ✅ 4. शाखा अपडेट करा (Update)
 // 🟢 Edit / बदल करा
    @GetMapping("/branch/edit/{id}")
    public String editBranch(@PathVariable("id") Long id, Model model) {
        Branch branch = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid branch id: " + id));
        model.addAttribute("branch", branch);  // आधीच्या values सह form भरेल
        return "branch_registration"; // तोच form वापरणार
    }
//    @PostMapping("/branch/update/{id}")
//    public String updateBranch(@PathVariable("id") Long id, @ModelAttribute Branch branch) {
//        branch.setId(id);   // existing record update करण्यासाठी id परत set करणे गरजेचे आहे
//        repo.save(branch);
//        return "redirect:/branch/list"; 
//    }
    @PostMapping("/branch/update/{id}")
    public String updateBranch(@PathVariable("id") Long id,
                               @ModelAttribute Branch branch) {
        Branch existingBranch = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid branch id: " + id));

        // update values
        existingBranch.setBranchName(branch.getBranchName());
        existingBranch.setAddress(branch.getAddress());
        existingBranch.setMobile(branch.getMobile());
        existingBranch.setBranchCode(branch.getBranchCode());
        existingBranch.setDate(branch.getDate());
        existingBranch.setCstNo(branch.getCstNo());
        existingBranch.setGstNo(branch.getGstNo());
        existingBranch.setDistrict(branch.getDistrict());
        existingBranch.setTaluka(branch.getTaluka());
        existingBranch.setPincode(branch.getPincode());
        existingBranch.setVillage(branch.getVillage());

        repo.save(existingBranch); // DB मध्ये save (update)
        return "redirect:/branch/list"; // update झाल्यावर list page वर redirect
    }


   

// ✅ 3. शाखा पुसून टाका (Delete)
    
    @GetMapping("/branch/delete/{id}")
    public String deleteBranch(@PathVariable("id") Long id) {
        repo.deleteById(id); // त्या id चा branch delete होईल
        return "redirect:/branch/list"; // delete झाल्यावर list page ला redirect
    }


    // 🟢 6. बाहेर पडा (Home Page किंवा Redirect)
    @GetMapping("/")
    public String homePage() {
        return "index";  // home.html तयार करून ठेव
    }
    
}
