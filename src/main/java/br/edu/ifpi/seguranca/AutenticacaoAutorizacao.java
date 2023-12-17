package br.edu.ifpi.seguranca;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import br.edu.ifpi.Dao.ConexaoDao;
import br.edu.ifpi.Dao.UsuarioDao;
import br.edu.ifpi.entidades.Usuario;
import br.edu.ifpi.enums.PapeisUsuario;
import br.edu.ifpi.utilidades.Mensagem;

public class AutenticacaoAutorizacao {

    private PapeisUsuario tipoUsuario;
    private Mensagem mensagem = new Mensagem();

    public void gerenciarPermissoes() {
        Mensagem.limparConsole();
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.consultar();
        System.out.println("Digite o ID do usuario!");
        Scanner scanner2 = new Scanner(System.in);
        int id = scanner2.nextInt();
        Mensagem.limparConsole();
        System.out.println("|---------------------|");
        System.out.println("| 1 - ADMINISTRADOR   |");
        System.out.println("| 2 - PROFESSOR       |");
        System.out.println("| 3 - ALUNO           |");
        System.out.println("|---------------------|");

        System.out.println("Digite uma opção!");
        Scanner scanner = new Scanner(System.in);
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

        Usuario usuario = new Usuario(id, tipo);
        usuarioDao.alterarTipo(usuario);
    }

    public Usuario autenticarUsuario() {
        UsuarioDao usuarioDao = new UsuarioDao();
        Scanner scanner = new Scanner(System.in);
        System.out.println("|--------SISTEMA DE AUTENTICACAO-------|");
        System.out.println("|  Usuario padrao -> user@gmail.com    |");
        System.out.println("|  Senha padrao -> user                |");
        System.out.println("|--------------------------------------|");
        System.out.println();
        System.out.println("Digite seu email");
        String email = scanner.nextLine();
        System.out.println("Digite sua senha");
        String senha = scanner.nextLine();
        Mensagem.limparConsole();

        Usuario usuario = new Usuario(0, null, email, null,senha);

        return usuarioDao.authenticar(usuario);
        
    }
}
