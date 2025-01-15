package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int code, Client client, String morada, float precoTotal, List<Integer> productsId) {

        //falta fazer o find do cliente para confirmar que existe

        List<Product> products = productsId.stream()        //localizar os produtos pelo id para passar a lista de produtos para o construtor da order
                .map(productId -> {
                    Product product = entityManager.find(Product.class, productId);
                    if (product == null) {
                        throw new IllegalArgumentException("Product with id " + productId + " not found.");
                    }
                    return product;
                })
                .collect(Collectors.toList());

        var order = new Order(code, client, morada, precoTotal, products);
        entityManager.persist(order);

        //falta ainda criar depois os volumes a partir daqui
    }

    public Order find(int code) {
        var order = entityManager.find(Order.class, code);
        if (order == null) {
            throw new RuntimeException("order " + code + " not found");
        }
        return order;
    }

    public List<Order> findAll() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

}
