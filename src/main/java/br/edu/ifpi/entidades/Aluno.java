package br.edu.ifpi.entidades;

import java.util.Scanner;

import br.edu.ifpi.Dao.AlunoDao;
import br.edu.ifpi.Dao.CursoDao;
import br.edu.ifpi.Dao.CursoAlunoDao;
import br.edu.ifpi.enums.StatusMatricula;
import br.edu.ifpi.utilidades.Mensagem;

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

    public void realizarCadastro() {
        Mensagem.limparConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o nome do aluno");
        String nome = scanner.next();
        System.out.println("Informe o e-mail do aluno");
        String email = scanner.next();
        Mensagem.limparConsole();

        Aluno aluno = new Aluno(0, nome, email);
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.cadastrar(aluno);
    }

    public void atualizarCadastro() {
        Mensagem.limparConsole();
        AlunoDao alunoDao = new AlunoDao();
        Scanner scanner = new Scanner(System.in);
        alunoDao.consultar();
        System.out.println("Informe o ID do aluno");
        int id = scanner.nextInt();
        Mensagem.limparConsole();
        System.out.println("Informe o novo nome do aluno");
        String nome = scanner.next();
        System.out.println("Informe o novo e-mail do aluno");
        String email = scanner.next();
        Mensagem.limparConsole();

        Aluno aluno = new Aluno(id, nome, email);
        alunoDao.alterar(aluno);
    }

    public void realizarMatricula() {
        Mensagem.limparConsole();
        CursoDao cursoDao = new CursoDao();
        AlunoDao alunoDao = new AlunoDao();
        Scanner scanner = new Scanner(System.in);
        alunoDao.consultar();
        System.out.println("Informe o ID do aluno");
        int idAluno = scanner.nextInt();
        Mensagem.limparConsole();
        cursoDao.consultar();
        System.out.println("Informe o ID do curso");
        int idCurso = scanner.nextInt();
        Mensagem.limparConsole();

       alunoDao.realizarMatricula(idCurso, idAluno);

    }

    public void visualizarPerfil() {
        Mensagem.limparConsole();
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.consultar();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o ID do aluno");
        int idAluno = scanner.nextInt();
        alunoDao.visualizarPerfil(idAluno);
        Mensagem.pausar();
    }

    public void cancelarMatricula() {
        Mensagem.limparConsole();
        AlunoDao alunoDao = new AlunoDao();
        CursoDao cursoDao = new CursoDao();
        CursoAlunoDao matriculaDao = new CursoAlunoDao();
        Scanner scanner = new Scanner(System.in);
        alunoDao.consultar();
        System.out.println("Informe o ID do aluno");
        int idAluno = scanner.nextInt();
        Mensagem.limparConsole();
        cursoDao.consultar();
        System.out.println("Informe o ID do curso");
        int idCurso = scanner.nextInt();
        alunoDao.cancelarMatricula(idCurso, idAluno);
    }

    public void concluirMatricula() {
        Mensagem.limparConsole();
        AlunoDao alunoDao = new AlunoDao();
        CursoDao cursoDao = new CursoDao();
        CursoAlunoDao matriculaDao = new CursoAlunoDao();
        Scanner scanner = new Scanner(System.in);
        alunoDao.consultar();
        System.out.println("Informe o ID do aluno");
        int idAluno = scanner.nextInt();
        Mensagem.limparConsole();
        cursoDao.consultar();
        System.out.println("Informe o ID do curso");
        int idCurso = scanner.nextInt();

        CursoAluno matricula = new CursoAluno(idCurso, idAluno, null, null, StatusMatricula.CONCLUIDO);
        matriculaDao.alterar(matricula);
    }

    public void gerarRelatorioDesempenho() {
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.gerarRelatorioDesempenho();
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
