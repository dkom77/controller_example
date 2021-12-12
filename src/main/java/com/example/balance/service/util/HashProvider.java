package com.example.balance.service.util;

import com.example.balance.service.model.Secret;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashProvider {

    private HashProvider() {
    }

    public static Secret calculateHashForString(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            byte[] encodedPassword = generateSecret(password, salt);
            return new Secret(toBase64(encodedPassword), toBase64(salt));
        }catch (Exception e) {
            throw new RuntimeException("Failed to generate password", e);
        }
    }

    public static boolean isValid(String passwordCandidate, String password, String base64salt) {
        try {
            byte[] salt = fromBase64(base64salt);
            byte[] encodedCandidate = generateSecret(passwordCandidate, salt);
            return password.equals(toBase64(encodedCandidate));
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate password", e);
        }
    }

    private static byte[] generateSecret(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    private static String toBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] fromBase64(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }
}
