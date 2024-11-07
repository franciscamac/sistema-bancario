package Models;

import Exceptions.SaldoInsuficienteException;
import Util.Utilitarios;


//  Classe que representa uma conta poupança, um tipo específico de conta bancária
//  com rendimento sobre o saldo, baseado em uma taxa de rendimento.

public class ContaPoupanca extends ContaBancaria {

    // Taxa de rendimento da conta poupança.
    private double taxaRendimento;


//        Construtor da classe ContaPoupanca.
//        Inicializa a conta com o número da conta, saldo inicial e taxa de rendimento.
//    parametro numeroConta Número da conta poupança.
//    parametro saldoInicial Saldo inicial da conta.
//    parametro taxaRendimento Taxa de rendimento aplicada ao saldo.

    public ContaPoupanca(int numeroConta, double saldoInicial, double taxaRendimento) {
        super(numeroConta, saldoInicial);
        this.taxaRendimento = taxaRendimento;
    }


//      Calcula o rendimento atual da conta com base no saldo e na taxa de rendimento.
//      returna O valor do rendimento calculado.

    public double calcularRendimento() {
        return getSaldo() * taxaRendimento;
    }


//      Realiza um saque na conta poupança.
//      Permite o saque caso o valor solicitado seja positivo e não exceda o saldo disponível.
//      Lança uma exceção SaldoInsuficienteException caso o saldo seja insuficiente.

//     @throws SaldoInsuficienteException Se o saldo for insuficiente para o saque.

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > 0 && getSaldo() >= valor) {
            setSaldo(getSaldo() - valor);
            System.out.println("Saque de Conta Poupança realizado com sucesso! Saldo atual: " + Utilitarios.doubleToString(getSaldo()));
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente para o saque!");
        }
    }

//     Retorna uma representação textual da conta poupança, incluindo o número da conta,
//     saldo atual e o rendimento calculado com base na taxa de rendimento.
//     returna Uma string com as informações da conta poupança.
    @Override
    public String toString() {
        return super.toString() + "Taxa de Rendimento: " + Utilitarios.doubleToString(calcularRendimento()) + "\n";
    }
}
