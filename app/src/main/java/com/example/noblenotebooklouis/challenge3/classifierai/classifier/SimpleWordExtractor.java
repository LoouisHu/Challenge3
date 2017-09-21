package nl.utwente.mod6.ai.classifier;

import nl.utwente.mod6.ai.documents_and_words.Word;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by omer on 5-12-15.
 */
public class SimpleWordExtractor implements WordExtractor {

    private final int occurrences = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Word> extractWords(Map<String, Word> words) {
        Iterator<String> i = words.keySet().iterator();
        String w = null;
        while (i.hasNext()) {
            w = i.next();
            if (words.get(w).getCount() < this.occurrences || w.equals("")) {
                i.remove();
            }
        }
        return words;
    }
}
