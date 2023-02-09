package com.example.config;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.Map;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {

    static DockerImageName postgresImage = DockerImageName.parse("postgres:15-alpine").asCompatibleSubstituteFor("postgres");
    static PostgreSQLContainer<?> db = new PostgreSQLContainer<>(postgresImage)
            .withDatabaseName("forum")
            .withUsername("quarkus")
            .withPassword("password")
            .withInitScript("testing-data/init-test-data.sql");

    @Override
    public Map<String, String> start() {
        db.start();
        return Collections.singletonMap(
                "quarkus.datasource.reactive.url", db.getJdbcUrl().replace("jdbc:", ""));
    }

    @Override
    public void stop() {
        db.stop();
    }
}
