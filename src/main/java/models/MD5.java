package models;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 implements EncryptionAlgorithm {
    private SecretKey key;
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
    public void generateKey(String algorithm) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        key = keyGen.generateKey();
    }
    public String hmacHashFunction(String algorithm, String data)
            throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        return VietnameseTextHelper.bytesToHex(mac.doFinal(data.getBytes()));
    }
    public static void main(String[] args) {
        MD5 md5 = new MD5();
        System.out.println(md5.calculate("vudeptrai"));
    }
}
