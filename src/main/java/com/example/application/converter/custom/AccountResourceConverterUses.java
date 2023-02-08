package com.example.application.converter.custom;

import com.example.application.model.response.AccountResponse;
import com.example.domain.entity.value.AccountRole;

public class AccountResourceConverterUses {

    public AccountResponse.AccountRole toResponse(AccountRole role) {
        return new AccountResponse.AccountRole(role.getRoleId(), role.getRoleName());
    }
}
