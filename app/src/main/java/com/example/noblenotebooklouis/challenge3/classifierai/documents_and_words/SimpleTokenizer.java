package nl.utwente.mod6.ai.documents_and_words;

/**
 *
 * Created by omer on 30-11-15.
 */
public class SimpleTokenizer implements Tokenizer {

    /**
     * {@inheritDoc}
     */
    public String normalizeToken(String token) {
        return removeChars(token.toLowerCase());
    }

    /**
     * {@inheritDoc}
     */
    public String removeChars(String token) {
        return token.replaceAll("[^A-Za-z0-9 ]", "");
    }


}
