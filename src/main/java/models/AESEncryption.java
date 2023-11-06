package models;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESEncryption implements EncryptionAlgorithm{
    private SecretKey key;
    public void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        key = keygen.generateKey();
    }

    @Override
    public String calculate(String inputText) {
        byte[] result;
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result = cipher.doFinal(inputText.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(result);
    }

    @Override
    public String decrypt(String inputText) {
        byte[] result;
        Cipher encrypt = null;
        try {
            byte[] text = Base64.getDecoder().decode(inputText);
            encrypt = Cipher.getInstance("AES/ECB/PKCS5Padding");
            encrypt.init(Cipher.DECRYPT_MODE, key);
            result = encrypt.doFinal(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String(result);
    }
    public SecretKey getKey() {
        return key;
    }
    public static void main(String[] args) throws Exception {
        String msg = "vudeptrai";
        AESEncryption aesEncryption = new AESEncryption();
        aesEncryption.generateKey();
        System.out.println(aesEncryption.key);
        String encrypt = aesEncryption.calculate(msg);
        String decrypt = aesEncryption.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}
