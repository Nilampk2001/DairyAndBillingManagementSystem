package com.example.dairy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.dairy.service.SupplierService1;

@Controller
public class SupplierController1 {

    private final SupplierService1 service;

    public SupplierController1(SupplierService1 service) {
        this.service = service;
    }

    @GetMapping("/supplier-ui")
    public String form() {
        return "supplier-ui1";
    }

    @PostMapping("/supplier-result")
    public String showList(Model model) {

        model.addAttribute("list", service.getAll());
        return "supplier-list1";
    }

    @GetMapping("/print-report")
    public String printReport(Model model) {

        model.addAttribute("list", service.getAll());
        return "supplier-list1";
    }
}

