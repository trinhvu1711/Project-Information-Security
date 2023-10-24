package models;

public interface EncryptionAlgorithm {
    String calculate(String inputText);
    String decrypt(String inputText);
}
