package dao;

import domain.Times;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class TimesDAO {
    // O DAO é responsável pela operação entre JPA e o banco. Os métodos são especificados aqui
    private final EntityManager entityManager;

    public TimesDAO(final EntityManager entityManager){
        this.entityManager = entityManager;
    }

    // Método para inserir um time
    public void insert (final Times times){
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(times);
        transaction.commit();
    }

    // Método para listar todos os times
    public List<Times> findAll(){
        final TypedQuery<Times> times  =entityManager.createQuery(
                "from Times",
                Times.class
        );
        return times.getResultList();
    }

    // Método para encontrar time por id
    public Times findById(final Long id){
        return entityManager.find(Times.class, id);
    }

    // Método para atualizar os times
    public void update(final Times times){
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(times);
        transaction.commit();
    }

    // Método para excluir um time pelo id
    public void deleteById(final Long id){
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final Times times = findById(id);
        entityManager.remove(times);
        transaction.commit();
    }

}
