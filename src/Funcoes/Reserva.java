package Funcoes;

import POO.Hospede;
import POO.Quarto;

import java.sql.*;
import java.util.Scanner;

public class Reserva {

    private final Hospede hospede = new Hospede();
    private final Quarto quarto = new Quarto();
    private final Scanner scanner = new Scanner(System.in);
    private final Conexao conexao = new Conexao();
    public void criarReserva() {
        conexao.connection();
        Connection c = conexao.getConnection();
        System.out.println("Digite o ID da pessoa que fará a reserva: ");
        try {
            String sql = "SELECT id, nomePessoa FROM pessoa";
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);

            int i = 1;
            while (rs.next()) {
                String pessoa = rs.getString("nomePessoa");
                int id = rs.getInt("id");
                System.out.println(i + ". " + pessoa + " (ID: " + id + ")");
                i++;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("");
            int option = scanner.nextInt() - 1;
            rs.absolute(option + 1);

            String pessoaSelecionada = rs.getString("nomePessoa");
            hospede.setNome(pessoaSelecionada);
            System.out.println("Você selecionou: " + pessoaSelecionada);

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

        String dataFormatada = null;
        while (dataFormatada == null) {
            System.out.println("Digite a data da reserva no formato dd/MM/yyyy:");
            String dataString = scanner.nextLine();
            dataFormatada = FormatarData.formatarData(dataString);

            if (dataFormatada == null) {
                System.out.println("Data invalida. Por favor, digite a data novamente.");
            }
        }
        hospede.setData(dataFormatada);
        System.out.println(dataFormatada);

        int opcao;
        do {
            System.out.println("Selecione uma opção de cama:");
            System.out.println("1. Solteiro");
            System.out.println("2. Casal");
            System.out.println("3. Queen");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Você selecionou a opção 1");
                    quarto.setTipoCama("Solteiro");
                    break;
                case 2:
                    System.out.println("Você selecionou a opção 2");
                    quarto.setTipoCama("Casal");
                    break;
                case 3:
                    System.out.println("Você selecionou a opção 3");
                    quarto.setTipoCama("Queen");
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, selecione uma opção válida.");
                    break;
            }
        } while (opcao < 1 || opcao > 3);

        int opcao2;
        do {
            System.out.println("Selecione uma opção de quarto:");
            System.out.println("1. Suite Plus(Não aceita animais)");
            System.out.println("2. Suite Grande(Aceita animais)");
            System.out.println("3. Suite Master(Aceita animais)");
           opcao2 = scanner.nextInt();

            switch (opcao2) {
                case 1:
                    System.out.println("Você selecionou a opção 1");
                    quarto.setTipoQuarto("Suite Plus");
                    quarto.setAceitaAnimais(false);
                    break;
                case 2:
                    System.out.println("Você selecionou a opção 2");
                    quarto.setTipoQuarto("Suite Grande");
                    quarto.setAceitaAnimais(true);
                    break;
                case 3:
                    System.out.println("Você selecionou a opção 3");
                    quarto.setTipoQuarto("Suite Master");
                    quarto.setAceitaAnimais(true);
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, selecione uma opção válida.");
                    break;
            }
        } while(opcao2 < 1 || opcao2 > 3);

        int opcao3;
        do {
            System.out.println("Precisa de serviço de quarto?");
            System.out.println("1. Sim");
            System.out.println("2. Não");
            opcao3 = scanner.nextInt();

            switch (opcao3) {
                case 1:
                    System.out.println("Você selecionou a opção 1");
                    quarto.setServicoDeQuarto(true);
                    break;
                case 2:
                    System.out.println("Você selecionou a opção 2");
                    quarto.setServicoDeQuarto(false);
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, selecione uma opção válida.");
                    break;
            }
        } while(opcao3 < 1 || opcao3 > 3);

        int opcao4;
        do {
            System.out.println("Ira precisar de garagem?");
            System.out.println("1. Sim");
            System.out.println("2. Não");
            opcao4 = scanner.nextInt();
            switch (opcao4) {
                case 1:
                    System.out.println("Você selecionou a opção 1");
                    hospede.setGaragem("Sim");
                    break;
                case 2:
                    System.out.println("Você selecionou a opção 2");
                    hospede.setGaragem("Não");
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, selecione uma opção válida.");
                    break;
            }
        }while(opcao4 < 1 || opcao4 > 2);

        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO database.hospede (nomeHospede, dataReserva, tipoQuarto, servicoDeQuarto, tipoCama, garagem, aceitaAnimais) VALUE (? ,?, ?, ?, ?, ?, ?)");
            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getData());
            stmt.setString(3, quarto.getTipoQuarto());
            stmt.setBoolean(4, quarto.getServicoDeQuarto());
            stmt.setString(5, quarto.getTipoCama());
            stmt.setString(6, hospede.getGaragem());
            stmt.setBoolean(7, quarto.isAceitaAnimais());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Funções.Reserva de " + hospede.getNome() + " para a data " + hospede.getData() + " realizado com sucesso!");
                conexao.closeConnection();
            } else {
                System.out.println("Erro ao cadastrar");
                conexao.closeConnection();
            }
        } catch (SQLException e) {
            System.out.println("Não foi possivel salvar os dados no banco de dados." + e);
            throw new RuntimeException(e);
        }
    }
}
