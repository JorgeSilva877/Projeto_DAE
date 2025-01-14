package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;

@Entity
public class Manager extends User {
    public Manager() {
    }

    public Manager(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
