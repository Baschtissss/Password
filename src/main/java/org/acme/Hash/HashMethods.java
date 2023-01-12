package org.acme.Hash;

import com.arjuna.ats.jta.exceptions.NotImplementedException;

import java.util.Random;


public class HashMethods {

    static Random RANDOM = new Random();

    public static String saltGenerator() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);

        return new String(salt);
    }

    public static String hashPassword(String pw, String salt) throws NotImplementedException {
        String stringToHash = pw + salt;
        throw new NotImplementedException();
    }
}
