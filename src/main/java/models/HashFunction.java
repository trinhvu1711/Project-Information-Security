package models;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {
    private SecretKey key;
    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1 = "SHA1";
    public static final String ALGORITHM_SHA224 = "SHA224";
    public static final String ALGORITHM_SHA256 = "SHA256";
    public static final String ALGORITHM_SHA384 = "SHA384";
    public static final String ALGORITHM_SHA512 = "SHA512";

    public String calculate(String inputText, String algorithm) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] output = md.digest(inputText.getBytes());
        BigInteger num = new BigInteger(1, output);
        return num.toString(16);
    }

    public static void main(String[] args) {
        String md5 = new HashFunction().calculate("Vudeptrai", ALGORITHM_MD5);
        System.out.println(md5);
        String sha1 = new HashFunction().calculate("Vudeptrai", ALGORITHM_SHA1);
        System.out.println(sha1);
    }
}
