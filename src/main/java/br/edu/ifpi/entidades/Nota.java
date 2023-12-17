package br.edu.ifpi.entidades;

public class Nota {
    private double nota;
    private int idCurso;
    private int idAluno;
    private String nomeCurso;
    private String nomeAluno;

    public Nota(String nomeAluno, String nomeCurso, int idAluno, int idCurso, double nota) {
        this.nota = nota;
        this.nomeCurso = nomeCurso;
        this.nomeCurso = nomeCurso;
        this.idAluno = idAluno;
        this.idCurso = idCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public double getNota() {
        return nota;
    }
}
