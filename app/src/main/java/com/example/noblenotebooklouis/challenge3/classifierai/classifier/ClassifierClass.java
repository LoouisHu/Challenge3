package nl.utwente.mod6.ai.classifier;

import java.util.Set;

import nl.utwente.mod6.ai.documents_and_words.Word;

/**
 *  * This is a representation of a ClassifierClass
 * Created by omer on 30-11-15.
 */
public class ClassifierClass {

    private String name;
    private double prior;
    private int amountOfDocuments;
    private Set<Word> wordsInClass;

    /**
     * Constructors
     * @param name the name to give the class
     */
    public ClassifierClass(String name) {
        this.name = name;
    }

    /**
     * Get the amount of documents that belong to this class
     *
     * @return the amount of documents that belong to this class
     */
    public int getAmountOfDocuments() {
        return amountOfDocuments;
    }

    /**
     * Set the amount of documents that belong to this class
     *
     * @param amountOfDocuments the amount of documents that belong to this class to set
     */
    public void setAmountOfDocuments(int amountOfDocuments) {
        this.amountOfDocuments = amountOfDocuments;
    }

    /**
     * Get the prior chance of this class
     * @return
     */
    public double getPrior() {
    	return prior;
    }

    /**
     * Set the prior chance of this class
     *
     * @param prior The prior chance to set
     */
    public void setPrior(double prior) {
        this.prior = prior;
    }

    /**
     * Get the name of this class
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * set the words with this class
     * 
     * @param wordsInClass a set with words with this class
     */
    public void setWordsInClass(Set<Word> wordsInClass) {
    	this.wordsInClass = wordsInClass;
    }

    /**
     * Get the set of words with this class
     * @return
     */
    public Set<Word> getWordsInClass() {
		return wordsInClass;
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassifierClass aClassifierClass = (ClassifierClass) o;

        return !(name != null ? !name.equals(aClassifierClass.name) : aClassifierClass.name != null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
