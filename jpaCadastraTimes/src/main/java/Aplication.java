import dao.TimesDAO;
import domain.Times;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Aplication {
    public static void main(String[] args) {

        // Construindo EntityMangaer para definir as persistências
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cadastro_jpa");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("Conectado ao banco");

        final TimesDAO timeDAO = new TimesDAO(entityManager);

        // Inserindo um time
        timeDAO.insert(new Times("Incubavrau"));

        // Listando os times
        System.out.println(timeDAO.findAll());

        // Listando o time pelo id
        //final Times times = timeDAO.findById(2L);
        //System.out.println(times);

       // // Atualizando um time
       // times.setNome("Toca do Lobo");
       // timeDAO.update(times);
        //System.out.println(timeDAO.findById(2L));

        // Excluindo um time
        //timeDAO.deleteById(3L);
        //System.out.println(timeDAO.findAll());
    }

}

