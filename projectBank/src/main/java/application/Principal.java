package application;

import DAO.AccountDAO;
import entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.sql.SQLOutput;


public class Principal {
    public static void main(String[] args) {

        int option;

        final EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("incuba_bank");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("######## Conectado ao banco! #########");
        final AccountDAO accountsDAO = new AccountDAO(entityManager);
        Account account = new Account();
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
                    account.createAccount(accountsDAO);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, accountsDAO.findAll());
                    break;
                case 3:
                    Long id = Long.valueOf(JOptionPane.showInputDialog("Digite o id da conta"));
                    JOptionPane.showMessageDialog(null, accountsDAO.findById(id));
                    break;
                case 4:
                    account.updateAccount(accountsDAO);
                    break;
                case 5:
                    account.deleteAccount(accountsDAO);
                    break;
                case 6:
                    account.operationAccount(account, accountsDAO);
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
