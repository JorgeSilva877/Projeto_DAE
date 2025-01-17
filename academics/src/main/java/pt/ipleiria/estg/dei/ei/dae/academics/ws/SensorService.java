package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import java.util.List;

@Path("sensor") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @GET
    @Authenticated
    @RolesAllowed({"Manager", "Employee"})
    @Path("/")
    public List<SensorDTO> getAllSensors() {
        return SensorDTO.from(sensorBean.findAll());
    }

    @POST
    @Authenticated
    @RolesAllowed({"Manager", "Employee"})
    @Path("/")
    public Response create(SensorDTO sensorDTO) {
        sensorBean.create(
                sensorDTO.getType()
        );
        Sensor sensor = sensorBean.find(sensorDTO.getId());
        return Response.status(Response.Status.CREATED).entity(SensorDTO.from(sensor)).build();
    }

    @GET
    @Authenticated
    @RolesAllowed({"Manager", "Employee"})
    @Path("/{id}")
    public SensorDTO getSensor(@PathParam("id") int id) {
        return SensorDTO.from(sensorBean.find(id));
    }

    //update do valor do sensor
    @PATCH
    @Path("/{id}")
    public Response updateValor(@PathParam("id") int id, SensorDTO sensorDTO) {
        sensorBean.updateValue(id, sensorDTO.getValor());
        return Response.ok().build();
    }

}
