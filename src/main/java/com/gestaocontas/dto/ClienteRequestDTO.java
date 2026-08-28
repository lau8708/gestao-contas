package com.gestaocontas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ClienteRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String nome,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "CPF em formato inválido.")
        String cpf,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "E-mail em formáto inválido.")
        String email,


        @NotBlank(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser no passado")
        LocalDate dataNascimento
) {}
