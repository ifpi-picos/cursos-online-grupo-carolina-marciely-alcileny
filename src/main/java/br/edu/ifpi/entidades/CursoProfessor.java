package br.edu.ifpi.entidades;

public class CursoProfessor {

    private int idCurso;
    private int idProfessor;
    private String nomeCurso;
    private String nomeProfessor;

    public CursoProfessor(int idCurso, int idProfessor, String nomeCurso, String nomeProfessor) {
        this.nomeCurso = nomeCurso;
        this.nomeProfessor = nomeProfessor;
        this.idCurso = idCurso;
        this.idProfessor = idProfessor;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public int getIdCurso() {
        return idCurso;
    } 
}
