package com.example.dairy.controller;

import com.example.dairy.model.Bill;
import com.example.dairy.model.Customer;
import com.example.dairy.model.Department;
import com.example.dairy.service.CustomerService;
import com.example.dairy.service.DairyService;
import com.example.dairy.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BillController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DairyService dairyService;

    // Department List
    @GetMapping("/department/list")
    public String departmentList(Model model) {
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "department-list";
    }

    // Normal Bill per department
    @GetMapping("/bill/normal/{deptId}")
    public String showNormalBillByDepartment(@PathVariable Long deptId, Model model) {
        Department dept = departmentService.findById(deptId);
        List<Customer> customers = customerService.findByDepartmentId(deptId);

        double totalAmount = customers.stream().mapToDouble(Customer::getAmount).sum();

        model.addAttribute("department", dept);
        model.addAttribute("customers", customers);
        model.addAttribute("totalAmount", totalAmount);
        
        // ⭐ फक्त रिकामा Bill object द्या, method call करू नका
        model.addAttribute("savebill", new Bill());  // ✅ बरोबर
        return "bill-normal"; // Thymeleaf template
    }

    // Normal Bill Form (All Customers)
    @GetMapping("/bill/NORMAL")
    public String showNormalBillForm(Model model) {
        List<Customer> customers = customerService.getAllCustomers();

        Bill bill = new Bill();

        model.addAttribute("bill", bill);
        model.addAttribute("customers", customers);

        return "bill_normal"; // Thymeleaf template
    }

    // Save Bill
//    @PostMapping("/bill/save")
//    public String saveBill(@ModelAttribute Bill bill) {
//        // Amount automatically calculate
//        bill.setAmount(bill.getQuantity() * bill.getRate());
//
//        // Save bill in DB
//        dairyService.saveBill(bill);
//
//        return "redirect:/bill/NORMAL";
//    }

    // Optional: Calculate totalAmount for Normal Bill view
    @ModelAttribute("totalAmount")
    public double calculateTotalAmount() {
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream().mapToDouble(Customer::getAmount).sum();
    }
    
    @PostMapping("/bill/save")
    public String saveBill(@ModelAttribute("savebill") Bill bill) {
        bill.setAmount(bill.getQuantity() * bill.getRate());
        dairyService.saveBill(bill);
        return "redirect:/bill/normal/";
    }


}
