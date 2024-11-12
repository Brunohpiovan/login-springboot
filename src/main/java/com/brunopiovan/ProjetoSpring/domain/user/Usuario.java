package com.brunopiovan.ProjetoSpring.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//classe da entidade usuario
@Entity(name = "usuarios")
@Table(name = "usuarios")
@Getter //gera o getter automaticamente
@Setter //gera o setter automaticamente
@AllArgsConstructor //gera o construtor com argumentos automaticamente
@NoArgsConstructor  //gera o construtor sem argumentos automaticamente
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}
