package br.edu.ifpi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {

    private static final String url = "jdbc:postgresql://monorail.proxy.rlwy.net:17620/railway";
    private static final String user = "postgres";
    private static final String password = "C2cdd56gCCb43de*aFgaCBDCB65Ce63B";
    private static Connection conexao = null;

    public static Connection connectar() {
        if (conexao == null) {
            try {
                conexao = DriverManager.getConnection(url, user, password);
                System.out.println("Conectado com o banco de dados com sucesso");
            } catch (SQLException e) {
                System.out.println("Falha ao conectar com o banco de dados");
            }
        }
        return conexao;
    }
}
