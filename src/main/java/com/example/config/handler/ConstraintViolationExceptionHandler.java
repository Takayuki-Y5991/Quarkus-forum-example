package com.example.config.handler;

import com.example.application.model.response.ApiExceptionResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return convertApiExceptionResponse(exception);
    }

    private Response convertApiExceptionResponse(ConstraintViolationException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ApiExceptionResponse(
                        ex.getConstraintViolations().stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.toList())
                )).build();
    }
}
