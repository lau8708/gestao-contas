package com.gestaocontas.exception;

import java.time.LocalDateTime;

public record ErroRespostaDTO(
        int status,
        String erro,
        String mensagem,
        LocalDateTime timestamp
) {}
