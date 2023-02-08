package com.example.application.converter;

import com.example.application.model.response.AccountResponse;
import com.example.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AccountResourceConverter {

    @Mapping(source = "accountRole", target = "roles")
    AccountResponse toResponse(Account account);
}
