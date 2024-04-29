package ca.ulaval.glo2003.entities.api.exceptions;

import ca.ulaval.glo2003.api.responses.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class ConstraintViolationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("MISSING_PARAMETER", responseBuilder(exception)))
                .build();
    }

    private String responseBuilder(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(". "));
    }
}
