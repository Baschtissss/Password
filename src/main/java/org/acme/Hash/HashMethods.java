package org.acme.Hash;

import com.arjuna.ats.jta.exceptions.NotImplementedException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class HashMethods {

    static Random RANDOM = new Random();

    //generates 16 bytes
    public static String saltGenerator() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);

        return salt.toString();

        /*StringBuilder salt = new StringBuilder();

        for(int i = 0; i< 16; i++){
            salt.append((char) RANDOM.nextInt(0, 255));
        }
        return salt.toString();*/
    }


    //using Hash Algorithm SHA-512
    public static String hashPassword(String pw, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update((pw + salt).getBytes());

        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
