package com.brunopiovan.ProjetoSpring.dtos;

import java.util.List;

//classe para o usuario acessar o register sem acessar diretamente a classe de usuario
public record RegisterRequestDTO (String nome, String email, String senha, List<String> roles){
}
