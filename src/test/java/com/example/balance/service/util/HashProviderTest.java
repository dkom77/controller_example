package com.example.balance.service.util;

import com.example.balance.service.model.Secret;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class HashProviderTest {

    @Test
    void checkPasswordValidationTest() {
        String password = "test_password";
        Secret secret = HashProvider.calculateHashForString(password);

        String hashedPassword = secret.getPasswordHash();
        String salt = secret.getSalt();

        String badSalt = Base64.getEncoder().encodeToString("bad_salt".getBytes(StandardCharsets.UTF_8));

        assertTrue(HashProvider.isValid("test_password", hashedPassword, salt));
        assertFalse(HashProvider.isValid("test_password_2", hashedPassword, salt));
        assertFalse(HashProvider.isValid("test_password", hashedPassword, badSalt));

    }

}
