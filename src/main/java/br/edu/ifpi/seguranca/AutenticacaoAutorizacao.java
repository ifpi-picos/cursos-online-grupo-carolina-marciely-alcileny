package br.edu.ifpi.seguranca;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import br.edu.ifpi.conexaoBD.ConexaoBancoDeDados;
import br.edu.ifpi.entidades.Usuario;
import br.edu.ifpi.enums.PapeisUsuario;

public class AutenticacaoAutorizacao extends ConexaoBancoDeDados {

    private PapeisUsuario tipoUsuario;

    public void gerenciarPermissoes() {
        Usuario usuario = new Usuario();
        limparConsole();
        int idUsuario = usuario.carregarDadosDoUsuario();
        limparConsole();
        if (idUsuario != 0) {
            System.out.println("|---------------------|");
            System.out.println("| 1 - ADMINISTRADOR   |");
            System.out.println("| 2 - PROFESSOR       |");
            System.out.println("| 3 - ALUNO           |");
            System.out.println("|---------------------|");

            System.out.println("Digite uma opção!");
            Scanner scanner = new Scanner(System.in);
            int opcao = scanner.nextInt();
            limparConsole();
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
                Statement stm = connectar().createStatement();
                stm.executeUpdate(query);
                imprimirMenssagemDeAtualizacao(SUCESSO, "Permissao");
            } catch (SQLException e) {
                imprimirMenssagemDeAtualizacao(ERRO, "Permissao");
            }
        } else {
            imprimirMensagemNenhumDado("usuario");
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
        limparConsole();
        boolean resposta = false;

        String query = "SELECT tipo FROM usuario WHERE email = '" + email + "' AND senha = '" + senha + "'";

        try {
            Statement stm = connectar().createStatement();
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
            imprimirErroAoCarregarDados("usuario");
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
