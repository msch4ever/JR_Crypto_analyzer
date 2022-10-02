package cz.los.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

    private Dictionary() {
    }

    public static final List<Character> LOWERCASE_LATIN = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    public static final List<Character> UPPERCASE_LATIN = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    public static final int LATIN_ALPHABET_SIZE = 26;
    public static final List<Character> LOWERCASE_CYRILLIC = Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я');
    public static final List<Character> UPPERCASE_CYRILLIC = Arrays.asList('А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я');
    public static final int CYRILLIC_ALPHABET_SIZE = 33;

    public static Map<Character, Character> provideShiftedDictionary(int offset) {
        Map<Character, Character> shiftedDictionary = new HashMap<>();
        prepareLatinDictionary(offset, shiftedDictionary);
        prepareCyrillicDictionary(offset, shiftedDictionary);
        return shiftedDictionary;
    }

    private static void prepareLatinDictionary(int offset, Map<Character, Character> shiftedDictionary) {
        populateDictionary(offset, LOWERCASE_LATIN, UPPERCASE_LATIN, LATIN_ALPHABET_SIZE, shiftedDictionary);
    }

    private static void prepareCyrillicDictionary(int offset, Map<Character, Character> shiftedDictionary) {
        populateDictionary(offset, LOWERCASE_CYRILLIC, UPPERCASE_CYRILLIC, CYRILLIC_ALPHABET_SIZE, shiftedDictionary);
    }

    private static void populateDictionary(int offset, List<Character> lowercaseSource, List<Character> uppercaseSource,
                                           int alphabet_size, Map<Character, Character> shiftedDictionary) {
        int absoluteShift = offset < alphabet_size ? offset : offset % alphabet_size;
        populateDictionaryFromSource(lowercaseSource, shiftedDictionary, absoluteShift);
        populateDictionaryFromSource(uppercaseSource, shiftedDictionary, absoluteShift);
    }

    private static void populateDictionaryFromSource(List<Character> source,
                                                     Map<Character, Character> shiftedDictionary,
                                                     int absoluteShift) {
        for (int i = 0; i < source.size(); i++) {
            int newPosition = i + absoluteShift;
            if (newPosition > source.size() - 1) {
                newPosition = newPosition - source.size();
            }
            char current = source.get(i);
            shiftedDictionary.put(current, source.get(newPosition));
        }
    }
}
