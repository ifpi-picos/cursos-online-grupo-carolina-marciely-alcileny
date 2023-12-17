package br.edu.ifpi.entidades;

import java.util.Scanner;
import br.edu.ifpi.Dao.CursoDao;
import br.edu.ifpi.Dao.ProfessorDao;
import br.edu.ifpi.utilidades.Mensagem;

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

  public void realizarCadastro() {
    Mensagem.limparConsole();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Informe o nome do professor");
    String nome = scanner.next();
    System.out.println("Informe o email do professor");
    String email = scanner.next();
    Mensagem.limparConsole();

    Professor professor = new Professor(0, nome, email);
    ProfessorDao professorDao = new ProfessorDao();
    professorDao.cadastrar(professor);
  }

  public void atualizarCadastro() {
    Mensagem.limparConsole();
    ProfessorDao professorDao = new ProfessorDao();
    professorDao.consultar();
    System.out.println("Digite o ID do professor!");
    Scanner scanner = new Scanner(System.in);
    int id = scanner.nextInt();
    Mensagem.limparConsole();
    System.out.println("Informe o novo nome do professor");
    String nome = scanner.next();
    System.out.println("Informe o novo e-mail do professor");
    String email = scanner.next();
    Professor professor = new Professor(id, nome, email);
    professorDao.alterar(professor);
  }

  public void associarCurso() {
    Mensagem.limparConsole();
    Scanner scanner = new Scanner(System.in);
    ProfessorDao professorDao = new ProfessorDao();
    professorDao.consultar();
    System.out.println("Informe o ID do professor");
    int idProfessor = scanner.nextInt();
    Mensagem.limparConsole();
    CursoDao cursoDao = new CursoDao();
    cursoDao.consultar();
    System.out.println("Informe o ID do curso");
    int idCurso = scanner.nextInt();
    Mensagem.limparConsole();
    
    CursoProfessor associacaoProfessor = new CursoProfessor(idCurso, idProfessor, null, null);
    professorDao.associarCurso(associacaoProfessor);
  }

  public void visualizarListaProfessores() {
    Mensagem.limparConsole();
    ProfessorDao professorDao = new ProfessorDao();
    professorDao.listarProfessores();
    Mensagem.pausar();
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