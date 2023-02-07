package com.example.resource;

import com.oracle.svm.core.annotate.Delete;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/account")
public class AccountResource {

    @GET
    @ResponseStatus(RestResponse.StatusCode.OK)
    public Uni<List> fetchAccounts() {
        return null;
    }

    @GET
    @Path("/{accountId}")
    @ResponseStatus(RestResponse.StatusCode.OK)
    public Uni<Object> fetchAccount(@PathParam("accountId") long accountId) {
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    public Uni<Object> createAccount(Object obj) {
        return null;
    }

    @PATCH
    @Path("/{accountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(RestResponse.StatusCode.OK)
    public Uni<Object> updateAccount(Object obj) {
        return null;
    }

    @PATCH
    @Path("/self/password")
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(RestResponse.StatusCode.OK)
    public Uni<Object> changePassword(Object obj) {
        return null;
    }

    @Delete
    @Path("/{accountId}")
    @ResponseStatus(RestResponse.StatusCode.NO_CONTENT)
    public Uni<Void> deleteAccount(@PathParam("accountId") long accountId) {
        return null;
    }
}
