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
    public static int[][] inverse(int[][] keyMatrix, int mod) {
        int n = keyMatrix.length;
        int[][] augmentedMatrix = new int[n][2 * n];

        // Khởi tạo ma trận mở rộng bằng ma trận đơn vị và ma trận khóa
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = keyMatrix[i][j];
                augmentedMatrix[i][j + n] = (i == j) ? 1 : 0; // Ma trận đơn vị
            }
        }

        // Biến đổi ma trận thành ma trận echelon
        for (int i = 0; i < n; i++) {
            int pivotIdx = -1;
            for (int j = i; j < n; j++) {
                if (augmentedMatrix[j][i] != 0) {
                    pivotIdx = j;
                    break;
                }
            }

            if (pivotIdx == -1) {
                throw new IllegalArgumentException("Ma trận không khả nghịch");
            }

            // Hoán đổi các dòng để có phần tử chính nằm ở vị trí (i, i)
            int[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[pivotIdx];
            augmentedMatrix[pivotIdx] = temp;

            // Chuẩn hóa dòng để phần tử chính là 1
            int pivotInverse = modInverse(augmentedMatrix[i][i], mod);
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] = (augmentedMatrix[i][j] * pivotInverse) % mod;
            }

            // Loại bỏ các phần tử không chính ở cột i
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    int factor = augmentedMatrix[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix[k][j] = (augmentedMatrix[k][j] - factor * augmentedMatrix[i][j] + mod) % mod;
                    }
                }
            }
        }

        // Lấy ma trận nghịch đảo từ phần sau của ma trận mở rộng
        int[][] inverseMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, inverseMatrix[i], 0, n);
        }

        return inverseMatrix;
    }

    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("Không tồn tại nghịch đảo modulo");
    }
}
