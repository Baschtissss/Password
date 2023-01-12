package org.acme.Hash;

import com.arjuna.ats.jta.exceptions.NotImplementedException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class HashMethods {

    static Random RANDOM = new Random();

    //generates 16 bytes
    public static byte[] saltGenerator() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);

        return salt;
    }

    //using Hash Algorithm SHA-512
    public static String hashPassword(String pw, byte[] salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);
        byte[] hashedPassword = md.digest(pw.getBytes(StandardCharsets.UTF_8));

        return hashedPassword.toString();
    }
}
