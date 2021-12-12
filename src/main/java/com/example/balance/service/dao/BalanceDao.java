package com.example.balance.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class BalanceDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BalanceDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String CREATE_BALANCE = "" +
            "INSERT INTO DOMAIN_SCHEMA.BALANCES (LOGIN, BALANCE) VALUES (:login, :balance)";
    private static final String SET_BALANCE = "" +
            "UPDATE DOMAIN_SCHEMA.BALANCES SET BALANCE = :balance WHERE LOGIN = :login";
    private static final String GET_BALANCE = "" +
            "SELECT BALANCE FROM DOMAIN_SCHEMA.BALANCES WHERE LOGIN = :login";

    public Long getBalance(String login) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("login", login);

        return jdbcTemplate.query(GET_BALANCE, sqlParameterSource,
                (rs, rowNum) -> rs.getLong("BALANCE")).stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), (balances -> {
                    if (balances.size() > 1) {
                        throw new IllegalStateException("Ambiguous search result");
                    }
                    return balances.size() == 1 ? balances.get(0) : null;
                })));

    }

    public void setClientBalance(String login, Long balance) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("login", login)
                .addValue("balance", balance);

        Long clientBalance = getBalance(login);
        String expression = clientBalance == null ? CREATE_BALANCE : SET_BALANCE;
        jdbcTemplate.update(expression, sqlParameterSource);
    }
}
