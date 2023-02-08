package com.example.resource;

import com.example.infrastructure.entity.Account;
import com.example.infrastructure.entity.AccountRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/v1/account")
public class AccountResource {

    private final AccountRepository repository;

    @Inject
    public AccountResource(AccountRepository repository) {
        this.repository = repository;
    }

    @GET
    public Multi<Object> fetchAccounts() {
        return null;
    }

    @GET
    @Path("/{accountId}")
    public Uni<Account> fetchAccount(@PathParam("accountId") long accountId) {
        return repository.fetchById(accountId);
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
    public Uni<Object> updateAccount(Object obj) {
        return null;
    }

    @PATCH
    @Path("/self/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Object> changePassword(Object obj) {
        return null;
    }

    @DELETE
    @Path("/{accountId}")
    @ResponseStatus(RestResponse.StatusCode.NO_CONTENT)
    public Uni<Void> deleteAccount(@PathParam("accountId") long accountId) {
        return null;
    }
}
