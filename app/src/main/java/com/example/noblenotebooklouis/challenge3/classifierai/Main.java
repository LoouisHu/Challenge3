package nl.utwente.mod6.ai;

import nl.utwente.mod6.ai.GUI.MainScreen;
import nl.utwente.mod6.ai.classifier.Classifier;
import nl.utwente.mod6.ai.classifier.NaiveBayes;

/**
 * This class is for test purposes
 */
public class Main {

    public static Classifier CLASSIFIER;
    public static int SMOOTHING_FACTOR = 500;

    public static void main(String[] args) {
        MainScreen screen = new MainScreen();
        CLASSIFIER = new NaiveBayes();
        screen.launch();

//        List<ClassDocument> trainngsDataSet = CLASSIFIER.setUpDataForTraining("resources/trainingdata");
//        CLASSIFIER.trainClassifier(trainngsDataSet, SMOOTHING_FACTOR);
//        List<ClassDocument> classDocs = ((NaiveBayes) CLASSIFIER).getClassDocuments();
//
////        for (ClassDocument classDoc : classDocs) {
////            System.out.println("ClassifierClass name: " + classDoc.getClassifierClass().getName());
////            System.out.println("ClassifierClass prior: " + classDoc.getClassifierClass().getPrior());
////            System.out.println("ClassifierClass documents amount: " + classDoc.getClassifierClass().getAmountOfDocuments());
////            System.out.println("Number of documents in class: " + classDoc.getNumberOfDocumentsInClass());
////            int i = 0;
////            Map<String, Word> map = classDoc.getDocument().getWordsInDocument();
////            for (String token : map.keySet()) {
////                Word word = map.get(token);
////                if (word.getCount() > 3000) {
////                    System.out.println("\tWord name: " + word.getToken());
////                    System.out.println("\tWord count: " + word.getCount());
////                    System.out.println("\tWord condProb: " + word.getCondProb());
////                    System.out.println("\n\n");
////                    i++;
////                }
////                if (i == classDoc.getDocument().getWordsInDocument().size()) {
////                    i = 0;
////
////                }
////            }
////        }
//
//        List<ClassDocument> testDataSet = CLASSIFIER.setUpDataForTest("resources/testdata");
//        List<Document> incorrect = CLASSIFIER.testClassifier(testDataSet);
//        for (Document doc : incorrect) {
//            //System.out.println("PATH: " + doc.path);
//        }
//        System.out.println("ACCURACY: " + NaiveBayes.CURRENT_ACCURACY);


    }
}
