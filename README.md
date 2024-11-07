# Sistema Bancário

Este é um sistema bancário simples implementado em Java com uma interface gráfica usando **Swing**. Este projeto permite a gestão de contas bancárias, incluindo funcionalidades para cadastro de usuários, criação de contas, depósitos, saques e impressão de extratos.

## Tecnologias Utilizadas
- Java 11 ou superior
- Swing para interface gráfica
  
## Como Rodar o Projeto

1. Clone este repositório:  
   `git clone https://github.com/franciscamac/sistema-bancario.git`

2. Abra o projeto em sua IDE favorita (como IntelliJ IDEA, Eclipse, etc.).

3. Acesse a Classe BancoApp.java em src/Main compile e execute a aplicação.

## Instruções de Uso

### 1. Para Cadastrar um Usuário:
- **Passo 1**: Preencha os campos **'Nome'**, **'CPF'** e **'E-Mail'**.
- **Passo 2**: Clique no botão **'Cadastrar Usuário'**.

### 2. Para Criar uma Conta:
- **Passo 1**: Selecione o usuário desejado no **ComboBox** de **'Usuário Selecionado'**.
- **Passo 2**: Preencha os seguintes campos:
  - **'Número da Conta'**
  - **'Saldo Inicial'**
  - **'Limite do Cheque Especial'**
- **Passo 3**: Escolha o tipo de conta: **Poupança** ou **Corrente**.

### 3. Realizar um Depósito:
- **Passo 1**: Selecione a conta em que deseja realizar o depósito, preenchendo o **Número da Conta**.
- **Passo 2**: Preencha o valor a ser depositado.
- **Passo 3**: Clique no botão **'Depositar'**.

### 4. Realizar um Saque:
- **Passo 1**: Selecione a conta de destino, preenchendo o **Número da Conta**.
- **Passo 2**: Preencha o valor a ser sacado.
- **Passo 3**: Clique no botão **'Sacar'**.

### 5. Imprimir o Extrato da Conta:
- **Passo 1**: Preencha o **Número da Conta** para a qual deseja ver o extrato.
- **Passo 2**: Clique no botão **'Imprimir Extrato'**.


## Diagrama de Classe

```mermaid
classDiagram
    class MovimentacaoBancaria {
        <<interface>>
        +depositar(double valor)
        +sacar(double valor)
    }

    class ContaBancaria {
        <<abstract>>
        -int numeroConta
        -double saldo
        +ContaBancaria(int numeroConta, double saldoInicial)
        +getNumeroConta(): int
        +getSaldo(): double
        +setSaldo(double saldo): void
        +depositar(double valor): void
        +sacar(double valor): void
        +toString(): String
    }

    class ContaCorrente {
        -double limiteChequeEspecial
        +ContaCorrente(int numeroConta, double saldoInicial, double limiteChequeEspecial)
        +sacar(double valor): void
        +toString(): String
    }

    class ContaPoupanca {
        -double taxaRendimento
        +ContaPoupanca(int numeroConta, double saldoInicial, double taxaRendimento)
        +calcularRendimento(): double
        +sacar(double valor): void
        +toString(): String
    }

    class Utilitarios {
        <<utility>>
        +doubleToString(double valor): String
    }

    class Usuario {
        -String nome
        -String cpf
        -String email
        +Usuario(String nome, String cpf, String email)
        +getNome(): String
        +getCpf(): String
        +getEmail(): String
    }

    class SaldoInsuficienteException {
        <<exception>>
        +SaldoInsuficienteException(String message)
    }

    class BancoApp {
        +BancoApp()
        +cadastrarUsuario(): void
        +criarConta(): void
        +realizarDeposito(): void
        +realizarSaque(): void
        +imprimirExtrato(): void
        +obterUsuarioParaConta(): Usuario
        +exibirInstrucoes(): void
    }

    MovimentacaoBancaria <|-- ContaBancaria
    ContaBancaria <|-- ContaCorrente
    ContaBancaria <|-- ContaPoupanca
    BancoApp --> Usuario : associa
    BancoApp --> ContaBancaria : cria
    BancoApp --> Utilitarios : usa
    BancoApp --> SaldoInsuficienteException : lança

