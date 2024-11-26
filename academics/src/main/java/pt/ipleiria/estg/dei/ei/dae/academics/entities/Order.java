package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;
@Entity
public class Order extends Versionable{
    @Id
    private long code;
    @ManyToOne
    private Client client;
    private String morada;

}
