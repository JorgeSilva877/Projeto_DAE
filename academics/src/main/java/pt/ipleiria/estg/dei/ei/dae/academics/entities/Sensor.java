package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensors",
                query = "SELECT s FROM Sensor s ORDER BY s.id"
        )
})

@Table(name = "sensor")

public class Sensor {
    @Id
    private int id;
    private String type;
    private int valor;
    @OneToOne
    private Volume volume;
    private boolean isActive;

    public Sensor() {
    }

    public Sensor(int id, String type, Volume volume) {
        this.id = id;
        this.type = type;
        this.valor = 0;
        this.volume = volume;
        this.isActive = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
