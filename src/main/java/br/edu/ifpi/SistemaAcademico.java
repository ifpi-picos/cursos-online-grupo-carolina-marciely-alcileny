package br.edu.ifpi;

import java.sql.Connection;
import java.util.Scanner;

import br.edu.ifpi.enums.PapeisUsuario;

public class SistemaAcademico {

    private Connection conexao;
    private Usuario usuario;
    private boolean continuar = true;
    // metodo usuario 
    public void carregarSistema(Usuario usuario, Connection conexao) {
        this.usuario = usuario;
        this.conexao = conexao;
        while (continuar) {
            limparConsole();
            mostrarMenuPrincipal();
        }
    }

    public void mostrarMenuPrincipal() {
        if (AutenticacaoAutorizacao.autorizarUsuario(usuario, PapeisUsuario.ADMINISTRADOR)) {
            mostrarMenuPrincipalAdministrador();
        } else if (AutenticacaoAutorizacao.autorizarUsuario(usuario, PapeisUsuario.PROFESSOR)) {
            mostrarMenuPrincipalProfessor();
        } else {
            mostrarMenuPrincipalAluno();
        }
    }

    public void mostrarMenuPrincipalAdministrador() {
        limparConsole();

        System.out.println("|---MENU PRINCIPAL DO ADMINISTRADOR(A)--|");
        System.out.println("| 1 - Gestao de Cursos                  |");
        System.out.println("| 2 - Gestao de Professores             |");
        System.out.println("| 3 - Gestao de Alunos                  |");
        System.out.println("| 4 - Gestao de Usuarios                |");
        System.out.println("| 5 - Sistema de Autorizacao do usuario |");
        System.out.println("| 6 - Sair do Sistema                   |");
        System.out.println("|---------------------------------------|");

        System.out.println("Digite uma opção!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                mostrarMenuCurso();
                break;
            case 2:
                mostrarMenuProfessor();
                break;
            case 3:
                mostrarMenuAluno();
                break;
            case 4:
                mostrarMenuUsuario();
                break;
            case 5:
                mostrarMenuAutenticacaoAutorizacao();
                break;
            case 6:
                limparConsole();
                System.out.println("Voce saiu do sistema");
                this.continuar = false;
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 6!");
                break;
        }
    }

    private void mostrarMenuPrincipalProfessor() {
        limparConsole();
        System.out.println("|---MENU PRINCIPAL DO PROFESSOR(A)--|");
        System.out.println("| 1 - Gestao de Professores         |");
        System.out.println("| 2 - Gestao de Alunos              |");
        System.out.println("| 3 - Sair do Sistema               |");
        System.out.println("|-----------------------------------|");

        System.out.println("Digite uma opção!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                mostrarMenuProfessor();
                break;
            case 2:
                mostrarMenuAluno();
                break;
            case 3:
                limparConsole();
                System.out.println("Voce saiu do sistema");
                this.continuar = false;
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 3!");
                break;
        }
    }

    private void mostrarMenuPrincipalAluno() {
        limparConsole();
        System.out.println("|------MENU PRINCIPAL DO ALUNO------|");
        System.out.println("| 1 - Gestao de Alunos              |");
        System.out.println("| 2 - Sair do Sistema               |");
        System.out.println("|-----------------------------------|");

        System.out.println("Digite uma opção!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                mostrarMenuAluno();
                break;
            case 2:
                limparConsole();
                System.out.println("Voce saiu do sistema");
                this.continuar = false;
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 2!");
                break;
        }
    }

    private void mostrarMenuUsuario() {
        limparConsole();
        Usuario usuario = new Usuario(conexao);
        System.out.println("|-----------MENU DO USUARIO---------|");
        System.out.println("| 1 - Cadastrar Usuario             |");
        System.out.println("| 2 - Visualizar Usuarios           |");
        System.out.println("| 3 - Atualizar Usuario             |");
        System.out.println("| 4 - Excluir Usuario               |");
        System.out.println("| 5 - Menu Princial                 |");
        System.out.println("|-----------------------------------|");

        System.out.println("Digite uma opção!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                usuario.cadastrarUsuario();
                break;
            case 2:
                usuario.visualizarListaDeUsuarios();
                break;
            case 3:
                usuario.atualizarUsuario();
                break;
            case 4:
                usuario.excluirUsuario();
                break;
            case 5:
                mostrarMenuPrincipal();
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 3!");
                break;
        }
    }

