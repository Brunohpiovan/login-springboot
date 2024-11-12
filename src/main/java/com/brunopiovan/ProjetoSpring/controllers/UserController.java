package com.brunopiovan.ProjetoSpring.controllers;


import com.brunopiovan.ProjetoSpring.domain.user.Usuario;
import com.brunopiovan.ProjetoSpring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//classe para testar as permicoes dos cargos
@RestController
public class UserController {

    @Autowired
    UserRepository repository;

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("Sucesso");
    }

    //somente cargo ADMIN pode acessar esse endpoint
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Página de administrador";
    }

    //somente cargo ADMIN pode acessar esse endpoint
    @GetMapping("/admin/lista")
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    //qualquer usuario cadastrado pode acessar esse endpoint
    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "Página de usuário";
    }
    @GetMapping("/")
    public String homePage() {
        return "Página inicial";
    }
}
