package com.example.dairy.controller;


import com.example.dairy.model.Supplier;
import com.example.dairy.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping("/supplier-form")
    public String form(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier-form";
    }

    @PostMapping("/supplier-save")
    public String save(@ModelAttribute Supplier supplier) {
        service.save(supplier);
        return "redirect:/supplier-list";
    }

    @GetMapping("/supplier-list")
    public String list(Model model) {
        model.addAttribute("list", service.getAll());
        return "supplier-list";
    }

    @GetMapping("/supplier/edit/{id}")
    public String editSupplier(@PathVariable Long id, Model model) {
        Supplier supplier = service.getById(id);
        model.addAttribute("supplier", supplier);
        return "supplier-form";
    }

    @GetMapping("/supplier/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/supplier-list";
    }
}
