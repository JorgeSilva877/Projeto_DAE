package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;


    public void create(long code, String name, String category, String estado, int stock, double price) {
        var product = new Product( code,  name,  category,  estado,  stock,  price);
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
