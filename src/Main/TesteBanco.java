package Main;

import Models.ContaCorrente;
import Models.ContaPoupanca;
import Exceptions.SaldoInsuficienteException;

public class TesteBanco {
    public static void main(String[] args) {
        ContaCorrente contaCorrente = new ContaCorrente(1, 500.0, 700);
        ContaPoupanca contaPoupanca = new ContaPoupanca(2, 200, 0.05 );

        try {
            contaCorrente.depositar(1000);
            contaCorrente.sacar(2000); // Deve permitir com cheque especial
            contaPoupanca.depositar(500);
            contaPoupanca.sacar(100);
            double rendimento = contaPoupanca.calcularRendimento();
            System.out.println("Rendimento da conta poupança: " + rendimento);
        } catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }
    }
}
