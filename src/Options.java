import java.util.Scanner;

public class Options {

    public void optionsHotel() {
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
        scanner.close();
    }
}
