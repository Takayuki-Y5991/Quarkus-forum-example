package com.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;

    private String accountName;

    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDate birthday;

    private String email;

    private String phoneNumber;

    private String password;
}
