package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.*;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import java.util.ArrayList;
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

    public Employee find(String username) throws MyEntityNotFoundException {
        var employee = entityManager.find(Employee.class, username);
        if (employee == null) {
            throw new MyEntityNotFoundException("Employ " + employee + " not found");
        }
        return employee;
    }

    public List<Volume> findAllByWarehouseEmployee(int idWarehouseEmployee) {
        List<Volume> volumes = entityManager.createNamedQuery("getAllVolums", Volume.class).getResultList();
        List<Volume> volumesADevolver = new ArrayList<Volume>();

        for (Volume volume : volumes) {
            ProductAmount productAmmount = volume.getProductAmount();
            Product product = entityManager.find(Product.class, productAmmount.getProductId());
            if(product.getWarehouse().getId() == idWarehouseEmployee && volume.getEmployee() == null){
                volumesADevolver.add(volume);
            }
        }
        return volumesADevolver;
    }
}
