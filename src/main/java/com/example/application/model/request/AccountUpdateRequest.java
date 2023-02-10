package com.example.application.model.request;

import com.example.config.annotation.ContactNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public record AccountUpdateRequest(
        long id,
        @NotNull
        @Size(min = 1, max = 20)
        String accountName,
        @NotNull
        @Size(min = 1, max = 20)
        String firstName,

        String middleName,
        @NotNull
        @Size(min = 1, max = 20)
        String lastName,

        @NotNull
        @Past
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthday,

        @NotNull
        @Email
        String email,

        @NotNull
        @ContactNumber
        String phoneNumber,

        @NotNull
        @Min(1)
        Long accountRole
) {
}
