package com.example.application.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public record AccountResponse(
        Long id,
        String accountName,
        String firstName,
        String middleName,
        String lastName,
        LocalDate birthday,
        String email,
        String phoneNumber,
        @JsonIgnore
        String password,
        AccountRole roles
) {
    public record AccountRole(
            long roleId,
            String roleName
    ) {
    }
}
