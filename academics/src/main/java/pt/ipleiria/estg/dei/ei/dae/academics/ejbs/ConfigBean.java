package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.ProductAmount;

import java.awt.geom.QuadCurve2D;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private ProductBean productBean;
    @EJB
    private WarehouseBean warehouseBean;
    @EJB
    private ClientBean clientBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ManagerBean managerBean;
    @EJB
    private EmployeeBean employeeBean;
    @EJB
    private OrderBean orderBean;
    @EJB
    private VolumeBean volumeBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populate() {
        System.out.println("Hello Java EE!");

        try {
            warehouseBean.create(1);
            warehouseBean.create(2);
            productBean.create("Chocolate", "Comida", 10, 50, 2.79, 1 );
            productBean.create("Salsa", "Comida", 5, 500, 1.01, 1 );
            productBean.create("Cerveja", "Comida", 10, 100, 1.99, 2 );
            clientBean.create("Mariozin","123","Camelo","ReiDoGado@gmail.com");
            managerBean.create("Laura01","123","Laura","laurinha@gmail.com");
            employeeBean.create("John","123","John","john@gmail.com",1 );
            employeeBean.create("Mary","123","Mary","mary@gmail.com",2 );
            sensorBean.create("Comida");
            //volumeBean.create(1);
            //volumeBean.create(2);

            List<ProductAmount> produtos = new LinkedList<>();
            produtos.add(new ProductAmount(1, 2));

            orderBean.create("Mariozin","Rua almirante candido dos reis",10, produtos);

            volumeBean.enrrollEmployeeInVolume(1,"John");
            volumeBean.enrollSensorInVolume(1,1);

        }
        catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
