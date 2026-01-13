package com.example.dairy.controller;

import com.example.dairy.model.Farmer;
import com.example.dairy.model.MilkRecord;
import com.example.dairy.repo.FarmerRepository;
import com.example.dairy.repo.MilkRecordRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class WebController {
    private final FarmerRepository farmerRepo;
    private final MilkRecordRepository recordRepo;

    public WebController(FarmerRepository farmerRepo, MilkRecordRepository recordRepo) {
        this.farmerRepo = farmerRepo;
        this.recordRepo = recordRepo;
    }

//    @PostMapping("/farmer/add")
//    public String addFarmer(@RequestParam String name, @RequestParam(required=false) String phone) {
//        Farmer f = new Farmer(name, phone);
//        farmerRepo.save(f);
//        return "redirect:/";
//    }
//
//    @PostMapping("/record/add")
//    public String addRecord(@RequestParam Long farmerId,
//                            @RequestParam String dateCollected,
//                            @RequestParam String shift,
//                            @RequestParam Double liters,
//                            @RequestParam Double fat,
//                            @RequestParam Double snf,
//                            @RequestParam Double ratePerLiter) {
//        Optional<Farmer> opt = farmerRepo.findById(farmerId);
//        if (opt.isEmpty()) return "redirect:/?error=farmer";
//
//        MilkRecord r = new MilkRecord();
//        r.setFarmer(opt.get());
//        r.setDateCollected(LocalDate.parse(dateCollected));
//        r.setShift(shift);
//        r.setLiters(liters);
//        r.setFat(fat);
//        r.setSnf(snf);
//        r.setRatePerLiter(ratePerLiter);
//        recordRepo.save(r);
//        return "redirect:/";
//    }
}
