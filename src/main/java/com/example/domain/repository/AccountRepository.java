package com.example.domain.repository;

import com.example.domain.entity.Account;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface AccountRepository {
    Uni<Account> fetchById(long id);

    Multi<Account> fetchAccounts(int index, int size);

    Uni<Account> fetchByAccountName(String name);

    Uni<Account> createAccount(Account domain);

    Uni<Boolean> deleteAccount(long id);

    Uni<Account> updateAccount(long id, Account domain);
}
