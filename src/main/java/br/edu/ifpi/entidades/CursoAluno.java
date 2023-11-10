package br.edu.ifpi.entidades;

import br.edu.ifpi.enums.StatusMatricula;

public class CursoAluno {

    private StatusMatricula status;
    private int idCurso;
    private int idAluno;
    private String nomeCurso;
    private String nomeAluno;
    private int porcentagemNota;

    public CursoAluno(int idCurso, int idAluno, String nomeAluno, String nomeCurso,StatusMatricula status, int porcentagemNota) {
        this.status = status;
        this.idCurso = idCurso;
        this.idAluno = idAluno;
        this.nomeCurso = nomeCurso;
        this.nomeAluno = nomeAluno;
        this.porcentagemNota = porcentagemNota;
    }

    public StatusMatricula getStatus() {
        return status;
    }
    public int getIdCurso() {
        return idCurso;
    }
    public int getIdAluno() {
        return idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

     public int getPorcentagemNota() {
        return porcentagemNota;
    }
}
