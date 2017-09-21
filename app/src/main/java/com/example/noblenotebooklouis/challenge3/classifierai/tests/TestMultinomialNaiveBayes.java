package nl.utwente.mod6.ai.tests;

import java.util.HashSet;
import java.util.Set;

import nl.utwente.mod6.ai.documents_and_words.Document;
import nl.utwente.mod6.ai.classifier.NaiveBayes;

public class TestMultinomialNaiveBayes {
	
	private NaiveBayes classifier;
	private Set<Document> incorrect;
	
	public TestMultinomialNaiveBayes(NaiveBayes classifier) {
		this.classifier = classifier;
		this.incorrect = new HashSet<Document>();
		}


}
