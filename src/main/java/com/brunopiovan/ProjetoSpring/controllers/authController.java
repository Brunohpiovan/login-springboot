package com.brunopiovan.ProjetoSpring.controllers;

import com.brunopiovan.ProjetoSpring.domain.user.Usuario;
import com.brunopiovan.ProjetoSpring.dtos.LoginResquestDTO;
import com.brunopiovan.ProjetoSpring.dtos.RegisterRequestDTO;
import com.brunopiovan.ProjetoSpring.dtos.ResponseDTO;
import com.brunopiovan.ProjetoSpring.infra.secutiry.TokenService;
import com.brunopiovan.ProjetoSpring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//classe que recebe as requisicoes de login e registro,na rota /auth/login ou /auth/register
//todos usuarios tem acesso a essas rotas

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginResquestDTO body){
        Usuario user = this.repository.findByEmail(body.email()).orElseThrow(()-> new RuntimeException("User not found")); //verifica se existe um usuario com o login na base de dados
        if (passwordEncoder.matches(body.senha(), user.getSenha())){ //verifica se a senha encodada é correta
            String token = this.tokenService.generateToken(user);   //gera um token
            return ResponseEntity.ok(new ResponseDTO(user.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<Usuario> user = this.repository.findByEmail(body.email()); //procura na base de dados se ja existe um usuario com aquele email
        if(user.isEmpty()){ //se nao existir
            Usuario newUser = new Usuario();
            newUser.setSenha(passwordEncoder.encode(body.senha()));
            newUser.setEmail(body.email());
            newUser.setNome(body.nome());
            List<String> roles = new ArrayList<>(body.roles());
            if(!roles.contains("ROLE_USER")){ //se o usuario setado nao vier com o cargo de USER,aqui é setado automaticamente
                roles.add("ROLE_USER");
            }
            newUser.setRoles(roles);
            this.repository.save(newUser); //salva esse novo usuario na

            String token = this.tokenService.generateToken(newUser); //gera um token
            return ResponseEntity.ok(new ResponseDTO(newUser.getNome(), token));
        }


        return ResponseEntity.badRequest().build();
    }

}
