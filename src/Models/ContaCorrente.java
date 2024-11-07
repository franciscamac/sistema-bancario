package Models;

import Exceptions.SaldoInsuficienteException;
import Util.Utilitarios;

public class ContaCorrente extends ContaBancaria {
    private double limiteChequeEspecial;

    public ContaCorrente(int numeroConta,double saldoInicial ,double limiteChequeEspecial) {
        super(numeroConta, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > 0 && (getSaldo() + limiteChequeEspecial) >= valor) {
            setSaldo(getSaldo() - valor);
            System.out.println("Saque de Conta Corrente realizado com sucesso! Saldo atual: " + Utilitarios.doubleToString(getSaldo()));
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente ou valor inválido para saque!");
        }
    }
    @Override
    public String toString() {
        return super.toString() + "Limite Cheque Especial: " + Utilitarios.doubleToString(limiteChequeEspecial) + "\n";
    }
}
