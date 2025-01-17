package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

@Stateless
public class ManagerBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String email){
        var manager = entityManager.find(Manager.class, username);

        if(manager != null){
            throw new MyEntityExistsException("Esse username já existe!!");
        }
        manager = new Manager(username, hasher.hash(password), name, email);
        entityManager.persist(manager);
    }

}
