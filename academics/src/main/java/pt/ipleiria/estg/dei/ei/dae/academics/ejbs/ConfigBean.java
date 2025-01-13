package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private ProductBean productBean;
    @EJB
    private WarehouseBean warehouseBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populate() {
        System.out.println("Hello Java EE!");

        try {
            warehouseBean.create(1);
            productBean.create(1, "Chocolate", "Comida", "10", 50, 2.79, 1 );
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
