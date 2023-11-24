package com.example.apiTeste.repository;

import com.example.apiTeste.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    boolean existsByCnpj(String cnpj);
}
