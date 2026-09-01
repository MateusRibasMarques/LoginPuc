package com.example.LoginPuc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureRegisterController {
     @GetMapping("/registro")
    public String registro() {
        return "registro";
    }
    
}
