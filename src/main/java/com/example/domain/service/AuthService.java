package com.example.domain.service;

import io.smallrye.mutiny.Uni;

public interface AuthService {

    Uni<String> authenticate(String accountName, String password);
}
