package ru.simplelib.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJdbcRepositories("ru.simplelib.library.repositories")
public class Datasource {

    @Bean
    public Map<String, SimpleJdbcInsertOperations> insertMap() {
        return new HashMap<String, SimpleJdbcInsertOperations>() {{
            put("userInsert", userJdbcInsert());
            put("personInsert", personJdbcInsert());
            put("roleRefInsert", roleRefJdbcInsert());
        }};

    }

    public SimpleJdbcInsertOperations userJdbcInsert() {
        return new SimpleJdbcInsert(dataSource())
                .withTableName("User")
                .usingColumns("login", "password")
                .usingGeneratedKeyColumns("id");
    }

    public SimpleJdbcInsertOperations personJdbcInsert() {
        return new SimpleJdbcInsert(dataSource())
                .withTableName("Person")
                .usingColumns("userId", "firstName", "lastName", "email")
                .usingGeneratedKeyColumns("id");
    }

    public SimpleJdbcInsertOperations roleRefJdbcInsert() {
        return new SimpleJdbcInsert(dataSource())
                .withTableName("UserRoles")
                .usingColumns("userId", "roleId");
    }


    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }
}
