import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/database";
        String user = "root";
        String password = "";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);

            String tableName = "pessoa";
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, tableName, null);
            if (!tables.next()) {
                String sql1 = "CREATE TABLE pessoa " +
                        " (id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        " nomePessoa VARCHAR(255), " +
                        " dataNascimento DATE)";
                Statement stmt1 = conn.createStatement();
                stmt1.executeUpdate(sql1);
                System.out.println("Tabela pessoa criada com sucesso");

                String sql2 = "CREATE TABLE hospede " +
                        " (idHospede INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        " nomeHospede VARCHAR(255), " +
                        " dataReserva DATE, " +
                        " garagem VARCHAR(20), " +
                        " tipoQuarto VARCHAR(45), " +
                        " tipoCama VARCHAR(45), " +
                        " aceitaAnimais TINYINT(4), " +
                        " dataCheckout DATE)";
                Statement stmt2 = conn.createStatement();
                stmt2.executeUpdate(sql2);
                System.out.println("Tabela hospede criada com sucesso");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        Reserva reserva = new Reserva();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecione uma opção:");
        System.out.println("1. Check-in");
        System.out.println("2. Check-out");
        System.out.println("3. Reserva");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Você selecionou a opção 1");
                reserva.checkIn();
                break;
            case 2:
                System.out.println("Você selecionou a opção 2");
                reserva.checkOut();
                break;
            case 3:
                System.out.println("Você selecionou a opção 3");
                reserva.criarReserva();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}
