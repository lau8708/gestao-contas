package com.gestaocontas.repository;

import com.gestaocontas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método customizado para verificar se já existe um CPF cadastrado no banco
    boolean existsByCpf(String cpf);

    // Método customizado para buscar um cliente pelo CPF
    Optional<Cliente> findByCpf(String cpf);
}
