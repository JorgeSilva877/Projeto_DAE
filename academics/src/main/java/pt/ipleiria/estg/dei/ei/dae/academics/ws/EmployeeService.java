package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.EmployeeDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EmployeeBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Employee;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import java.util.List;

@Path("employee") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"Manager", "Employee"})
public class EmployeeService {
    @EJB
    private EmployeeBean employeeBean;

    @GET
    @Authenticated
    @RolesAllowed({"Manager"})
    @Path("/")
    public List<EmployeeDTO> getAllEmployees() {
        return EmployeeDTO.from(employeeBean.findAll());
    }

    @POST
    @Authenticated
    @RolesAllowed({"Manager"})
    @Path("/")
    public Response createEmployee(EmployeeDTO employeeDTO) {
        employeeBean.create(
                employeeDTO.getUsername(),
                employeeDTO.getPassword(),
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getWarehouseId()
        );
        return Response.ok().build();
    }

    @GET
    @Authenticated
    @Path("/{username}")
    public EmployeeDTO getEmploy(@PathParam("username") String username) {
        return EmployeeDTO.from(employeeBean.find(username));
    }


    @GET
    @Authenticated
    @Path("/{idWarehouseEmployee}/volumes")
    public List<VolumeDTO> getAllVolumesByWarehouseEmployee(@PathParam("idWarehouseEmployee") int idWarehouseEmployee) {
        return VolumeDTO.from(employeeBean.findAllByWarehouseEmployee(idWarehouseEmployee));
    }


}
