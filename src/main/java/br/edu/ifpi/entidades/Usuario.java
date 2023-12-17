package br.edu.ifpi.entidades;

import java.util.Scanner;
import br.edu.ifpi.Dao.UsuarioDao;
import br.edu.ifpi.enums.PapeisUsuario;
import br.edu.ifpi.utilidades.Mensagem;

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

    public void realizarCadastro() {
        Mensagem.limparConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o nome do usuario");
        String nome = scanner.nextLine();
        System.out.println("Informe o email do usuario");
        String email = scanner.nextLine();
        System.out.println("Informe a senha do usuario");
        String senha = scanner.nextLine();
        Mensagem.limparConsole();

        System.out.println("|---------------------|");
        System.out.println("| 1 - ADMINISTRADOR   |");
        System.out.println("| 2 - PROFESSOR       |");
        System.out.println("| 3 - ALUNO           |");
        System.out.println("|---------------------|");

        System.out.println("Digite uma opção!");
        int opcao = scanner.nextInt();
        Mensagem.limparConsole();
        PapeisUsuario tipo;

        if (opcao == 1) {
            tipo = PapeisUsuario.ADMINISTRADOR;
        } else if (opcao == 2) {
            tipo = PapeisUsuario.PROFESSOR;
        } else {
            tipo = PapeisUsuario.ALUNO;
        }

        Usuario usuario = new Usuario(0, nome, email, tipo, senha);
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.cadastrar(usuario);
    }

    public void visualizarListaDeUsuarios() {
        Mensagem.limparConsole();
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.consultar();
        Mensagem.pausar();
    }

    public void atualizarCadastro() {
        Mensagem.limparConsole();
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.consultar();
        System.out.println("Digite o ID do usuario!");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        Mensagem.limparConsole();
        System.out.println("Informe o novo nome do usuario");
        String nome = scanner.next();
        System.out.println("Informe o novo e-mail do usuario");
        String email = scanner.next();
        System.out.println("Informe a nova senha do usuario");
        String senha = scanner.next();

        Usuario usuario = new Usuario(id, nome, email, papel, senha);
        usuarioDao.alterar(usuario);
    }

    public void excluirCadastro() {
        Mensagem.limparConsole();
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.consultar();
        System.out.println("Digite o ID do usuario!");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        Mensagem.limparConsole();
        
        Usuario usuario = new Usuario(id, nome, email, papel, senha);
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