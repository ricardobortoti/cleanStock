package com.bortoti.cleanstock.testcontainers;

import org.testcontainers.containers.Container;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MysqlTestContainer extends MySQLContainer<MysqlTestContainer> {
    private static final String IMAGE_VERSION = "mysql:8.0";
    private static MysqlTestContainer container;

    private MysqlTestContainer() {
        super(IMAGE_VERSION);
    }

    public static MysqlTestContainer getInstance() {
        if (container == null) {
            container = new MysqlTestContainer()
                    .withUsername("root")
                    .withPassword("root")
                    .withDatabaseName("cleanstock");

        }

        return container;
    }

    @Override
    public void start() {
        super.start();

        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
