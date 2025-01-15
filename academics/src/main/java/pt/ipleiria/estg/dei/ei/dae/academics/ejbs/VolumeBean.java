package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Lob;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Employee;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.logging.Logger;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger("VolumeBean.logger");

    public void create(int id){
        var volume = entityManager.find(Volume.class, id);
        if (volume != null) {
            throw new RuntimeException("Volume already exists");
        }
        volume = new Volume(id);
        entityManager.persist(volume);
    }

    public Volume find(int id) {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("Volume not found");
        }
        return volume;
    }

    public List<Volume> findAll() {
        return entityManager.createNamedQuery("getAllVolums", Volume.class).getResultList();
    }

    public void enrrollEmployeeInVolume(int volume_id, String username) {
        var volume = entityManager.find(Volume.class, volume_id);
        if (volume == null) {
            throw new RuntimeException("Volume not found");
        }
        var employee = entityManager.find(Employee.class, username);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        volume.addEmployee(employee);
        employee.addVolume(volume);
    }

    public void enrollSensorInVolume(int sensor_id, int volume_id) throws MyEntityNotFoundException {
        var sensor = entityManager.find(Sensor.class, sensor_id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor não encontrado");
        }

        var volume = entityManager.find(Volume.class, volume_id);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume não encontrado");
        }
        sensor.addVolume(volume);
        sensor.setActive(true);
        volume.addSensor(sensor);
        entityManager.merge(sensor);
        entityManager.merge(volume);
    }

    public void unrollSensorFromVolume(int sensor_id, int volume_id) throws MyEntityNotFoundException {
        var sensor = entityManager.find(Sensor.class, sensor_id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor não encontrado");
        }

        var volume = entityManager.find(Volume.class, volume_id);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume não encontrado");
        }
        sensor.removeVolume(volume);
        sensor.setActive(false);
        volume.removeSensor(sensor);
        entityManager.merge(sensor);
        entityManager.merge(volume);
    }

}
