package com.example.infrastructure.mapper.custom;

import com.example.domain.entity.value.AccountRole;
import com.example.infrastructure.entity.AccountRoleEntity;
import com.example.infrastructure.mapper.AccountRoleMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountMapperUses {

    @Inject
    private AccountRoleMapper accountRoleMapper;

    public AccountRole toDomain(AccountRoleEntity entity) {
        return accountRoleMapper.toDomain(entity);
    }

    public AccountRoleEntity toEntity(AccountRole domain) {
        return accountRoleMapper.toEntity(domain);
    }
}
