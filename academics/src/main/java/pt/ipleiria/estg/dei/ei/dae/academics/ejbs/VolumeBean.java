package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Lob;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.*;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.logging.Logger;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;


    private static final Logger logger = Logger.getLogger("VolumeBean.logger");

    public void create(int id, long productAmmountId, Order order){
        var volume = entityManager.find(Volume.class, id);
        if (volume != null) {
            throw new RuntimeException("Volume already exists");
        }

        var productAmount = entityManager.find(ProductAmount.class, productAmmountId);
        if (productAmount == null) {
            throw new RuntimeException("productAmount doesnt exists");
        }

        volume = new Volume(id, productAmount, order);
        entityManager.persist(volume);
    }

    public Volume find(int id) {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("Volume not found");
        }
        return volume;
    }

    public int findLastId() {
        return entityManager.createNamedQuery("getAllVolums", Volume.class)
                .getResultList()
                .stream()
                .mapToInt(Volume::getId)
                .max()
                .orElse(0);
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

        int warehouseIdEmp = employee.getWarehouse().getId();
        int productId = volume.getProductAmount().getProductId();
        int warehouseIdProd = entityManager.find(Product.class, productId).getWarehouse().getId();
        if(warehouseIdEmp != warehouseIdProd){
            throw new RuntimeException("Employee and product are not in the same warehouse");
        }

        volume.addEmployee(employee);
        employee.addVolume(volume);
        entityManager.merge(volume);
        entityManager.merge(employee);

        var order = volume.getOrder();
        for (Volume volumeOrder : order.getVolumes()){
            if (volumeOrder.getEmployee() == null){
                return;
            }
        }

        order.setEstado("Empacotada e enviada!");

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

    public List<Volume> findAllByWarehouseEmployee(int idWarehouseEmployee) {
        List<Volume> volumes = entityManager.createNamedQuery("getAllVolums", Volume.class).getResultList();

        for (Volume volume : volumes) {
            ProductAmount productAmmount = volume.getProductAmount();
            Product product = entityManager.find(Product.class, productAmmount.getProductId());
            if(product.getWarehouse().getId() != idWarehouseEmployee || volume.getEmployee() != null){
                volumes.remove(volume);
            }
        }
        return volumes;
    }
}
