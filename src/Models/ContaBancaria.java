package Models;

import Exceptions.SaldoInsuficienteException;
import Interfaces.MovimentacaoBancaria;
import Util.Utilitarios;


public abstract class ContaBancaria implements MovimentacaoBancaria {
    private int numeroConta;
    private double saldo;

    public ContaBancaria(int numeroConta, double saldoInicial) {
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    @Override
    public void depositar(double valor) {
        if(valor > 0){
            setSaldo(getSaldo() + valor);
            System.out.println("Depósito realizado com sucesso! Saldo atual: " + saldo);
        }else {
            System.out.println("Não foi possivel realizar o deposito!");
        }
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > 0 && this.getSaldo() >= valor) {
            setSaldo(getSaldo() - valor);
            System.out.println("Saque realizado com sucesso. Saldo atual: " + Utilitarios.doubleToString(saldo));
        } else {
            throw new SaldoInsuficienteException("Não foi possivel realizar o saque!");
        }
    }
    @Override
    public String toString() {
        return "Número da Conta: " + numeroConta + "\nSaldo: " + Utilitarios.doubleToString(saldo) + "\n";
    }
}
