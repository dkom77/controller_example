package com.example.balance.service.service;

import com.example.balance.service.dao.BalanceDao;
import com.example.balance.service.dao.ClientsDao;
import com.example.balance.service.dao.CredentialsDao;
import com.example.balance.service.exceptions.ClientNotFoundException;
import com.example.balance.service.model.*;
import com.example.balance.service.util.HashProvider;
import com.example.balance.service.util.ResponseComposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final static Long INITIAL_BALANCE = 0L;

    private final ClientsDao clientsDao;
    private final CredentialsDao credentialsDao;
    private final BalanceDao balanceDao;

    @Autowired
    public ClientService(ClientsDao clientsDao, CredentialsDao credentialsDao, BalanceDao balanceDao) {
        this.clientsDao = clientsDao;
        this.credentialsDao = credentialsDao;
        this.balanceDao = balanceDao;
    }

    @Transactional
    public Response registerNewUser(Request registrationRequest) {
        Map<String, String> extras = registrationRequest.getExtras().stream()
                .collect(Collectors.toMap(Extra::getName, Extra::getValue));

        String login = extras.get("login");
        String password = extras.get("password");

        clientsDao.insertClient(login);
        credentialsDao.saveCredentials(login, password);
        balanceDao.setClientBalance(login, INITIAL_BALANCE);

        return ResponseComposer.compose(ResponseCode.OK, "Client created");
    }

    public Response getBalance(Request balanceRequest) {
        Map<String, String> extras = balanceRequest.getExtras().stream()
                .collect(Collectors.toMap(Extra::getName, Extra::getValue));

        String login = extras.get("login");
        String passwordToCheck = extras.get("password");

        if(!clientsDao.loginExist(login)) {
            return ResponseComposer.compose(ResponseCode.USER_NOT_EXIST);
        }

        if (!authenticateClient(login, passwordToCheck)) {
            return ResponseComposer.compose(ResponseCode.WRONG_PASSWORD);
        }

        Long balance = balanceDao.getBalance(login);
        Map<String, String> extrasMap = new HashMap<>();
        extrasMap.put("balance", balance == null ? "No balance" : balance.toString());
        return ResponseComposer.compose(ResponseCode.OK, extrasMap);
    }

    private boolean authenticateClient(String login, String passwordToCheck) {
        Secret secret = credentialsDao.getSecret(login);
        if (secret == null) {
            throw new ClientNotFoundException(ResponseComposer.compose(ResponseCode.USER_NOT_EXIST));
        }
        return HashProvider.isValid(passwordToCheck, secret.getPasswordHash(), secret.getSalt());
    }

}
