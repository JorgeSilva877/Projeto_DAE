package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.LinkedList;
import java.util.List;

@Table(name = "warehouses")

@Entity
public class Warehouse {
    @Id
    private int id;
    @OneToMany (mappedBy = "warehouse")
    private List<Product> products;

    public Warehouse() {
        this.products = new LinkedList<>();
    }

    public Warehouse(int id) {
        this.id = id;
        this.products = new LinkedList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }




}
