package com.example.dairy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dairy.service.MilkDistributionService;

@Controller
public class MilkDistributionController 
{
	 private final MilkDistributionService service;

	    public MilkDistributionController(MilkDistributionService service) {
	        this.service = service;
	    }

	    @GetMapping("/MilkDistribution-ui")
	    public String form() {
	        return "MilkDistribution-ui";
	    }

	    @PostMapping("/MilkDistribution-result")
	    public String showList(Model model) {

	        model.addAttribute("list", service.getAll());
	        return "MilkDistribution-list";
	    }

	    @GetMapping("/print-report1")
	    public String printReport(Model model) {

	        model.addAttribute("list", service.getAll());
	        return "MilkDistribution-list";
	    }
}
