package models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 implements EncryptionAlgorithm{

    @Override
    public String calculate(String inputText) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA256");
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
        System.out.println(new SHA256().calculate("vudeptrai"));

    }
}
