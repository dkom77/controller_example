package com.example.balance.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSource h2DataSource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("datasource.driver"))
                .url(env.getProperty("datasource.connection.string"))
                .username(env.getProperty("datasource.username"))
                .password(env.getProperty("datasource.password"))
                .build();
    }
}
