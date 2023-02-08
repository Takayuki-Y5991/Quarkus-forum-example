package com.example.domain.service.impl;

import com.example.domain.entity.Account;
import com.example.domain.repository.AccountRepository;
import com.example.domain.service.AuthService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.AuthenticationFailedException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.BiFunction;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    private final String issuer;
    private final AccountRepository accountRepository;

    @Inject
    public AuthServiceImpl(@ConfigProperty(name = "mp.jwt.verify.issuer") String issuer, AccountRepository accountRepository) {
        this.issuer = issuer;
        this.accountRepository = accountRepository;
    }

    @Override
    public Uni<String> authenticate(String accountName, String password) {
        return accountRepository.fetchByAccountName(accountName)
                .onItem()
                .transform(account -> {
                    if (Objects.isNull(account) || !comparePassword.apply(account, password)) {
                        throw new AuthenticationFailedException("Invalid credentials");
                    }
                    return Jwt.issuer(issuer)
                            .upn(account.getAccountName())
                            .groups(new HashSet<>() {
                                {
                                    add(account.getAccountRole().getRoleName());
                                }
                            })
                            .expiresIn(Duration.ofHours(1L))
                            .sign();
                });
    }

    private final BiFunction<Account, String, Boolean> comparePassword = (store, current) -> {
        return BcryptUtil.matches(current, store.getPassword());
    };
}
