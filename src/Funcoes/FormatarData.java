package Funcoes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormatarData {
    public static String formatarData(String dataString) {
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate data = LocalDate.parse(dataString, formatoEntrada);
            return data.format(formatoSaida);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
