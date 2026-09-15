package com.example.LoginPuc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@restController: API Rest
@Controller
public class SecureLoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/register")
    public String handleRegister(
        @RequestParam("nome") String nome,
        @RequestParam("senha") String senha) {

        // Aqui você pode adicionar lógica para salvar os dados do usuário, por exemplo:
        // userService.saveUser(new User(nome, email, cpf, rg, endereco, instituicao, senha));

        // Redirecionar ou exibir uma mensagem de sucesso
        System.out.println("Registro: Redirecionado para a página de login.");
        return "redirect:/login"; 
    }

    @GetMapping("/recoverpassword")
    public String recoverpassword() {
        return "recoverpassword";
    }

    @PostMapping("/recoverpassword")
    public String handleRecoverPassword(
            @RequestParam("email") String email) {

        // Aqui você pode adicionar lógica para recuperar a senha.
        // userService.recoverPassword(email);

        // Redirecionar ou exibir uma mensagem de sucesso
        System.out.println("Recuperação de E-mail: Redirecionado para a página de login.");
        return "redirect:/login"; // Após a recuperação de senha, redirecionar para a página de login
    }
}
