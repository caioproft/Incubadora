package dao;

import domain.Membros;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class MembrosDAO {

    // EntityManager realiza a persistência dos dados entre aplicação e banco
    private final EntityManager entityManager;
    // Construtor para o EntityManager
    public MembrosDAO(final EntityManager entityManager){
        this.entityManager = entityManager;
    }

    // Método para inserção de membro
    public void insert(final Membros membro){
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(membro);
        transaction.commit();
    }

    // Método para listar todos os membros
    public List<Membros> findAll(){
        final TypedQuery<Membros>query = entityManager.createQuery(
                "Select m FROM Membros m JOIN FETCH m.time",
                Membros.class
        );
        return query.getResultList();
    }

    // Listar os membros por id
    public Membros findById(final Long id){
        return entityManager.find(Membros.class, id);
    }

    // Atualizar os dados de um membro
    public void update (final Membros membro){
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(membro);
        transaction.commit();
    }

    // Deletar um membro
    public void deleteById(final Long id){
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final Membros membro = findById(id);
        entityManager.remove(membro);
        transaction.commit();
    }

    // Localizar um membro pelo id do time
    public List<Membros> findAllByTeamId(final Long timeId){
        final TypedQuery<Membros> query = entityManager.createQuery(
                "SELECT m FROM Membros m" +
                        "JOIN FETCH m.time t " +
                        "WHERE t.id = :timeId",
                Membros.class
        );
        query.setParameter("timeId", timeId );
        return query.getResultList();
    }



}
