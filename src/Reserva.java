import POO.Hospede;
import POO.Pessoa;
import POO.Quarto;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Reserva {
    Pessoa pessoa = new Pessoa();
    Hospede hospede = new Hospede();
    Quarto quarto = new Quarto();

    Scanner scanner = new Scanner(System.in);
    Connection c;

    {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "");
        } catch (SQLException e) {
            System.out.println("Não foi possivel acessar o banco de dados." + e);
            throw new RuntimeException(e);
        }
    }

    public void checkIn() {
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
        pessoa.setNome(nome);
        System.out.println("Digite a data de nascimendo no formato dd-MM-yyyy:");
        String dataString = scanner.nextLine();
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate data = LocalDate.parse(dataString, formatoEntrada);
        DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = data.format(formatoSaida);
        pessoa.setDataNascimento(dataFormatada);
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO database.pessoa (nomePessoa, dataNascimento) VALUE (?, ?)");
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getDataNascimento());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Check-in de " + pessoa.getNome() + " data de nascimento " + pessoa.getDataNascimento() + " realizado com sucesso!");
                c.close();
            } else {
                System.out.println("Erro ao cadastrar");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void criarReserva() {
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

        System.out.println("Digite a data da reserva no formato dd-MM-yyyy:");
        String dataString = scanner.nextLine();
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate data = LocalDate.parse(dataString, formatoEntrada);
        DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = data.format(formatoSaida);
        System.out.println(dataFormatada);
        hospede.setData(dataFormatada);

        System.out.println("Selecione uma opção de cama:");
        System.out.println("1. Solteiro");
        System.out.println("2. Casal");
        System.out.println("3. Queen");
        int opcao = scanner.nextInt();
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
                System.out.println("Opção inválida!");
                break;
        }
        System.out.println("Selecione uma opção de quarto:");
        System.out.println("1. Suite Plus(Não aceita animais)");
        System.out.println("2. Suite Grande(Aceita animais)");
        System.out.println("3. Suite Master(Aceita animais)");
        int opcao2 = scanner.nextInt();

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
                System.out.println("Opção inválida!");
                break;
        }

        System.out.println("Ira precisar de garagem?");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        int opcao3 = scanner.nextInt();
        switch (opcao3) {
            case 1:
                System.out.println("Você selecionou a opção 1");
                hospede.setGaragem("Sim");
                break;
            case 2:
                System.out.println("Você selecionou a opção 2");
                hospede.setGaragem("Não");
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO database.hospede (nomeHospede, dataReserva, tipoQuarto, tipoCama, garagem, aceitaAnimais) VALUE (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getData());
            stmt.setString(3, quarto.getTipoQuarto());
            stmt.setString(4, quarto.getTipoCama());
            stmt.setString(5, hospede.getGaragem());
            stmt.setBoolean(6, quarto.isAceitaAnimais());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Reserva de " + hospede.getNome() + " para a data " + hospede.getData() + " realizado com sucesso!");
                c.close();
            } else {
                System.out.println("Erro ao cadastrar");
            }
        } catch (SQLException e) {
            System.out.println("Não foi possivel salvar os dados no banco de dados." + e);
            throw new RuntimeException(e);
        }
    }

    public void checkOut() {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatoData);
            System.out.println("Para quem será o check-out");
            try {
                Statement stmt = c.createStatement();
                String sql = "SELECT nomeHospede, dataReserva FROM hospede";
                ResultSet rs = stmt.executeQuery(sql);
                int i = 1;
                while (rs.next()) {
                    String info1 = rs.getString("nomeHospede");
                    String info2 = rs.getString("dataReserva");
                    DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate data = LocalDate.parse(info2, formatoEntrada);
                    DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    System.out.println(i + ". " + info1 + " com a reserva para o dia " + formatoSaida.format(data));
                    i++;
                }
                System.out.print("Digite o nome da opção desejada: ");
                String nome = scanner.nextLine();
                try {
                    String sqlverificar = "SELECT * FROM hospede WHERE nomeHospede = ?";
                    PreparedStatement verificar = c.prepareStatement(sqlverificar);
                    verificar.setString(1, nome);
                    ResultSet rsverificar = verificar.executeQuery();
                    if (!rsverificar.next()) {
                        System.out.println("Esse nome não está registrado.");
                        System.exit(0);
                    } else {
                        PreparedStatement stmtout = c.prepareStatement("UPDATE hospede SET dataCheckout = ? WHERE nomeHospede = ?;");
                        stmtout.setDate(1, Date.valueOf(dataAtual));
                        stmtout.setString(2, nome);
                        int linhas = stmtout.executeUpdate();
                        if (linhas > 0) {
                            System.out.println("Checkout de " + nome + " realizado com sucesso!");
                            c.close();
                        } else {
                            System.out.println("Erro ao cadastrar");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
