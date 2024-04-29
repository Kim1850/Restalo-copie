package ca.ulaval.glo2003.entities.api.exceptions;

import ca.ulaval.glo2003.api.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("UNEXPECTED_ERROR", exception.getMessage()))
                .build();
    }
}
