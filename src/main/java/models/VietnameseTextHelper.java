package models;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class VietnameseTextHelper {
    public static final String ALPHABET_VIETNAMESE = "aáàạãảăắằặẵẳâấầậẫẩbcdđeéèẹẽẻêếềệễểghiíìịĩỉklmnoóòọõỏôốồộỗổơớờợỡởpqrstuúùụũủưứừựữửvxyýỳỵỹỷ";
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
}
