package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jdk.jfr.Name;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllVolums",
                query = "SELECT v FROM Volume v ORDER BY v.id"
        )
})

@Table(name = "volums")
public class Volume {
    @Id
    private int id;
    @ManyToOne
    private Employee employee;
    private boolean isOk;
    @OneToOne (mappedBy = "volume")
    @Nullable
    private Sensor sensor;
    @Version
    private int version;
    @OneToOne (mappedBy = "volume")
    private ProductAmount productAmount;
    @ManyToOne
    private Order order;


    public Volume() {
    }

    public Volume(int id, ProductAmount productAmount, Order order) {
        this.id = id;
        this.employee = null;
        this.sensor = null;
        this.isOk = true;
        this.productAmount = productAmount;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        if(employee == null){
            employee = new Employee();
        }
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public Sensor getSensor() {
        if(sensor == null){
            sensor = new Sensor();
        }
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }


    public void addSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void removeSensor(Sensor sensor) {
        this.sensor = null;
    }

    public void addEmployee(Employee employee) {
        this.employee = employee;
    }

    public ProductAmount getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(ProductAmount productAmount) {
        this.productAmount = productAmount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
