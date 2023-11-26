package br.edu.ifpi;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        SistemaAcademico.limparConsole();
        System.out.println("Aguarde, conectando com o banco de dados!");
        Connection conexao = ConexaoBancoDeDados.connectar();

        AutenticacaoAutorizacao autenticacaoAutorizacao = new AutenticacaoAutorizacao(conexao);
        while (true) {
            if (autenticacaoAutorizacao.autenticarUsuario()) {
                Usuario usuario = new Usuario(autenticacaoAutorizacao.getTipoUsuario(), conexao);
                SistemaAcademico sistemaAcademico = new SistemaAcademico();
                sistemaAcademico.carregarSistema(usuario, conexao);
                
            } else {
            System.out.println("Erro email ou senha nao conferem!");
            }
       }
    }
}
