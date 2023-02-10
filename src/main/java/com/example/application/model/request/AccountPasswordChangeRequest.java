package com.example.application.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AccountPasswordChangeRequest(
        @NotNull
        @NotBlank
        String currentPassword,
        @NotNull
        @NotBlank
        String newPassword
) {
}
