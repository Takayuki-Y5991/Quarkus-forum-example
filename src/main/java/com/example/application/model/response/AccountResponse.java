package com.example.application.model.response;

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
        String password
) {
}
