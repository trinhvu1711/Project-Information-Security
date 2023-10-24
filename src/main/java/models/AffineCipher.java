package models;

public class AffineCipher implements EncryptionAlgorithm{
    private int key1;
    private int key2;

    public AffineCipher(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public String calculate(String inputText) {
        String result = "";
        for (int i = 0; i < inputText.length(); i++) {
            char word = inputText.charAt(i);
            int index = VietnameseTextHelper.findIndexAlphabet(word);

            if (!Character.isLetter(word)) {
                result += word;

            } else if (VietnameseTextHelper.findIndexAlphabet(word) == -1) {
                result += word;

            } else if (Character.isUpperCase(word)) {
//              find index of char in alphabet
                word = Character.toLowerCase(word);
                index = VietnameseTextHelper.findIndexAlphabet(word);
                int cipherIndex = (index * key1 + key2) % VietnameseTextHelper.ALPHABET_SIZE;
                result += VietnameseTextHelper.getCharacterAtIndexAndUpperCase(cipherIndex);

            } else {
                int cipherIndex = (index * key1 + key2) % VietnameseTextHelper.ALPHABET_SIZE;
                result += VietnameseTextHelper.getCharacterAtIndexAndLowerCase(cipherIndex);
            }
        }
        return result;
    }

    @Override
    public String decrypt(String inputText) {
        String result = "";
        int key_inv = VietnameseTextHelper.findIverseKey(key1);
        System.out.println(key_inv);
        for (int i = 0; i < inputText.length(); i++) {
            char word = inputText.charAt(i);
            int index = VietnameseTextHelper.findIndexAlphabet(word);

            if (!Character.isLetter(word)) {
                result += word;

            } else if (VietnameseTextHelper.findIndexAlphabet(word) == -1) {
                result += word;

            } else if (Character.isUpperCase(word)) {
//              find index of char in alphabet
                word = Character.toLowerCase(word);
                index = VietnameseTextHelper.findIndexAlphabet(word);
                int cipherIndex = (key_inv * (index - key2 + VietnameseTextHelper.ALPHABET_SIZE)) % VietnameseTextHelper.ALPHABET_SIZE;
                result += VietnameseTextHelper.getCharacterAtIndexAndUpperCase(cipherIndex);

            } else {
                int cipherIndex = (key_inv * (index - key2 + VietnameseTextHelper.ALPHABET_SIZE)) % VietnameseTextHelper.ALPHABET_SIZE;
                result += VietnameseTextHelper.getCharacterAtIndexAndLowerCase(cipherIndex);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String text = "Cụ thể, đối với nhóm 47 dự án (gồm 8.159 căn) chưa cấp sổ hồng không có vướng mắc về mặt pháp lý, chỉ chờ xác nhận hoàn thành nghĩa vụ tài chính. Nguyên nhân dẫn đến việc phát sinh chậm trễ trong quá trình giải quyết thủ tục về thuế để có cơ sở thực hiện cấp sổ cho người mua nhà là do điều kiện cơ sở vật chất khi thực hiện liên thông thuế điện tử trong giải quyết thủ tục đất đai giữa cơ quan đăng ký đất đai và cơ quan thuế.";
        AffineCipher affineCipher = new AffineCipher(3, 5);
        String encrypt = affineCipher.calculate(text);
        String decrypt = affineCipher.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}
