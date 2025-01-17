package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import java.beans.Transient;
import java.util.List;

@Path("client")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class ClientService {

    @Context
    private SecurityContext securityContext;

    @EJB
    private ClientBean clientBean;

    @GET
    @Authenticated
    @RolesAllowed({"Manager"})
    @Path("/")
    public List<ClientDTO> getAllClients() throws MyEntityNotFoundException {
        return ClientDTO.fromClient(clientBean.findAll());
    }


    @POST
    @Path("/")
    public Response create(ClientDTO clientDTO) throws MyEntityNotFoundException {
        clientBean.create(
                clientDTO.getUsername(),
                clientDTO.getPassword(),
                clientDTO.getName(),
                clientDTO.getEmail()

        );

        Client client = clientBean.find(clientDTO.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(ClientDTO.from(client))
                .build();
    }

    @GET
    @Authenticated
    @RolesAllowed({"Client"})
    @Path("{username}/orders")
    public Response getClientOrders(@PathParam("username") String username) throws MyEntityNotFoundException {
        var principal = securityContext.getUserPrincipal();
        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        var orders = clientBean.findWithOrders(username);
        return Response.ok(OrderDTO.from(orders)).build();
    }

}
