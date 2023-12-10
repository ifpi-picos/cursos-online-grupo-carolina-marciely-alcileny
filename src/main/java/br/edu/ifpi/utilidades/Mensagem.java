package br.edu.ifpi.utilidades;

import java.util.Scanner;

public abstract class Mensagem {

    protected final String SUCESSO = "Sucesso";
    protected final String ERRO = "Erro";

    public void imprimirMenssagemDeCadastro(String status, String tipo) {
        if (status.equals(SUCESSO)) {
            System.out.println("|-----------------------------------|");
            System.out.println( tipo + " cadastrado com sucesso! ");
            System.out.println("|-----------------------------------|");
        } else {
            System.out.println("|-------------------------------|");
            System.out.println("| Erro ao realizar cadastrado!  |");
            System.out.println("|-------------------------------|");
        }
    }

    public void imprimirMenssagemDeAtualizacao(String status) {
        if (status.equals(SUCESSO)) {
            System.out.println("|------------------------------------|");
            System.out.println("|Informacoes atualizado com sucesso! |");
            System.out.println("|------------------------------------|");
        } else {
            System.out.println("|------------------------------------|");
            System.out.println("| Erro ao atualizar informacoes!     |");
            System.out.println("|------------------------------------|");
        }
    }

    public void imprimirMenssagemDeExclusao(String status, String tipo) {
        if (status.equals(SUCESSO)) {
            System.out.println("|-------------------------------------|");
            System.out.println( tipo + " excluido com sucesso!       ");
            System.out.println("|-------------------------------------|");
        } else {
            System.out.println("|-------------------------------------|");
            System.out.println("| Erro ao excluir informacoes!        |");
            System.out.println("|-------------------------------------|");
        }
    }

    public void imprimirErroAoConsultar() {
        System.out.println("|----------------------------------|");
        System.out.println("| Erro ao realizar consulta!       |");
        System.out.println("|----------------------------------|");
    }

    public void imprimirErroAoCarregarDados(String tipo) {
        System.out.println("|----------------------------------------------|");
        System.out.println(" Erro ao carregar dados do " + tipo + "!");
        System.out.println("|----------------------------------------------|");
    }


    public void imprimirNenhumDado(String tipo) {
        System.out.println("|-----------------------------------|");
        System.out.println(" Nenhum "+ tipo +" encontrado!");
        System.out.println("|-----------------------------------|");
    }

    public void pausar() {
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
