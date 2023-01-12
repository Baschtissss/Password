package org.acme.Hash;

import com.arjuna.ats.jta.exceptions.NotImplementedException;

import java.util.Random;


public final class HashMethods {

    Random RANDOM = new Random();

    public String saltGenerator() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);

        return new String(salt);
    }

    public String hashPassword(String pw, String salt) throws NotImplementedException {
        String stringToHash = pw + salt;
        throw new NotImplementedException();
    }
}
