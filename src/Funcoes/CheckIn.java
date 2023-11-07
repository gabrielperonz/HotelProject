package Funcoes;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class CheckIn {

    private final Conexao conexao = new Conexao();

    public void checkIn() {
        conexao.connection();
        Connection c = conexao.getConnection();
        LocalDate dataAtual = LocalDate.now();
        Scanner scanner = new Scanner(System.in);
        boolean idEncontrado = false;
        do {
            try {
                String sql = "SELECT idHospede, nomeHospede FROM hospede WHERE dataCheckIn IS NULL";
                Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(sql);

                int i = 1;
                while (rs.next()) {
                    String pessoa = rs.getString("nomeHospede");
                    int id = rs.getInt("idHospede");
                    System.out.println(i + ". " + pessoa + " (ID: " + id + ")");
                    i++;
                }

                int option = scanner.nextInt() - 1;
                System.out.print("");

                rs.beforeFirst();
                while (rs.next()) {
                    int id = rs.getInt("idHospede");
                    if (option == id - 1) {
                        String pessoaSelecionada = rs.getString("nomeHospede");
                        System.out.println("Você selecionou: " + pessoaSelecionada);

                        PreparedStatement stmtout = c.prepareStatement("UPDATE hospede SET dataCheckIn = ? WHERE idHospede = ?;");
                        stmtout.setDate(1, Date.valueOf(dataAtual));
                        stmtout.setInt(2, id);
                        int linhas = stmtout.executeUpdate();
                        if (linhas > 0) {
                            System.out.println("Check-In de " + pessoaSelecionada + " realizado com sucesso!");
                            conexao.closeConnection();
                        } else {
                            System.out.println("Erro ao fazer Check-In");
                            conexao.closeConnection();
                        }
                        idEncontrado = true;
                        break;
                    }
                }
                if (!idEncontrado) {
                    System.out.println("ID invalido. Por favor, digite o ID novamente.");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } while (!idEncontrado);
    }
}
