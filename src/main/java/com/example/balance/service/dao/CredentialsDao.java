package com.example.balance.service.dao;

import com.example.balance.service.exceptions.ClientCreationException;
import com.example.balance.service.model.ResponseCode;
import com.example.balance.service.model.Secret;
import com.example.balance.service.util.HashProvider;
import com.example.balance.service.util.ResponseComposer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CredentialsDao {

    private static final String SAVE_CREDENTIALS = "" +
            "INSERT INTO DOMAIN_SCHEMA.CREDENTIALS (LOGIN, PASSWORD_HASH, SALT_HASH) VALUES (:login, :password, :salt)";
    private static final String GET_SECRET = "SELECT PASSWORD_HASH, SALT_HASH FROM DOMAIN_SCHEMA.CREDENTIALS " +
            "WHERE LOGIN = :login";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static Logger log = LoggerFactory.getLogger(CredentialsDao.class);

    public CredentialsDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCredentials(String login, String password) {
        Secret secret = HashProvider.calculateHashForString(password);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("login", login)
                .addValue("password", secret.getPasswordHash())
                .addValue("salt", secret.getSalt());
        try {
            jdbcTemplate.update(SAVE_CREDENTIALS, sqlParameterSource);
        } catch (Exception e) {
            log.warn("Failed to save credentials for client {} reason: {}", login, e.getMessage());
            throw new ClientCreationException(
                    ResponseComposer.compose(ResponseCode.TECHNICAL_ERROR, "Failed to create client"));
        }
    }

    public Secret getSecret(String login) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("login", login);

        return jdbcTemplate.query(GET_SECRET, sqlParameterSource, (rs, rowNum) -> {
            String passwordHash = rs.getString("PASSWORD_HASH");
            String salt = rs.getString("SALT_HASH");
            return new Secret(passwordHash, salt);
        }).stream().collect(Collectors.collectingAndThen(Collectors.toList(), (secrets -> {
            if (secrets.size() > 1) {
                throw new IllegalStateException("Ambiguous search result");
            }
            return secrets.size() == 1 ? secrets.get(0) : null;
        })));
    }
}
