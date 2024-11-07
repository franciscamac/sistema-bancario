package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import Models.ContaBancaria;
import Models.ContaCorrente;
import Models.ContaPoupanca;
import Exceptions.SaldoInsuficienteException;
import Models.Usuario;
import Util.Utilitarios;

// Classe principal que representa o aplicativo de sistema bancário com interface gráfica, permitindo a criação de contas, depósitos, saques e impressão de extratos.
// A interface gráfica é construída usando Swing.

public class BancoApp {
    private JFrame frame;
    private JTextField nomeUsuarioField;
    private JTextField cpfUsuarioField;
    private JTextField emailUsuarioField;
    private JTextField numeroContaField;
    private JTextField valorField;
    private JTextField saldoInicialField;
    private JTextField limiteField; // Para contas correntes
    private JTextArea outputArea;
    private Map<Integer, ContaCorrente> contasCorrente;
    private Map<Integer, ContaPoupanca> contasPoupanca;
    private Map<Usuario, Map<Integer, ContaBancaria>> usuarios;
    private JRadioButton contaCorrenteRadioButton;
    private JRadioButton contaPoupancaRadioButton;
    private JComboBox<String> usuarioComboBox;


//    Construtor da classe BancoApp.
//    Inicializa a interface gráfica e os mapas de contas.
    public BancoApp() {

        exibirInstrucoes();
        // Inicialização dos mapas de contas
        contasCorrente = new HashMap<>();
        contasPoupanca = new HashMap<>();
        usuarios = new HashMap<>();

        // Configuração da interface gráfica
        frame = new JFrame("Sistema Bancário");
        frame.setPreferredSize(new Dimension(900, 600));  // Define o tamanho preferencial
        frame.pack();  // Ajusta a janela para o tamanho preferencial
        frame.setResizable(true);
        numeroContaField = new JTextField(15);
        valorField = new JTextField(10);
        saldoInicialField = new JTextField(10);
        limiteField = new JTextField(10);
        nomeUsuarioField = new JTextField(15);
        cpfUsuarioField = new JTextField(15);
        emailUsuarioField = new JTextField(15);
        outputArea = new JTextArea(10, 40);

        usuarioComboBox = new JComboBox<>();

        // Botões de opção para escolher o tipo de conta
        contaCorrenteRadioButton = new JRadioButton("Conta Corrente");
        contaPoupancaRadioButton = new JRadioButton("Conta Poupança");
        ButtonGroup contaGroup = new ButtonGroup();
        contaGroup.add(contaCorrenteRadioButton);
        contaGroup.add(contaPoupancaRadioButton);
        contaCorrenteRadioButton.setSelected(true); // Seleciona a conta corrente por padrão

        //Cria os botões de ações
        JButton cadastrarUsuarioButton = new JButton("Cadastrar Usuário");
        JButton criarContaButton = new JButton("Criar Conta");
        JButton depositButton = new JButton("Depositar");
        JButton sacarButton = new JButton("Sacar");
        JButton extratoButton = new JButton("Imprimir Extrato");

        //Associa os botões de ação aos botões criado anteriormente
        cadastrarUsuarioButton.addActionListener(e -> cadastrarUsuario());
        criarContaButton.addActionListener(e -> criarConta());
        depositButton.addActionListener(e -> realizarDeposito());
        sacarButton.addActionListener(e -> realizarSaque());
        extratoButton.addActionListener(e -> imprimirExtrato());

        //Painel principal da interface
        JPanel panel = new JPanel();
        panel.add(new JLabel("Nome do Usuário:"));
        panel.add(nomeUsuarioField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfUsuarioField);
        panel.add(new JLabel("E-mail:"));
        panel.add(emailUsuarioField);
        panel.add(cadastrarUsuarioButton);
        panel.add(new JLabel("Usuário Selecionado:"));
        panel.add(usuarioComboBox); // Adiciona o combo box para selecionar o usuário
        panel.add(new JLabel("Número da Conta:"));
        panel.add(numeroContaField);
        panel.add(new JLabel("Saldo Inicial:"));
        panel.add(saldoInicialField);
        panel.add(new JLabel("Limite Cheque Especial:"));
        panel.add(limiteField);
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

    // Método para cadastrar um usuário
    private void cadastrarUsuario() {
        String nome = nomeUsuarioField.getText();
        String cpf = cpfUsuarioField.getText();
        String email = emailUsuarioField.getText();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty()) {
            outputArea.append("Erro: Todos os campos do usuário devem ser preenchidos.\n");
            return;
        }

        Usuario usuario = new Usuario(nome, cpf, email);
        usuarios.putIfAbsent(usuario, new HashMap<>()); // Adiciona o usuário ao mapa

        // Adiciona o nome do usuário ao JComboBox
        usuarioComboBox.addItem(usuario.getNome());

        outputArea.append("Usuário " + nome + " cadastrado com sucesso.\n");

        // Limpa os campos de usuário
        nomeUsuarioField.setText("");
        cpfUsuarioField.setText("");
        emailUsuarioField.setText("");
    }


//    Cria uma nova conta (corrente ou poupança) com base nos dados fornecidos na interface.
//    Exibe mensagens de erro caso os dados sejam inválidos.
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
    double limiteChequeEspecial = 0;

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

    // Procurar o usuário que irá criar a conta
    Usuario usuario = obterUsuarioParaConta();
    if (usuario == null) {
        outputArea.append("Erro: Nenhum usuário selecionado.\n");
        return;
    }

