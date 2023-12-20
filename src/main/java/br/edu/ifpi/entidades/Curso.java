package br.edu.ifpi.entidades;

import br.edu.ifpi.enums.StatusCurso;


public class Curso {

    private int id;
    private String nome;
    private String cargaHoraria;
    private StatusCurso status;

    public Curso(int id, String nome, String cargaHoraria, StatusCurso status) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.status = status;
    }

    public Curso() {
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