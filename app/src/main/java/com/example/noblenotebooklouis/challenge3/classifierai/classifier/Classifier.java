package nl.utwente.mod6.ai.classifier;

import nl.utwente.mod6.ai.documents_and_words.ClassDocument;
import nl.utwente.mod6.ai.documents_and_words.Document;

import java.util.List;

/**
 * Created by omer on 30-11-15.
 * This class represents the CLASSIFIER, it will have to be extended to use the right type of CLASSIFIER
 */
public interface Classifier {
    public List<ClassDocument> setUpDataForTraining(String path);

    public List<ClassDocument> setUpDataForTest(String path);

    public void trainClassifier(List<ClassDocument> docs, int smoothingFactor);

    public ClassifierClass applyClassifier(Document doc);

    public List<Document> testClassifier(List<ClassDocument> docs);

    public void addWordsToClassifier(List<ClassDocument> docs, int smoothingFactor);



}
