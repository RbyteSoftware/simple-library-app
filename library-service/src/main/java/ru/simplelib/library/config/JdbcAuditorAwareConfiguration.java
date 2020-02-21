package ru.simplelib.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@Configuration
@EnableJdbcAuditing
public class JdbcAuditorAwareConfiguration extends AbstractJdbcConfiguration {

//    @Bean
//    public AuditorAware<User> auditorProvider() {
//        return new SpringSecurityAuditorAware();
//    }

}
