package com.battlefield.demo.usuario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "NM_NOME", nullable = false)
    private String nmNome;


    @Column(name = "NM_EMAIL", nullable = false)
    private String nmEmail;

    @Column(name = "NM_SENHA", nullable = false)
    private String nmSenha;

    @Column(name = "NM_ENDERECO", nullable = false)
    private String nmEndereco;

    public Usuario() {
        this.idUsuario = idUsuario;
        this.nmNome = nmNome;
        this.nmEmail = nmEmail;
        this.nmSenha = nmSenha;
        this.nmEndereco = nmEndereco;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nmNome='" + nmNome + '\'' +
                ", nmEmail='" + nmEmail + '\'' +
                ", nmSenha='" + nmSenha + '\'' +
                ", nmEndereco='" + nmEndereco + '\'' +
                '}';
    }
}