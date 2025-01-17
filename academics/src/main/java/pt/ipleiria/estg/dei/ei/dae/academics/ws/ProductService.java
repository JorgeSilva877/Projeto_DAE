package pt.ipleiria.estg.dei.ei.dae.academics.ws;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import java.util.List;

@Path("product") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class ProductService {
    @EJB
    private ProductBean productBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Authenticated
    @Path("/") // means: the relative url path is “/api/product/”
    public List<ProductDTO> getAllProducts() {
        return ProductDTO.from(productBean.findAll());
    }

    @POST
    @Authenticated
    @RolesAllowed({"Manager"})
    @Path("/")
    public Response create(ProductDTO productDTO) {
        productBean.create(
                productDTO.getCode(),
                productDTO.getName(),
                productDTO.getCategory(),
                productDTO.getLimite(),
                productDTO.getStock(),
                productDTO.getPrice(),
                productDTO.getWarehouseId()
        );
        Product product = productBean.find(productDTO.getCode());
        return Response.status(Response.Status.CREATED)
                .entity(ProductDTO.from(product))
                .build();
    }

}
