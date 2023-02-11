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


    @Override
    public Uni<Account> fetchById(long id) {
        return findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Multi<Account> fetchAccounts(int index, int size) {
        return findAll().page(index, size).stream().map(mapper::toDomain);
    }

    @Override
    public Uni<Account> fetchByAccountName(String name) {
        return find("account_name", name).firstResult().map(mapper::toDomain);
    }

    @Override
    public Uni<Account> createAccount(Account domain) {
        return persistAndFlush(mapper.toEntity(domain)).map(mapper::toDomain);
    }

    @Override
    public Uni<Boolean> deleteAccount(long id) {
        return deleteById(id);
    }

    @Override
    public Uni<Account> updatePassword(Account domain) {
        return update("password = ?1 Where id = ?2", domain.getPassword(), domain.getId())
//                Parameters.with("password", domain.getPassword()),
//                Parameters.with("accountId", domain.getId()))
                .chain(() -> findById(domain.getId()).map(mapper::toDomain));
//        return persist(mapper.toEntity(domain)).map(mapper::toDomain);
    }

    @Override
    public Uni<Mutiny.Session> getSession() {
        return PanacheRepository.super.getSession();
    }

    @Override
    public Uni<Account> merge(Mutiny.Session session, Account domain) {
        return session.merge(mapper.toEntity(domain)).map(mapper::toDomain);
    }
}
