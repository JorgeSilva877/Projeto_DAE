package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.*;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ClientBean clientBean;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private EmailBean emailBean;

    public int create(String usernameCliente, String morada, float precoTotal, List<ProductAmount> products) {

        var cliente = clientBean.find(usernameCliente);
        if (cliente == null) {
            throw new MyEntityNotFoundException("Client" + cliente + " does not exist"); //comentei para testar por n termos nenhum ainda
        }

        int code = findLastId() + 1;

        var order = new Order(code, cliente, morada, precoTotal, products);
        var orderCode = order.getCode();
        entityManager.persist(order);

        //falta ainda criar depois os volumes a partir daqui
        int id = volumeBean.findLastId();

        for(ProductAmount p : products) {
            id++;
            volumeBean.create(id, p.getCode(), order);
            Volume volume = volumeBean.find(id);
            p.setVolume(volume);
            order.addVolume(volume);
        }
        //ENVIAR EMAIL
        var emailClient = clientBean.find(usernameCliente).getEmail();
        emailBean.send(emailClient, "Order", "Thanks for your order with code: " + orderCode + ", your order is being processed");

        return code;
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

    public int findLastId() {
        return entityManager.createNamedQuery("getAllOrders", Order.class)
                .getResultList()
                .stream()
                .mapToInt(Order::getCode)
                .max()
                .orElse(0);
    }

}
