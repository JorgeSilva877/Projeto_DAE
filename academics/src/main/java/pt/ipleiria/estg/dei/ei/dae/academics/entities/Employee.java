package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEmployees",
                query = "SELECT e FROM Employee e ORDER BY e.id"
        )
})

@Table(name = "employees")
public class Employee extends User {
    @OneToMany (mappedBy = "employee", cascade = CascadeType.MERGE)
    private List<Volume> volumes;
    @ManyToOne
    private Warehouse warehouse;

    public Employee() {
        this.volumes = new LinkedList<>();
    }

    public Employee(String username, String password, String name, String email, Warehouse warehouse) {
        super(username, password, name, email);
        this.volumes = new LinkedList<>();
        this.warehouse = warehouse;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public void addVolume(Volume volume) {
        volumes.add(volume);
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

}
