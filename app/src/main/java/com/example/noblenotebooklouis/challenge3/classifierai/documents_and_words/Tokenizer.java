package nl.utwente.mod6.ai.documents_and_words;

/**
 * Created by omer on 26-12-15.
 */
public interface Tokenizer {

    /**
     * Normalize the token that is given (i.e. make all letters lowercase and remove all non-letter characters).
     *
     * @param token the token to modidy
     * @return the normalized token
     */
    public String normalizeToken(String token);

    /**
     * Remove all non-letter characters from the Stirng
     *
     * @param token The String to remove the chars from
     * @return The String with only letters and spaces.
     */
    public String removeChars(String token);

}
