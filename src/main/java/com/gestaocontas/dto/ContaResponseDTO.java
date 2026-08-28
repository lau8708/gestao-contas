package com.gestaocontas.dto;

import com.gestaocontas.model.Conta;
import com.gestaocontas.model.enums.TipoConta;

import java.math.BigDecimal;

public record ContaResponseDTO(
        Long id,
        String agencia,
        String numeroConta,
        BigDecimal saldo,
        TipoConta tipoConta,
        String nomeCliente,
        String cpfCliente
) {
    public static ContaResponseDTO fromEntity(Conta conta){
        return new ContaResponseDTO(
                conta.getId(),
                conta.getAgencia(),
                conta.getNumeroConta(),
                conta.getSaldo(),
                conta.getTipoConta(),
                conta.getCliente().getNome(),
                conta.getCliente().getCpf()
        );
    }
}