package com.example.apiTeste.repository;

import com.example.apiTeste.model.Funcionario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    List<Funcionario> findByEmpresaId(Integer empresaId);
    Optional<Funcionario> findByIdAndEmpresaId(Integer funcionarioId, Integer empresaId);

    Optional<Funcionario> findByCpfAndEmpresaId(String cpf, Integer empresaId);
    void deleteByIdAndEmpresaId(Integer funcionarioId, Integer empresaId);

}
