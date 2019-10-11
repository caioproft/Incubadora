package entities;

import DAO.AccountDAO;
import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

import javax.persistence.*;
import javax.swing.*;
import java.util.List;
import java.util.Scanner;

@Entity
public class Account {

    // Atributos da classe
//    @Column(name= "account_debt")
    private Double accountDebt;
//    @Column(name = "initial_limit")
    private Double initialLimit;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "account_number", nullable = false)
    private Integer accountNumber;

//    @Column(name = "account_balance", nullable = false)
    private Double accountBalance;

//    @Column(name = "account_limit", nullable = false)
    private Double accountLimit;

//    @Column(name = "user_name", nullable = false)
    private String userName;

//    @Column(name = "user_cpf", nullable = false, length = 14)
    private String userCPF;

    // Construtores
    public Account(){
    }

    public Account(Integer accountNumber, Double accountBalance, Double accountLimit, String userName, String userCPF,
                   Double accountDebt, Double initialLimit) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountLimit = accountLimit;
        this.userName = userName;
        this.userCPF = userCPF;
        this.accountDebt = accountDebt;
        this.initialLimit = this.accountLimit;
    }

    //Getters e Setters
    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Double getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(Double accountLimit) {
        this.accountLimit = accountLimit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCPF() {
        return userCPF;
    }

    public void setUserCPF(String userCPF) {
        this.userCPF = userCPF;
    }

    public Double getAccountDebt() {
        return accountDebt;
    }

    public Double getInitialLimit() {
        return initialLimit;
    }

    public void setInitialLimit(Double initialLimit) {
        this.initialLimit = initialLimit;
    }

    // Retornando o objeto com toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Account ID: " + id + "\n");
        sb.append(" | User name: " + userName );
        sb.append(" | User CPF: " + userCPF );
        sb.append(" | Account number: " + accountNumber);
        sb.append(" | Account balance: " + accountBalance);
        sb.append(" | Account limit: " + accountLimit);
        sb.append(" | Account debt: " + accountDebt + "\n\n");
        return sb.toString();
    }

    // Método para validar o CPF usando dependencia da Caelum Stella Core - Gradle
    public static boolean validateCPF(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
        if(erros.size() > 0) {
            return false;
        }else{
            return true;
        }
    }

    // Método para saque na conta
    public void withdrawn(double value) {
        if (value > this.accountBalance + this.accountLimit){ // verifica se o saque é maior que o saldo + limite
            JOptionPane.showMessageDialog(null,"Limite indisponível! :(");
        }
        else if(value >= this.accountBalance){ // verifica se o saque é maior que o saldo e retira do limite
//            this.initialLimit += this.accountLimit;
            this.accountLimit = this.accountLimit + this.accountBalance - value;
            this.accountBalance = 0.0;
            accountDebt = initialLimit - accountLimit;
            JOptionPane.showMessageDialog(null, "Boas compras! :)"
                    + "Obrigado por ser Incuba Bank.");
        }
        else { // se o saque é menor que o saldo, retira apenas do saldo
            this.accountBalance -= value;
           JOptionPane.showMessageDialog(null, "Boas compras! :)"
                   + "Obrigado por ser Incuba Bank.");
        }
    }

    // Método para depósito na conta
    public void deposit(double value){
        if(accountDebt > 0){ // verifica se no momento do depósito exite divida e calcula a nova divida a partir do deposito
            accountDebt = value - accountDebt;
            if(accountDebt > 0){ // se não há mais divida o limite retorna, a dpivida é zerada e o saldo atualizado
                accountBalance = accountDebt;
                accountLimit = initialLimit;
                accountDebt = 0.0;
            }else{ // se ainda há dívida o valor depositado é retornado ao limite e o valor da divida atualizado
                accountLimit += value;
                accountDebt = accountDebt * (-1);
            }
        }
        else{ // se não há dívida o valor depositado entra direto no saldo
            accountBalance += value;
        }
        JOptionPane.showMessageDialog(null, "Depósito efetuado com sucesso!\n"
                + "Obrigado por ser Incuba Bank.");
    }

    // Método para criar uma conta
    public void createAccount(AccountDAO accountsDAO){
        String userName = (JOptionPane.showInputDialog("Digite o nome do correntista: "));
        String userCPF = (JOptionPane.showInputDialog("Digite o CPF do correntista: "));
        while (Account.validateCPF(userCPF) == false){
            JOptionPane.showMessageDialog(null, "CPF inválido");
            userCPF = (JOptionPane.showInputDialog("Digite o CPF do correntista: "));
            Account.validateCPF(userCPF);
        }
        int accountNumber = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da conta"));
        double accountLimit = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite da conta: "));
        accountsDAO.insert(new Account(accountNumber, 0.0, accountLimit, userName, userCPF,
                0.0, accountLimit));
    }

    // Método para atualizar uma conta
    public void updateAccount(AccountDAO accountsDAO){
        id = Long.valueOf(JOptionPane.showInputDialog("Digite o id da conta"));
        Account accounts = accountsDAO.findById(id);
        accountLimit = Double.parseDouble(JOptionPane.showInputDialog("Digite o novo limite da conta: "));
        userName = (JOptionPane.showInputDialog("Digite o novo nome do correntista: "));
        userCPF = (JOptionPane.showInputDialog("Digite o novo CPF do correntista: "));
        while (Account.validateCPF(userCPF) == false) {
            JOptionPane.showMessageDialog(null, "CPF inválido");
            userCPF = (JOptionPane.showInputDialog("Digite o CPF do correntista: "));
            Account.validateCPF(userCPF);
        }
        accounts.setAccountLimit(accountLimit);
        accounts.setUserName(userName);
        accounts.setUserCPF(userCPF);
        accounts.setInitialLimit(accountLimit);
        accountsDAO.update(accounts);
    }

    // Método para deletar uma conta
    public void deleteAccount(AccountDAO accountsDAO){
        id = Long.valueOf(JOptionPane.showInputDialog("Digite o id da conta que será excluida"));
        accountsDAO.deleteById(id);
    }

    // Método para operaçãos de saque e depósito da conta
    public void operationAccount(Account accounts, AccountDAO accountsDAO){
        id = Long.valueOf(JOptionPane.showInputDialog("Digite o id da conta"));
                    accounts = accountsDAO.findById(id);
                    int choice = Integer.parseInt(JOptionPane.showInputDialog("1 - Sacar\n2 - Depositar"));
                    if(choice == 1){
                        double value = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do saque: "));
                        accounts.withdrawn(value);
                        accountsDAO.update(accounts);
                    }else if(choice == 2){
                        double value = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do depósito: "));
                        accounts.deposit(value);
                        accountsDAO.update(accounts);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                    }
    }
}
