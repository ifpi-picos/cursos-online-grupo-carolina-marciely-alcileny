package br.edu.ifpi;

import java.util.List;

public class Curso {

    private int idCurso;
    private String nome;
    private String cargaHoraria;
    private List<Disciplina> listaDisciplinas;
    private boolean status;


    public void adicionarDisciplina(Disciplina disciplina) {
        this.listaDisciplinas.add(disciplina);
    }

     public void registrarNotas(Aluno aluno) {
       
    }

    public void atualizarCurso(Curso curso) {
         
    }

    public void visualizarDisciplinas() {

    }

    public void gerarEstatisticasDesempenho(Aluno aluno) {

    }
}
