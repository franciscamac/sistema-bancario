package Models;

import Exceptions.SaldoInsuficienteException;
import Util.Utilitarios;


//Classe que representa uma conta corrente, um tipo específico de conta bancária.
//Possui um limite de cheque especial, que permite saques que ultrapassam o saldo, até o valor do limite.

public class ContaCorrente extends ContaBancaria {

    // Limite do cheque especial da conta corrente.
    private double limiteChequeEspecial;


     // Construtor da classe ContaCorrente.
     //Inicializa a conta com o número da conta, saldo inicial e limite do cheque especial.
    public ContaCorrente(int numeroConta, double saldoInicial, double limiteChequeEspecial) {
        super(numeroConta, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }


//     Realiza um saque na conta corrente, considerando o limite do cheque especial.
//     Permite o saque caso o valor seja positivo e não exceda o saldo disponível, incluindo o limite.
//     Lança uma exceção SaldoInsuficienteException caso o saldo, somado ao limite, seja insuficiente.

//    paramametro valor Valor a ser sacado.
//     @throws SaldoInsuficienteException Se o saldo e o limite forem insuficientes para o saque.

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > 0 && (getSaldo() + limiteChequeEspecial) >= valor) {
            setSaldo(getSaldo() - valor);
            System.out.println("Saque de Conta Corrente realizado com sucesso! Saldo atual: " + Utilitarios.doubleToString(getSaldo()));
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente ou valor inválido para saque!");
        }
    }


//    Retorna uma representação textual da conta corrente, incluindo o número da conta,
//    saldo atual e o limite do cheque especial.
//        returna Uma string com as informações da conta corrente.

    @Override
    public String toString() {
        return super.toString() + "Limite Cheque Especial: " + Utilitarios.doubleToString(limiteChequeEspecial) + "\n";
    }
}
