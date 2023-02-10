package com.example.config.handler;

import com.example.config.exception.AuthorizationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthorizationExceptionHandler implements ExceptionMapper<AuthorizationException> {
    @Override
    public Response toResponse(AuthorizationException exception) {
        return convertApiExceptionResponse(exception);
    }

    private Response convertApiExceptionResponse(AuthorizationException ex) {
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(ex.getMessage())
                .build();
    }
}
