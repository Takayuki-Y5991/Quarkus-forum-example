package com.example.application.resource;

import io.smallrye.mutiny.Multi;
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

@Path("/v1/topic")
public class TopicResource {

    @GET
    public Multi<Object> fetchTopics() {
        return null;
    }

    @GET
    @Path("/{topicId}")
    public Uni<Object> fetchTopic(@PathParam("topicId") long topicId) {
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    public Uni<Object> createTopic() {
        return null;
    }

    @PATCH
    @Path("/{topicId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Object> updateTopic(@PathParam("topicId") long topicId) {
        return null;
    }

    @DELETE
    @Path("/{topicId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(RestResponse.StatusCode.NO_CONTENT)
    public Uni<Void> deleteTopic(@PathParam("topicId") long topicId) {
        return null;
    }

}
