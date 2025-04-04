package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;


import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.User;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public User findOrFail(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }
    public boolean canLogin(String username, String password) {
        var user = findOrFail(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}
