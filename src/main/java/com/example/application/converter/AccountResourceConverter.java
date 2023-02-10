package com.example.application.converter;

import com.example.application.model.request.AccountCreateRequest;
import com.example.application.model.request.AccountUpdateRequest;
import com.example.application.model.response.AccountResponse;
import com.example.domain.entity.Account;
import io.quarkus.runtime.annotations.IgnoreProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountResourceConverter {

    @Mapping(source = "accountRole", target = "roles")
    AccountResponse toResponse(Account account);

    @Mapping(source = "accountRole", target = "accountRole.roleId")
    @IgnoreProperty
    Account toDomain(AccountCreateRequest request);

    @Mapping(source = "accountRole", target = "accountRole.roleId")
    @IgnoreProperty
    Account toDomain(AccountUpdateRequest request);
}
