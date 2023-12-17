package br.edu.ifpi;

import br.edu.ifpi.Dao.ConexaoDao;
import br.edu.ifpi.entidades.Usuario;
import br.edu.ifpi.seguranca.AutenticacaoAutorizacao;
import br.edu.ifpi.utilidades.SistemaAcademico;

public class Main {
    public static void main(String[] args) {

        ConexaoDao.getConexao();
        AutenticacaoAutorizacao autenticacaoAutorizacao = new AutenticacaoAutorizacao();
        while (true) {
            Usuario usuario = autenticacaoAutorizacao.autenticarUsuario();
            if (usuario != null) {
                SistemaAcademico sistemaAcademico = new SistemaAcademico();
                sistemaAcademico.carregarSistema(usuario);
            } else {
                System.out.println("Erro email ou senha nao conferem!");
            }
        }
    }
}
