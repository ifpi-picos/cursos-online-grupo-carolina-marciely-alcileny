package br.edu.ifpi.entidades;

import java.util.List;
import java.util.Scanner;

import br.edu.ifpi.Dao.AlunoDao;
import br.edu.ifpi.Dao.CursoDao;
import br.edu.ifpi.Dao.NotaDao;
import br.edu.ifpi.enums.StatusCurso;
import br.edu.ifpi.utilidades.Mensagem;

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

    public void realizarCadastro() {
        Mensagem.limparConsole();
        Scanner scanner = new Scanner(System.in);
        int papel = 0;
        System.out.println("Informe o nome do curso");
        String nome = scanner.next();
        System.out.println("Informe a carga horaria do curso");
        String cargaHoraria = scanner.next();
        Mensagem.limparConsole();
        System.out.println("|-------STATUS DO CURSO------|");
        System.out.println("| 1 - Aberto                 |");
        System.out.println("| 2 - Fechado                |");
        System.out.println("|----------------------------|");
        System.out.println("Informe o status do curso");
        papel = scanner.nextInt();
        StatusCurso status = StatusCurso.ABERTO;

        if (papel == 2) {
            status = StatusCurso.FECHADO;
        }

        Curso curso = new Curso(0, nome, cargaHoraria, status);
        CursoDao cursoDao = new CursoDao();
        cursoDao.cadastrar(curso);
    }

    public void atualizarCadastro() {
        Mensagem.limparConsole();
        CursoDao cursoDao = new CursoDao();
        cursoDao.consultar();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe ID do curso");
        int id = scanner.nextInt();
        Mensagem.limparConsole();
        System.out.println("Informe o novo nome do curso");
        String nome = scanner.next();
        System.out.println("Informe a nova carga horaria do curso");
        String cargaHoraria = scanner.next();
        Mensagem.limparConsole();
        System.out.println("|-------STATUS DO CURSO------|");
        System.out.println("| 1 - Aberto                 |");
        System.out.println("| 2 - Fechado                |");
        System.out.println("|----------------------------|");
        System.out.println("Informe o novo status do curso");
        int papel = 0;
        papel = scanner.nextInt();
        StatusCurso status = StatusCurso.ABERTO;

        if (papel == 2) {
            status = StatusCurso.FECHADO;
        }

        Curso curso = new Curso(id, nome, cargaHoraria, status);
        cursoDao.alterar(curso);
    }

    public void registrarNotas() {
        Mensagem.limparConsole();
        AlunoDao alunoDao = new AlunoDao();
        CursoDao cursoDao = new CursoDao();
        alunoDao.consultar();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o ID do aluno");
        int idAluno = scanner.nextInt();
        Mensagem.limparConsole();
        cursoDao.consultar();
        System.out.println("Informe o ID do curso");
        int idCurso = scanner.nextInt();
        Mensagem.limparConsole();
        System.out.println("Informe a nota do aluno");
        double notaDoAluno = scanner.nextDouble();

        cursoDao.registrarNotas(idAluno, idCurso, notaDoAluno);
    }

    public void visualizarListaDeCursos() {
        Mensagem.limparConsole();
        CursoDao cursoDao = new CursoDao();
        cursoDao.listarCursos(null);
        Mensagem.pausar();
    }

    public void gerarEstatisticasDesempenho() {
        Mensagem.limparConsole();
        CursoDao cursoDao = new CursoDao();
        cursoDao.gerarEstatisticasDesempenho();
        Mensagem.pausar();
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