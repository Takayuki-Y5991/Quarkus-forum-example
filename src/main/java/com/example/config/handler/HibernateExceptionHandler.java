package com.example.config.handler;

import com.example.application.model.response.ApiExceptionResponse;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class HibernateExceptionHandler implements ExceptionMapper<HibernateException> {
    @Override
    public Response toResponse(HibernateException exception) {
        if (exception instanceof ObjectNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
        if (exception instanceof StaleStateException) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ApiExceptionResponse(List.of(exception.getMessage())))
                .build();
    }
}
