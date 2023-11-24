package com.example.apiTeste.model;

import com.example.apiTeste.dto.CepDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_funcionario")
@Data @AllArgsConstructor @NoArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column
    private String cep;

    @Column
    private String cidade;

    @Column
    private  String estado;

    @Column
    private String bairro;


    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonBackReference
    private Empresa empresa;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "funcionario_projeto",
    joinColumns = @JoinColumn(name = "funcionario_id"),
    inverseJoinColumns = @JoinColumn(name = "projeto_id"))
    private List<Projeto> projetos;

    public Funcionario (CepDTO cepDTO){
        this.cep = cepDTO.getCep();
        this.estado = cepDTO.getState();
        this.cidade = cepDTO.getCity();
        this.bairro = cepDTO.getNeighborhood();
    }
}
