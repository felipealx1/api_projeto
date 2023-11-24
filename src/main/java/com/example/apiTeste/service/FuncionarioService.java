package com.example.apiTeste.service;


import com.example.apiTeste.dto.CepDTO;
import com.example.apiTeste.exception.EmpresaNotFoundException;
import com.example.apiTeste.exception.FuncionarioNotFoundException;
import com.example.apiTeste.model.Empresa;
import com.example.apiTeste.model.Funcionario;
import com.example.apiTeste.repository.EmpresaRepository;
import com.example.apiTeste.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Funcionario> listarFuncionarios(){
        return funcionarioRepository.findAll();
    }


    public List<Funcionario> listarFuncionariosByEmpresaId(Integer empresaId) {
        return funcionarioRepository.findByEmpresaId(empresaId);
    }

    public Optional<Funcionario> listarFuncionariosEmpresaId(Integer funcionarioId, Integer empresaId){
        return funcionarioRepository.findByIdAndEmpresaId(funcionarioId, empresaId);
    }


    public Funcionario criarFuncionario(Integer empresaId, Funcionario novoFuncionario) {

        if (funcionarioRepository.findByCpfAndEmpresaId(novoFuncionario.getCpf(), empresaId).isPresent()) {
            throw new FuncionarioNotFoundException("CPF já está em uso por outro funcionário na mesma empresa.");
        }

        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new EmpresaNotFoundException("Empresa não encontrada com o ID: " + empresaId));
        novoFuncionario.setEmpresa(empresa);

        String urlApiExterna = "https://brasilapi.com.br/api/cep/v1/" + novoFuncionario.getCep();
        RestTemplate restTemplate = new RestTemplate();
        CepDTO cepDTO = restTemplate.getForObject(urlApiExterna, CepDTO.class);

        if (cepDTO != null) {
            novoFuncionario.setCidade(cepDTO.getCity());
            novoFuncionario.setEstado(cepDTO.getState());
            novoFuncionario.setBairro(cepDTO.getNeighborhood());
        }
        return funcionarioRepository.save(novoFuncionario);
    }

    @Transactional
    public Funcionario atualizarFuncionario(Integer empresaId, Integer funcionarioId, Funcionario updatedFuncionario) {
        Funcionario existingFuncionario = funcionarioRepository.findByIdAndEmpresaId(funcionarioId, empresaId)
                .orElseThrow(() -> new FuncionarioNotFoundException("Funcionário não encontrado com o ID: " + funcionarioId));
        existingFuncionario.setNome(updatedFuncionario.getNome());
        existingFuncionario.setCpf(updatedFuncionario.getCpf());
        existingFuncionario.setCep(updatedFuncionario.getCep());

        String urlApiExterna = "https://brasilapi.com.br/api/cep/v1/" + existingFuncionario.getCep();
        RestTemplate restTemplate = new RestTemplate();
        CepDTO cepDTO = restTemplate.getForObject(urlApiExterna, CepDTO.class);
        if (cepDTO != null) {
            existingFuncionario.setCidade(cepDTO.getCity());
            existingFuncionario.setEstado(cepDTO.getState());
            existingFuncionario.setBairro(cepDTO.getNeighborhood());
            existingFuncionario.setCep(cepDTO.getCep());
        }

        return funcionarioRepository.save(existingFuncionario);
    }
    @Transactional
    public void deleteFuncionario(Integer empresaId, Integer funcionarioId) {
        funcionarioRepository.deleteByIdAndEmpresaId(funcionarioId, empresaId);
    }

}
