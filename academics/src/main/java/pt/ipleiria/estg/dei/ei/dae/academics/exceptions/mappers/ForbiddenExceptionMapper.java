package pt.ipleiria.estg.dei.ei.dae.academics.exceptions.mappers;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<jakarta.ws.rs.ForbiddenException> {
    private static Logger logger =
            Logger.getLogger(jakarta.ws.rs.ForbiddenException.class.getCanonicalName());

    @Override
    public Response toResponse(ForbiddenException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.FORBIDDEN)
                .entity(errorMsg)
                .build();
    }
}
