package models;

public class CaesarCipher implements  EncryptionAlgorithm{
    private int shift;
    public CaesarCipher(int shift) {
        this.shift = shift;
    }
    @Override
    public String calculate(String inputText) {
        String result = "";
        for (int i = 0; i < inputText.length(); i++) {
            char charItem = inputText.charAt(i);
            int index = 0;
            if (!Character.isLetter(charItem)) {
                result += charItem;
            } else if (VietnameseTextHelper.findIndexAlphabet(charItem) == -1) {
                result += charItem;

            } else if (Character.isUpperCase(charItem)) {
//              find index of char in alphabet
                charItem = Character.toLowerCase(charItem);
                index = (VietnameseTextHelper.findIndexAlphabet(charItem) + shift) % 89;
                result += VietnameseTextHelper.getCharacterAtIndexAndUpperCase(index);
            } else {
                index = (VietnameseTextHelper.findIndexAlphabet(charItem) + shift) % 89;
                result += VietnameseTextHelper.getCharacterAtIndexAndLowerCase(index);
            }

        }
        return result;
    }

    @Override
    public String decrypt(String inputText) {
        String result = "";
        for (int i = 0; i < inputText.length(); i++) {
            char charItem = inputText.charAt(i);
            int index = 0;
            if (!Character.isLetter(charItem)) {
                result += charItem;

            } else if (VietnameseTextHelper.findIndexAlphabet(charItem) == -1) {
                result += charItem;

            } else if (Character.isUpperCase(charItem)) {
//              find index of char in alphabet
                charItem = Character.toLowerCase(charItem);
                index = (VietnameseTextHelper.findIndexAlphabet(charItem) - shift);
                if (index < 0)
                    index = index + 89;
                result += VietnameseTextHelper.getCharacterAtIndexAndUpperCase(index);
            } else {
                index = (VietnameseTextHelper.findIndexAlphabet(charItem) - shift);
                if (index < 0)
                    index = index + 89;
                result += VietnameseTextHelper.getCharacterAtIndexAndLowerCase(index);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CaesarCipher caesarCipher = new CaesarCipher(3); // Sử dụng khóa 3
        String word = "Nhận được tin báo vụ robot đào đường khiến nhiều nhà dân trên đường số 18 bị nứt,"
                + " nghiêng, UBND P.An Khánh (TP.Thủ Đức, TP.HCM) đã xuống kiểm tra,"
                + " yêu cầu nhà thầu dừng việc thi công để bảm đảm an toàn,"
                + " thế nhưng đơn vị thi công vẫn tiếp tục đào hầm.";
        String encrypt = caesarCipher.calculate(word);
        String decrypt = caesarCipher.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);

    }
}
