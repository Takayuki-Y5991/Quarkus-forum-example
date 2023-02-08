package com.example.infrastructure.mapper;

import com.example.domain.entity.value.AccountRole;
import com.example.infrastructure.entity.AccountRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AccountRoleMapper {

    @Mapping(source = "id", target = "roleId")
    @Mapping(source = "roleName", target = "roleName")
    AccountRole toDomain(AccountRoleEntity entity);

    @Mapping(source = "roleId", target = "id")
    @Mapping(source = "roleName", target = "roleName")
    AccountRoleEntity toEntity(AccountRole domain);
}
