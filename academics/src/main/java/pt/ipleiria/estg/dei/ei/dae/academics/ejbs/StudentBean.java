package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;

@Stateless // Entreprise Java Bean (EJB) sem estado, ou seja, não guarda estado entre pedidos.
public class StudentBean {
    @PersistenceContext
    private EntityManager entityManager;
    public void create(String username, String password, String name, String email) {
        var student = new Student(username, email, name, password);
        entityManager.persist(student);
    }

}
