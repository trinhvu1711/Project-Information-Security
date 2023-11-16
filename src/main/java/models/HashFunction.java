package models;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
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
        MessageDigest md = getMessageDigest(algorithm);
        byte[] output = md.digest(inputText.getBytes());
        BigInteger num = new BigInteger(1, output);
        return num.toString(16);
    }

    public String calculateFromFile(String filePath, String algorithm) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
        MessageDigest md = getMessageDigest(algorithm);
        byte[] output = md.digest(fileBytes);
        BigInteger num = new BigInteger(1, output);
        return num.toString(16);
    }

    private MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        HashFunction hashFunction = new HashFunction();

        String md5 = hashFunction.calculate("Vudeptrai", ALGORITHM_MD5);
        System.out.println("MD5: " + md5);

        String sha1 = hashFunction.calculate("Vudeptrai", ALGORITHM_SHA1);
        System.out.println("SHA1: " + sha1);

        try {
            String fileMd5 = hashFunction.calculateFromFile("D:\\Downloads\\matsau.jpg", ALGORITHM_MD5);
            System.out.println("File MD5: " + fileMd5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
