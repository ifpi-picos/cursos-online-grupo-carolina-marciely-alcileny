package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.CursoProfessor;
import br.edu.ifpi.entidades.Professor;

public class ProfessorDao implements Dao<Professor> {

  @Override
  public void cadastrar(Professor professor) {
    String query = "INSERT INTO professor (nome, email) VALUES (?,?)";
    try (PreparedStatement stm = ConexaoDao.getConexao()
        .prepareStatement(query)) {
      stm.setString(1, professor.getNome());
      stm.setString(2, professor.getEmail());
      stm.executeUpdate();

      System.out.println("Professor cadastrado com sucesso!");
     
    } catch (SQLException e) {
      System.out.println("Ocorreu um erro ao cadastrar este professor");
     
    }
  }

  @Override
  public void consultar() {
    List<Professor> professores = new ArrayList<>();
    professores = carregarDados();
    if (!professores.isEmpty()) {
      System.out.println("|=============LISTA DE PROFESSORES=================");
      for (int i = 0; i < professores.size(); i++) {

        System.out.println("| ID -> " + professores.get(i).getId());
        System.out.println("| Nome " + professores.get(i).getNome());
        System.out.println("| Email " + professores.get(i).getEmail());
        System.out.println("|-----------------------------------------------");
      }
      System.out.println("|===============================================");
     
    } else {
      System.out.println("Nenhum professor cadastrado encontrado");
     
    }
  }

  @Override
  public List<Professor> carregarDados() {
    List<Professor> professores = new ArrayList<>();
    String query = "SELECT * FROM professor ORDER BY id";
    try (PreparedStatement stm = ConexaoDao.getConexao()
        .prepareStatement(query)) {
      ResultSet resultSet = stm.executeQuery();

      while (resultSet.next()) {

        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");
        String email = resultSet.getString("email");

        Professor professor = new Professor(id, nome, email);
        professores.add(professor);

      }
      return professores;
    } catch (SQLException e) {
      System.out.println("Ocorreu um erro ao carregar dados dos professores!");
     
      return null;
    }
  }

  @Override
  public void remover(Professor professor) {
    String query = "DELETE FROM professor WHERE ? = id";
    try (PreparedStatement stm = ConexaoDao.getConexao()
        .prepareStatement(query)) {
      stm.setInt(1, professor.getId());
      stm.executeUpdate();

      System.out.println("Professor removido com sucesso!");
     
    } catch (SQLException e) {
      System.out.println("Ocorreu um erro ao remover este professor");
     
    }
  }

  @Override
  public void alterar(Professor professor) {
    String query = "UPDATE professor SET nome = ?, email = ? WHERE id = ?";
    try (PreparedStatement stm = ConexaoDao.getConexao()
        .prepareStatement(query)) {
      stm.setString(1, professor.getNome());
      stm.setString(2, professor.getEmail());
      stm.setInt(3, professor.getId());
      stm.executeUpdate();
      System.out.println("Informacoes do professor alterada com sucesso!");
     
    } catch (SQLException e) {
        System.out.println("Ocorreu um erro ao alterar informacoes deste professor");
       
    }
  }

  public void associarCurso(CursoProfessor associacaoProfessor) {
    CursoProfessorDao associacaoProfessorDao = new CursoProfessorDao();
    associacaoProfessorDao.cadastrar(associacaoProfessor);
  }

  public void listarProfessores() {
    List<Professor> professores = new ArrayList<>();
    professores = carregarDados();
    List<CursoProfessor> associados = new ArrayList<>();
    CursoProfessorDao associacaoProfessorDao = new CursoProfessorDao();
    associados = associacaoProfessorDao.carregarDados();

    if (!professores.isEmpty()) {
      System.out.println("|=============LISTA DE PROFESSORES=================");
      for (int i = 0; i < professores.size(); i++) {

        System.out.println("| ID -> " + professores.get(i).getId());
        System.out.println("| Nome " + professores.get(i).getNome());
        System.out.println("| Email " + professores.get(i).getEmail());
        System.out.println("|-------------Associado nos cursos--------------");

        for (CursoProfessor associado : associados) {
          if (associado.getNomeProfessor().equals(professores.get(i).getNome())) {
            System.out.println("| " + associado.getNomeCurso());
          }
        }
        System.out.println("|===============================================");
      }
     
    } else {
      System.out.println("Nenhum professor cadastrado encontrado");
     
    }
  }
}