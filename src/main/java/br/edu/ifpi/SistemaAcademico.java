package br.edu.ifpi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SistemaAcademico {

    Connection conexao;
    Aluno aluno;
    boolean sair = false;

    public void carregarSistema() {
        conexao = ConexaoBancoDeDados.connectar();
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {

        System.out.println("|-------MENU PRINCIPAL------|");
        System.out.println("| 1 - Assessar Aluno        |");
        System.out.println("| 2 - Assessar Professor    |");
        System.out.println("| 3 - Assessar Curso        |");
        System.out.println("|---------------------------|");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite uma opção!");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                mostrarMenuAluno();
                break;
            case 2:
                mostrarMenuProfessor();
                break;
            case 3:
                mostrarMenuCurso();
                break;
            default:
                System.out.println("Opeção invalida, tente novamente de 1 a 3!");
                mostrarMenuPrincipal();
                break;
        }
    }

    private void mostrarMenuAluno() {
        System.out.println("|--------MENU DO ALUNO-----------|");
        System.out.println("| 1 - Realizar matricula         |");
        System.out.println("| 2 - Visualizar Perfil          |");
        System.out.println("| 3 - Atualizar Informacoes      |");
        System.out.println("| 4 - Gerar relatorio desempenho |");
        System.out.println("| 5 - Menu principal             |");
        System.out.println("|--------------------------------|");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite uma opção!");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                Aluno.realizarMatricula(conexao);
                break;
            case 2:
                aluno = carregarDadosDoAluno(conexao);
                aluno.visualizarPerfil(conexao);
                break;
            case 3:
                aluno = carregarDadosDoAluno(conexao);
                aluno.atualizarInformacoes(conexao);
                break;
            case 4:
                aluno = carregarDadosDoAluno(conexao);
                aluno.gerarRelatorioDesempenho(conexao);
                break;
            case 5:
                mostrarMenuPrincipal();
                break;
            default:
                System.out.println("Opeção invalida, tente novamente de 1 a 5!");
                mostrarMenuPrincipal();
                break;
        }
    }

    private void mostrarMenuCurso() {
        System.out.println("|--------------MENU CURSO------------|");
        System.out.println("| 1 - Adicionar Disciplina           |");
        System.out.println("| 2 - Registrar Notas                |");
        System.out.println("| 3 - Atualizar Curso                |");
        System.out.println("| 4 - Visualizar Disciplinas         |");
        System.out.println("| 5 - Visializar Estatiticas do Aluno|");
        System.out.println("| 6 - Menu principal                 |");
        System.out.println("|------------------------------------|");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite uma opção!");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:
                mostrarMenuPrincipal();
                break;
            default:
                System.out.println("Opeção invalida, tente novamente de 1 a 6!");
                mostrarMenuPrincipal();
                break;
        }
    }

    private void mostrarMenuProfessor() {
        System.out.println("|------MENU DO PROFESSOR----|");
        System.out.println("| 1 - Associar Curso        |");
        System.out.println("| 2 - Atualizar Informacoes |");
        System.out.println("| 3 - Visualizar Cursos     |");
        System.out.println("| 4 - Menu principal        |");
        System.out.println("|---------------------------|");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite uma opção!");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                mostrarMenuPrincipal();
                break;
            default:
                System.out.println("Opeção invalida, tente novamente de 1 a 4!");
                mostrarMenuPrincipal();
                break;
        }
    }

    public void cadastrarAluno() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o nome do aluno");
        String nome = scanner.nextLine();
        System.out.println("Informe o email do aluno");
        String email = scanner.nextLine();

        String query = "INSERT INTO aluno (nome, email) VALUES ('" + nome + "','" + email + "')";

        try {
            Statement stm = conexao.createStatement();
            stm.executeUpdate(query);
            System.out.println(nome + " cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao realizar cadastrado!");
        }
    }

    public Aluno carregarDadosDoAluno(Connection conexao) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o ID do aluno");
        int id = scanner.nextInt();

        String query = "SELECT id, nome, email, curso_id FROM aluno WHERE " + id + "= id";
        Aluno aluno = null;

        try {
            Statement stm = conexao.createStatement();
            ResultSet result = stm.executeQuery(query);

            result.next();
            String nome = result.getString("nome");
            String email = result.getString("email");
            int idCurso = result.getInt("curso_id");

            aluno = new Aluno(id, nome, email, idCurso);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar dados do aluno!");
        }

        return aluno;
    }
}
