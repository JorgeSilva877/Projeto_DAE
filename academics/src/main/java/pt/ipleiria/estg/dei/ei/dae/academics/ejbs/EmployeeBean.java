package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Employee;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Manager;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Warehouse;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import java.util.List;

@Stateless
public class EmployeeBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String email, int warehouseId) throws MyEntityExistsException {
        var employee = entityManager.find(Employee.class, username);

        if (employee != null) {
            throw new MyEntityExistsException("Employee with username " + username + " already exists");
        }
        Warehouse warehouse = entityManager.find(Warehouse.class, warehouseId);
        if (warehouse == null) {
            throw new MyEntityExistsException("Warehouse with id " + warehouseId + " not found");
        }
        employee = new Employee(username, hasher.hash(password), name, email, warehouse);
        entityManager.persist(employee);
    }

    public List<Employee> findAll() {
        return entityManager.createNamedQuery("getAllEmployees", Employee.class).getResultList();
    }
}
