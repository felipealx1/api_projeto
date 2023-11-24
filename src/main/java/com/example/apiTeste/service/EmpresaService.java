package com.example.apiTeste.service;

import com.example.apiTeste.exception.EmpresaNotFoundException;
import com.example.apiTeste.model.Empresa;
import com.example.apiTeste.model.Funcionario;
import com.example.apiTeste.repository.EmpresaRepository;
import com.example.apiTeste.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Empresa> listarEmpresas(){
        return empresaRepository.findAll();
    }

    public Optional<Empresa> listarEmpresaId(Integer id){
        return empresaRepository.findById(id);
    }


    public Empresa criarEmpresa(Empresa empresa){
        if (empresaRepository.existsByCnpj(empresa.getCnpj())){
            throw new EmpresaNotFoundException("Já existe uma empresa com esse Cnpj");
        }

        return empresaRepository.save(empresa);
    }

    public Empresa atualizarEmpresa(Integer id, Empresa empresaAtualizada){
        Optional<Empresa> empresaExistente = empresaRepository.findById(id);

        if(empresaExistente.isPresent()){
            Empresa empresa = empresaExistente.get();
            empresa.setCidade(empresaAtualizada.getCidade());
            empresa.setCnpj(empresaAtualizada.getCnpj());
            empresa.setEndereco(empresaAtualizada.getEndereco());

            if (!empresa.getCnpj().equals(empresaAtualizada.getCnpj())
                    && empresaRepository.existsByCnpj(empresaAtualizada.getCnpj())){
                throw new EmpresaNotFoundException("Já existe uma empresa com o mesmo Cnpj");
            }

             return empresaRepository.save(empresa);

        }else {
                throw new EmpresaNotFoundException("Empresa não encontrada pelo id: " + id);
            }
    }

    public void deletarEmpresa(Integer id){

        Optional<Empresa> empresa = empresaRepository.findById(id);

        if (empresa.isPresent()){
            List<Funcionario> funcionarios = funcionarioRepository.findByEmpresaId(id);
            funcionarios.forEach(funcionario -> funcionario.setEmpresa(null));
            funcionarioRepository.saveAll(funcionarios);

            empresaRepository.deleteById(id);
        }else {
            throw new EmpresaNotFoundException("\"Empresa não encontrada com o ID: " + id);
        }
    }




}
