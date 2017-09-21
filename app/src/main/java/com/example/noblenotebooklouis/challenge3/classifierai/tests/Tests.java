package nl.utwente.mod6.ai.tests;

import nl.utwente.mod6.ai.documents_and_words.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omer on 26-12-15.
 */
public class Tests {

    public static void main(String[] args) {
        Map<String, Word> map = new HashMap<>();
        String token = "omer";
        map.put(token, new Word(token, 10));
        map.get(token).incCount();

        System.out.println(map.get(token).getCount());
    }

}