    // Criar a conta e associá-la ao usuário
    if (contaCorrenteRadioButton.isSelected()) {
        if (contasCorrente.containsKey(numeroConta)) {
            outputArea.append("Erro: Conta com este número já existe.\n");
        } else {
            ContaCorrente conta = new ContaCorrente(numeroConta, saldoInicial, limiteChequeEspecial);
            contasCorrente.put(numeroConta, conta);
            usuarios.get(usuario).put(numeroConta, conta); // Vincula a conta ao usuário
            outputArea.append("Conta Corrente " + numeroConta + " criada com sucesso para " + usuario.getNome() + ".\n");
        }
    } else if (contaPoupancaRadioButton.isSelected()) {
        if (contasPoupanca.containsKey(numeroConta)) {
            outputArea.append("Erro: Conta com este número já existe.\n");
        } else {
            ContaPoupanca conta = new ContaPoupanca(numeroConta, saldoInicial, 0.05);
            contasPoupanca.put(numeroConta, conta);
            usuarios.get(usuario).put(numeroConta, conta); // Vincula a conta ao usuário
            outputArea.append("Conta Poupança " + numeroConta + " criada com sucesso para " + usuario.getNome() + ".\n");
        }
    }

    numeroContaField.setText("");
    saldoInicialField.setText("");
    limiteField.setText("");
    contaCorrenteRadioButton.setSelected(true);
}
    // Método para obter o usuário selecionado para vincular à conta
    private Usuario obterUsuarioParaConta() {
        // Verifica se há usuários cadastrados
        if (usuarios.isEmpty()) {
            outputArea.append("Erro: Nenhum usuário cadastrado.\n");
            return null;
        }

        // Pega o nome do usuário selecionado no ComboBox
        String nomeSelecionado = (String) usuarioComboBox.getSelectedItem();

        // Busca o usuário correspondente no mapa de usuários
        for (Usuario usuario : usuarios.keySet()) {
            if (usuario.getNome().equals(nomeSelecionado)) {
                return usuario; // Retorna o usuário correspondente
            }
        }

        outputArea.append("Erro: Usuário não encontrado.\n");
        return null;
    }

//    Realiza um depósito em uma conta com o número informado na interface.
//    Exibe mensagens de erro caso o número da conta ou o valor sejam inválidos.

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
//    Realiza um saque em uma conta com o número informado na interface.
//    Lança uma exceção personalizada (SaldoInsuficienteException) caso o saldo seja insuficiente.
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
//    Imprime o extrato da conta com o número informado na interface.
//    Exibe uma mensagem de erro caso a conta não seja encontrada.
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

    // Procurar a conta e o usuário associado
    Usuario usuario = null;
    ContaBancaria conta = null;

    // Verificar se a conta é uma conta corrente
    if (contasCorrente.containsKey(numeroConta)) {
        conta = contasCorrente.get(numeroConta);
    }
    // Verificar se a conta é uma conta poupança
    else if (contasPoupanca.containsKey(numeroConta)) {
        conta = contasPoupanca.get(numeroConta);
    }

    if (conta != null) {
        // Procurar o usuário associado à conta
        for (Usuario u : usuarios.keySet()) {
            if (usuarios.get(u).containsKey(numeroConta)) {
                usuario = u;
                break;
            }
        }

        if (usuario != null) {
            // Exibir o extrato junto com o nome do usuário
            outputArea.append("Extrato da conta " + numeroConta + " de " + usuario.getNome() + ":\n");
            outputArea.append(conta.toString());
        } else {
            outputArea.append("Erro: Usuário não encontrado para a conta " + numeroConta + ".\n");
        }
    } else {
        outputArea.append("Erro: Conta não encontrada.\n");
    }
}
    private static void exibirInstrucoes() {
        String mensagem = "Bem-vindo ao Sistema Bancário!\n\n" +
                "1. Para Cadastrar um usuário: \n" +
                "Passo 1: Preencha os campos 'Nome ', 'CPF ' e 'E-Mail '. \n" +
                "Passo 2: Clique em 'Cadastrar Usuário'.\n\n" +
                "2. Para criar uma conta: \n" +
                "Passo 1: Selecione o usuário desejado no ComboBox de 'Usuário Selecionado'. \n " +
                "Passo 2: Preencha os seguintes campos: " +
                "'Número da Conta ', " +
                "'Saldo Inicial ', " +
                "'Limite do Cheque Especial'. \n" +
                "Passo 3: Escolha o tipo de conta 'Poupança ou Corrente'. \n\n" +
                "3. Realizar um Depósito: \n" +
                "Passo 1: Selecione a conta em que deseja realizar o depósito, preenchendo o Número da Conta. \n" +
                "Passo 2: Preencha o valor a ser depositado. \n" +
                "Passo 3: Clique no botão 'Depositar'.\n\n" +
                "4. Realizar um Saque: \n" +
                "Passo 1: Selecione a conta de destino, preenchendo o Número da Conta. \n" +
                "Passo 2: Preencha o valor a ser sacado. \n" +
                "Passo 3: Clique no botão 'Sacar'. \n\n " +
                "5. Imprimir o Extrato da Conta: \n" +
                "Passo 1: Preencha o Número da Conta para a qual deseja ver o extrato. \n" +
                "Passo 2: Clique no botão 'Imprimir Extrato' \n\n";


        JOptionPane.showMessageDialog(null, mensagem, "Instruções de Uso", JOptionPane.INFORMATION_MESSAGE);
    }


    //Método principal que inicia a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BancoApp::new);
    }
}
