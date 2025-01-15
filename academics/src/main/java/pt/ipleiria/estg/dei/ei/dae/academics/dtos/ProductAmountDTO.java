package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.ProductAmount;

import java.util.List;

public class ProductAmountDTO {
    private long code;
    private int productId;
    private int amount;

    public ProductAmountDTO() {
    }

    public ProductAmountDTO(long code, int productId, int amount) {
        this.code = code;
        this.productId = productId;
        this.amount = amount;
    }

    public static ProductAmountDTO from(ProductAmount productAmount) {
        return new ProductAmountDTO(
                productAmount.getCode(),
                productAmount.getProductId(),
                productAmount.getAmount()
        );
    }

    public static List<ProductAmountDTO> from(List<ProductAmount> productAmounts) {
        return productAmounts.stream().map(ProductAmountDTO::from).toList();
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
