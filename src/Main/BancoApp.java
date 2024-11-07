package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import Models.ContaCorrente;
import Models.ContaPoupanca;
import Exceptions.SaldoInsuficienteException;
import Util.Utilitarios; // Importa a classe Utilitarios

public class BancoApp {
    private JFrame frame;
    private JTextField numeroContaField;
    private JTextField valorField;
    private JTextField saldoInicialField;
    private JTextField limiteField; // Para contas correntes
    private JTextArea outputArea;
    private Map<Integer, ContaCorrente> contasCorrente;
    private Map<Integer, ContaPoupanca> contasPoupanca;
    private JRadioButton contaCorrenteRadioButton;
    private JRadioButton contaPoupancaRadioButton;

    public BancoApp() {
        // Inicializa o mapa de contas
        contasCorrente = new HashMap<>();
        contasPoupanca = new HashMap<>();

        // Inicializa a interface gráfica
        frame = new JFrame("Sistema Bancário");
        numeroContaField = new JTextField(15);
        valorField = new JTextField(10);
        saldoInicialField = new JTextField(10);
        limiteField = new JTextField(10);
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        // Botões de opção para escolher o tipo de conta
        contaCorrenteRadioButton = new JRadioButton("Conta Corrente");
        contaPoupancaRadioButton = new JRadioButton("Conta Poupança");
        ButtonGroup contaGroup = new ButtonGroup();
        contaGroup.add(contaCorrenteRadioButton);
        contaGroup.add(contaPoupancaRadioButton);
        contaCorrenteRadioButton.setSelected(true); // Seleciona a conta corrente por padrão

        JButton criarContaButton = new JButton("Criar Conta");
        JButton depositButton = new JButton("Depositar");
        JButton sacarButton = new JButton("Sacar");
        JButton extratoButton = new JButton("Imprimir Extrato");

        criarContaButton.addActionListener(e -> criarConta());
        depositButton.addActionListener(e -> realizarDeposito());
        sacarButton.addActionListener(e -> realizarSaque());
        extratoButton.addActionListener(e -> imprimirExtrato());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Número da Conta:"));
        panel.add(numeroContaField);
        panel.add(new JLabel("Saldo Inicial:"));
        panel.add(saldoInicialField);
        panel.add(new JLabel("Limite Cheque Especial:"));
        panel.add(limiteField); // Para ContaCorrente
        panel.add(contaCorrenteRadioButton);
        panel.add(contaPoupancaRadioButton);
        panel.add(new JLabel("Valor:"));
        panel.add(valorField);
        panel.add(criarContaButton);
        panel.add(depositButton);
        panel.add(sacarButton);
        panel.add(extratoButton);
        panel.add(new JScrollPane(outputArea));

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void criarConta() {
        int numeroConta;
        String saldoInicialText = saldoInicialField.getText();
        String limiteText = limiteField.getText();

        if (numeroContaField.getText().isEmpty() || saldoInicialText.isEmpty()) {
            outputArea.append("Erro: Número da conta e saldo inicial devem ser preenchidos.\n");
            return;
        }

        try {
            numeroConta = Integer.parseInt(numeroContaField.getText());
            if (numeroConta <= 0) {
                outputArea.append("Erro: O número da conta deve ser um valor positivo.\n");
                return;
            }
        } catch (NumberFormatException e) {
            outputArea.append("Erro: Número da conta deve ser um número válido.\n");
            return;
        }

        double saldoInicial;
        double limiteChequeEspecial = 0; // Inicializa o limite para conta corrente

        try {
            saldoInicial = Double.parseDouble(saldoInicialText);
            if (saldoInicial < 0) {
                outputArea.append("Erro: O saldo inicial deve ser um valor positivo.\n");
                return;
            }
            if (!limiteText.isEmpty()) {
                limiteChequeEspecial = Double.parseDouble(limiteText);
                if (limiteChequeEspecial < 0) {
                    outputArea.append("Erro: O limite do cheque especial deve ser um valor positivo.\n");
                    return;
                }
            }
        } catch (NumberFormatException e) {
            outputArea.append("Erro: Saldo inicial e limite devem ser números válidos.\n");
            return;
        }

        if (contaCorrenteRadioButton.isSelected()) {
            if (contasCorrente.containsKey(numeroConta)) {
                outputArea.append("Erro: Conta com este número já existe.\n");
            } else {
                contasCorrente.put(numeroConta, new ContaCorrente(numeroConta, saldoInicial, limiteChequeEspecial));
                outputArea.append("Conta Corrente " + numeroConta + " criada com sucesso.\n");
            }
        } else if (contaPoupancaRadioButton.isSelected()) {
            if (contasPoupanca.containsKey(numeroConta)) {
                outputArea.append("Erro: Conta com este número já existe.\n");
            } else {
                contasPoupanca.put(numeroConta, new ContaPoupanca(numeroConta, saldoInicial, 0.05)); // Taxa de rendimento padrão
                outputArea.append("Conta Poupança " + numeroConta + " criada com sucesso.\n");
            }
        }

        // Limpa os campos
        numeroContaField.setText("");
        saldoInicialField.setText("");
        limiteField.setText("");
        contaCorrenteRadioButton.setSelected(true); // Reinicia a seleção para conta corrente
    }

    private void realizarDeposito() {
        int numeroConta;
        double valor;

        if (numeroContaField.getText().isEmpty() || valorField.getText().isEmpty()) {
            outputArea.append("Erro: Todos os campos devem ser preenchidos.\n");
            return;
        }

        try {
            numeroConta = Integer.parseInt(numeroContaField.getText());
            if (numeroConta <= 0) {
                outputArea.append("Erro: O número da conta deve ser um valor positivo.\n");
                return;
            }
        } catch (NumberFormatException e) {
            outputArea.append("Erro: Número da conta deve ser um número válido.\n");
            return;
        }

        try {
            valor = Double.parseDouble(valorField.getText());
            if (valor <= 0) {
                outputArea.append("Erro: O valor deve ser positivo.\n");
                return;
            }
        } catch (NumberFormatException e) {
            outputArea.append("Erro: Valor inválido. Insira um número válido.\n");
            return;
        }

        if (contasCorrente.containsKey(numeroConta)) {
            contasCorrente.get(numeroConta).depositar(valor);
            outputArea.append("Depósito de " + Utilitarios.doubleToString(valor) + " na conta " + numeroConta + " realizado com sucesso.\n");
        } else if (contasPoupanca.containsKey(numeroConta)) {
            contasPoupanca.get(numeroConta).depositar(valor);
            outputArea.append("Depósito de " + Utilitarios.doubleToString(valor) + " na conta " + numeroConta + " realizado com sucesso.\n");
        } else {
            outputArea.append("Erro: Conta não encontrada.\n");
        }
    }

    private void realizarSaque() {
        int numeroConta;
        double valor;

        if (numeroContaField.getText().isEmpty() || valorField.getText().isEmpty()) {
            outputArea.append("Erro: Todos os campos devem ser preenchidos.\n");
            return;
        }

        try {
            numeroConta = Integer.parseInt(numeroContaField.getText());
            if (numeroConta <= 0) {
                outputArea.append("Erro: O número da conta deve ser um valor positivo.\n");
                return;
            }
        } catch (NumberFormatException e) {
            outputArea.append("Erro: Número da conta deve ser um número válido.\n");
            return;
        }

        try {
            valor = Double.parseDouble(valorField.getText());
            if (valor <= 0) {
                outputArea.append("Erro: O valor deve ser positivo.\n");
                return;
            }
        } catch (NumberFormatException e) {
            outputArea.append("Erro: Valor inválido. Insira um número válido.\n");
            return;
        }

        try {
            if (contasCorrente.containsKey(numeroConta)) {
                contasCorrente.get(numeroConta).sacar(valor);
                outputArea.append("Saque de " + Utilitarios.doubleToString(valor) + " na conta " + numeroConta + " realizado com sucesso.\n");
            } else if (contasPoupanca.containsKey(numeroConta)) {
                contasPoupanca.get(numeroConta).sacar(valor);
                outputArea.append("Saque de " + Utilitarios.doubleToString(valor) + " na conta " + numeroConta + " realizado com sucesso.\n");
            } else {
                outputArea.append("Erro: Conta não encontrada.\n");
            }
        } catch (SaldoInsuficienteException e) {
            outputArea.append(e.getMessage() + "\n");
        }
    }

    private void imprimirExtrato() {
        int numeroConta;

        if (numeroContaField.getText().isEmpty()) {
            outputArea.append("Erro: Número da conta deve ser preenchido.\n");
            return;
        }

        try {
            numeroConta = Integer.parseInt(numeroContaField.getText());
            if (numeroConta <= 0) {
                outputArea.append("Erro: O número da conta deve ser um valor positivo.\n");
                return;
            }
        } catch (NumberFormatException e) {
            outputArea.append("Erro: Número da conta deve ser um número válido.\n");
            return;
        }

        if (contasCorrente.containsKey(numeroConta)) {
            outputArea.append("Extrato da conta " + numeroConta + ":\n");
            outputArea.append(contasCorrente.get(numeroConta).toString());
        } else if (contasPoupanca.containsKey(numeroConta)) {
            outputArea.append("Extrato da conta " + numeroConta + ":\n");
            outputArea.append(contasPoupanca.get(numeroConta).toString());
        } else {
            outputArea.append("Erro: Conta não encontrada.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BancoApp::new);
    }
}
