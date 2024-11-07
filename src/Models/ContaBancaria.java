package Models;

import Exceptions.SaldoInsuficienteException;
import Interfaces.MovimentacaoBancaria;
import Util.Utilitarios;


// Classe abstrata que representa uma conta bancária genérica.
// Fornece os métodos básicos para operações bancárias como depósito e saque.
// Classes específicas de conta, como Conta Corrente e Conta Poupança, herdam desta classe.

public abstract class ContaBancaria implements MovimentacaoBancaria {

    // Número da conta bancária.
    private int numeroConta;

    // Saldo atual da conta.
    private double saldo;


//     Construtor da classe ContaBancaria.
//     Inicializa a conta com o número da conta e o saldo inicial.

//     parametro numeroConta Número da conta bancária.
//     parametro saldoInicial Saldo inicial da conta.

    public ContaBancaria(int numeroConta, double saldoInicial) {
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
    }


//     Retorna o número da conta.

    public int getNumeroConta() {
        return numeroConta;
    }


     // Retorna o saldo atual da conta.

    public double getSaldo() {
        return saldo;
    }


     // Define um novo saldo para a conta.

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


//      Realiza um depósito na conta, adicionando o valor especificado ao saldo atual.
//      Imprime uma mensagem de sucesso no console se o valor for positivo.

    @Override
    public void depositar(double valor) {
        if (valor > 0) {
            setSaldo(getSaldo() + valor);
            System.out.println("Depósito realizado com sucesso! Saldo atual: " + saldo);
        } else {
            System.out.println("Não foi possível realizar o depósito!");
        }
    }


//    Realiza um saque na conta, subtraindo o valor especificado do saldo atual.
//    Lança uma exceção SaldoInsuficienteException caso o saldo seja insuficiente ou o valor seja inválido.

//    parametro valor Valor a ser sacado.
//    @throws SaldoInsuficienteException Se o saldo for insuficiente para o saque.

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > 0 && this.getSaldo() >= valor) {
            setSaldo(getSaldo() - valor);
            System.out.println("Saque realizado com sucesso. Saldo atual: " + Utilitarios.doubleToString(saldo));
        } else {
            throw new SaldoInsuficienteException("Não foi possível realizar o saque!");
        }
    }


//     Retorna uma representação textual da conta bancária, incluindo o número da conta e o saldo atual.
//     returna Uma string contendo o número da conta e o saldo formatado.
    @Override
    public String toString() {
        return "Número da Conta: " + numeroConta + "\nSaldo: " + Utilitarios.doubleToString(saldo) + "\n";
    }
}
