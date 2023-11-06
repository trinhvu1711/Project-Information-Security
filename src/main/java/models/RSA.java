package models;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class RSA implements EncryptionAlgorithm{
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public void generateKey() throws Exception{
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        keyPair = keyGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }


    @Override
    public String calculate(String inputText) {
        byte[] result;
        Cipher encrypt = null;
        try {
            encrypt = Cipher.getInstance("RSA");
            encrypt.init(Cipher.ENCRYPT_MODE, publicKey);
            result = encrypt.doFinal(inputText.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(result);
    }

    @Override
    public String decrypt(String inputText) {
        byte[] result;
        try {
        byte[] text = Base64.getDecoder().decode(inputText);
        Cipher encrypt = Cipher.getInstance("RSA");
        encrypt.init(Cipher.DECRYPT_MODE, privateKey);
        result = encrypt.doFinal(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String(result);
    }
    public void FileEncrypt(String inputPath, String outputPath) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        byte[] iv = new byte[16];
        IvParameterSpec spec = new IvParameterSpec(iv);
        SecretKey secretKey = keyGen.generateKey();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

        CipherInputStream cis = new CipherInputStream(new BufferedInputStream(new FileInputStream(inputPath)), cipher);
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputPath)));

        String keyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        dos.writeUTF(calculate(keyString));
        dos.writeLong(new File(inputPath).length());
        dos.writeUTF(Base64.getEncoder().encodeToString(iv));

        byte[] buff = new byte[1024];
        int i;
        while ((i = cis.read(buff)) != -1) {
            dos.write(buff, 0, i);
        }

        cis.close();
        dos.flush();
        dos.close();

    }


    public void fileDecrypt(String inputPath, String outputPath) throws Exception{
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(inputPath)));
        String keyString = dis.readUTF();
        long size = dis.readLong();
        byte [] iv = Base64.getDecoder().decode(dis.readUTF());

        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(decrypt(keyString)), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        CipherInputStream cis = new CipherInputStream(dis, cipher);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputPath));

        byte[] buff = new byte[1024];
        int i;
        while ((i = cis.read(buff)) != -1) {
            bufferedOutputStream.write(buff, 0, i);
        }
        cis.close();
        bufferedOutputStream.close();
    }

    public static void main(String[] args) throws Exception{
        String test = "trinhvu";
        RSA rsa = new RSA();
        rsa.generateKey();
        String encrypt = rsa.calculate(test);
        String decrypt = rsa.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
        rsa.FileEncrypt("D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_input\\rsa_input.zip", "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_output\\rsa_output.zip");
        rsa.fileDecrypt("D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_output\\rsa_output.zip", "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_output\\rsa_output_des.zip");
    }
}
