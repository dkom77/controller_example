package com.example.balance.service.dao;

import com.example.balance.service.exceptions.ClientCreationException;
import com.example.balance.service.model.ResponseCode;
import com.example.balance.service.util.ResponseComposer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public class ClientsDao{
    private static final String ADD_CLIENT = "INSERT INTO DOMAIN_SCHEMA.CLIENTS (LOGIN) VALUES (:login)";
    private static final String GET_CLIENT = "SELECT LOGIN FROM DOMAIN_SCHEMA.CLIENTS WHERE LOGIN = :login";

    private final static Logger log = LoggerFactory.getLogger(ClientsDao.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ClientsDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertClient(String login) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("login", login);

        try {
            jdbcTemplate.update(ADD_CLIENT, sqlParameterSource);
        } catch (DuplicateKeyException dke) {
            log.warn("Client: {} already exist", login);
            throw new ClientCreationException(
                    ResponseComposer.compose(ResponseCode.USER_EXIST, "Client already exist"));
        } catch (Exception e) {
            throw new ClientCreationException(
                    ResponseComposer.compose(ResponseCode.TECHNICAL_ERROR, "Failed to create client"));
        }
    }

    public boolean loginExist(String login) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("login", login);

        return jdbcTemplate.query(GET_CLIENT, sqlParameterSource,
                (rs, rowNum) -> rs.getString("LOGIN")).size() > 0;
    }



}
