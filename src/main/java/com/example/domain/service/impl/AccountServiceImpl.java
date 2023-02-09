package com.example.domain.service.impl;

import com.example.domain.entity.Account;
import com.example.domain.repository.AccountRepository;
import com.example.domain.service.AccountService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Slf4j
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

    @Override
    @ReactiveTransactional
    public Uni<Account> createAccount(Account account) {
        account.setPassword(
                BcryptUtil.bcryptHash(account.getPassword()));
        return accountRepository.createAccount(account);
    }
}
