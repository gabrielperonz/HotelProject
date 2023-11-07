package Funcoes;

import java.util.Scanner;

public class Options {

    public void optionsHotel() {
        Scanner scanner = new Scanner(System.in);
        Reserva Reserva = new Reserva();
        CheckOut CheckOut = new CheckOut();
        Cadastro Cadastro = new Cadastro();
        CheckIn CheckIn = new CheckIn();

        System.out.println("Selecione uma opção:");
        System.out.println("1. Cadastros");
        System.out.println("2. Check-in");
        System.out.println("3. Check-out");
        System.out.println("4. Reserva");

        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
                System.out.println("Você selecionou a opção 1");
                Cadastro.cadastro();
                break;
            case 2:
                System.out.println("Você selecionou a opção 2");
                CheckIn.checkIn();
                break;
            case 3:
                System.out.println("Você selecionou a opção 3");
                CheckOut.checkOut();
                break;
            case 4:
                System.out.println("Você selecionou a opção 4");
                Reserva.criarReserva();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
        scanner.close();
    }
}
