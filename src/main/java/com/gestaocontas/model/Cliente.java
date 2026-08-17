package com.gestaocontas.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    // Construtor padrão (Obrigatório para o JPA)
    public Cliente() {
    }

    // Construtor completo
    public Cliente(Long id, String nome, String cpf, String email, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    // equals() define quando dois objetos Cliente são considerados iguais.
    // Neste caso, a regra é: dois Clientes são iguais se possuem o mesmo ID.
    // O ID foi escolhido como identificador da entidade.
    @Override
    public boolean equals(Object o){

        // Se for exatamente o mesmo objeto na memória, ele é obviamente igual.
        if (this == o){
            return true;
        }

        // Se for null ou não for exatamente da classe Cliente, não são iguais.
        if (o == null || this.getClass() != o.getClass()){
            return false;
        }

        Cliente cliente = (Cliente) o;

        // Comparamos os IDs porque o ID define a identidade do Cliente.
        return Objects.equals(this.id, cliente.id);
    }


    // hashCode() deve usar os mesmos atributos usados no equals().
    // Como equals() considera apenas o ID, hashCode() também usa apenas o ID.
    //
    // Isso é necessário para que estruturas como HashSet e HashMap
    // funcionem corretamente:
    // se dois objetos são iguais segundo equals(), eles devem possuir
    // o mesmo hashCode().
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}