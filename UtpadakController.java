package com.example.dairy.controller;

import com.example.dairy.model.Utpadak;
import com.example.dairy.service.UtpadakService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UtpadakController {

    private final UtpadakService service;

    public UtpadakController(UtpadakService service) {
        this.service = service;
    }

    @GetMapping("/utpadak-form")
    public String showForm(Model model) {
        model.addAttribute("utpadak", new Utpadak());
        return "utpadak-form";
    }

    @PostMapping("/utpadak-save")
    public String save(@ModelAttribute("utpadak") Utpadak utpadak, Model model) {
        service.save(utpadak);
        model.addAttribute("success", "डाटा यशस्वीरित्या सेव्ह झाला");
        return "redirect:/utpadak-form";
    }
    
    @GetMapping("/utpadak-list")
    public String listAllUtpadak(Model model) {
        model.addAttribute("list", service.getAllUtpadak());
        return "utpadak-list";
    }
    

    // -------------------- EDIT PAGE (LOAD OLD DATA) --------------------
    @GetMapping("/utpadak/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        Utpadak utpadak = service.getById(id);
        model.addAttribute("utpadak", utpadak);

        return "utpadak-form";   // same form used for edit
    }

    // -------------------- UPDATE (AFTER EDIT) --------------------
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("utpadak") Utpadak updated) {

        Utpadak old = service.getById(id);

        old.setDinank(updated.getDinank());
        old.setVibhag(updated.getVibhag());
        old.setGrahakCode(updated.getGrahakCode());
        old.setDudhPrakar(updated.getDudhPrakar());

        old.setNav(updated.getNav());
        old.setEnglishNav(updated.getEnglishNav());
        old.setPatta(updated.getPatta());
        old.setGav(updated.getGav());

        old.setTaluka(updated.getTaluka());
        old.setJilha(updated.getJilha());

        old.setUtpadakNo(updated.getUtpadakNo());
        old.setFixDar(updated.getFixDar());

        old.setBankName(updated.getBankName());
        old.setIfsc(updated.getIfsc());
        old.setAccountNo(updated.getAccountNo());
        old.setBranch(updated.getBranch());

        service.save(old);

        return "redirect:/utpadak-list";
    }

    // -------------------- DELETE --------------------
    @GetMapping("/utpadak/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/utpadak-list";
    }


}
