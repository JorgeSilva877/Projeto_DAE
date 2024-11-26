package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;

@Entity
public class Volume {
    @Id
    private long code;
    @ManyToOne
    private Employee employee;
    private boolean isOk;
    @OneToOne (mappedBy = "volume")
    private Sensor sensor;
    @Version
    private int version;

    public Volume() {
    }

    public Volume(long code, Employee employee, Sensor sensor) {
        this.code = code;
        this.employee = employee;
        this.sensor = sensor;
        this.isOk = true;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Employee getEmployee() {
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
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }




}
