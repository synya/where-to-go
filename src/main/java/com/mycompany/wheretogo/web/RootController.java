package com.mycompany.wheretogo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.mycompany.wheretogo.web.AbstractRestController.getRestBaseUrl;

@Controller
public class RootController {
    @GetMapping("/")
    public String root() {
        return "redirect:" + getRestBaseUrl() + "/restaurants";
    }
}
