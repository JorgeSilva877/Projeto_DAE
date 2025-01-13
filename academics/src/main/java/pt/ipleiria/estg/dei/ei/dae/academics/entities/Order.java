package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;


import java.util.LinkedList;
import java.util.List;
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.code" // JPQL
        )
})
@Table(name = "products")
public class Order {
    @Id
    private int code;
    @ManyToOne
    private Client client;
    private String morada;
    private float precoTotal;
    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(
                    name = "order_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "product_code",
                    referencedColumnName = "code"
            )
    )
    private List<Product> products;
    @Version
    private int version;

    public Order() {
        this.products = new LinkedList<>();
    }

    public Order(int code, Client client, String morada, float precoTotal, List<Product> products) {
        this.code = code;
        this.client = client;
        this.morada = morada;
        this.precoTotal = precoTotal;
        this.products = new LinkedList<>(products);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public float getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(float precoTotal) {
        this.precoTotal = precoTotal;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void calculateTotalPrice() {
        float total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        this.precoTotal = total;
    }




}
