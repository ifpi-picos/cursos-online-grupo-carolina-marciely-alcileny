package br.edu.ifpi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import br.edu.ifpi.enums.PapeisUsuario;

public class AutenticacaoAutorizacao {

    private Connection conexao;
    private PapeisUsuario tipoUsuario;

    public AutenticacaoAutorizacao(Connection conexao) {
        this.conexao = conexao;
    }

    public void gerenciarPermissoes() {
        Usuario usuario = new Usuario(conexao);
        SistemaAcademico.limparConsole();
        int idUsuario = usuario.carregarDadosDoUsuario();
        SistemaAcademico.limparConsole();
        if (idUsuario != 0) {
            System.out.println("|---------------------|");
            System.out.println("| 1 - ADMINISTRADOR   |");
            System.out.println("| 2 - PROFESSOR       |");
            System.out.println("| 3 - ALUNO           |");
            System.out.println("|---------------------|");

            System.out.println("Digite uma opção!");
            Scanner scanner = new Scanner(System.in);
            int opcao = scanner.nextInt();
            SistemaAcademico.limparConsole();
            String tipo;

            if (opcao == 1) {
                tipo = "ADMINISTRADOR";
            } else if (opcao == 2) {
                tipo = "PROFESSOR";
            } else {
                tipo = "ALUNO";
            }

            String query = "UPDATE usuario SET tipo = '" + tipo + "' WHERE id = '" + idUsuario + "'";

            try {
                Statement stm = this.conexao.createStatement();
                stm.executeUpdate(query);
                System.out.println("|-----------------------------------|");
                System.out.println("| Permissao atualizada com sucesso! |");
                System.out.println("|-----------------------------------|");
            } catch (SQLException e) {
                System.out.println("|-------------------------------|");
                System.out.println("| Erro ao atualizar permissao!  |");
                System.out.println("|-------------------------------|");
            }
            SistemaAcademico.pausar();
        } else {
            System.out.println("|-------------------------------|");
            System.out.println("| Nenhum usuario encontrado!    |");
            System.out.println("|-------------------------------|");
            SistemaAcademico.pausar();
        }
    }

    public boolean autenticarUsuario() {
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
        SistemaAcademico.limparConsole();
        boolean resposta = false;

        String query = "SELECT tipo FROM usuario WHERE email = '" + email + "' AND senha = '" + senha + "'";

        try {
            Statement stm = conexao.createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.next()) {
                String tipo = result.getString("tipo");
                if (tipo.equals("ADMINISTRADOR")) {
                    tipoUsuario = PapeisUsuario.ADMINISTRADOR;
                } else if (tipo.equals("PROFESSOR")) {
                    tipoUsuario = PapeisUsuario.PROFESSOR;
                } else {
                    tipoUsuario = PapeisUsuario.ALUNO;
                }

                resposta = true;
            }

        } catch (SQLException e) {
            System.out.println("|-------------------------------------|");
            System.out.println("| Erro ao carregar dados do usuario!  |");
            System.out.println("|-------------------------------------|");
        }
        return resposta;
    }

    public static boolean autorizarUsuario(Usuario usuario, PapeisUsuario papel) {
        return usuario.getPapel() == papel;
    }

    public PapeisUsuario getTipoUsuario() {
        return tipoUsuario;
    }
}
