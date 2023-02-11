package com.example.domain.service.impl;

import com.example.config.exception.AuthorizationException;
import com.example.domain.entity.Account;
import com.example.domain.repository.AccountRepository;
import com.example.domain.service.AccountService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.hibernate.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.BiPredicate;

@ApplicationScoped
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final JsonWebToken jsonWebToken;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository, JsonWebToken jsonWebToken) {
        this.accountRepository = accountRepository;
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    public Uni<Account> fetchAccount(long accountId) {
        return accountRepository.fetchById(accountId)
                .onItem().ifNull().failWith(() -> new ObjectNotFoundException(accountId, "Account"))
                .chain(account -> {
                    if (!jsonWebToken.getGroups().contains("Admin") && !jsonWebToken.getName().equals(account.getAccountName())) {
                        throw new AuthorizationException("Failure call, Only my information can be verified.");
                    }
                    return Uni.createFrom().item(account);
                });
    }

    @Override
    @ReactiveTransactional
    public Uni<Account> createAccount(Account account) {
        account.setPassword(
                BcryptUtil.bcryptHash(account.getPassword()));
        return accountRepository.createAccount(account);
    }

    @Override
    public Multi<Account> fetchAccounts(int index, int size) {
        return accountRepository.fetchAccounts(index, size);
    }

    @Override
    @ReactiveTransactional
    public Uni<Void> deleteAccount(long accountId) {
        return accountRepository.deleteAccount(accountId)
                .chain(() -> {
                    return Uni.createFrom().voidItem();
                });
    }

    @Override
    @ReactiveTransactional
    public Uni<Account> updateAccount(long accountId, Account account) {
        return accountRepository.fetchById(accountId)
                .onItem().ifNull().failWith(() -> new ObjectNotFoundException(accountId, "Account"))
                .chain(e -> {
                    if (!jsonWebToken.getGroups().contains("Admin") && !jsonWebToken.getName().equals(e.getAccountName())) {
                        throw new AuthorizationException("Failure call, Only my information can be verified.");
                    }
                    account.setId(accountId);
                    account.setPassword(e.getPassword());
                    return accountRepository.getSession();
                }).chain(session -> accountRepository.merge(session, account));
    }

    @Override
    @ReactiveTransactional
    public Uni<Account> changePassword(String currentPassword, String newPassword) {
        return accountRepository.fetchByAccountName(jsonWebToken.getName())
                .onItem().transform(account -> {
                    if (!matchPassword.test(account.getPassword(), currentPassword)) {
                        // REVIEW: Exception 見直し予定
                        throw new AuthorizationException("\"Current password does not match");
                    }
                    account.setPassword(BcryptUtil.bcryptHash(newPassword));
                    return account;
                }).chain(account -> accountRepository.updatePassword(account));
    }

    private final BiPredicate<String, String> matchPassword = (storedPassword, inputPassword) -> {
        return BcryptUtil.matches(inputPassword, storedPassword);
    };
}
