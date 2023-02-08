package com.example.domain.service;

import com.example.domain.entity.Account;
import io.smallrye.mutiny.Uni;

public interface AccountService {
    Uni<Account> fetchAccount(long accountId);
}
