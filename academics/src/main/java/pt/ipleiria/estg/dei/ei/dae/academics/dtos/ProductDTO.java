package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Warehouse;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {
    private int code;
    private String name;
    private String category;
    private int limite; // valor ao qual o produto se estraga
    private int stock;
    private double price;
    private int warehouseId;

    public ProductDTO() {
    }

    public ProductDTO(int code, String name, String category, int limite, int stock, double price, int warehouseId) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.limite = limite;
        this.stock = stock;
        this.price = price;
        this.warehouseId = warehouseId;
    }

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getName(),
                product.getCategory(),
                product.getLimite(),
                product.getStock(),
                product.getPrice(),
                product.getWarehouse().getId()
        );
    }

    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
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

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
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

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
}
