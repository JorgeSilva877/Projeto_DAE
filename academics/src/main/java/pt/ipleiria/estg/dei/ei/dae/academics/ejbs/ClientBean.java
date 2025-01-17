package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.User;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import java.util.List;

@Stateless

public class ClientBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String mail) throws EntityExistsException, EntityNotFoundException {
        var checkClient = entityManager.find(Client.class, username);
        if (checkClient != null) {
            throw new EntityExistsException("username already exists");
        }
        var client = new Client(username, hasher.hash(password), name, mail);
        entityManager.persist(client);
    }

    public Client find(String username){
        var cliente = entityManager.find(Client.class,username);
        if(cliente == null){
            throw new MyEntityNotFoundException("username not found");
        }
        return cliente;
    }

    public List<Client>findAll() {
        if (entityManager.createNamedQuery("getAllClients", Client.class).getResultList().isEmpty()) {
            throw new MyEntityNotFoundException("No clients found");
        }
        return entityManager.createNamedQuery("getAllClients", Client.class).getResultList();
    }
}
