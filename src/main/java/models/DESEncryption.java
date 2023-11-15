package models;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DESEncryption implements EncryptionAlgorithm{
    private static final byte[] IV = new byte[16];
    private SecretKey key;
    public void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("DES");
        keygen.init(56);
        key = keygen.generateKey();
    }
    public byte[] encrypt(String plainText) throws Exception {
        byte[] result;
        Cipher encrypt = Cipher.getInstance("DES");
        encrypt.init(Cipher.ENCRYPT_MODE, key);
        result = encrypt.doFinal(plainText.getBytes());
        return result;
    }

    public void encryptFile(String sourceFile, String destFile) throws Exception {
        File file = new File(sourceFile);
        if (file.isFile()){
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destFile);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] input = new byte[64];
            int bytesRead;

            while ((bytesRead = fis.read(input)) != -1){
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) fos.write(output);
            }
            byte[] output = cipher.doFinal();
            if (output != null) fos.write(output);
            fis.close();
            fos.flush();
            fos.close();
            System.out.println("done encrypt");
        }
        else {
            System.out.println("encrypt error");
        }
    }

    public void decryptFile(String sourceFile, String destFile) throws Exception{
        try (FileInputStream fis = new FileInputStream(new File(sourceFile));
             FileOutputStream fos = new FileOutputStream(new File(destFile))) {

            Cipher cipher = Cipher.getInstance("DES");
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

    @Override
    public String calculate(String plainText) {
        byte[] result;
        Cipher encrypt = null;
        try {
            encrypt = Cipher.getInstance("DES");
            encrypt.init(Cipher.ENCRYPT_MODE, key);
            result = encrypt.doFinal(plainText.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(result);
    }

    @Override
    public String decrypt(String plainText) {
        byte[] result;
        byte[] text = Base64.getDecoder().decode(plainText);
        try {
        Cipher encrypt = Cipher.getInstance("DES");
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

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public void setKeyFromText(String keyText) {
        if (keyText.length() != 16) {
            throw new IllegalArgumentException("Key length should be 16 characters (128 bits)");
        }
        SecretKey key = VietnameseTextHelper.hexStringToSecretKey(keyText);
        setKey(key);
    }

    public void setDESKeyFromString(String keyString) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(keyString);
            key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
        } catch (Exception e) {
            System.out.println("Error setting AES key: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String msg = "vudeptrai";
        DESEncryption desEncryption = new DESEncryption();
//        desEncryption.generateKey();
//        System.out.println(desEncryption.key);
//        String encrypt = desEncryption.calculate(msg);
//        String decrypt = desEncryption.decrypt(encrypt);
//        System.out.println(encrypt);
//        System.out.println(decrypt);
//        String sourcePath = "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\des_input\\des_input.zip";
//        String descPath = "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\des_output\\des_output.zip";
//        String descResultPath = "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\des_output\\des_result.zip";
//        desEncryption.encryptFile(sourcePath, descPath);
//        desEncryption.decryptFile(descPath, descResultPath);
//        desEncryption.setKeyFromText("E6315B2354235B83");
        desEncryption.setDESKeyFromString("RVLT3B/LExA=");
        System.out.println(desEncryption.key);
        String encrypt = desEncryption.calculate(msg);
        String decrypt = desEncryption.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);

    }
}
