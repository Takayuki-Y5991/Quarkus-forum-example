package com.example.application.resource;

import com.example.application.model.request.AuthRequest;
import com.example.application.model.response.AuthResponse;
import com.example.domain.service.AuthService;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/v1/auth")
@Tag(name = "Authenticate Operations", description = "Operations for Authentication")
public class AuthResource {

    private final AuthService authService;

    @Inject
    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    @Operation(description = "Create JWT Token from UserInformation")
    public Uni<AuthResponse> login(AuthRequest request) {
        //TODO: will Delete
        return authService.authenticate(request.accountName(), request.password()).onItem().transform(AuthResponse::new);
    }
}
