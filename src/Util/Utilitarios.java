package Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;


// Classe utilitária para formatar valores monetários em reais (R$).
// A classe fornece um método para converter valores do tipo double para uma representação de string formatada em reais.

public class Utilitarios {

    // Formata valores monetários com duas casas decimais e símbolo de moeda R$
    static NumberFormat valoresFormatados = new DecimalFormat("R$ #,##0.00");


//     Converte um valor do tipo double para uma string formatada em reais.
//     returna String representando o valor formatado com o símbolo de reais (R$) e duas casas decimais.
    public static String doubleToString(double valor) {
        return valoresFormatados.format(valor);
    }
}
