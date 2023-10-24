package models;

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

    public static String getCharacterAtIndexAndLowerCase(int index) {
        if (index >= 0 && index < ALPHABET_SIZE) {
            char character = ALPHABET_VIETNAMESE.charAt(index);
            return String.valueOf(character);
        }
        return "";
    }
}
