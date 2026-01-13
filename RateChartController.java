package com.example.dairy.controller;

import com.example.dairy.model.RateChart;
import com.example.dairy.service.RateChartService;
import com.example.dairy.util.ExcelUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;

@Controller
public class RateChartController {

    private final RateChartService service;

    public RateChartController(RateChartService service) {
        this.service = service;
    }

    // =================== FORM PAGE ===================
    @GetMapping("/ratechart/form")
    public String showForm(Model model) {
        model.addAttribute("rateChart", new RateChart());
        return "ratechart-form";
    }

    // =================== SAVE + GENERATE EXCEL ===================
    @PostMapping("/ratechart/save")
    public String saveRateChart(@ModelAttribute("rateChart") RateChart rateChart) {

        try {
            // DB Save
            service.save(rateChart);

            // Excel तयार करा (Image-1 प्रमाणे)
            ExcelUtils.generateFullRateChart(
                    rateChart.getMilkRate(),
                    rateChart.getFatDiff(),
                    rateChart.getSnfDiff()
            );

            System.out.println("Excel तयार झाले!!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/ratechart/form";
    }

    // =================== REPORT DOWNLOAD ===================
    @GetMapping("/ratechart/report")
    public ResponseEntity<Resource> downloadReport() {

        try {
            File file = new File("RateChart.xlsx");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=RateChart.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // =================== HOME ===================
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
