package models;

import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.util.HashSet;
import java.util.Set;

public class VietnameseTextHelper {
    public static final String ALPHABET_VIETNAMESE = "aàáãảạăằắẵẳặâầấẫẩậbcdđeéèẹẽẻêếềệễểghiìíĩỉịklmnoòóõỏọôồốỗổộơờớỡởợpqrstuùúũủụưừứữửựvxyỳýỷỹỵ1234567890 ~!@#$%^&*()-=_+[]{}|;:'\\\",.<>?/";
    public static final int ALPHABET_SIZE = ALPHABET_VIETNAMESE.length();

    public static int findIndexAlphabet(char text) {
        return ALPHABET_VIETNAMESE.indexOf(text);
    }

    public static int findIverseKey(int key) {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if ((key * i) % ALPHABET_SIZE == 1) {
                return i;
            }
        }
        return -1;
    }
    public static char getCharacterAtIndex(int index) {
        if (index >= 0 && index < ALPHABET_SIZE) {
            return ALPHABET_VIETNAMESE.charAt(index);
        } else {
            return '?';
        }
    }
    public static String getCharacterAtIndexAndUpperCase(int index) {
        if (index >= 0 && index < ALPHABET_SIZE) {
            char character = ALPHABET_VIETNAMESE.charAt(index);
            return String.valueOf(Character.toUpperCase(character));
        }
        return "";
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public static SecretKey hexStringToSecretKey(String hexString) {
        try {
            // Chuyển chuỗi hex thành mảng byte
            byte[] keyData = hexStringToByteArray(hexString);

            // Sử dụng SecretKeySpec để tạo SecretKey
            SecretKey key = new SecretKeySpec(keyData, "DES");

            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getCharacterAtIndexAndLowerCase(int index) {
        if (index >= 0 && index < ALPHABET_SIZE) {
            char character = ALPHABET_VIETNAMESE.charAt(index);
            return String.valueOf(character);
        }
        return "";
    }
    public static void printMatrix(int[][] matrix) {
        // In ra ma trận
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static BigInteger[][] addItem(int [][] arr){
        BigInteger[][] key = new BigInteger[arr.length][arr.length];
        for (int i =0; i < arr.length; i++) {
            for (int j = 0; j< arr.length; j++){
                key[i][j] = new BigInteger(String.valueOf(arr[i][j]));
            }
        }
        return key;
    }

    public static int[][] getReverseModulo(int[][] data){

        BigInteger[][] key = addItem(data);
        ModMatrix modMatrix = new ModMatrix(key);
        ModMatrix inverse = modMatrix.inverse(modMatrix);
        int[][] result = new int[inverse.getNrows()][inverse.getNrows()];
        for (int i = 0; i < inverse.getNrows(); i++) {
            for (int j = 0; j < inverse.getNcols(); j++) {
                result[i][j] = inverse.getData()[i][j].intValue();
            }
        }
        return result;
    }



    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
    }
}
