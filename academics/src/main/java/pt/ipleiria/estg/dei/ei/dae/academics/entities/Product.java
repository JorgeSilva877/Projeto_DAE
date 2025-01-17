package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "getAllProducts",
        query = "SELECT p FROM Product p ORDER BY p.name"
    )
})
@Table(name = "products")

public class Product {
    @Id
    private int code;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private int limite; // valor ao qual o produto se estraga
    @NotNull
    private int stock;
    @NotNull
    private double price;
    @ManyToOne
    private Warehouse warehouse;
    @Version
    private int version;

    public Product() {
    }

    public Product(int code, String name, String category, int limite, int stock, double price, Warehouse warehouse) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.limite = limite;
        this.stock = stock;
        this.price = price;
        this.warehouse = warehouse;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
