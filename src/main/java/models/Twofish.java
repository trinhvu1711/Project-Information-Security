package models;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

public class Twofish implements EncryptionAlgorithm  {
    private SecretKey key;

    public Twofish() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public void generateKey(int keySize) throws NoSuchAlgorithmException {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("Twofish", "BC");
            keygen.init(keySize); // or your preferred key size
            key = keygen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error generating Twofish key: " + e.getMessage());
        }
    }
    @Override
    public String calculate(String plainText) {
        byte[] result;
        Cipher encrypt = null;
        try {
            encrypt = Cipher.getInstance("Twofish", "BC");
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
        try {
            byte[] text = Base64.getDecoder().decode(plainText);
            Cipher encrypt = Cipher.getInstance("Twofish", "BC");
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

    public void setTwoFishKeyFromString(String keyString) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(keyString);
            key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "Twofish");
        } catch (Exception e) {
            System.out.println("Error setting Twofish key: " + e.getMessage());
        }
    }

    public void encryptFile(String sourceFile, String destFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(new File(sourceFile));
             FileOutputStream fos = new FileOutputStream(new File(destFile))) {

            Cipher encryptCipher = Cipher.getInstance("Twofish", "BC");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            CipherOutputStream cos = new CipherOutputStream(fos, encryptCipher);

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }

            cos.close();

            System.out.println("done encrypt");
        } catch (Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
        }
    }

    public void decryptFile(String sourceFile, String destFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(new File(sourceFile));
             FileOutputStream fos = new FileOutputStream(new File(destFile))) {

            Cipher decryptCipher = Cipher.getInstance("Twofish", "BC");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);

            CipherInputStream cis = new CipherInputStream(fis, decryptCipher);

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            cis.close();

            System.out.println("done decrypt");
        } catch (Exception e) {
            System.out.println("Error during decryption: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        Twofish twofish = new Twofish();
//        twofish.generateKey();
        String key = "1rltsvVpMNKmdW5cJQ20rHaTDk0gnssmTOn+FSFLDTA=";
        twofish.setTwoFishKeyFromString(key);
        System.out.println(twofish.key);
        byte[] keyBytes = twofish.key.getEncoded();
        System.out.println("Key: " + VietnameseTextHelper.bytesToHex(keyBytes));
        String text = "Hello, Bouncy Castle!";
        String encrypt = twofish.calculate(text);
        String decrypt = twofish.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}
