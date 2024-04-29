package ca.ulaval.glo2003.entities.api.exceptions;

import ca.ulaval.glo2003.api.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {
    @Override
    public Response toResponse(NullPointerException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("MISSING_PARAMETER", exception.getMessage()))
                .build();
    }
}
