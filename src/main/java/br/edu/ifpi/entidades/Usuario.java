package br.edu.ifpi.entidades;

import br.edu.ifpi.Dao.UsuarioDao;
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

    public void realizarCadastro(String nomeUsuario, String emailUsuario, PapeisUsuario papeisUsuario, String senhaUsuario) {

        Usuario usuario = new Usuario(0, nomeUsuario, emailUsuario, papeisUsuario, senhaUsuario);
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.cadastrar(usuario);
    }

    public void visualizarListaDeUsuarios() {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.consultar();
        
    }

    public void atualizarCadastro(int idUsuario, String nomeUsuario, String emailUsuario, PapeisUsuario PapeisUsuario, String senhaUsuario) {
        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario usuario = new Usuario(idUsuario, nomeUsuario, emailUsuario, PapeisUsuario, senhaUsuario);
        usuarioDao.alterar(usuario);
    }

    public void excluirCadastro(int idUsuario) {
        UsuarioDao usuarioDao = new UsuarioDao();
        
        Usuario usuario = new Usuario(idUsuario, null, null, null, null);
        usuarioDao.remover(usuario);
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