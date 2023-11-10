package br.edu.ifpi.entidades;

import br.edu.ifpi.Dao.AlunoDao;
import br.edu.ifpi.Dao.CursoAlunoDao;
import br.edu.ifpi.enums.StatusMatricula;

public class Aluno {
    private int id;
    private String nome;
    private String email;

    public Aluno(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Aluno() {
    }

    public void realizarCadastro(String nomeAluno, String emailAluno) {

        Aluno aluno = new Aluno(0, nomeAluno, emailAluno);
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.cadastrar(aluno);
    }

    public void atualizarCadastro(int idAluno, String nomeAluno, String emailAluno) {

        Aluno aluno = new Aluno(idAluno, nomeAluno, emailAluno);
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.alterar(aluno);
    }

    public void realizarMatricula(int idCurso, int idAluno) {
    AlunoDao alunoDao = new AlunoDao();
       alunoDao.realizarMatricula(idCurso, idAluno);

    }

    public void visualizarPerfil(int idAluno) {
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.visualizarPerfil(idAluno);
        
    }

    public void cancelarMatricula(int idCurso, int idAluno) {
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.cancelarMatricula(idCurso, idAluno);
    }

    public void concluirMatricula(int idCurso, int idAluno) {
        CursoAlunoDao matriculaDao = new CursoAlunoDao();
        CursoAluno matricula = new CursoAluno(idCurso, idAluno, null, null, StatusMatricula.CONCLUIDO,0);
        matriculaDao.alterar(matricula);
    }

    public void gerarRelatorioDesempenho() {
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.gerarRelatorioDesempenho();
        
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
