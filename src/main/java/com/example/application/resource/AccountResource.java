package com.example.application.resource;

import com.example.application.converter.AccountResourceConverter;
import com.example.application.model.request.AccountCreateRequest;
import com.example.application.model.request.AccountPasswordChangeRequest;
import com.example.application.model.request.AccountUpdateRequest;
import com.example.application.model.response.AccountResponse;
import com.example.domain.service.AccountService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
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
    @RolesAllowed("Admin")
    @Operation(description = "Fetch all accounts")
    public Multi<AccountResponse> fetchAccounts(@RestQuery int index, @RestQuery int size) {
        return accountService.fetchAccounts(index, size).onItem().transform(converter::toResponse);
    }


    @GET
    @Path("/{accountId}")
    @PermitAll
    @Operation(description = "Fetch account by account_id")
    public Uni<AccountResponse> fetchAccount(@PathParam("accountId") long accountId) {
        return accountService.fetchAccount(accountId).onItem().transform(converter::toResponse);
    }

    // REVIEW: 登録後、Roleに関する情報が取得できていないため、見直しする必要がある。
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    public Uni<AccountResponse> createAccount(@Valid AccountCreateRequest request) {
        return accountService.createAccount(converter.toDomain(request))
                .onItem().transform(converter::toResponse);
    }

    @PATCH
    @Path("/{accountId}")
    @RolesAllowed({"Admin", "Normal"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<AccountResponse> updateAccount(@PathParam("accountId") long accountId, @Valid AccountUpdateRequest request) {
        return accountService.updateAccount(accountId, converter.toDomain(request))
                .onItem().transform(converter::toResponse);
    }

    @PATCH
    @Path("/self/password")
    @RolesAllowed("Normal")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<AccountResponse> changePassword(@Valid AccountPasswordChangeRequest request) {
        return accountService.changePassword(request.currentPassword(), request.newPassword())
                .onItem().transform(converter::toResponse);
    }

    @DELETE
    @Path("/{accountId}")
    @RolesAllowed("Admin")
    @ResponseStatus(RestResponse.StatusCode.NO_CONTENT)
    public Uni<Void> deleteAccount(@PathParam("accountId") long accountId) {
        return accountService.deleteAccount(accountId);
    }
}
