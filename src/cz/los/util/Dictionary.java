package cz.los.util;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    private Dictionary() {
    }

    public static final Character[] LOWERCASE = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static final Character[] UPPERCASE = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final int ALPHABET_SIZE = 26;

    public static Map<Character, Character> provideShiftedDictionary(int offset) {
        Map<Character, Character> shiftedDictionary = new HashMap<>();
        int absoluteShift = offset < ALPHABET_SIZE ? offset : offset % ALPHABET_SIZE;
        populateDictionaryFromSource(LOWERCASE, shiftedDictionary, absoluteShift);
        populateDictionaryFromSource(UPPERCASE, shiftedDictionary, absoluteShift);
        return shiftedDictionary;
    }

    private static void populateDictionaryFromSource(Character[] source,
                                                     Map<Character, Character> shiftedDictionary,
                                                     int absoluteShift) {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            int newPosition = i + absoluteShift;
            if (newPosition > source.length - 1) {
                newPosition = newPosition - ALPHABET_SIZE;
            }
            char current = source[i];
            shiftedDictionary.put(current, source[newPosition]);
        }
    }
}
