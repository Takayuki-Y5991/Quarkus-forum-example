package com.example.infrastructure.mapper;

import com.example.domain.entity.Account;
import com.example.infrastructure.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface AccountMapper {

    Account toDomain(AccountEntity entity);

    AccountEntity toEntity(Account domain);
}
