package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.List;

@Path("volume") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class VolumeService {
    @EJB
    private VolumeBean volumeBean;

    @GET
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
    @Path("/enrollSensor")
    public Response enrollSensor(VolumeDTO volumeDTO) {
        volumeBean.enrollSensorInVolume(
                volumeDTO.getId(),
                volumeDTO.getSensorId()
        );
        return Response.ok().build();
    }

    @DELETE
    @Path("/unrollSensor")
    public Response unrollSensor(VolumeDTO volumeDTO) {
        volumeBean.enrollSensorInVolume(
                volumeDTO.getId(),
                volumeDTO.getSensorId()
        );
        return Response.ok().build();
    }

    @POST
    @Path("/enrollEmployee")
    public Response enrollEmployee(VolumeDTO volumeDTO) {
        volumeBean.enrrollEmployeeInVolume(
                volumeDTO.getId(),
                volumeDTO.getEmployeeUsername()
        );
        return Response.ok().build();
    }



}
