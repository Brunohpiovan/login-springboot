package com.brunopiovan.ProjetoSpring.infra.secutiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Habilita a configuração de segurança da web para o Spring Security
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, SecurityFilter securityFilter) {
        this.userDetailsService = userDetailsService;
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() //permite o end point para todos que tentarem acessar
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() //permite o end point para todos que tentarem acessar
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() //permite o end point para todos que tentarem acessar
                        .requestMatchers(HttpMethod.POST, "/recover-password").permitAll() //permite o end point para todos que tentarem acessar
                        .requestMatchers(HttpMethod.POST, "/reset-password").permitAll() //permite o end point para todos que tentarem acessar
                        .requestMatchers("/admin/**").hasRole("ADMIN") //permite o end point apenas para usuarios com cargo de ADMIN
                        .requestMatchers("/user/**").hasRole("USER")    //permite o end point apenas para usuarios com cargo de USER
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro de segurança personalizado antes do filtro padrão de autenticação de usuário.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
