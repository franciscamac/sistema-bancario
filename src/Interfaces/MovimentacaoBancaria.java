package Interfaces;

import Exceptions.SaldoInsuficienteException;
//Interface que define os métodos de movimentação bancária para depósitos e saques.
//Qualquer classe que implementar essa interface deverá fornecer implementações para as operações de depósito e saque em uma conta bancária.

public interface MovimentacaoBancaria {
    void depositar(double valor);
    void sacar(double valor) throws SaldoInsuficienteException;
}
