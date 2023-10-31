package models;

public class VigenereCipher implements EncryptionAlgorithm{
    private String key;

    public VigenereCipher(String key) {
        this.key = key;
    }

    public String generateKey(String keyword, int textSize){
        String result ="";
        for (int i = 0;; i++) {
            if (i == keyword.length()){
                i = 0;
            }
            if (result.length() == textSize) break;
            result += keyword.charAt(i);
        }
        return result;
    }

    @Override
    public String calculate(String inputText) {
        String result = "";
        String keyword = generateKey(key, inputText.length());
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
                index = (VietnameseTextHelper.findIndexAlphabet(charItem) + VietnameseTextHelper.findIndexAlphabet(keyword.charAt(i))) % 89;
                result += VietnameseTextHelper.getCharacterAtIndexAndUpperCase(index);
            } else {
                index = (VietnameseTextHelper.findIndexAlphabet(charItem) + VietnameseTextHelper.findIndexAlphabet(keyword.charAt(i))) % 89;
                result += VietnameseTextHelper.getCharacterAtIndexAndLowerCase(index);
            }
        }
        return result;
    }

    @Override
    public String decrypt(String inputText) {
        String result = "";
        String keyword = generateKey(key, inputText.length());
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
                index = (VietnameseTextHelper.findIndexAlphabet(charItem) - VietnameseTextHelper.findIndexAlphabet(keyword.charAt(i)));
                if (index < 0)
                    index = index + 89;
                result += VietnameseTextHelper.getCharacterAtIndexAndUpperCase(index);
            } else {
                index = (VietnameseTextHelper.findIndexAlphabet(charItem) - VietnameseTextHelper.findIndexAlphabet(keyword.charAt(i)));
                if (index < 0)
                    index = index + 89;
                result += VietnameseTextHelper.getCharacterAtIndexAndLowerCase(index);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String text = "Cụ thể, đối với nhóm 47 dự án (gồm 8.159 căn) chưa cấp sổ hồng không có vướng mắc về mặt pháp lý, chỉ chờ xác nhận hoàn thành nghĩa vụ tài chính. Nguyên nhân dẫn đến việc phát sinh chậm trễ trong quá trình giải quyết thủ tục về thuế để có cơ sở thực hiện cấp sổ cho người mua nhà là do điều kiện cơ sở vật chất khi thực hiện liên thông thuế điện tử trong giải quyết thủ tục đất đai giữa cơ quan đăng ký đất đai và cơ quan thuế.";
        String text2 = "test";
        String cipher =  new VigenereCipher( "uiqwyeuiqwye").calculate(text);
        String decrypt = new VigenereCipher("uiqwyeuiqwye").decrypt(cipher);
        System.out.println(cipher);
        System.out.println(decrypt);
    }
}
