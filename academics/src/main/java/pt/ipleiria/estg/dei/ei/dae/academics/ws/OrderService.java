package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
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
    public Response create(OrderDTO orderDTO) throws MyEntityNotFoundException {
        orderBean.create(
                orderDTO.getCode(),
                orderDTO.getClient(),
                orderDTO.getMorada(),
                orderDTO.getPrecoTotal(),
                orderDTO.getProductsId()
        );

        Order order = orderBean.find(orderDTO.getCode());
        return Response.status(Response.Status.CREATED)
                .entity(OrderDTO.from(order))
                .build();
    }
}
