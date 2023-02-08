package com.example.domain.service.impl;

import com.example.domain.entity.Account;
import com.example.domain.repository.AccountRepository;
import com.example.domain.service.AccountService;
import io.smallrye.mutiny.Uni;
import org.hibernate.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Uni<Account> fetchAccount(long accountId) {
        return accountRepository.fetchById(accountId)
                .onItem().ifNull().failWith(() -> new ObjectNotFoundException(accountId, "Account"));
    }
}
