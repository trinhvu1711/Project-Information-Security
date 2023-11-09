package models;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.util.Base64;

public class HashFunction {
    private static final String ALGORITHM_MD5 = "HmacMD5";
    private static final String ALGORITHM_SHA1 = "HmacSHA1";
    private static final String ALGORITHM_SHA224 = "HmacSHA224";
    private static final String ALGORITHM_SHA256 = "HmacSHA256";
    private static final String ALGORITHM_SHA384 = "HmacSHA384";
    private static final String ALGORITHM_SHA512 = "HmacSHA512";

    private SecretKey key;

    public SecretKey generateKey(String algorithm) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        return keyGen.generateKey();
    }

    public String hmacHashFunction(String algorithm, String data, SecretKey key)
            throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        return VietnameseTextHelper.bytesToHex(mac.doFinal(data.getBytes()));
    }
    public static void main(String[] args) {
        try {
            String data = "Hello, HMAC!";
            HashFunction hmacHashFunction = new HashFunction();

            SecretKey md5Key = hmacHashFunction.generateKey(ALGORITHM_MD5);
            String hmacMD5 = hmacHashFunction.hmacHashFunction(ALGORITHM_MD5, data, md5Key);

            SecretKey sha1Key = hmacHashFunction.generateKey(ALGORITHM_SHA1);
            String hmacSHA1 = hmacHashFunction.hmacHashFunction(ALGORITHM_SHA1, data, sha1Key);

            // Repeat the pattern for other algorithms...

            System.out.println("HMAC MD5: " + hmacMD5);
            System.out.println("HMAC SHA-1: " + hmacSHA1);
            // Print other HMAC values...

        } catch (Exception e) {
            // Handle exceptions more gracefully or log them.
            e.printStackTrace();
        }
    }
}
