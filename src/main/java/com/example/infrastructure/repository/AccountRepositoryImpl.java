package com.example.infrastructure.repository;

import com.example.domain.entity.Account;
import com.example.domain.repository.AccountRepository;
import com.example.infrastructure.entity.AccountEntity;
import com.example.infrastructure.mapper.AccountMapper;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountRepositoryImpl implements PanacheRepository<AccountEntity>, AccountRepository {

    private final AccountMapper mapper;

    @Inject
    public AccountRepositoryImpl(AccountMapper mapper) {
        this.mapper = mapper;
    }


    public Uni<Account> fetchById(long id) {
        return findById(id)
                .map(mapper::toDomain);
    }

    public Multi<Account> fetchAccounts(int index, int size) {
        return findAll().page(index, size).stream().map(mapper::toDomain);
    }

    public Uni<Account> fetchByAccountName(String name) {
        return find("account_name", name).firstResult().map(mapper::toDomain);
    }

    public Uni<Account> createAccount(Account domain) {
        return persistAndFlush(mapper.toEntity(domain)).map(mapper::toDomain);
    }

    public Uni<Boolean> deleteAccount(long id) {
        return deleteById(id);
    }

    public Uni<Account> updateAccount(Account domain) {
        return persistAndFlush(mapper.toEntity(domain)).map(mapper::toDomain);
    }

    @Override
    public Uni<Mutiny.Session> getSession() {
        return PanacheRepository.super.getSession();
    }

    public Uni<Account> merge(Mutiny.Session session, Account domain) {
        return session.merge(mapper.toEntity(domain)).map(mapper::toDomain);
    }
}
