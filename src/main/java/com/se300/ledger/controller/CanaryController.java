package com.se300.ledger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class CanaryController {
    @RequestMapping("/html")
    public String sayHello(Model model) {
        model.addAttribute("date", new Date());
        return "example";
    }

}
