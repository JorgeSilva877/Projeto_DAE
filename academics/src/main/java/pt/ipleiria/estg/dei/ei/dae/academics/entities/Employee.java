package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

@Entity
public class Employee extends User {
    @OneToMany (mappedBy = "employee")
    private List<Volume> volumes;

    public Employee() {
        this.volumes = new LinkedList<>();
    }

    public Employee(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.volumes = new LinkedList<>();
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }
}
