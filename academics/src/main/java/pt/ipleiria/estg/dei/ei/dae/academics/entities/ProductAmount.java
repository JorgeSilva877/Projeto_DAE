package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;

@Entity
public class ProductAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;
    private int productId;
    private int amount;
    @OneToOne
    private Volume volume;

    public ProductAmount() {
    }

    public ProductAmount(int productId, int amount) {
        this.productId = productId;
        this.amount = amount;
        this.volume = null;
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

    public void setCode(long code) {
        this.code = code;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }
}
