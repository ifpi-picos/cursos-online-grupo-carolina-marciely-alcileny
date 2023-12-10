package br.edu.ifpi.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import br.edu.ifpi.conexaoBD.ConexaoBancoDeDados;

public class Professor extends ConexaoBancoDeDados {

  public void cadastroProfessor() {
    Scanner scanner = new Scanner(System.in);
    limparConsole();
    System.out.println("Informe o nome do professor(a)");
    String nome = scanner.nextLine();
    System.out.println("Informe o e-mail do professor(a)");
    String email = scanner.nextLine();
    limparConsole();

    String query = "INSERT INTO professor (nome, email) VALUES ('" + nome + "','" + email + "')";

    try {
      Statement stm = connectar().createStatement();
      stm.executeUpdate(query);
      imprimirMenssagemDeCadastro(SUCESSO, "Professor");
      imprimirAssociarProfessorACurso();
      associarCurso();
    } catch (SQLException e) {
      imprimirMenssagemDeCadastro(ERRO, "Professor");
    }
  }

  public void associarCurso() {
    Curso curso = new Curso();
    limparConsole();
    int idProfessor = carregarDadosDoProfessor();
    limparConsole();
    if (idProfessor != 0) {
      int idCurso = curso.carregarDadosCursoNaoAssociado(idProfessor);
      limparConsole();
      if (idCurso != 0) {
        String query = "INSERT INTO curso_e_professor (curso_id , professor_id) VALUES ('" + idCurso + "','"
            + idProfessor
            + "')";

        try {
          Statement stm = connectar().createStatement();
          stm.executeUpdate(query);
          imprimirMenssagemDeAssociacao(SUCESSO, "Curso");
        } catch (SQLException e) {
          imprimirMenssagemDeAssociacao(ERRO, "Curso");
        }
      } else {
        imprimirNenhumCursoAssociadoEnc();
      }
    } else {
      imprimirMensagemNenhumDado("professor");
    }
  }

  public void atualizarInformacoes() {
    Scanner scanner = new Scanner(System.in);
    limparConsole();
    int idProfessor = carregarDadosDoProfessor();
    limparConsole();
    if (idProfessor != 0) {
      System.out.println("Informe o novo nome do professor");
      String nome = scanner.nextLine();
      System.out.println("Informe a nova e-mail do professor");
      String email = scanner.nextLine();
      limparConsole();

      String query = "UPDATE professor SET nome = '" + nome + "', email = '" + email + "' WHERE id = '" + idProfessor
          + "'";

      try {
        Statement stm = connectar().createStatement();
        stm.executeUpdate(query);
        imprimirMenssagemDeAtualizacao(SUCESSO, "Professor");
      } catch (SQLException e) {
        imprimirMenssagemDeAtualizacao(ERRO, "Professor");
      }
    } else {
      imprimirMensagemNenhumDado("professor");
    }
  }

  public void visualizarListaProfessores() {
    limparConsole();
    String query = "SELECT assoc.*, prof.nome AS profnome, prof.email, cur.nome AS curso FROM professor prof LEFT JOIN curso_e_professor assoc ON prof.id = assoc.professor_id LEFT JOIN curso cur ON cur.id = assoc.curso_id";

    try {
      Statement stm = connectar().createStatement();
      ResultSet result = stm.executeQuery(query);

      if (result.next()) {
        String nome = result.getString("profnome");
        String email = result.getString("email");
        String curso = result.getString("curso");
        System.out.println("|================LISTA DE PROFESSORES=================|");
        System.out.println("| Nome: " + nome);
        System.out.println("| E-mail: " + email);
        if (curso != null) {
          System.out.println("| Curso Associado: " + curso);
          System.out.println("|-----------------------------------------------------|");
        } else {
          System.out.println("| Curso Associado: Nao esta associado em nenhum curso |");
          System.out.println("|-----------------------------------------------------|");
        }
        while (result.next()) {
          nome = result.getString("profnome");
          email = result.getString("email");
          curso = result.getString("curso");
          System.out.println("| Nome: " + nome);
          System.out.println("| E-mail: " + email);
          if (curso != null) {
            System.out.println("| Curso Associado: " + curso);
            System.out.println("|-----------------------------------------------------|");
          } else {
            System.out.println("| Curso Associado: Nao esta associado em nenhum curso |");
            System.out.println("|-----------------------------------------------------|");
          }
        }
        System.out.println("|=====================================================|");
        pausar();
      } else {
        imprimirNenhumProfessorAssociadoEnc();
      }
    } catch (SQLException e) {
      imprimirErroAoCarregarDados("aluno");
    }
  }

  private int carregarDadosDoProfessor() {
    String query = "SELECT id, nome FROM professor";
    int idSelecionado = 0;

    try {
      Statement stm = connectar().createStatement();
      ResultSet result = stm.executeQuery(query);

      if (result.next()) {
        System.out.println("----------------------------------");
        int id = result.getInt("id");
        String nome = result.getString("nome");

        System.out.println(" ID -> " + id + " Nome: " + nome);

        while (result.next()) {
          id = result.getInt("id");
          nome = result.getString("nome");
          System.out.println(" ID -> " + id + " Nome: " + nome);
        }
        System.out.println("----------------------------------");
        System.out.println("Selecione o ID do professor(a)!");
        Scanner scanner = new Scanner(System.in);
        idSelecionado = scanner.nextInt();
      } else {
        imprimirMensagemNenhumDado("professor");
      }

    } catch (SQLException e) {
      imprimirErroAoCarregarDados("professor");
    }
    return idSelecionado;
  }
}