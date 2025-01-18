package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import jakarta.ws.rs.core.Link;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.ProductAmount;

import java.util.LinkedList;
import java.util.List;

public class OrderDTO {
    private int code;
    private String usernameClient;
    private String morada;
    private String estado;
    private float precoTotal;
    private List<ProductAmountDTO> products;

    public OrderDTO() {
        this.products = new LinkedList<>();
    }

    public OrderDTO(int code, String usernameClient, String morada, float precoTotal, List<ProductAmountDTO> products, String estado) {
        this.code = code;
        this.usernameClient = usernameClient;
        this.morada = morada;
        this.precoTotal = precoTotal;
        this.products = new LinkedList<>(products);
        this.estado = estado;
    }

    public static OrderDTO from(Order order) {
        return new OrderDTO(
                order.getCode(),
                order.getClient().getUsername(),
                order.getMorada(),
                order.getPrecoTotal(),
                ProductAmountDTO.from(order.getProducts()),
                order.getEstado()
        );
    }

    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).toList();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsernameClient() {
        return usernameClient;
    }

    public void setUsernameClient(String usernameClient) {
        this.usernameClient = usernameClient;
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

    public List<ProductAmountDTO> getProducts() {
        return new LinkedList<>(products);
    }

    public void setProducts(List<ProductAmountDTO> products) {
        this.products = new LinkedList<>(products);
    }

    public List<ProductAmount> getProductsService() {
        LinkedList<ProductAmount> productsService = new LinkedList<>();
        for (ProductAmountDTO productAmountDTO : products) {
            productsService.add(new ProductAmount(productAmountDTO.getProductId(), productAmountDTO.getAmount()));
        }

        return productsService;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    //    public List<Integer> getProductsId() {
//        List<Integer> productIds = new LinkedList<>();
//        for (ProductAmountDTO product : products) {
//            productIds.add(product.getProductId());
//        }
//        return productIds;
//    }
}
