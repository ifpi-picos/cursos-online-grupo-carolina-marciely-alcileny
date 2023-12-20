package br.edu.ifpi.entidades;

import br.edu.ifpi.enums.PapeisUsuario;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private PapeisUsuario papel;
    
    public Usuario(int id, String nome, String email, PapeisUsuario papel, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
    }

    public Usuario(PapeisUsuario papel) {
        this.papel = papel;
    }

    public Usuario() {
    }

    public Usuario(int id, PapeisUsuario papel) {
        this.id = id;
        this.papel = papel;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public PapeisUsuario getPapel() {
        return papel;
    }

}