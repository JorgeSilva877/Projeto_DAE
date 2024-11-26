package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT c FROM Client c ORDER BY c.name"
        )
})
public class Client extends User implements Serializable {
    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client() {
        this.orders = new LinkedList<>();
    }

    public Client(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.orders = new LinkedList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
    }
}
