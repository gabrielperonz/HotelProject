package Funcoes;

import POO.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Cadastro {
    private final Pessoa pessoa = new Pessoa();
    private final Scanner scanner = new Scanner(System.in);
    private final Conexao conexao = new Conexao();

    public void cadastro() {
        String nome;
        String[] palavras;
        do {
            System.out.println("Qual o nome completo da pessoa?");
            nome = scanner.nextLine();
            palavras = nome.split("\\s+");

            if (palavras.length <= 1) {
                System.out.println("Por favor, insira um nome com mais de uma palavra.");
            }

        } while (palavras.length <= 1);

        conexao.connection();
        Connection c = conexao.getConnection();
        String sql = "SELECT * FROM pessoa WHERE nomePessoa = ?";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Esse nome já está registrado.");
                System.exit(0);
            }
            pessoa.setNome(nome);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String cpf = null;
        while (cpf == null) {
            System.out.println("Qual o CPF da pessoa?");
            cpf = scanner.nextLine();
            if (!validar(cpf)) {
                System.out.println("Cpf inválido, por gentileza digite novamente");
                cpf = null;
            }
            pessoa.setCpf(cpf);
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
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO database.pessoa (nomePessoa, cpf, dataNascimento) VALUE (?, ?, ?)");
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getDataNascimento());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Cadastro de " + pessoa.getNome() + " realizado com sucesso!");
                conexao.closeConnection();
            } else {
                System.out.println("Erro ao cadastrar");
                conexao.closeConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean validar(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || cpf.equals("00000000000") || cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444") ||
                cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false;
        }
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);
        if (digitoVerificador1 != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        return digitoVerificador2 == Character.getNumericValue(cpf.charAt(10));
    }

}
