package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;

import java.util.List;

@Path("sensor") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @GET
    @Path("/")
    public List<SensorDTO> getAllSensors() {
        return SensorDTO.from(sensorBean.findAll());
    }

    @POST
    @Path("/")
    public Response create(SensorDTO sensorDTO) {
        sensorBean.create(
                sensorDTO.getType()
        );
        Sensor sensor = sensorBean.find(sensorDTO.getId());
        return Response.status(Response.Status.CREATED).entity(SensorDTO.from(sensor)).build();
    }

    @GET
    @Path("/{id}")
    public SensorDTO getSensor(@PathParam("id") int id) {
        return SensorDTO.from(sensorBean.find(id));
    }

    @PATCH
    @Path("/{id}")
    public void updateValor(@PathParam("id") int id, SensorDTO sensorDTO) {
        sensorBean.updateValue(id, sensorDTO.getValor());
    }

}
