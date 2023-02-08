package com.example.infrastructure.entity;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {

    public Uni<Account> fetchById(long id) {
        return findById(id);
    }
}