    private void mostrarMenuAluno() {
        limparConsole();
        System.out.println("|--------MENU DO ALUNO-----------|");
        System.out.println("| 1 - Cadastrar aluno            |");
        System.out.println("| 2 - Realizar matricula         |");
        System.out.println("| 3 - Visualizar Perfil          |");
        System.out.println("| 4 - Atualizar Informacoes      |");
        System.out.println("| 5 - Gerar relatorio desempenho |");
        System.out.println("| 6 - Cancelar Matricula         |");
        System.out.println("| 7 - Menu principal             |");
        System.out.println("|--------------------------------|");

        Aluno aluno = new Aluno(conexao);

        System.out.println("Digite uma opção!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                aluno.cadastrarAluno();
                break;
            case 2:
                aluno.realizarMatricula();
                break;
            case 3:
                aluno.visualizarPerfil();
                break;
            case 4:
                aluno.atualizarInformacoes();
                break;
            case 5:
                mostrarMenuRelatorioDesempenho();
                break;
            case 6:
                aluno.cancelarMatricula();
                break;
            case 7:
                mostrarMenuPrincipal();
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 7!");
                break;
        }
    }

    private void mostrarMenuRelatorioDesempenho() {
        limparConsole();
        Aluno aluno = new Aluno(conexao);
        System.out.println("|---------MENU RELATORIO DE DESEMPENHO---------|");
        System.out.println("| 1 - Exibir cursos concluidos                 |");
        System.out.println("| 2 - Exibir cursos matriculado                |");
        System.out.println("| 3 - Exibir porcentagem de aproveitamento     |");
        System.out.println("| 4 - Menu principal                           |");
        System.out.println("|----------------------------------------------|");

        System.out.println("Digite uma opcao!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                aluno.cursosConcluidos();
                break;
            case 2:
                aluno.visualizarPerfil();
                break;
            case 3:
                break;
            case 4:
                mostrarMenuPrincipal();
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 4!");
                break;
        }
    }

    private void mostrarMenuCurso() {
        limparConsole();
        Curso curso = new Curso(conexao);
        System.out.println("|-------------------MENU CURSO-------------------|");
        System.out.println("| 1 - Cadastrar Curso                            |");
        System.out.println("| 2 - Atualizar Curso                            |");
        System.out.println("| 3 - Visualizar Lista de Cursos                 |");
        System.out.println("| 4 - Registrar Notas                            |");
        System.out.println("| 5 - Gerar Estatiticas de desempenho dos Alunos |");
        System.out.println("| 6 - Menu principal                             |");
        System.out.println("|------------------------------------------------|");

        System.out.println("Digite uma opcao!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                curso.cadastrarCurso();
                break;
            case 2:
                curso.atualizarCurso();
                break;
            case 3:
                curso.visualizarListaDeCursos();
                break;
            case 4:
                curso.registrarNotas();
                break;
            case 5:
                curso.gerarEstatisticasDesempenho();
                break;
            case 6:
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 6!");
                break;
        }
    }

    private void mostrarMenuProfessor() {
        limparConsole();
        Professor professor = new Professor(conexao);
        System.out.println("|----------MENU DO PROFESSOR----------|");
        System.out.println("| 1 - Cadastrar professor(a)          |");
        System.out.println("| 2 - Associar Curso                  |");
        System.out.println("| 3 - Atualizar Informacoes           |");
        System.out.println("| 4 - Visualizar Lista de Professores |");
        System.out.println("| 5 - Menu principal                  |");
        System.out.println("|-------------------------------------|");

        System.out.println("Digite uma opção!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                professor.cadastroProfessor();
                break;
            case 2:
                professor.associarCurso();
                break;
            case 3:
                professor.atualizarInformacoes();
                break;
            case 4:
                professor.visualizarListaProfessores();
                break;
            case 5:
                mostrarMenuPrincipal();
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 5!");
                break;
        }
    }

    public void mostrarMenuAutenticacaoAutorizacao() {
        limparConsole();
        AutenticacaoAutorizacao autenticacaoAutorizacao = new AutenticacaoAutorizacao(conexao);

        System.out.println("|--------MENU DE AUTORIZACAO---------|");
        System.out.println("| 1 - Gerenciar Permissoes           |");
        System.out.println("| 2 - Menu principal                 |");
        System.out.println("|------------------------------------|");

        System.out.println("Digite uma opção!");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                autenticacaoAutorizacao.gerenciarPermissoes();
                break;
            case 2:
                mostrarMenuPrincipal();
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 2!");
                break;
        }
    }

    public static void limparConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
