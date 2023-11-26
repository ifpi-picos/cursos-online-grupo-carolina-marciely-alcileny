package br.edu.ifpi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import br.edu.ifpi.enums.PapeisUsuario;

public class Usuario {
    private PapeisUsuario papel;
    private Connection conexao;

    public Usuario(Connection conexao) {
        this.conexao = conexao;
    }

    public Usuario(PapeisUsuario papel, Connection conexao) {
        this.papel = papel;
        this.conexao = conexao;
    }

    public void cadastrarUsuario() {

        Scanner scanner = new Scanner(System.in);
        SistemaAcademico.limparConsole();
        System.out.println("Informe o nome do usuario");
        String nome = scanner.nextLine();
        System.out.println("Informe o email do usuario");
        String email = scanner.nextLine();
        System.out.println("Informe a senha do usuario");
        String senha = scanner.nextLine();
        SistemaAcademico.limparConsole();

        System.out.println("|---------------------|");
        System.out.println("| 1 - ADMINISTRADOR   |");
        System.out.println("| 2 - PROFESSOR       |");
        System.out.println("| 3 - ALUNO           |");
        System.out.println("|---------------------|");

        System.out.println("Digite uma opção!");
        int opcao = scanner.nextInt();
        SistemaAcademico.limparConsole();
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
            Statement stm = this.conexao.createStatement();
            stm.executeUpdate(query);
            System.out.println("|---------------------------------|");
            System.out.println("| Usuario cadastrado com sucesso! |");
            System.out.println("|---------------------------------|");
        } catch (SQLException e) {
            System.out.println("|---------------------------------|");
            System.out.println("| Erro ao cadastrar usuario!      |");
            System.out.println("|---------------------------------|");
        }
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter para ir para o menu principal");
        scanner2.nextLine();
    }

    public void visualizarListaDeUsuarios() {
        SistemaAcademico.limparConsole();
        String query = "SELECT nome, email, tipo FROM usuario";

        try {
            Statement stm = this.conexao.createStatement();
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
                System.out.println("|------------------------------------|");
                System.out.println("| Nenhum usuario encontrado!         |");
                System.out.println("|------------------------------------|");
            }

        } catch (SQLException e) {
            System.out.println("|------------------------------------|");
            System.out.println("| Erro ao carregar dados do usuario! |");
            System.out.println("|------------------------------------|");
        }
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter para ir para o menu principal");
        scanner2.nextLine();
    }

    public void atualizarUsuario() {
        SistemaAcademico.limparConsole();
        Scanner scanner = new Scanner(System.in);
        int idUsuario = carregarDadosDoUsuario();
        if (idUsuario != 0) {
            SistemaAcademico.limparConsole();
            System.out.println("Informe o novo nome do usuario");
            String nome = scanner.nextLine();
            System.out.println("Informe a nova e-mail do usuario");
            String email = scanner.nextLine();
            System.out.println("Informe a nova senha do usuario");
            String senha = scanner.nextLine();
            SistemaAcademico.limparConsole();

            String query = "UPDATE usuario SET nome = '" + nome + "', email = '" + email + "', senha = '" + senha
                    + "'  WHERE id = '" + idUsuario
                    + "'";

            try {
                Statement stm = this.conexao.createStatement();
                stm.executeUpdate(query);
                System.out.println("|------------------------------------------|");
                System.out.println("| Dados do usuario atualizado com sucesso! |");
                System.out.println("|------------------------------------------|");
            } catch (SQLException e) {
                System.out.println("|-------------------------------------|");
                System.out.println("| Erro ao atualizar dados do usuario! |");
                System.out.println("|-------------------------------------|");
            }
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Enter para ir para o menu principal");
            scanner2.nextLine();
        } else {
            System.out.println("|------------------------------------|");
            System.out.println("| Erro ao carregar dados do usuario! |");
            System.out.println("|------------------------------------|");
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Enter para ir para o menu principal");
            scanner2.nextLine();
        }

    }

    public int carregarDadosDoUsuario() {
        String query = "SELECT id, nome FROM usuario";
        int idSelecionado = 0;

        try {
            Statement stm = this.conexao.createStatement();
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
                System.out.println("|------------------------------------|");
                System.out.println("| Nenhum usuario encontrado!         |");
                System.out.println("|------------------------------------|");
            }

            System.out.println("Selecione o ID do usuario!");
            Scanner scanner = new Scanner(System.in);
            idSelecionado = scanner.nextInt();

        } catch (SQLException e) {
            System.out.println("Erro ao carregar dados do usuario!");
        }

        return idSelecionado;
    }

    public void excluirUsuario() {
        SistemaAcademico.limparConsole();
        int idUsuario = carregarDadosDoUsuario();
        SistemaAcademico.limparConsole();

        String query = "DELETE FROM usuario WHERE " + idUsuario + " = id";

        try {
            Statement stm = this.conexao.createStatement();
            stm.executeUpdate(query);
            System.out.println("|-------------------------------------|");
            System.out.println("| Usuario excluido com sucesso!       |");
            System.out.println("|-------------------------------------|");
        } catch (SQLException e) {
            System.out.println("|-------------------------------------|");
            System.out.println("| Erro ao excluir usuario!            |");
            System.out.println("|-------------------------------------|");
        }
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter para ir para o menu principal");
        scanner2.nextLine();
    }

    public PapeisUsuario getPapel() {
        return papel;
    }
}
