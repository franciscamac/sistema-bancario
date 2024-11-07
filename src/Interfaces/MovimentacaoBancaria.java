package Interfaces;

import Exceptions.SaldoInsuficienteException;

public interface MovimentacaoBancaria {
    void depositar(double valor);
    void sacar(double valor) throws SaldoInsuficienteException;
}
