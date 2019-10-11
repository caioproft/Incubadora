package DAO;

import entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountDAO {

    private final EntityManager entityManager;

    public AccountDAO(final EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void insert(final Account accounts){
        final EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(accounts);
        transaction.commit();
    }
    public List<Account> findAll(){
        final TypedQuery<Account> accounts = entityManager.createQuery(
                "from Account",
                Account.class
        );
        return accounts.getResultList();
    }
    public Account findById(final Long id){
        return entityManager.find(Account.class, id);
    }
    public void update(final Account accounts){
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(accounts);
        transaction.commit();
    }

    public void deleteById(final Long id){
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final Account accounts = findById(id);
        entityManager.remove(accounts);
        transaction.commit();
    }
}
