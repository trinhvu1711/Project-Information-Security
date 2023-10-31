package models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 implements EncryptionAlgorithm {

    @Override
    public String calculate(String inputText) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] output = md.digest(inputText.getBytes());
        BigInteger num = new BigInteger(1, output);
        return num.toString(16);
    }

    @Override
    public String decrypt(String inputText) {
        return null;
    }

    public static void main(String[] args) {
        MD5 md5 = new MD5();
        System.out.println(md5.calculate("vudeptrai"));
    }
}
