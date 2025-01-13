package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Warehouse;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private WarehouseBean warehouseBean;


    public void create(long code, String name, String category, String limite, int stock, double price, int idWarehouse) {
        var warehouse = warehouseBean.find(idWarehouse);
        if (warehouse == null) {
            throw new MyEntityNotFoundException("Warehouse" + warehouse + " does not exist"); //comentei para testar por n termos nenhum ainda
        }

        var product = new Product( code,  name,  category, limite, stock,  price, warehouse);
        entityManager.persist(product);
    }

    public List<Product> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(long code) {
        var produto = entityManager.find(Product.class, code);
        if (produto == null) {
            throw new RuntimeException("produto " + code + " not found");
        }
        return produto;
    }
}
