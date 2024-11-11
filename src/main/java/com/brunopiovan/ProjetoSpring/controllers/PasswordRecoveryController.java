package com.brunopiovan.ProjetoSpring.controllers;

import com.brunopiovan.ProjetoSpring.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
public class PasswordRecoveryController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/recover-password")
    public String recoverPassword(@RequestBody String email) {
        String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8).trim();
        if (decodedEmail.isEmpty()) {
            return "Endereço de e-mail inválido.";
        }
        String token = UUID.randomUUID().toString();
        String recoveryUrl = "http://localhost:8080/reset-password?token=" + token;
        emailService.sendEmail(decodedEmail, "Recuperação de Senha", "Clique no link para resetar sua senha: " + recoveryUrl);

        return "Instruções de recuperação de senha enviadas para seu e-mail.";
    }
}

