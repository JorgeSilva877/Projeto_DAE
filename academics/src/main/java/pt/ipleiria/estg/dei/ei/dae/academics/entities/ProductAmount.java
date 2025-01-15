package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductAmount {
    @Id
    private long code;
    private int productId;
    private int amount;

    public ProductAmount() {
    }

    public ProductAmount(long code, int productId, int amount) {
        this.code = code;
        this.productId = productId;
        this.amount = amount;
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

    public void setProductId(int product) {
        this.productId = product;
    }

    public Long getCode() {
        return code;
    }

    public void setId(long id) {
        this.code = id;
    }
}
