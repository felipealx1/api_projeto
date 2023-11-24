package com.example.apiTeste.controller;

import com.example.apiTeste.exception.FuncionarioNotFoundException;
import com.example.apiTeste.model.Funcionario;
import com.example.apiTeste.service.EmpresaService;
import com.example.apiTeste.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//"/api/empresas/{empresaId}/funcionarios"
@RestController
@RequestMapping("/api/empresas")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private EmpresaService empresaService;

    //lista todos os funcionarios existentes
    @GetMapping("/funcionarios")
    public ResponseEntity<List<Funcionario>> listarFuncionariosEmpresa(){
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    // esse endpoint lista todos os funcionarios cadastrados pelo id da empresa
    @GetMapping("/{empresaId}/funcionarios")
    public ResponseEntity<List<Funcionario>> listarFuncionariosEmpresaId(@PathVariable Integer empresaId){
        List<Funcionario> funcionarios = funcionarioService.listarFuncionariosByEmpresaId(empresaId);
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }
    //aqui esse endpiint lista o funcionario passando o id da empresa e do funcionario
    @GetMapping("/{empresaId}/funcionarios/{funcionarioId}")
    public ResponseEntity<Funcionario> getFuncionarioById(
            @PathVariable("empresaId") Integer funcionarioId,
            @PathVariable("funcionarioId") Integer empresaId
    ) {
        return funcionarioService.listarFuncionariosEmpresaId(empresaId, funcionarioId)
                .map(funcionario -> new ResponseEntity<>(funcionario, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // endpoint para criar um novo funcionário em uma empresa pelo id da empresaa
    @PostMapping("/{empresaId}/funcionarios")
    public ResponseEntity<Funcionario> createFuncionario(
            @PathVariable("empresaId") Integer empresaId,
            @RequestBody Funcionario novoFuncionario
    ) {
        Funcionario createdFuncionario = funcionarioService.criarFuncionario(empresaId, novoFuncionario);
        return new ResponseEntity<>(createdFuncionario, HttpStatus.CREATED);
    }

    // enndpoint para atualizar um funcionario de uma empresa pelo id do funcionario
    @PutMapping("/{empresaId}/funcionarios/{funcionarioId}")
    public ResponseEntity<Funcionario> updateFuncionario(
            @PathVariable Integer empresaId,
            @PathVariable Integer funcionarioId,
            @RequestBody Funcionario updatedFuncionario
    )  throws FuncionarioNotFoundException {
        Funcionario updated = funcionarioService.atualizarFuncionario(empresaId, funcionarioId, updatedFuncionario);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // esse eendpoint para deletar um funcionario de uma empresa pelo id
    @DeleteMapping("/{empresaId}/funcionarios/{funcionarioId}")
    public ResponseEntity<Void> deleteFuncionario(
            @PathVariable Integer empresaId,
            @PathVariable Integer funcionarioId
    ) {
            funcionarioService.deleteFuncionario(empresaId, funcionarioId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
