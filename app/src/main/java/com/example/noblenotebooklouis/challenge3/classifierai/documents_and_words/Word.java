package nl.utwente.mod6.ai.documents_and_words;

/**
 * Created by omer on 30-11-15.
 * This class represents a token or word
 */
public class Word {

    private String token;
    private int count;
    private double condProb;
    private Tokenizer tokenizer;

    public Word(String word, int count) {
        this.token = (this.tokenizer = new SimpleTokenizer()).normalizeToken(word);
        if (count == 0) {
            this.count = 1;
        } else {
            this.count = count;
        }
    }

    public Word(String word, int count, Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.token = tokenizer.normalizeToken(word);
        if (count == 0) {
            this.count = 1;
        } else {
            this.count = count;
        }
    }
    /**
     * Get the token/word of this object
     *
     * @return the word stored in this object
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the token of this object.
     * This method should not be used. Instead another Word object should be made.
     *
     * @param token The word to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get the number of occurrences of this token in the document
     *
     * @return the number of occurrences
     */
    public int getCount() {
        return count;
    }

    /**
     * Set the number of occurrences.
     * This method should not be used. Instead incCount and decCount should be used
     *
     * @param count the value to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Increment the number of occurrences by one
     */
    public void incCount() {
        this.incCount(1);
    }

    public void incCount(int incrementBy) {
        this.count += incrementBy;
    }

    /**
     * Decrement the number of occurrences by one
     */
    public void decCount() {
        this.count--;
    }

    /**
     * Get the conditional probability of this word
     *
     * @return the conditional probability
     */
    public double getCondProb() {
        return condProb;
    }

    /**
     * Set the conditional probability
     *
     * @param condProb the value to set
     */
    public void setCondProb(double condProb) {
        this.condProb = condProb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return !(token != null ? !token.equals(word.token) : word.token != null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
