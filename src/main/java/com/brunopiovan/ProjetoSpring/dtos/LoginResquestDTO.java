package com.brunopiovan.ProjetoSpring.dtos;

//classe para o usuario acessar o login sem acessar diretamente a classe de usuario
public record LoginResquestDTO(String email, String senha) {

}
