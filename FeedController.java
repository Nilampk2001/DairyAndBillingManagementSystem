package com.example.dairy.controller;

import com.example.dairy.model.Feed;
import com.example.dairy.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService service;

    // फॉर्म दाखवण्यासाठी (Image 1)
    @GetMapping("/registration")
    public String showForm(Model model) {
        model.addAttribute("feed", new Feed());
        return "feed-form";
    }

    // डेटा सेव्ह करण्यासाठी
    @PostMapping("/save")
    public String saveFeed(@ModelAttribute("feed") Feed feed, RedirectAttributes ra) {
        service.saveFeed(feed);
        ra.addFlashAttribute("successMsg", "माहिती जतन पूर्ण!"); // Image 2 साठी
        return "redirect:/feed/registration";
    }

    // नोंदी दाखवण्यासाठी (Image 3)
    @GetMapping("/list")
    public String listFeeds(Model model) {
        model.addAttribute("feeds", service.getAllFeeds());
        return "feed-list";
    }

    // एडिट करण्यासाठी
    @GetMapping("/edit/{id}")
    public String editFeed(@PathVariable Long id, Model model) {
        model.addAttribute("feed", service.getFeedById(id));
        return "feed-form";
    }

    // डिलीट करण्यासाठी
    @GetMapping("/delete/{id}")
    public String deleteFeed(@PathVariable Long id) {
        service.deleteFeed(id);
        return "redirect:/feed/list";
    }
}
