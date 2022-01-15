package com.bortoti.cleanstock.adapter.out.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;

public class CustomNamingStrategy implements PhysicalNamingStrategy, Serializable {
    @Serial
    private static final long serialVersionUID = -5937286882099274612L;

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        String nameText = name == null ? "" : name.getText();
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        String nameText = name == null ? "" : name.getText();
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String nameText = name == null ? "" : name.getText().replace("JpaEntity", "").toLowerCase(Locale.ROOT);
        return Identifier.toIdentifier(nameText);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        String nameText = name == null ? "" : name.getText();
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String nameText = name == null ? "" : name.getText();
        return name;
    }
}