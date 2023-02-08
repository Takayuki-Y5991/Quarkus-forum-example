package com.example.application.resource;

import com.example.application.model.request.AuthRequest;
import com.example.domain.service.AuthService;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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
    @Path("/login")
    public Uni<String> login(AuthRequest request) {
        return authService.authenticate(request.accountName(), request.password());
    }
}
