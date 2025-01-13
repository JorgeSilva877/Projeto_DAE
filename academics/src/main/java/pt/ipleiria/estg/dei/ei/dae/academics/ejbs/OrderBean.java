package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;

import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int code, Client client, String morada, float precoTotal, List<Product> products) {
        var order = new Order(code, client, morada, precoTotal, products);
        entityManager.persist(order);
    }

    public List<Order> findAll() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

}
