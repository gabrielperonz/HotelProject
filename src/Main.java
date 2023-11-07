import Funcoes.Options;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static void main(String[] args) {
        Options options = new Options();
        createTablesIfNotExists();
        options.optionsHotel();
    }

    private static void createTablesIfNotExists() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String tableNamePessoa = "pessoa";
            String tableNameHospede = "hospede";
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, tableNamePessoa, null);
            if (!tables.next()) {
                String sql1 = "CREATE TABLE pessoa " +
                        " (id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        " nomePessoa VARCHAR(255), " +
                        " cpf VARCHAR(11), " +
                        " dataNascimento DATE)";
                try (Statement stmt1 = conn.createStatement()) {
                    stmt1.executeUpdate(sql1);
                    System.out.println("Tabela pessoa criada com sucesso");
                }
            }

            tables = dbmd.getTables(null, null, tableNameHospede, null);
            if (!tables.next()) {
                String sql2 = "CREATE TABLE hospede " +
                        " (idHospede INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        " nomeHospede VARCHAR(255), " +
                        " dataReserva DATE, " +
                        " garagem VARCHAR(20), " +
                        " tipoQuarto VARCHAR(45), " +
                        " servicoDeQuarto TINYINT(4), " +
                        " tipoCama VARCHAR(45), " +
                        " aceitaAnimais TINYINT(4), " +
                        " dataCheckIn DATE, " +
                        " dataCheckOut DATE)";
                try (Statement stmt2 = conn.createStatement()) {
                    stmt2.executeUpdate(sql2);
                    System.out.println("Tabela hospede criada com sucesso");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
