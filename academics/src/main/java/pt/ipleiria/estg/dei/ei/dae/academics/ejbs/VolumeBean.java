package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Lob;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
        // Encontrar o Volume
        var volume = entityManager.find(Volume.class, volume_id);
        if (volume == null) {
            throw new RuntimeException("Volume not found");
        }
        System.out.println("Buscando volume com ID: " + volume.getId());

        // Encontrar o Employee
        var employee = entityManager.find(Employee.class, username);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        System.out.println("Buscando employ com ID: " + employee.getUsername());

        // Garantir que o Employee esteja gerenciado
        employee = entityManager.merge(employee); // Isso já vai garantir que o Employee está no contexto de persistência
        System.out.println("Teste 1");

        // Verificar se o ProductAmount está associado ao Volume
        var productAmount = volume.getProductAmount();
        if (productAmount == null) {
            throw new RuntimeException("ProductAmount is not associated with the volume");
        }
        System.out.println("Teste 2");

        // Verificar se o Employee e o Product estão no mesmo armazém
        int warehouseIdEmp = employee.getWarehouse().getId();
        System.out.println("Id warehouse: " + warehouseIdEmp);
        int productId = productAmount.getProductId();
        System.out.println("Id product: " + productId);
        int warehouseIdProd = entityManager.find(Product.class, productId).getWarehouse().getId();
        System.out.println("Id warehouse 2: " + warehouseIdProd);
        if (warehouseIdEmp != warehouseIdProd) {
            throw new RuntimeException("Employee and product are not in the same warehouse");
        }
        System.out.println("Warehouses sao iguais");

        // Associa o Employee ao Volume
        volume.setEmployee(employee);
        System.out.println("TESTE 1");
        employee.addVolume(volume); // Adiciona o Volume ao Employee
        System.out.println("TESTE 2");
        // Não é necessário chamar o entityManager.merge(volume) aqui, porque o Volume já está gerenciado

        // Verificar o status do pedido
        checkOrderCompletion(volume.getOrder());
        System.out.println("TESTE 3");
    }


    @Transactional
    public void checkOrderCompletion(Order order) {
        for (Volume volumeOrder : order.getVolumes()){
            if (volumeOrder.getEmployee() == null){
                return;
            }
        }
        order.setEstado("Empacotada e enviada!");
        entityManager.merge(order);
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
