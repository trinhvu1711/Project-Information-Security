package models;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

public class Serpent implements EncryptionAlgorithm {
    private SecretKey key;

    public Serpent() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public String calculate(String plainText) {
        byte[] result;
        Cipher encrypt = null;
        try {
            encrypt = Cipher.getInstance("Twofish");
            encrypt.init(Cipher.ENCRYPT_MODE, key);
            result = encrypt.doFinal(plainText.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(result);
    }
    public void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("Serpent");
        key = keygen.generateKey();
    }


    @Override
    public String decrypt(String plainText) {
        byte[] result;
        try {
            byte[] text = Base64.getDecoder().decode(plainText);
            Cipher encrypt = Cipher.getInstance("Twofish");
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

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Serpent serpent = new Serpent();
        serpent.generateKey();
        System.out.println(serpent.key);
        byte[] keyBytes = serpent.key.getEncoded();
        System.out.println("Key: " + VietnameseTextHelper.bytesToHex(keyBytes));
        String text = "Hello, Bouncy Castle!";
        String encrypt = serpent.calculate(text);
        String decrypt = serpent.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }

}
