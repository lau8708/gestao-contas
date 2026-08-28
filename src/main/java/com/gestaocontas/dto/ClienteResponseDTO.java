package com.gestaocontas.dto;

import com.gestaocontas.model.Cliente;

import java.time.LocalDate;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento
) {
    // Mapeador estático de conveniência: Converte entidade -> DTO
    public static ClienteResponseDTO fromEntity(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getDataNascimento()
        );
    }
}
