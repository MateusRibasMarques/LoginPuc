package com.example.LoginPuc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.LoginPuc.dto.EmailRequestDTO;
import com.example.LoginPuc.service.UserService;

//@restController: API Rest
@Controller
public class SecureLoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String handleRegister(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("cpf") String cpf,
            @RequestParam("rg") String rg,
            @RequestParam("endereco") String endereco,
            @RequestParam("instituicao") String instituicao,
            @RequestParam("senha") String senha) {

        // Aqui você pode adicionar lógica para salvar os dados do usuário, por exemplo:
        // userService.saveUser(new User(nome, email, cpf, rg, endereco, instituicao, senha));

        // Redirecionar ou exibir uma mensagem de sucesso
        System.out.println("Registro: Redirecionado para a página de login.");
        return "redirect:/login"; // Após o registro, redirecionar para a página de login
    }

    @GetMapping("/recoverpassword")
    public String recoverpassword() {
        return "recoverpassword";
    }

    @PostMapping("/recover-password")
    @ResponseBody 
    public String sendEmail(@RequestBody EmailRequestDTO emailRequest) {
        userService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return "Email enviado com sucesso!";
    }
}
