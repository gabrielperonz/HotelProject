package Funcoes;

import POO.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CheckIn {
    private final Pessoa pessoa = new Pessoa();
    private final Scanner scanner = new Scanner(System.in);
    private final Conexao conexao = new Conexao();
    public void checkIn() {
        conexao.connection();
        Connection c = conexao.getConnection();
        System.out.println("Qual o nome completo da pessoa?");
        String nome = scanner.nextLine();
        String sql = "SELECT * FROM pessoa WHERE nomePessoa = ?";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Esse nome já está registrado.");
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dataFormatada = null;
        while (dataFormatada == null) {
            System.out.println("Digite a data de nascimento no formato dd/MM/yyyy:");
            String dataString = scanner.nextLine();
            dataFormatada = FormatarData.formatarData(dataString);
            if (dataFormatada == null) {
                System.out.println("Data invalida. Por favor, digite a data novamente.");
            }
        }
        pessoa.setDataNascimento(dataFormatada);
        pessoa.setNome(nome);
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO database.pessoa (nomePessoa, dataNascimento) VALUE (?, ?)");
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getDataNascimento());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Check-in de " + pessoa.getNome() + " data de nascimento " + pessoa.getDataNascimento() + " realizado com sucesso!");
                conexao.closeConnection();
            } else {
                System.out.println("Erro ao cadastrar");
                conexao.closeConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
