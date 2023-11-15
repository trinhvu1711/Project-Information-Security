package models;
public class Hill implements EncryptionAlgorithm {
    int keyMatrix[][];
    public Hill(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
    }

    //    int inverseKeyMatrix[][];
    @Override
    public String calculate(String inputData) {
        StringBuilder encryptedData = new StringBuilder();
        int alphabetSize = VietnameseTextHelper.ALPHABET_SIZE;
        int matrixSize = keyMatrix.length;

        for (int i = 0; i < inputData.length(); i += matrixSize) {
            String block = inputData.substring(i, Math.min(i + matrixSize, inputData.length()));
            int[] blockVector = new int[matrixSize];
            while (block.length() < matrixSize) {
                block += '/';
            }

            for (int j = 0; j < block.length(); j++) {
                char charItem = block.charAt(j);
                if (Character.isLetter(charItem)){
                    charItem = Character.toLowerCase(charItem);
                }
                int index = VietnameseTextHelper.findIndexAlphabet(charItem);
                blockVector[j] = index;
            }

            for (int j = 0; j < matrixSize; j++) {
                int sum = 0;
                for (int k = 0; k < matrixSize; k++) {
                    sum += keyMatrix[j][k] * blockVector[k];
                }

                int index = (sum % alphabetSize + alphabetSize) % alphabetSize;
                char encryptedChar = VietnameseTextHelper.getCharacterAtIndex(index);

                if (Character.isLetter(block.charAt(j)) && Character.isUpperCase(block.charAt(j))) {
                    encryptedChar = Character.toUpperCase(encryptedChar);
                }

                encryptedData.append(encryptedChar);
            }
        }

        return encryptedData.toString();
    }

    @Override
    public String decrypt(String encryptedData) {
        StringBuilder decryptedData = new StringBuilder();
        int alphabetSize = VietnameseTextHelper.ALPHABET_SIZE;
        int matrixSize = keyMatrix.length;

        for (int i = 0; i < encryptedData.length(); i += matrixSize) {
            String block = encryptedData.substring(i, Math.min(i + matrixSize, encryptedData.length()));
            int[] blockVector = new int[matrixSize];

            for (int j = 0; j < block.length(); j++) {
                char charItem = block.charAt(j);
                if (Character.isLetter(charItem)){
                    charItem = Character.toLowerCase(charItem);
                }
                int index = VietnameseTextHelper.findIndexAlphabet(charItem);
                blockVector[j] = index;

            }
//            int[][] inverseKeyMatrix = VietnameseTextHelper.inverse(keyMatrix);
            int[][] inverseKeyMatrix = VietnameseTextHelper.getReverseModulo(keyMatrix);
            for (int j = 0; j < matrixSize; j++) {
                int sum = 0;
                for (int k = 0; k < matrixSize; k++) {
                    sum += inverseKeyMatrix[j][k] * blockVector[k];
                }

                int index = sum % alphabetSize ;
                char decryptedChar = VietnameseTextHelper.getCharacterAtIndex(index);

                if (Character.isLetter(block.charAt(j)) && Character.isUpperCase(block.charAt(j))) {
                    decryptedChar = Character.toUpperCase(decryptedChar);
                }

                decryptedData.append(decryptedChar);
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
        // Ma trận khóa (3x3) cho ví dụ
        int[][] keyMatrix = {
                {6, 24, 1},
                {13, 16, 10},
                {20, 17, 15},
        };
        int [][] keyMatrix2 = {
                {6, 24},
                {13, 16},
        };

        // Chuỗi cần mã hóa
        String plaintext = "nghiêng, UBND P.An Khánh (TP.Thủ Đức, TP.HCM) đã xuống kiểm tra 123";
        String text = "Cụ thể,@#!$> đối với nhóm 47 dự án (gồm 8.159 căn) chưa cấp sổ hồng không có vướng mắc về mặt pháp lý, chỉ chờ xác nhận hoàn thành nghĩa vụ tài chính. Nguyên nhân dẫn đến việc phát sinh chậm trễ trong quá trình giải quyết thủ tục về thuế để có cơ sở thực hiện cấp sổ cho người mua nhà là do điều kiện cơ sở vật chất khi thực hiện liên thông thuế điện tử trong giải quyết thủ tục đất đai giữa cơ quan đăng ký đất đai và cơ quan thuế.";

        // Tạo đối tượng mã hóa
        Hill hillCipher = new Hill(keyMatrix);

        // Mã hóa
        String encryptedText = hillCipher.calculate(text);
        System.out.println("Encrypted Text: " + encryptedText);

//         Giải mã
        String decryptedText = hillCipher.decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
