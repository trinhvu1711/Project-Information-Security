package models;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA implements EncryptionAlgorithm{
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public void generateKey(int keysize) throws Exception{
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(keysize);
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
    public void fileEncrypt(String inputPath, String outputPath) throws Exception {
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

    public void storingPublicKey(String publicKeyFilePath){
        try {
            PublicKey publicKey = keyPair.getPublic();
            try (FileOutputStream fos = new FileOutputStream(publicKeyFilePath)) {
                fos.write(publicKey.getEncoded());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storingPrivateKey(String privateKeyFilePath){
        PrivateKey privateKey = keyPair.getPrivate();
        try (FileOutputStream fos = new FileOutputStream(privateKeyFilePath)) {
            fos.write(privateKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PublicKey readPublicKey(String publicKeyFilePath) throws Exception{
        File publicKeyFile = new File(publicKeyFilePath);
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(publicKeySpec);
    }

    public PrivateKey readPrivateKey(String privateKeyFilePath) throws  Exception{
        File privateKeyFile = new File(privateKeyFilePath);
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    public static void main(String[] args) throws Exception{
        String test = "trinhvu";
        RSA rsa = new RSA();
        rsa.generateKey(1024);
        System.out.println("public key : \n"+ Base64.getEncoder().encodeToString(rsa.publicKey.getEncoded()));
        System.out.println("private key : \n"+ Base64.getEncoder().encodeToString(rsa.privateKey.getEncoded()));
        String encrypt = rsa.calculate(test);
        String decrypt = rsa.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
        String publicKeyPath = "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_output\\publicKey.key";
        String privateKeyPath = "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_output\\privateKey.key";
        rsa.storingPublicKey(publicKeyPath);
        rsa.storingPrivateKey(privateKeyPath);
        PublicKey publicKey = rsa.readPublicKey(publicKeyPath);
        PrivateKey privateKey = rsa.readPrivateKey(privateKeyPath);
        System.out.println("public key : \n"+ Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        System.out.println("private key : \n"+ Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(rsa.publicKey.getEncoded()).equals(Base64.getEncoder().encodeToString(publicKey.getEncoded())));
        System.out.println(Base64.getEncoder().encodeToString(rsa.privateKey.getEncoded()).equals(Base64.getEncoder().encodeToString(privateKey.getEncoded())));
//        rsa.FileEncrypt("D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_input\\rsa_input.zip", "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_output\\rsa_output.zip");
//        rsa.fileDecrypt("D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_output\\rsa_output.zip", "D:\\VuxBaox\\University Document\\Semester 7\\test_attt\\rsa_output\\rsa_output_des.zip");
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPrivateKeyFromString(String privateKeyString) throws Exception{
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString); // Giả sử chuỗi chứa dữ liệu base64

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        setPrivateKey(keyFactory.generatePrivate(privateKeySpec));
    }
}



