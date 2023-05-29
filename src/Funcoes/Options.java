package Funcoes;

import java.util.Scanner;

public class Options {

    public void optionsHotel() {
        Scanner scanner = new Scanner(System.in);
        Reserva reserva = new Reserva();
        CheckOut checkOut = new CheckOut();
        CheckIn checkIn = new CheckIn();

        System.out.println("Selecione uma opção:");
        System.out.println("1. Check-in");
        System.out.println("2. Check-out");
        System.out.println("3. Reserva");

        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
                System.out.println("Você selecionou a opção 1");
                checkIn.checkIn();
                break;
            case 2:
                System.out.println("Você selecionou a opção 2");
                checkOut.checkOut();
                break;
            case 3:
                System.out.println("Você selecionou a opção 3");
                reserva.criarReserva();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
        scanner.close();
    }
}
