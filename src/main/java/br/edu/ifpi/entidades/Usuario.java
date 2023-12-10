package br.edu.ifpi.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import br.edu.ifpi.conexaoBD.ConexaoBancoDeDados;
import br.edu.ifpi.enums.PapeisUsuario;

public class Usuario  extends ConexaoBancoDeDados{
    private PapeisUsuario papel;

    public Usuario(PapeisUsuario papel) {
        this.papel = papel;
    }

    public Usuario() {
    }

    public void cadastrarUsuario() {

        Scanner scanner = new Scanner(System.in);
        limparConsole();
        System.out.println("Informe o nome do usuario");
        String nome = scanner.nextLine();
        System.out.println("Informe o email do usuario");
        String email = scanner.nextLine();
        System.out.println("Informe a senha do usuario");
        String senha = scanner.nextLine();
        limparConsole();

        System.out.println("|---------------------|");
        System.out.println("| 1 - ADMINISTRADOR   |");
        System.out.println("| 2 - PROFESSOR       |");
        System.out.println("| 3 - ALUNO           |");
        System.out.println("|---------------------|");

        System.out.println("Digite uma opção!");
        int opcao = scanner.nextInt();
        limparConsole();
        String tipo = "ALUNO";

        if (opcao == 1) {
            tipo = "ADMINISTRADOR";
        } else if (opcao == 2) {
            tipo = "PROFESSOR";
        } else {
            tipo = "ALUNO";
        }

        String query = "INSERT INTO usuario (nome, email, senha, tipo) VALUES ('" + nome + "','" + email + "', '"
                + senha
                + "', '" + tipo + "')";

        try {
            Statement stm = connectar().createStatement();
            stm.executeUpdate(query);
            imprimirMenssagemDeCadastro(SUCESSO,"Usuario");
        } catch (SQLException e) {
            imprimirMenssagemDeCadastro(ERRO,"Usuario");
        }
        pausar();
    }

    public void visualizarListaDeUsuarios() {
        limparConsole();
        String query = "SELECT nome, email, tipo FROM usuario";

        try {
            Statement stm = connectar().createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.next()) {
                System.out.println("|=============LISTA DE USUARIOS=================");
                while (result.next()) {

                    String nome = result.getString("nome");
                    String email = result.getString("email");
                    String tipo = result.getString("tipo");

                    System.out.println("| Nome " + nome);
                    System.out.println("| Email " + email);
                    System.out.println("| Tipo " + tipo);
                    System.out.println("|-----------------------------------------------");
                }
                System.out.println("|===============================================");
            } else {
                imprimirNenhumDado("usuario");
            }

        } catch (SQLException e) {
            imprimirErroAoCarregarDados("usuario");
        }
        pausar();
    }

    public void atualizarUsuario() {
        limparConsole();
        Scanner scanner = new Scanner(System.in);
        int idUsuario = carregarDadosDoUsuario();
        if (idUsuario != 0) {
            limparConsole();
            System.out.println("Informe o novo nome do usuario");
            String nome = scanner.nextLine();
            System.out.println("Informe a nova e-mail do usuario");
            String email = scanner.nextLine();
            System.out.println("Informe a nova senha do usuario");
            String senha = scanner.nextLine();
            limparConsole();

            String query = "UPDATE usuario SET nome = '" + nome + "', email = '" + email + "', senha = '" + senha
                    + "'  WHERE id = '" + idUsuario
                    + "'";

            try {
                Statement stm = connectar().createStatement();
                stm.executeUpdate(query);
                imprimirMenssagemDeAtualizacao(SUCESSO);
            } catch (SQLException e) {
                imprimirMenssagemDeAtualizacao(ERRO);
            }
            pausar();
        } else {
            imprimirErroAoCarregarDados("usuario");
            pausar();
        }

    }

    public int carregarDadosDoUsuario() {
        String query = "SELECT id, nome FROM usuario";
        int idSelecionado = 0;

        try {
            Statement stm = connectar().createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.next()) {
                System.out.println("----------------------------------");
                while (result.next()) {

                    int id = result.getInt("id");
                    String nome = result.getString("nome");

                    System.out.println(" ID -> " + id + " Nome: " + nome);
                }
                System.out.println("----------------------------------");
            } else {
                imprimirNenhumDado("usuario");
            }

            System.out.println("Selecione o ID do usuario!");
            Scanner scanner = new Scanner(System.in);
            idSelecionado = scanner.nextInt();

        } catch (SQLException e) {
            imprimirErroAoCarregarDados("usuario");
        }

        return idSelecionado;
    }

    public void excluirUsuario() {
        limparConsole();
        int idUsuario = carregarDadosDoUsuario();
        limparConsole();

        String query = "DELETE FROM usuario WHERE " + idUsuario + " = id";

        try {
            Statement stm = connectar().createStatement();
            stm.executeUpdate(query);
            imprimirMenssagemDeExclusao(SUCESSO, "Usuario");
        } catch (SQLException e) {
            imprimirMenssagemDeExclusao(ERRO, "Usuario");
        }
        pausar();
    }
    public PapeisUsuario getPapel() {
        return papel;
    }
}
