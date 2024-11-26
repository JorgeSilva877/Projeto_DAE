package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Manager;

@Stateless
public class ManagerBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email){
        var manager = new Manager(username, password, name, email);
        entityManager.persist(manager);
    }

}
