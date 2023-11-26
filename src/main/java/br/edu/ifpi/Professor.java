package br.edu.ifpi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Professor {

  private Connection conexao;

  public Professor(Connection conexao) {
    this.conexao = conexao;
  }

  public void cadastroProfessor() {
    Scanner scanner = new Scanner(System.in);
    SistemaAcademico.limparConsole();
    System.out.println("Informe o nome do professor(a)");
    String nome = scanner.nextLine();
    System.out.println("Informe o e-mail do professor(a)");
    String email = scanner.nextLine();
    SistemaAcademico.limparConsole();

    String query = "INSERT INTO professor (nome, email) VALUES ('" + nome + "','" + email + "')";

    try {
      Statement stm = this.conexao.createStatement();
      stm.executeUpdate(query);
      System.out.println("|------------------------------------|");
      System.out.println("| Professor cadastrado com sucesso!  |");
      System.out.println("|------------------------------------|");

      System.out.println("|---------------------------------------|");
      System.out.println("| Vamos associar o professor a um curso!|");
      System.out.println("|---------------------------------------|");
      Scanner scanner2 = new Scanner(System.in);
      System.out.println("Enter para ir para continuar");
      scanner2.nextLine();

      associarCurso();
    } catch (SQLException e) {
      System.out.println("|------------------------------------|");
      System.out.println("| Erro ao realizar cadastro!         |");
      System.out.println("|------------------------------------|");
      Scanner scanner2 = new Scanner(System.in);
      System.out.println("Enter para ir para o menu principal");
      scanner2.nextLine();
    }
  }

  public void associarCurso() {
    Curso curso = new Curso(conexao);
    SistemaAcademico.limparConsole();
    int idProfessor = carregarDadosDoProfessor();
    SistemaAcademico.limparConsole();
    if (idProfessor != 0) {
      int idCurso = curso.carregarDadosCursoNaoAssociado(idProfessor);
      SistemaAcademico.limparConsole();
      if (idCurso != 0) {
        String query = "INSERT INTO curso_e_professor (curso_id , professor_id) VALUES ('" + idCurso + "','"
            + idProfessor
            + "')";

        try {
          Statement stm = this.conexao.createStatement();
          stm.executeUpdate(query);
          System.out.println("|------------------------------------|");
          System.out.println("| Curso associado com sucesso!       |");
          System.out.println("|------------------------------------|");
        } catch (SQLException e) {
          System.out.println("|------------------------------------|");
          System.out.println("| Erro ao associar curso!            |");
          System.out.println("|------------------------------------|");
        }
      } else {
        System.out.println("|-------------------------------------------|");
        System.out.println("| Nao foi encontrado nenhum curso           |");
        System.out.println("| Que o professor nao esteja associado!     |");
        System.out.println("|-------------------------------------------|");
      }
    } else {
      System.out.println("|------------------------------------|");
      System.out.println("| Nenhum professor encontrado!       |");
      System.out.println("|------------------------------------|");
    }

    Scanner scanner2 = new Scanner(System.in);
    System.out.println("Enter para ir para o menu principal");
    scanner2.nextLine();
  }

  public void atualizarInformacoes() {
    Scanner scanner = new Scanner(System.in);
    SistemaAcademico.limparConsole();
    int idProfessor = carregarDadosDoProfessor();
    SistemaAcademico.limparConsole();
    if (idProfessor != 0) {
      System.out.println("Informe o novo nome do professor");
      String nome = scanner.nextLine();
      System.out.println("Informe a nova e-mail do professor");
      String email = scanner.nextLine();
      SistemaAcademico.limparConsole();

      String query = "UPDATE professor SET nome = '" + nome + "', email = '" + email + "' WHERE id = '" + idProfessor
          + "'";

      try {
        Statement stm = this.conexao.createStatement();
        stm.executeUpdate(query);
        System.out.println("|--------------------------------------------|");
        System.out.println("| Dados do professor atualizado com sucesso! |");
        System.out.println("|--------------------------------------------|");
      } catch (SQLException e) {
        System.out.println("|--------------------------------------|");
        System.out.println("| Erro ao atualizar dados do professor!|");
        System.out.println("|--------------------------------------|");
      }
    } else {
      System.out.println("|------------------------------------|");
      System.out.println("| Nenhum professor encontrado!       |");
      System.out.println("|------------------------------------|");
    }
    Scanner scanner2 = new Scanner(System.in);
    System.out.println("Enter para ir para o menu principal");
    scanner2.nextLine();
  }

  public void visualizarListaProfessores() {
    SistemaAcademico.limparConsole();
    String query = "SELECT assoc.*, prof.nome AS profnome, prof.email, cur.nome AS curso FROM professor prof LEFT JOIN curso_e_professor assoc ON prof.id = assoc.professor_id LEFT JOIN curso cur ON cur.id = assoc.curso_id";

    try {
      Statement stm = this.conexao.createStatement();
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
      }else{
      System.out.println("|-------------------------------------------------|");
      System.out.println("| Nenhum professor associado a cursos encontrado! |");
      System.out.println("|-------------------------------------------------|");
      }
    } catch (SQLException e) {
      System.out.println("|------------------------------------|");
      System.out.println("| Erro ao carregar dados do aluno!   |");
      System.out.println("|------------------------------------|");
    }

    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter para ir para o menu principal");
    scanner.nextLine();
  }

  private int carregarDadosDoProfessor() {
    String query = "SELECT id, nome FROM professor";
    int idSelecionado = 0;

    try {
      Statement stm = this.conexao.createStatement();
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
        System.out.println("|------------------------------------|");
        System.out.println("| Nenhum professor encontrado!           |");
        System.out.println("|------------------------------------|");
      }

    } catch (SQLException e) {
      System.out.println("Erro ao carregar dados do professor(a)!");
    }

    return idSelecionado;
  }
}