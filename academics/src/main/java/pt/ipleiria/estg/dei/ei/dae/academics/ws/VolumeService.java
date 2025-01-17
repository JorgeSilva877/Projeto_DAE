package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.List;

@Path("volume") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class VolumeService {
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ProductBean productBean;

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

        try {
            System.err.println("Buscando volume com ID: " + volumeDTO.getId());
            var volume = volumeBean.find(volumeDTO.getId());
            System.err.println("Volume encontrado: " + volume);

            System.err.println("Obtendo ProductAmount do volume...");
            var productAmount = volume.getProductAmount();
            System.err.println("ProductAmount obtido: " + productAmount);

            System.err.println("Buscando produto com ID: " + productAmount.getProductId());
            var produto = productBean.find(productAmount.getProductId());
            System.err.println("Produto encontrado: " + produto);

            System.err.println("Criando sensor para a categoria: " + produto.getCategory());
            int idSensor = sensorBean.create(produto.getCategory());
            System.err.println("Sensor criado com ID: " + idSensor);

            System.err.println("Associando sensor ao volume...");
            volumeBean.enrollSensorInVolume(idSensor, volumeDTO.getId());
            System.err.println("Sensor associado com sucesso.");

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return Response.ok().build();
    }



}
