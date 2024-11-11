package com.brunopiovan.ProjetoSpring.controllers;

import com.brunopiovan.ProjetoSpring.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PasswordRecoveryController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/recover-password")
    public String recoverPassword(@RequestParam String email) {
        // Lógica para gerar um token de recuperação e enviar o e-mail
        String token = UUID.randomUUID().toString();
        // Salvar o token no banco de dados associado ao usuário

        String recoveryUrl = "http://localhost:8080/reset-password?token=" + token;
        emailService.sendEmail(email, "Recuperação de Senha", "Clique no link para resetar sua senha: " + recoveryUrl);

        return "Instruções de recuperação de senha enviadas para seu e-mail.";
    }
}

