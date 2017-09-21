package nl.utwente.mod6.ai.classifier;

import nl.utwente.mod6.ai.documents_and_words.Word;

import java.util.Map;

/**
 * Created by omer on 5-12-15.
 */
public interface WordExtractor {

    /**
     * Extract certain words from a set
     *
     * @param words Word set to extract from
     * @return The set of words where some words are extracted
     */
    public Map<String, Word> extractWords(Map<String, Word> words);
}
