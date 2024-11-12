package com.brunopiovan.ProjetoSpring.controllers;

import com.brunopiovan.ProjetoSpring.domain.user.Usuario;
import com.brunopiovan.ProjetoSpring.infra.secutiry.TokenService;
import com.brunopiovan.ProjetoSpring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//classe responsavel por alterar a senha
@RestController
public class PasswordResetController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String email = tokenService.validateToken(token); //verifica se o token é valido
        if (email == null) {
            return "Token inválido ou expirado.";
        }

        Usuario user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));//verifica se existe o usuario com aquele email na base de dados
        user.setSenha(passwordEncoder.encode(newPassword));//encoda a nova senha
        userRepository.save(user);//faz o update da senha

        return "Senha alterada com sucesso.";
    }
}
