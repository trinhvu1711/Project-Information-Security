package models;

public class Hill implements EncryptionAlgorithm {
    int keyMatrix[][];
//    int inverseKeyMatrix[][];
    @Override
    public String calculate(String inputData) {
        StringBuilder encryptedData = new StringBuilder();
        int alphabetSize = VietnameseTextHelper.ALPHABET_SIZE;

        for (int i = 0; i < inputData.length(); i += 3) {
            String block = inputData.substring(i, Math.min(i + 3, inputData.length()));
            int[] blockVector = new int[3];

            for (int j = 0; j < block.length(); j++) {
                char ch = block.charAt(j);
                int index = VietnameseTextHelper.ALPHABET_VIETNAMESE.indexOf(ch);
                blockVector[j] = index;
            }

            for (int j = 0; j < 3; j++) {
                int sum = 0;
                for (int k = 0; k < 3; k++) {
                    sum += keyMatrix[j][k] * blockVector[k];
                }

                int index = (sum % alphabetSize + alphabetSize) % alphabetSize;
                encryptedData.append(VietnameseTextHelper.ALPHABET_VIETNAMESE.charAt(index));
            }
        }

        return encryptedData.toString();
    }

    @Override
    public String decrypt(String encryptedData) {
        StringBuilder decryptedData = new StringBuilder();
        int alphabetSize = VietnameseTextHelper.ALPHABET_SIZE;

        for (int i = 0; i < encryptedData.length(); i += 3) {
            String block = encryptedData.substring(i, Math.min(i + 3, encryptedData.length()));
            int[] blockVector = new int[3];

            for (int j = 0; j < block.length(); j++) {
                char ch = block.charAt(j);
                int index = VietnameseTextHelper.ALPHABET_VIETNAMESE.indexOf(ch);
                blockVector[j] = index;
            }

            int[][] inverseKeyMatrix = VietnameseTextHelper.inverse(keyMatrix, alphabetSize);

            for (int j = 0; j < 3; j++) {
                int sum = 0;
                for (int k = 0; k < 3; k++) {
                    sum += inverseKeyMatrix[j][k] * blockVector[k];
                }

                int index = (sum % alphabetSize + alphabetSize) % alphabetSize;
                decryptedData.append(VietnameseTextHelper.ALPHABET_VIETNAMESE.charAt(index));
            }
        }

        return decryptedData.toString();
    }

    public int[][] getKeyMatrix() {
        return keyMatrix;
    }

    public void setKeyMatrix(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
    }

    public static void main(String[] args) {
        // Ma trận khóa và ma trận khóa nghịch đảo (tạm thời)
        int[][] keyMatrix = {
                {6, 24, 1},
                {13, 16, 10},
                {20, 17, 15}
        };
        int[][] inverseKeyMatrix = {
                {8, 5, 10},
                {21, 8, 21},
                {21, 12, 8}
        };

        // Dữ liệu để kiểm tra
        String originalData = "HELLO";
        Hill hill = new Hill();
        hill.setKeyMatrix(keyMatrix);
//        hill.setInverseKeyMatrix(inverseKeyMatrix);
        // Mã hóa
        String encryptedData = hill.calculate(originalData);
        System.out.println("Dữ liệu đã được mã hóa: " + encryptedData);

        // Giải mã
        String decryptedData = hill.decrypt(encryptedData);
        System.out.println("Dữ liệu đã được giải mã: " + decryptedData);

        // Kiểm tra xem dữ liệu giải mã có khớp với dữ liệu gốc không
        if (originalData.equals(decryptedData)) {
            System.out.println("Kiểm tra thành công! Dữ liệu giải mã trùng khớp với dữ liệu gốc.");
        } else {
            System.out.println("Kiểm tra không thành công! Dữ liệu giải mã không khớp với dữ liệu gốc.");
        }
    }
}
