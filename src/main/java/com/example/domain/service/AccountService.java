package com.example.domain.service;

import com.example.domain.entity.Account;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface AccountService {
    Uni<Account> fetchAccount(long accountId);

    Uni<Account> createAccount(Account account);

    Multi<Account> fetchAccounts(int index, int size);

    Uni<Void> deleteAccount(long accountId);

    Uni<Account> updateAccount(long accountId, Account account);

    Uni<Account> changePassword(String currentPassword, String newPassword);
}
