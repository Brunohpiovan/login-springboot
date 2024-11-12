package com.brunopiovan.ProjetoSpring.infra.secutiry;


import com.brunopiovan.ProjetoSpring.domain.user.Usuario;
import com.brunopiovan.ProjetoSpring.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token); //verifica se o token é valido

        if (login != null) {
            Usuario user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found")); //procura um usuario com o email que veio em login
            List<SimpleGrantedAuthority> authorities = user.getRoles().stream() .map(role -> new SimpleGrantedAuthority(role))
                    .collect(   Collectors.toList());
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities); // Cria um objeto de autenticação com as authorities do usuário.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}