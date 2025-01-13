package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("order")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class OrderService {
    @EJB
    private OrderBean orderBean;

    @GET
    @Path("/")
    public List<OrderDTO> getAllOrders() throws MyEntityNotFoundException {
        return OrderDTO.from(orderBean.findAll());
    }

    @POST
    @Path("/")
    public void create(OrderDTO orderDTO) throws MyEntityNotFoundException {
        orderBean.create(
                orderDTO.getCode(),
                orderDTO.getClient(),
                orderDTO.getMorada(),
                orderDTO.getPrecoTotal(),
                orderDTO.getProducts()

        );
    }
}
