package models;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESEncryption implements EncryptionAlgorithm{
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = new byte[16];
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
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
            result = cipher.doFinal(inputText.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(result);
    }

    @Override
    public String decrypt(String inputText) {
        byte[] result;
        Cipher decrypt = null;

        try {
            byte[] text = Base64.getDecoder().decode(inputText);
            decrypt = Cipher.getInstance(TRANSFORMATION);
            decrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
            result = decrypt.doFinal(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String(result);
    }
    public SecretKey getKey() {
        return key;
    }

    public void encryptFile(String sourceFile, String destFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(new File(sourceFile));
             FileOutputStream fos = new FileOutputStream(new File(destFile))) {

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));

            byte[] input = new byte[64];
            int bytesRead;

            while ((bytesRead = fis.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) fos.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null) fos.write(output);

            System.out.println("done encrypt");
        } catch (Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
        }
    }

    public void decryptFile(String sourceFile, String destFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(new File(sourceFile));
             FileOutputStream fos = new FileOutputStream(new File(destFile))) {

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));

            byte[] input = new byte[64];
            int bytesRead;

            while ((bytesRead = fis.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) fos.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null) fos.write(output);

            System.out.println("done decrypt");
        } catch (Exception e) {
            System.out.println("Error during decryption: " + e.getMessage());
        }
    }

    public void setAESKeyFromString(String keyString) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(keyString);
            key = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
        } catch (Exception e) {
            System.out.println("Error setting AES key: " + e.getMessage());
        }
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public static void main(String[] args) throws Exception {
        String msg = "vudeptrai";
        AESEncryption aesEncryption = new AESEncryption();
//        aesEncryption.generateKey();
        String key = "pHDZV8+LoH+AKKhSO1JOUg==";
        aesEncryption.setAESKeyFromString(key);
        System.out.println(aesEncryption.key);
        String encrypt = aesEncryption.calculate(msg);
        String decrypt = aesEncryption.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}
