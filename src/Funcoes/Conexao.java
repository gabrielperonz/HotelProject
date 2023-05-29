package Funcoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private Connection connection;

    public Conexao() {
        // Construtor vazio
    }

    public void connection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "");
        } catch (SQLException e) {
            System.out.println("Não foi possível acessar o banco de dados." + e);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados." + e);
            }
        }
    }
}
