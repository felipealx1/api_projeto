package com.example.apiTeste.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeProjeto;

    @ManyToMany(mappedBy = "projetos", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;
}
