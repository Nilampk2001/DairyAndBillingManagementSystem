package com.example.dairy.controller;

import com.example.dairy.model.Department;
import com.example.dairy.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // 1️⃣ Show form for new department
    @GetMapping("/department")
    public String showForm(Model model) {
        model.addAttribute("department", new Department());
        return "department-form";
    }
    

    // 2️⃣ Save new department
    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        return "redirect:/list";
    }

    // 3️⃣ List all departments
    @GetMapping("/list")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "department-list";
    }

    // 4️⃣ Edit form
    @GetMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, Model model) {
        Department dept = departmentService.findById(id);
        model.addAttribute("department", dept);
        return "department-form";
    }

    // 5️⃣ Update department
    @PostMapping("/edit/{id}")
    public String updateDepartment(@PathVariable Long id,
                                   @ModelAttribute Department department) {
        department.setId(id);
        departmentService.save(department);
        return "redirect:/list";
    }

    // 6️⃣ Delete department
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteById(id);
        return "redirect:/list";
    }
    
    
    
}
