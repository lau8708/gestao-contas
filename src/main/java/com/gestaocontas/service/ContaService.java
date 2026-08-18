package com.gestaocontas.service;

import com.gestaocontas.exception.EntidadeNaoEncontradaException;
import com.gestaocontas.exception.RegraNegocioException;
import com.gestaocontas.model.Conta;
import com.gestaocontas.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteService clienteService;  // Serviço de cliente injetado

    public ContaService(ContaRepository contaRepository, ClienteService clienteService) {
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
    }

    @Transactional
    public Conta criarConta(Conta conta, Long clienteId){
        // Valida se o cliente informado realmente existe usando o outro serviço
        var cliente = clienteService.buscarPorId(clienteId);

        // Regra de negócio: Verificar se o número de conta já está em uso
        if (contaRepository.existsByNumeroConta(conta.getNumeroConta())){
            throw new RegraNegocioException("O número de conta informado já está em uso.");
        }

        conta.setCliente(cliente);
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta buscarPorNumero(String numeroConta){
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta não encontrada para o número: " + numeroConta));
    }

    @Transactional
    public Conta depositar(String numeroConta, BigDecimal valor){
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new RegraNegocioException("O valor do depósito deve ser maior que zero.");
        }

        Conta conta = buscarPorNumero(numeroConta);
        conta.setSaldo(conta.getSaldo().add(valor));

        return contaRepository.save(conta);
    }

    @Transactional
    public Conta sacar(String numeroConta, BigDecimal valor){
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new RegraNegocioException("O valor de saque deve ser maior que zero");
        }

        Conta conta = buscarPorNumero(numeroConta);

        // Regra de negócio: Verificar se há saldo suficiente
        if (conta.getSaldo().compareTo(valor) < 0){
            throw new RegraNegocioException("Saldo insuficiente para realizar o saque");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        return contaRepository.save(conta);
    }

    @Transactional
    public void transferir(String contaOrigemNumero, String contaDestinoNumero, BigDecimal valor){
        // Executa o saque na conta de origem (já valida saldo e lança exceção se falhar
        sacar(contaOrigemNumero, valor);

        // Executa depósito na conta destino
        depositar(contaDestinoNumero, valor);

        // A anotação @Trasactional garante que, se o depósito falhar,
        // o saque feito na linha anterior sofrerá rollback automático!

    }
}