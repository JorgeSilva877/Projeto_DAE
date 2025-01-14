package pt.ipleiria.estg.dei.ei.dae.academics.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {

    private static final Logger logger = Logger.getLogger(ExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(Exception e) {
        String errorMessage = e.getMessage();
        logger.warning("ERROR: " + errorMessage);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();

    }
}
