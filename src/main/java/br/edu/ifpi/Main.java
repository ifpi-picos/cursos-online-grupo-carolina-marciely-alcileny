package br.edu.ifpi;

import br.edu.ifpi.conexaoBD.ConexaoBancoDeDados;
import br.edu.ifpi.entidades.Usuario;
import br.edu.ifpi.seguranca.AutenticacaoAutorizacao;
import br.edu.ifpi.utilidades.SistemaAcademico;

public class Main{
    public static void main(String[] args) {
        
        ConexaoBancoDeDados.connectar();
        AutenticacaoAutorizacao autenticacaoAutorizacao = new AutenticacaoAutorizacao();
        while (true) {
            if (autenticacaoAutorizacao.autenticarUsuario()) {
                Usuario usuario = new Usuario(autenticacaoAutorizacao.getTipoUsuario());
                SistemaAcademico sistemaAcademico = new SistemaAcademico();
                sistemaAcademico.carregarSistema(usuario);
                
            } else {
            System.out.println("Erro email ou senha nao conferem!");
            }
       }
    }
}
