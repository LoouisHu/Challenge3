package nl.utwente.mod6.ai.documents_and_words;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.utwente.mod6.ai.classifier.ClassifierClass;

/**
 * This class contains a class with its document
 */
public class ClassDocument {

    private ClassifierClass c;
    private Document document;
    private int numberOfDocumentsInClass;
    private List<Document> documents;

    public ClassDocument(ClassifierClass c, List<Document> documents) {
        this.c = c;
        this.numberOfDocumentsInClass = documents.size();
        this.joinDocuments(documents);
    }

    public ClassDocument(ClassifierClass c, List<Document> documents, boolean tmp) {
        this.c = c;
        this.numberOfDocumentsInClass = documents.size();
        this.documents = documents;
    }

    public int getNumberOfWords() {
        Map<String, Word> map = this.document.getWordsInDocument();
        int result = 0;
        for (String token : map.keySet()) {
            result += map.get(token).getCount();
        }
        return result;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void incNumberOfDocs() {
        this.numberOfDocumentsInClass++;
    }
    /**
     * Join the list of documents into one
     *
     * @param documents the list of document to join
     */
    private void joinDocuments(List<Document> documents) {
        Set<Word> words = new HashSet<>();
        String contents = "";
        for (Document document : documents) {
            contents += document.getContents();
//            if (words.size() != 0) {
//                words = joinWords(words, document.getWordsInDocument());
//            } else {
//                words = document.getWordsInDocument(); }
        }
        Document classDoc = new Document(contents, true, new SimpleTokenizer());
        //classDoc.setWordsInDocument(words);
        this.document = classDoc;
    }

    /**
     * Get the number of documents that belong to this class
     *
     * @return the number of documents that belong to this class
     */
    public int getNumberOfDocumentsInClass() {
        return numberOfDocumentsInClass;
    }

    /**
     * Set the number of documents that belong to this class
     *
     * @param numberOfDocumentsInClass the number of documents that belong to this class to set
     */
    public void setNumberOfDocumentsInClass(int numberOfDocumentsInClass) {
        this.numberOfDocumentsInClass = numberOfDocumentsInClass;
    }

    /**
     * Join the two sets of words
     * @param one The set to join
     * @param two The set to join
     * @return the set of words that are joined
     */
    public Map<String, Word> joinWords(Map<String, Word> one, Map<String, Word> two) {
        Map<String, Word> result = one;
        for (String token : two.keySet()) {
            if (result.containsKey(token)) {
                result.get(token).incCount(
                        two.get(token).getCount()
                );
            } else {
                result.put(token, two.get(token));
            }
        }
        //result = (new SimpleWordExtractor()).extractWords(result);
        return result;
    }

    public ClassifierClass getClassifierClass() {
        return c;
    }

    public List<Document> getDocuments() {
        return documents;
    }
}
