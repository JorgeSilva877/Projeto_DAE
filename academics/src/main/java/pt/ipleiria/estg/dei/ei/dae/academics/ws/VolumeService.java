package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import java.util.List;

@Path("volume") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"Manager", "Employee"})
public class VolumeService {
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ProductBean productBean;

    @GET
    @RolesAllowed({"Manager"})
    @Path("/")
    public List<VolumeDTO> getAllVolumes() {
        return VolumeDTO.from(volumeBean.findAll());
    }

    @GET
    @Path("{id}")
    public VolumeDTO getVolume(@PathParam("id") int id) {
        return VolumeDTO.from(volumeBean.find(id));
    }

//    @POST
//    @Path("/")
//    public void create(VolumeDTO volumeDTO) {
//        volumeBean.create(
//                volumeDTO.getId()
//        );
//    }

    @POST
    @Path("/enrollEmployee")
    public Response enrollEmployee(VolumeDTO volumeDTO) {
        volumeBean.enrrollEmployeeInVolume(
                volumeDTO.getId(),
                volumeDTO.getEmployeeUsername()
        );

        try {
            var volume = volumeBean.find(volumeDTO.getId());
            var productAmount = volume.getProductAmount();
            var produto = productBean.find(productAmount.getProductId());
            int idSensor = sensorBean.create(produto.getCategory());

            volumeBean.enrollSensorInVolume(idSensor, volumeDTO.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return Response.ok().build();
    }

}
