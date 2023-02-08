package com.example.infrastructure.mapper;

import com.example.domain.entity.Account;
import com.example.infrastructure.entity.AccountEntity;
import com.example.infrastructure.mapper.custom.AccountMapperUses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", uses = AccountMapperUses.class)
public interface AccountMapper {

    @Mapping(source = "roles", target = "accountRole")
    Account toDomain(AccountEntity entity);

    @Mapping(source = "accountRole", target = "roles")
    AccountEntity toEntity(Account domain);
}
