package com.example.application.resource;

import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/v1/comment")
public class CommentResource {

    @DELETE
    @Path("/{commentId}")
    @ResponseStatus(RestResponse.StatusCode.NO_CONTENT)
    public Uni<Object> deleteComment(@PathParam("commentId") long commentId) {
        return null;
    }

    @PATCH
    @Path("/{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Object> updateComment(@PathParam("commentId") long commentId) {
        return null;
    }

    @GET
    @Path("/{topicId}")
    public Uni<Object> fetchComments(@PathParam("topicId") long topicId) {
        return null;
    }


    @POST
    @Path("/{topicId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    public Uni<Object> createComment(@PathParam("topicId") long topicId) {
        return null;
    }
}
