package models;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

public class Twofish implements EncryptionAlgorithm  {
    private SecretKey key;

    public Twofish() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("Twofish");
        key = keygen.generateKey();
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

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Twofish twofish = new Twofish();
        twofish.generateKey();
        System.out.println(twofish.key);
        byte[] keyBytes = twofish.key.getEncoded();
        System.out.println("Key: " + keyBytes);
        System.out.println("Key: " + Base64.getEncoder().encodeToString(keyBytes));
        String text = "vudeptrai";
        String encrypt = twofish.calculate(text);
        String decrypt = twofish.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}
