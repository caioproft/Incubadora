package application;

import DAO.AccountDAO;
import entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        int option;

        final EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("incuba_bank");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("######## Conectado ao banco! #########");
        final AccountDAO accountsDAO = new AccountDAO(entityManager);

        do{
            option = Integer.parseInt(JOptionPane.showInputDialog("\nBem-vindo ao Incuba Bank!\n"
                    +"O que vamos fazer hoje?\n"
                    + "\n1 - Cadastrar nova conta\n"
                    + "2 - Mostrar todas as contas\n"
                    + "3 - Buscar uma conta especifica\n"
                    + "4 - Atualizar os dados de uma conta\n"
                    + "5 - Deletar uma conta\n"
                    + "6 - Realizar uma transação\n"
                    + "7 - Sair"));
            switch (option){
                case 1:
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
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, accountsDAO.findAll());
                    break;
                case 3:
                    Long id = Long.valueOf(JOptionPane.showInputDialog("Digite o id da conta"));
                    JOptionPane.showMessageDialog(null, accountsDAO.findById(id));
                    break;
                case 4:
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
                    break;
                case 5:
                    id = Long.valueOf(JOptionPane.showInputDialog("Digite o id da conta que será excluida"));
                    accountsDAO.deleteById(id);
                    break;
                case 6:
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
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Obrigado por utilizar o Incuba Bank");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                    break;
            }
        }while(option != 7);
    }
}
