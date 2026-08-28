package com.gestaocontas.dto;

import com.gestaocontas.model.enums.TipoConta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContaRequestDTO(
        @NotBlank(message = "A agência é obrigatória.")
        @Size(min = 4, max = 4, message = "A agência deve ter 4 dígitos.")
        String agencia,

        @NotBlank(message = "O número da conta é obrigatório")
        @Size(min = 5, max = 10, message = "O número da conta deve ter entre 5 e 10 caracteres.")
        String numeroConta,

        @NotNull(message = "O tipo de conta é obrigatório")
        TipoConta tipoConta,

        @NotNull(message = "O ID do cliente é obrigatório")
        Long clienteId
) {}
