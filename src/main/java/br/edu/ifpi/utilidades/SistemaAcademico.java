package br.edu.ifpi.utilidades;

import java.util.Scanner;

import br.edu.ifpi.conexaoBD.ConexaoBancoDeDados;
import br.edu.ifpi.entidades.Aluno;
import br.edu.ifpi.entidades.Curso;
import br.edu.ifpi.entidades.Professor;
import br.edu.ifpi.entidades.Usuario;
import br.edu.ifpi.enums.PapeisUsuario;
import br.edu.ifpi.seguranca.AutenticacaoAutorizacao;

public class SistemaAcademico extends ConexaoBancoDeDados {

    private Usuario usuario;
    private boolean continuar = true;

    public void carregarSistema(Usuario usuario) {
        this.usuario = usuario;
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
                sairDoSistema();
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
                sairDoSistema();
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
                sairDoSistema();
                break;
            default:
                System.out.println("Opcao invalida, tente novamente de 1 a 2!");
                break;
        }
    }

    private void mostrarMenuUsuario() {
        limparConsole();
        Usuario usuario = new Usuario();
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

        Aluno aluno = new Aluno();

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
                aluno.relatorioDesempenho();
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

    private void mostrarMenuCurso() {
        limparConsole();
        Curso curso = new Curso();
        System.out.println("|-------------------MENU CURSO-----------------|");
        System.out.println("| 1 - Cadastrar Curso                          |");
        System.out.println("| 2 - Atualizar Curso                          |");
        System.out.println("| 3 - Visualizar Lista de Cursos               |");
        System.out.println("| 4 - Registrar Notas                          |");
        System.out.println("| 5 - Gerar Estatiticas de desempenho do Aluno |");
        System.out.println("| 6 - Menu principal                           |");
        System.out.println("|----------------------------------------------|");

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
        Professor professor = new Professor();
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
        AutenticacaoAutorizacao autenticacaoAutorizacao = new AutenticacaoAutorizacao();

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

    public void sairDoSistema() {
        limparConsole();
        System.out.println("Voce saiu do sistema");
        this.continuar = false;
    }
}