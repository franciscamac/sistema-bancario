package Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utilitarios {
    static NumberFormat valoresFormatados = new DecimalFormat("R$ #,##0.00");

    public static String doubleToString(double valor) {
        return valoresFormatados.format(valor);
    }
}
