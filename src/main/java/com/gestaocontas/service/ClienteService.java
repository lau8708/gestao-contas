package com.gestaocontas.service;

import com.gestaocontas.exception.EntidadeNaoEncontradaException;
import com.gestaocontas.exception.RegraNegocioException;
import com.gestaocontas.model.Cliente;
import com.gestaocontas.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // Injeção de dependência via Construtor (Boa prática de Baixo acoplamento)
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente salvarCliente(Cliente cliente){
        // Validação de regra de negócio: Verificar se CPF já existe no banco
        if (clienteRepository.existsByCpf(cliente.getCpf())){
            throw new RegraNegocioException("Já existe um cliente cadastrado com o CPF: " + cliente.getCpf());
        }

        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado para o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }
}