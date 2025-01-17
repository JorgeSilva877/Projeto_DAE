package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.*;
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
    @EJB
    private EmailBean emailBean;


    private static final Logger logger= Logger.getLogger(SensorBean.class.getName());

    public int create(String categoriaProduto) throws MyEntityNotFoundException, MyEntityExistsException {
        int id = findLastId() + 1;

        String type;

        switch (categoriaProduto) {
            case "Comida":
                type = "Temperatura";
                break;

            case "Eletrodoméstico":
                type = "Velocidade";
                break;

            default:
                throw new MyEntityNotFoundException("Categoria não encontrada");
        }

        var sensor = new Sensor(id, type);
        entityManager.persist(sensor);
        return id;
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

        var idVolume = sensor.getVolume().getId();
        Volume volume = volumeBean.find(idVolume);

        Order order = volume.getOrder();
        var orderCode = order.getCode();
        Client client = order.getClient();
        var clientEmail = client.getEmail();

        ProductAmount productAmount = volumeBean.find(idVolume).getProductAmount();
        var productId = productAmount.getProductId();

        Product product = entityManager.find(Product.class, productId);

        int limit = product.getLimite();

        if(valor > limit){
            volume.setOk(false);
            order.setEstado("Devolvida (danos a entregar)");
            emailBean.send(clientEmail, "Order", "The order with code:" + orderCode + " has returned to our facilities due to an issue. The money will be refunded. If you wish, you can place the order again through our website! I apologize for the inconvenience. ");
        }

    }

    public int findLastId() {
        return entityManager.createNamedQuery("getAllSensors", Sensor.class)
                .getResultList()
                .stream()
                .mapToInt(Sensor::getId)
                .max()
                .orElse(0);
    }
}

