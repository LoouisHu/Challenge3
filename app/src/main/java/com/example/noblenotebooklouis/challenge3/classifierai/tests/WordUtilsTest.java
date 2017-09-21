package nl.utwente.mod6.ai.tests;

import nl.utwente.mod6.ai.documents_and_words.SimpleTokenizer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by omer on 30-11-15.
 */
public class WordUtilsTest {

    @Test
    public void testNormalizeToken() throws Exception {
        String input = "abcdefghijklmnopqrstuvwxyz";
        input += input.toUpperCase();
        input += "0123456789";
        String result = input.toLowerCase();
        input += "!@#$%*()";
        System.out.println("Input: " + input +
                "\nResult: " + result + "\n");
        assertEquals(result, (new SimpleTokenizer()).normalizeToken(input));
    }

    @Test
    public void testRemoveChars() throws Exception {
        String input = "abcdefghijklmnopqrstuvwxyz";
        input += input.toUpperCase();
        input += "0123456789";
        String result = input;
        input += "!@#$%*()";
        System.out.println("Input: " + input +
                "\nResult: " + result + "\n");
        assertEquals(result, (new SimpleTokenizer()).removeChars(input));
    }
}