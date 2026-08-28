package com.gestaocontas.controller;

import com.gestaocontas.dto.ClienteRequestDTO;
import com.gestaocontas.dto.ClienteResponseDTO;
import com.gestaocontas.model.Cliente;
import com.gestaocontas.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO dto){
        // Converte o DTO de entrada para a Entidade JPA
        Cliente novoCliente = new Cliente(
                null,
                dto.nome(),
                dto.cpf(),
                dto.email(),
                dto.dataNascimento()
        );

        Cliente clienteSalvo = clienteService.salvarCliente(novoCliente);

        // Retorna HTTP 201 Created com o DTO de resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponseDTO.fromEntity(clienteSalvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ClienteResponseDTO.fromEntity(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        List<ClienteResponseDTO> lista = clienteService.listarTodos()
                .stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(lista);
    }
}