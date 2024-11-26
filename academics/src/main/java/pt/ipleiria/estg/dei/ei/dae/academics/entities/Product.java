package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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
    private long code;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private String estado;
    @NotBlank
    private int stock;
    @NotBlank
    private double price;
    @Version
    private int version;

    public Product() {
    }

    public Product(long code, String name, String category, String estado, int stock, double price) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.estado = estado;
        this.stock = stock;
        this.price = price;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
}
