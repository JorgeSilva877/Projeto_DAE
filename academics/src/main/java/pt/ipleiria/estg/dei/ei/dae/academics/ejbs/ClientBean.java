package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;

import java.util.List;

@Stateless

public class ClientBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String mail) throws EntityExistsException, EntityNotFoundException {
        var checkClient = entityManager.find(Client.class, username);
        if (checkClient != null) {
            throw new EntityExistsException("username already exists");
        }
        var client = new Client(username, password, name, mail);
        entityManager.persist(client);
    }
}
