package br.edu.ifpi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {

    private static final String url = "jdbc:postgresql://db.lpxbjoetgnrnjcurvqdy.supabase.co/postgres";
    private static final String user = "postgres";
    private static final String password = "carolina@##153!SISTEMA";
    private static Connection conexao = null;

    public static Connection connectar() {
        if (conexao == null) {
            try {
                conexao = DriverManager.getConnection(url, user, password);
                SistemaAcademico.limparConsole();
                System.out.println("Conectado com o banco de dados com sucesso!");
            } catch (SQLException e) {
                SistemaAcademico.limparConsole();
                System.out.println("Falha ao conectar com o banco de dados!");
            }
        }
        return conexao;
    }
}
