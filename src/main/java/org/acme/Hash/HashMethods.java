package org.acme.Hash;

import com.arjuna.ats.jta.exceptions.NotImplementedException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;


public class HashMethods {

    static Random RANDOM = new Random();

    //generates 16 bytes
    public static String saltGenerator() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);

        return salt.toString();

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



    /*public static String hashPassword(String pw, String salt) {
        try {
            KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }*/
}
