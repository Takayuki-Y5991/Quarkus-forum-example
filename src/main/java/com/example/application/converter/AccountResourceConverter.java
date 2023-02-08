package com.example.application.converter;

import com.example.application.model.response.AccountResponse;
import com.example.domain.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface AccountResourceConverter {

    AccountResponse toResponse(Account account);
}
