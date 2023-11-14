package br.edu.ifpi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Aluno {

    private int idAluno;
    private String nomeAluno;
    private String email;
    private int idCurso;

    public Aluno(int idAluno, String nomeAluno, String email, int idCurso) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.email = email;
        this.idCurso = idCurso;
    }

    public static void realizarMatricula(Connection conexao) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o nome do aluno");
        String nome = scanner.nextLine();
        System.out.println("Informe o email do aluno");
        String email = scanner.nextLine();
        System.out.println("Informe o ID do curso");
        String curso_id = scanner.nextLine();

        String query = "INSERT INTO aluno (nome, email, curso_id) VALUES ('" + nome + "','" + email + "', '" + curso_id
                + "')";

        try {
            Statement stm = conexao.createStatement();
            stm.executeUpdate(query);
            System.out.println(nome + " matriculado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao realizar matricula!");
        }
    }

    public void atualizarInformacoes(Connection conexao) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe novo nome do aluno");
        String novoNome = scanner.nextLine();
        System.out.println("Informe o novo email do aluno");
        String email = scanner.nextLine();
        System.out.println("Informe o ID do novo curso");
        String curso_id = scanner.nextLine();

        String query = "UPDATE aluno SET nome = '" + novoNome + "', email = '" + email + "', curso_id = '" + curso_id
                + "' WHERE id = '" + idAluno + "'";

        try {
            Statement stm = conexao.createStatement();
            stm.executeUpdate(query);
            System.out.println("Informacoes de " + novoNome + " atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar informacoes!");
        }

    }

    public void visualizarPerfil(Connection connection) {
        System.out.println(idAluno);
        System.out.println(nomeAluno);
        System.out.println(email);
        System.out.println(idCurso);
    }

    public void gerarRelatorioDesempenho(Connection conexao) {

        String query = "SELECT id, nota, disciplina_id FROM nota WHERE " + idAluno + " = aluno_id";

        try {
            Statement stm = conexao.createStatement();
            ResultSet result = stm.executeQuery(query);

            System.out.println("--------------------------");
            System.out.println("Nome: " + this.nomeAluno);
             System.out.println("--------------------------");

            while (result.next()) {
                String nota = result.getString("nota");
                String disciplinaId = result.getString("disciplina_id");

                System.out.println("Nota: " + nota);
                System.out.println("Disciplina: "+ disciplinaId);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao carregar notas do aluno!");
        }

    }

}