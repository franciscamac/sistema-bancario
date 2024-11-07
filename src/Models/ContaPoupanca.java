package Models;

import Exceptions.SaldoInsuficienteException;
import Util.Utilitarios;

public class ContaPoupanca extends ContaBancaria {
    private double taxaRendimento;

    public ContaPoupanca(int numeroConta,double saldoInicial, double taxaRendimento) {
        super(numeroConta, saldoInicial);
        this.taxaRendimento = taxaRendimento;
    }

    public double calcularRendimento() {
        return getSaldo() * taxaRendimento;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > 0 && getSaldo() >= valor) {
            setSaldo(getSaldo() - valor);
            System.out.println("Saque de Conta Poupança realizado com sucesso! Saldo atual: " + Utilitarios.doubleToString(getSaldo()));
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente para o saque!");
        }
    }
    @Override
    public String toString() {
        return super.toString() + "Taxa de Rendimento: " + Utilitarios.doubleToString(calcularRendimento()) + "\n";
    }
}
