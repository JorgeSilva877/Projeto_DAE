 package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Warehouse;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {
    private long code;
    private String name;
    private String category;
    private String limite; // valor ao qual o produto se estraga
    private int stock;
    private double price;
    private Warehouse warehouse;

    public ProductDTO() {
    }

    public ProductDTO(long code, String name, String category, String limite, int stock, double price, Warehouse warehouse) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.limite = limite;
        this.stock = stock;
        this.price = price;
        this.warehouse = warehouse;
    }

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getName(),
                product.getCategory(),
                product.getLimite(),
                product.getStock(),
                product.getPrice(),
                product.getWarehouse()
        );
    }

    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
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

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
