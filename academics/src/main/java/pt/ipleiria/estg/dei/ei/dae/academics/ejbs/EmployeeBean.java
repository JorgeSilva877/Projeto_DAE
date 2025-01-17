package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Employee;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;

import java.util.List;

@Stateless
public class EmployeeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email) throws MyEntityExistsException {
        var employee = entityManager.find(Employee.class, username);

        if (employee != null) {
            throw new MyEntityExistsException("Employee with username " + username + " already exists");
        }

        employee = new Employee(username, password, name, email);
        entityManager.persist(employee);
    }

    public List<Employee> findAll() {
        return entityManager.createNamedQuery("getAllEmployees", Employee.class).getResultList();
    }
}
