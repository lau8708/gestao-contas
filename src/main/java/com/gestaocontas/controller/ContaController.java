package com.gestaocontas.controller;

import com.gestaocontas.dto.ContaRequestDTO;
import com.gestaocontas.dto.ContaResponseDTO;
import com.gestaocontas.dto.TransacaoDTO;
import com.gestaocontas.model.Conta;
import com.gestaocontas.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@Valid @RequestBody ContaRequestDTO dto){
        // Converte o DTO recebido na requisição em um objeto da entidade Conta
        Conta novaConta = new Conta(
                null,
                dto.agencia(),
                dto.numeroConta(),
                BigDecimal.ZERO, // Conta inicia com saldo zero
                dto.tipoConta(),
                null // O cliente será buscado e associado no Service
        );

        Conta contaCriada = contaService.criarConta(novaConta, dto.clienteId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ContaResponseDTO.fromEntity(contaCriada));
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<ContaResponseDTO> buscarPorNumero(@PathVariable String numeroConta){
        Conta conta = contaService.buscarPorNumero(numeroConta);
        return ResponseEntity.ok(ContaResponseDTO.fromEntity(conta));
    }

    @PostMapping("/{numeroConta}/depositar")
    public ResponseEntity<ContaResponseDTO> depositar(
            @PathVariable String numeroConta,
            @Valid @RequestBody TransacaoDTO dto
    ){
        Conta contaAtualizada = contaService.sacar(numeroConta, dto.valor());
        return ResponseEntity.ok(ContaResponseDTO.fromEntity(contaAtualizada));
    }

    @PostMapping("/transferir")
    public ResponseEntity<Void> transferir(
            @RequestParam String contaOrigem,
            @RequestParam String contaDestino,
            @Valid @RequestBody TransacaoDTO dto
    ){
        contaService.transferir(contaOrigem, contaDestino, dto.valor());
        return ResponseEntity.ok().build();
    }
}