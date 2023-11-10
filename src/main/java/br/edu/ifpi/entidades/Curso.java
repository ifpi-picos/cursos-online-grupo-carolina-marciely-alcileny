package br.edu.ifpi.entidades;

import br.edu.ifpi.Dao.CursoDao;
import br.edu.ifpi.enums.StatusCurso;

public class Curso {

    private int id;
    private String nome;
    private String cargaHoraria;
    private StatusCurso status;

    public Curso(int id, String nome, String cargaHoraria, StatusCurso status){
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.status = status;
    }

    public Curso() {
    }

    public void realizarCadastro(String nomeCurso, String cargaHorariaCurso, StatusCurso statusCurso) {

        Curso curso = new Curso(0, nomeCurso, cargaHorariaCurso, statusCurso);
        CursoDao cursoDao = new CursoDao();
        cursoDao.cadastrar(curso);
    }

    public void atualizarCadastro(int idCurso, String nomeCurso, String cargaHorariaCurso, StatusCurso statusCurso) {
        
        CursoDao cursoDao = new CursoDao();
        Curso curso = new Curso(idCurso, nomeCurso, cargaHorariaCurso, statusCurso);
        cursoDao.alterar(curso);
    }

    public void registrarNotas(int idAluno, int idCurso, double notaDoAluno) {
        CursoDao cursoDao = new CursoDao();
        cursoDao.registrarNotas(idAluno, idCurso, notaDoAluno);
    }

    public void visualizarListaDeCursos() {
        CursoDao cursoDao = new CursoDao();
        cursoDao.listarCursos(StatusCurso.ABERTO);
        
    }

    public void gerarEstatisticasDesempenho() {
        CursoDao cursoDao = new CursoDao();
        cursoDao.gerarEstatisticasDesempenho();
        
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public StatusCurso getStatus() {
        return status;
    }   
}