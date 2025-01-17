package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.EmployeeDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EmployeeBean;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import java.util.List;

@Path("employee") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@RolesAllowed({"Manager"})
public class EmployeeService {
    @EJB
    private EmployeeBean employeeBean;

    @GET
    @Authenticated
    @Path("/")
    public List<EmployeeDTO> getAllEmployees() {
        return EmployeeDTO.from(employeeBean.findAll());
    }

    @POST
    @Authenticated
    @Path("/")
    public Response createEmployee(EmployeeDTO employeeDTO) {
        employeeBean.create(
                employeeDTO.getUsername(),
                employeeDTO.getPassword(),
                employeeDTO.getName(),
                employeeDTO.getEmail()
        );
        return Response.ok().build();
    }


}
