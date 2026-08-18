package com.gestaocontas.repository;

import com.gestaocontas.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    // Método customizado para buscar uma conta pelo número
    Optional<Conta> findByNumeroConta(String numeroConta);

    // Método customizado para verificar se já existe o número de conta
    boolean existsByNumeroConta(String numeroConta);
}
