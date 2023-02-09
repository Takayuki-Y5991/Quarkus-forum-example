package com.example.application.model.request;

import javax.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull
        String accountName,
        @NotNull
        String password
) {
}
