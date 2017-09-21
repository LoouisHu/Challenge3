package nl.utwente.mod6.ai.documents_and_words;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class represent a document of a training/testClassifier data set.
 * Created by omer on 1-12-15.
 */
public class Document {

    private Map<String, Word> wordsInDocument;
    private String contents;
    public String path;
    private Tokenizer tokenizer;

    /**
     * In this contructor the contents variable is set with the contents of the document under path.
     *
     * @param path the path of this document
     */
    public Document(String path, Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.path = path;
        this.wordsInDocument = new HashMap<>();
        try {
            this.contents = this.getContents(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.extractTokens();
    }

    public Document(String value, boolean which, Tokenizer tokenizer) {
        if (which) {
            this.contents = value;
        } else {
            this.path = value;
            return;
        }
        this.tokenizer = tokenizer;
        this.wordsInDocument = new HashMap<>();
        this.extractTokens();
    }

    /**
     * Get the number of words in the document.
     * @return the amount of words in the document.
     */
    public int getNumberOfWords() {
        int amount = 0;
        for (String token : this.wordsInDocument.keySet()) {
            amount += this.wordsInDocument.get(token).getCount();
        }
        return amount;
    }

    /**
     * Get the contents of the file under path.
     * @param path the path to look at
     * @return the contents of the file under path
     * @throws FileNotFoundException
     */
    public String getContents(String path) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String result = "";
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get the numbder of occurences of a word in a String.
     * @param toFind The words you are looking for.
     * @param in The String to search in
     * @return the number of occurences of the word in the String
     */
    public int countWordsInDocument(String toFind, String in) {
        return StringUtils.countMatches(toFind, in);
    }

    /**
     * Get the words in this document
     * @return A set of words in this document.
     */
    public Map<String, Word> getWordsInDocument() {
        return wordsInDocument;
    }

    /**
     * Set the words in this document.
     *
     * @param words The set of words to set
     */
    public void setWordsInDocument(Map<String, Word> words) {
        this.wordsInDocument = words;
    }

    /**
     * Normalize the tokens in this document and put them in a map.
     */
    public void extractTokens() {
        String normalized = tokenizer.normalizeToken(this.contents);
        String[] tokens = normalized.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            if (!this.wordsInDocument.keySet().contains(tokens[i])) {
                Word token = new Word(tokens[i], 1);
                this.wordsInDocument.put(tokens[i], token);
            } else {
                this.wordsInDocument.get(tokens[i]).incCount();
            }

        }

    }

    public String getContents() {
        return contents;
    }
}