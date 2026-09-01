package com.example.LoginPuc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@restController: API Rest
@Controller
public class SecureLoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    

    
}
