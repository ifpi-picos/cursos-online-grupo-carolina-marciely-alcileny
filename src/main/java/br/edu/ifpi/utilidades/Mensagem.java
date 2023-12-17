package br.edu.ifpi.utilidades;

import java.util.Scanner;

public class Mensagem {

    public final String SUCESSO = "Sucesso";
    public final String ERRO = "Erro";

    public void imprimirMenssagemDeCadastro(String status, String tipo) {
        if (status.equals(SUCESSO)) {
            System.out.println("|-----------------------------------|");
            System.out.println("  " + tipo + " cadastrado com sucesso! ");
            System.out.println("|-----------------------------------|");
        } else {
            System.out.println("|-------------------------------|");
            System.out.println("| Erro ao realizar cadastrado!  |");
            System.out.println("|-------------------------------|");
        }
        pausar();
    }

    public void imprimirNenhumCursoAbertoEnc() {
        System.out.println("|-------------------------------------------|");
        System.out.println("| Nao foi encontrado nenhum curso ABERTO    |");
        System.out.println("| Que o aluno nao esteja matriculado!       |");
        System.out.println("|-------------------------------------------|");
        pausar();
    }

    public void imprimirNenhumCursoAssociadoEnc() {
        System.out.println("|-------------------------------------------|");
        System.out.println("| Nao foi encontrado nenhum curso           |");
        System.out.println("| Que o professor nao esteja associado!     |");
        System.out.println("|-------------------------------------------|");
        pausar();
    }

    public void imprimirNenhumCursoMatriculadoEnc() {
        System.out.println("|---------------------------------------|");
        System.out.println("| Não está matriculado em nenhum curso! |");
        System.out.println("|---------------------------------------|");
        pausar();
    }

    public void imprimirAssociarProfessorACurso() {
        System.out.println("|---------------------------------------|");
        System.out.println("| Vamos associar o professor a um curso!|");
        System.out.println("|---------------------------------------|");
        pausar();
    }

    public void imprimirNenhumProfessorAssociadoEnc() {
        System.out.println("|-------------------------------------------------|");
        System.out.println("| Nenhum professor associado a cursos encontrado! |");
        System.out.println("|-------------------------------------------------|");
        pausar();
    }

    public void imprimirMenssagemDeAtualizacao(String status, String tipo) {
        if (status.equals(SUCESSO)) {
            System.out.println("|------------------------------------|");
            System.out.println("  " + tipo + " atualizado com sucesso!");
            System.out.println("|------------------------------------|");
        } else {
            System.out.println("|------------------------------------|");
            System.out.println("| Erro ao atualizar informacoes!     |");
            System.out.println("|------------------------------------|");
        }
        pausar();
    }

    public void imprimirMenssagemDeAssociacao(String status, String tipo) {
        if (status.equals(SUCESSO)) {
            System.out.println("|------------------------------------|");
            System.out.println("  " + tipo + " associado com sucesso!");
            System.out.println("|------------------------------------|");
        } else {
            System.out.println("|------------------------------------|");
            System.out.println("| Erro ao associar informacoes!     |");
            System.out.println("|------------------------------------|");
        }
        pausar();
    }

    public void imprimirMenssagemDeExclusao(String status, String tipo) {
        if (status.equals(SUCESSO)) {
            System.out.println("|-------------------------------------|");
            System.out.println("  " + tipo + " excluido com sucesso!       ");
            System.out.println("|-------------------------------------|");
        } else {
            System.out.println("|-------------------------------------|");
            System.out.println("| Erro ao excluir informacoes!        |");
            System.out.println("|-------------------------------------|");
        }
        pausar();
    }

    public void imprimirMenssagemDeCancelamento(String status, String tipo) {
        if (status.equals(SUCESSO)) {
            System.out.println("|-------------------------------------|");
            System.out.println("  " + tipo + " cancelado com sucesso!       ");
            System.out.println("|-------------------------------------|");
        } else {
            System.out.println("|-------------------------------------|");
            System.out.println("| Erro ao cancelar informacoes!       |");
            System.out.println("|-------------------------------------|");
        }
        pausar();
    }

    public void imprimirMenssagemDeConclusao(String status) {
        if (status.equals(SUCESSO)) {
            System.out.println("|-------------------------------------|");
            System.out.println("| Curso concluido com sucesso!        |");
            System.out.println("|-------------------------------------|");
        } else {
            System.out.println("|-------------------------------------|");
            System.out.println("| Erro ao concluir curso!             |");
            System.out.println("|-------------------------------------|");
        }
        pausar();
    }

    public void imprimirErroAoConsultar() {
        System.out.println("|----------------------------------|");
        System.out.println("| Erro ao realizar consulta!       |");
        System.out.println("|----------------------------------|");
        pausar();
    }

    public void imprimirErroAoCarregarDados(String tipo) {
        System.out.println("|----------------------------------------------|");
        System.out.println("  Erro ao carregar dados do " + tipo + "!");
        System.out.println("|----------------------------------------------|");
        pausar();
    }

    public void imprimirMensagemNenhumDado(String tipo) {
        System.out.println("|-----------------------------------|");
        System.out.println("  Nenhum " + tipo + " encontrado!");
        System.out.println("|-----------------------------------|");
        pausar();
    }

    public static void pausar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter para ir para o menu principal");
        scanner.nextLine();
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
