package com.example.application.resource;

import com.example.application.converter.AccountResourceConverter;
import com.example.application.model.response.AccountResponse;
import com.example.domain.service.AccountService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestQuery;
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
@Tag(name = "Account Operations", description = "Operations for account management")
public class AccountResource {

    private final AccountService accountService;
    private final AccountResourceConverter converter;

    @Inject
    public AccountResource(AccountService accountService, AccountResourceConverter converter) {
        this.accountService = accountService;
        this.converter = converter;
    }


    @GET
    @Operation(description = "Fetch all accounts")
    public Multi<Object> fetchAccounts(@RestQuery int index, @RestQuery int size) {
        return null;
    }

    @GET
    @Path("/{accountId}")
    public Uni<AccountResponse> fetchAccount(@PathParam("accountId") long accountId) {
        return accountService.fetchAccount(accountId).onItem().transform(converter::toResponse);
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