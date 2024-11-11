package com.brunopiovan.ProjetoSpring.dtos;

import java.util.List;

public record RegisterRequestDTO (String nome, String email, String senha, List<String> roles){
}
