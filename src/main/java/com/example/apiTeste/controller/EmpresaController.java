package com.example.apiTeste.controller;

import com.example.apiTeste.model.Empresa;
import com.example.apiTeste.model.Funcionario;
import com.example.apiTeste.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<Empresa>> exibirEmpresas(){
        List<Empresa> empresas = empresaService.listarEmpresas();
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> exibirEmpresaId(@PathVariable Integer id){
        return empresaService.listarEmpresaId(id).
                map(empresa -> new ResponseEntity<>(empresa, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    public ResponseEntity<Empresa> adcionarEmpresa(@RequestBody Empresa empresa){
        Empresa criarEmpresa = empresaService.criarEmpresa(empresa);
        return new ResponseEntity<>(criarEmpresa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atulaizarEmpresa(@PathVariable Integer id,
                                                    @RequestBody Empresa empresaAtualizada){
        Empresa atualizar = empresaService.atualizarEmpresa(id, empresaAtualizada);
        return new ResponseEntity<>(atualizar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Integer id){
        empresaService.deletarEmpresa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
