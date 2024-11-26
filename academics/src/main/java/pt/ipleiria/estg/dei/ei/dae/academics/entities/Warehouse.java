package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Warehouse {
    @Id
    private long id;
    @OneToMany (mappedBy = "warehouse")
    private List<Product> products;

    public Warehouse() {
        this.products = new LinkedList<>();
    }

    public Warehouse(long id) {
        this.id = id;
        this.products = new LinkedList<>();
    }

}
