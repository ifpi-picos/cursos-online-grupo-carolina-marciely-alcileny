package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.CursoProfessor;
import br.edu.ifpi.entidades.Professor;
import br.edu.ifpi.utilidades.Mensagem;

public class ProfessorDao implements Dao<Professor> {

  private Mensagem mensagem = new Mensagem();

  @Override
  public void cadastrar(Professor professor) {
    String query = "INSERT INTO professor (nome, email) VALUES (?,?)";
    try (PreparedStatement stm = ConexaoDao.getConexao()
        .prepareStatement(query)) {
      stm.setString(1, professor.getNome());
      stm.setString(2, professor.getEmail());
      stm.executeUpdate();

      mensagem.imprimirMenssagemDeCadastro(mensagem.SUCESSO, "professor");
    } catch (SQLException e) {
      mensagem.imprimirMenssagemDeCadastro(mensagem.ERRO, "professor");
    } catch (Exception e) {
      mensagem.imprimirMenssagemDeCadastro(mensagem.ERRO, "professor");

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
      mensagem.imprimirMensagemNenhumDado("professor");
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
      e.printStackTrace();
      mensagem.imprimirErroAoCarregarDados("professor");
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
      mensagem.imprimirMenssagemDeExclusao(mensagem.SUCESSO, "professor");
    } catch (SQLException e) {
      mensagem.imprimirMenssagemDeExclusao(mensagem.ERRO, "professor");
    } catch (Exception e) {
      mensagem.imprimirMenssagemDeExclusao(mensagem.ERRO, "professor");
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
      mensagem.imprimirMenssagemDeAtualizacao(mensagem.SUCESSO, "professor");
    } catch (SQLException e) {
      mensagem.imprimirMenssagemDeAtualizacao(mensagem.ERRO, "professor");
    } catch (Exception e) {
      mensagem.imprimirMenssagemDeAtualizacao(mensagem.ERRO, "professor");
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
      mensagem.imprimirMensagemNenhumDado("professor");
    }
  }
}