package br.edu.ifpi.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConexaoDao{

    private static final String url = "jdbc:postgresql://db.lpxbjoetgnrnjcurvqdy.supabase.co/postgres";
    private static final String user = "postgres";
    private static final String password = "carolina@##153!SISTEMA";
    private static Connection conexao = null;

    public static Connection getConexao() {
        
        if (conexao == null) {
            System.out.println("Aguarde, conectando com o banco de dados!");
            try {
                conexao = DriverManager.getConnection(url, user, password);
                
                System.out.println("Conectado com o banco de dados com sucesso!");
            } catch (SQLException e) {
                
                System.out.println("Falha ao conectar com o banco de dados!");
            }
        }
        return conexao;
    }
}
