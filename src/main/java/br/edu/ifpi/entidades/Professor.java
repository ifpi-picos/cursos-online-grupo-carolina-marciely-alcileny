package br.edu.ifpi.entidades;

import br.edu.ifpi.Dao.ProfessorDao;

public class Professor {

  private int id;
  private String nome;
  private String email;

  public Professor(int id, String nome, String email) {
    this.id = id;
    this.nome = nome;
    this.email = email;
  }

  public Professor() {
  }

  public void realizarCadastro(String nomeProfessor, String emailProfessor) {

    Professor professor = new Professor(0, nomeProfessor, emailProfessor);
    ProfessorDao professorDao = new ProfessorDao();
    professorDao.cadastrar(professor);
  }

  public void atualizarCadastro(int idProfessor, String nomeProfessor, String emailProfessor) {
    ProfessorDao professorDao = new ProfessorDao();
    Professor professor = new Professor(idProfessor, nomeProfessor, emailProfessor);
    professorDao.alterar(professor);
  }

  public void associarCurso(int idCurso, int idProfessor) {
    ProfessorDao professorDao = new ProfessorDao();
    
    CursoProfessor associacaoProfessor = new CursoProfessor(idCurso, idProfessor, null, null);
    professorDao.associarCurso(associacaoProfessor);
  }

  public void visualizarListaProfessores() {
    ProfessorDao professorDao = new ProfessorDao();
    professorDao.listarProfessores();
    
  }

  public int getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getEmail() {
    return email;
  }

}