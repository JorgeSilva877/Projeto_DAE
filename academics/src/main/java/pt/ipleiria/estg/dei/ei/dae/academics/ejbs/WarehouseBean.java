package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Warehouse;

@Stateless
public class WarehouseBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int id){
        var warehouse = new Warehouse(id);
        entityManager.persist(warehouse);
    }

    public Warehouse find(int id) {
        var warehouse = entityManager.find(Warehouse.class, id);
        return warehouse;
    }
}