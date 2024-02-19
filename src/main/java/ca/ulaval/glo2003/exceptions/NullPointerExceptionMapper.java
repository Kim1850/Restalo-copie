package ca.ulaval.glo2003.exceptions;

import ca.ulaval.glo2003.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {
    @Override
    public Response toResponse(NullPointerException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("MISSING_PARAMETER", e.getMessage()))
                .build();
    }
}
