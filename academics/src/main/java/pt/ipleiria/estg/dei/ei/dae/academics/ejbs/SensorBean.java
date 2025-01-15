package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.logging.Logger;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private VolumeBean volumeBean;


    private static final Logger logger= Logger.getLogger(SensorBean.class.getName());

    public void create(int id, String type) throws MyEntityNotFoundException, MyEntityExistsException {
        var sensor = entityManager.find(Sensor.class, id);
        if (sensor != null) {
            throw new MyEntityExistsException("Esse sensor já existe!!");
        }
        sensor = new Sensor(id, type);
        entityManager.persist(sensor);
    }

    public List<Sensor> findAll() {
        // remember, maps to: “SELECT s FROM Sensor”
        return entityManager.createNamedQuery("getAllSensors",Sensor.class).getResultList();
    }

    public Sensor find(int id) throws MyEntityNotFoundException {
        var sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor não encontrado");
        }
        return sensor;
    }
    public void updateValue(int id, int valor) throws MyEntityNotFoundException {
        var sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor não encontrado");
        }
        sensor.setValor(valor);
        entityManager.merge(sensor);

    }
}

