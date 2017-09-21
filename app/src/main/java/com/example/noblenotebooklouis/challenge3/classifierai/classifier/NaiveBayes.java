package nl.utwente.mod6.ai.classifier;

import java.util.*;

import nl.utwente.mod6.ai.documents_and_words.*;

/**
 *
 * Created by omer on 30-11-15.
 */
public class NaiveBayes implements Classifier {

    private ClassifierClass[] classifierClasses;
    private List<ClassDocument> classDocuments;
    public static String message = "";

    public NaiveBayes() {
        this.classDocuments = new ArrayList<>();

    }

    /**
     * Trains the CLASSIFIER
     *
     * @param docs					a list of documents used for training
     * @param smoothingFactor		the minimum amount of times a word has to occur to be used for classification
     */
    @Override
    public void trainClassifier(List<ClassDocument> docs, int smoothingFactor) {
        long time = System.currentTimeMillis();
        for (ClassDocument classDocuments : docs) {
            Map<String, Word> words = classDocuments.getDocument().getWordsInDocument();
            int totalAmountOfWords = classDocuments.getNumberOfWords();
            for (String token : words.keySet()) {
                words.get(token).setCondProb(calculateCondProb(words.get(token), totalAmountOfWords,
                        classDocuments.getDocument().getWordsInDocument().size(), smoothingFactor));
            }
        }
        this.classDocuments = docs;
    }

    @Override
    public void addWordsToClassifier(List<ClassDocument> docs, int smoothingFactor) {
        StringBuilder stringBuilder = new StringBuilder();
        int allwords = 0;
        for (ClassDocument classDocuments : docs) {
            Map<String, Word> words = classDocuments.getDocument().getWordsInDocument();
            int totalAmountOfWords = classDocuments.getNumberOfWords();
            allwords += classDocuments.getNumberOfWords();
            for (String token : words.keySet()) {
                words.get(token).setCondProb(calculateCondProb(words.get(token), totalAmountOfWords,
                        classDocuments.getDocument().getWordsInDocument().size(), smoothingFactor));
            }
            addToDictionary(words, classDocuments.getClassifierClass());
        }
        stringBuilder.append(allwords + " words added to vocabulary");

        message = stringBuilder.toString();
    }

    private double calculateCondProb(Word word, int totalNumberOfWords, int uniqueWordsInClass, int smoothingFactor) {
        return (double) (word.getCount() + smoothingFactor) / (double) (totalNumberOfWords
                + smoothingFactor);
    }

    /**
     * Classifies a document
     *
     * @param doc	the document to be classified
     * @return the class this document is classified as
     */
    @Override
    public ClassifierClass applyClassifier(Document doc) {
        Map<String, Word> tokens = doc.getWordsInDocument();
        ClassifierClass c = classDocuments.get(0).getClassifierClass();
        double highscore = 0;
        double score = 0;
        for (int i = 0; i < this.classDocuments.size(); i++) {
            score = 0;

            ClassifierClass cc = this.classDocuments.get(i).getClassifierClass();

            Map<String, Word> wordInDocument = this.classDocuments.get(i).getDocument().getWordsInDocument();
            score += Math.log(cc.getPrior());
            //System.out.println("Score:" + score);
            for (String token : tokens.keySet()) {
                if (wordInDocument.containsKey(token)) {
                    score -= Math.log(wordInDocument.get(token).getCondProb());
                }
            }
            //System.out.println("Score: " + score + "    Class: " + cc.getName());
            //score = Math.log(score);
            //System.out.println(classDocuments.size() +"\t\t\t\t\t\t" + (score >= highscore));
            if (score >= highscore) {
                highscore = score;
                c = cc;
            }
		}
        return c;
    }

    /**
     * Tests if the naive bayes CLASSIFIER classifies the data correctly.
     *
     * @param docs A map with as key set the documents to be tested.
     *             Each document's value is a string with its class name.
     */
    @Override
    public List<Document> testClassifier(List<ClassDocument> docs) {
        double correct = 0;
        int totalDocs = 0;
        List<Document> incorrect = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (ClassDocument classDocument : docs) {
            totalDocs += classDocument.getDocuments().size();
            int goodClassified = 0;
            for (Document doc : classDocument.getDocuments()) {
                if (this.applyClassifier(doc).equals(classDocument.getClassifierClass())) {
                    correct++;
                    goodClassified++;
                } else {
                    incorrect.add(doc);
                }
                addToDictionary(doc.getWordsInDocument(), classDocument.getClassifierClass());

            }
            stringBuilder.append("\nTEST: " + classDocument.getClassifierClass().getName() + "\n");
            stringBuilder.append("\tAmount of correctly classified documents of class: " + goodClassified + " of " + classDocument.getDocuments().size());

        }
        CURRENT_ACCURACY = correct / totalDocs;
        this.message = stringBuilder.toString();
        return incorrect;
    }

    public static double CURRENT_ACCURACY = 0;

    public void addToDictionary(Map<String, Word> words, ClassifierClass classifierClass) {
        this.classDocuments.stream().filter(cd -> cd.getClassifierClass().equals(classifierClass)).forEach(cd -> {
            cd.incNumberOfDocs();
            cd.getDocument().setWordsInDocument(cd.joinWords(cd.getDocument().getWordsInDocument(), words));
        });
    }

    @Override
    public List<ClassDocument> setUpDataForTraining(String path) {
        // Initialize a DocumentLoader
        Tokenizer tokenizer = new SimpleTokenizer();
        WordExtractor extractor = new SimpleWordExtractor();
        DocumentLoader dl = new DocumentLoader(tokenizer);
        StringBuilder stringBuilder = new StringBuilder();


        // Keep track of the time
        long timeToLoadDocuments = System.currentTimeMillis();
        Map<String, List<Document>> map = dl.getDocuments(path);
        stringBuilder.append("Time taken to load the documents: " + (System.currentTimeMillis() - timeToLoadDocuments) + " ms\n");

        // Keep track of the amount of documents
        int amountOfDocuments = 0;
        long timeToPrepareClassesAndDocuments = System.currentTimeMillis();

        ClassifierClass[] classifierClasses = new ClassifierClass[map.keySet().size()];
        int index = 0;
        stringBuilder.append("The different classes: \n");
        for (String classesName : map.keySet()) {
            ClassifierClass aClassifierClass = new ClassifierClass(classesName);
            amountOfDocuments += map.get(classesName).size();
            stringBuilder.append("\tClass: " + classesName + "\n");
            int amount = 0;
            for (Document doc : map.get(classesName)) {
                amount += doc.getWordsInDocument().size();
            }
            aClassifierClass.setAmountOfDocuments(map.get(classesName).size());
            classifierClasses[index] = aClassifierClass;
            index++;
            stringBuilder.append("\tAmount of documents in class: " + map.get(classesName).size() + "\n\n");
        }
        stringBuilder.append("Amount of documents in total: " + amountOfDocuments + "\n");

        List<ClassDocument> classDocuments = new ArrayList<>();

        System.out.println("amount of classifierClasses: " + classifierClasses.length);
        // Set the prior chances and add to the list of classDocuments
        for (ClassifierClass aClassifierClass : classifierClasses) {
            long time = System.currentTimeMillis();
            aClassifierClass.setPrior((double) aClassifierClass.getAmountOfDocuments() / (double) amountOfDocuments);
            System.out.println("1: " + amountOfDocuments + " 2: " + aClassifierClass.getAmountOfDocuments());

            classDocuments.add(new ClassDocument(aClassifierClass,
                    map.get(aClassifierClass.getName())));
            System.out.println("done with one: " + (System.currentTimeMillis() - time));
        }
        for (ClassDocument classDoc : classDocuments) {
            classDoc.getDocument().setWordsInDocument(extractor.extractWords(classDoc.getDocument().getWordsInDocument()));
        }
        stringBuilder.append("Time taken to prepare classifierClasses and documents: " + (System.currentTimeMillis() - timeToPrepareClassesAndDocuments) + " ms\n\n");
        this.message = stringBuilder.toString();
        return classDocuments;
    }

    @Override
    public List<ClassDocument> setUpDataForTest(String path) {
        // Initialize a DocumentLoader
        Tokenizer tokenizer = new SimpleTokenizer();
        WordExtractor extractor = new SimpleWordExtractor();
        DocumentLoader dl = new DocumentLoader(tokenizer);
        StringBuilder stringBuilder = new StringBuilder();

        // Keep track of the time
        long timeToLoadDocuments = System.currentTimeMillis();
        Map<String, List<Document>> map = dl.getDocuments(path);
        stringBuilder.append("Time taken to load the documents: " + (System.currentTimeMillis() - timeToLoadDocuments) + " ms\n");

        // Keep track of the amount of documents
        int amountOfDocuments = 0;
        long timeToPrepareClassesAndDocuments = System.currentTimeMillis();

        ClassifierClass[] classifierClasses = new ClassifierClass[map.keySet().size()];
        stringBuilder.append("The different classes: \n");
        int index = 0;
        for (String classesName : map.keySet()) {
            ClassifierClass aClassifierClass = new ClassifierClass(classesName);
            amountOfDocuments += map.get(classesName).size();
            stringBuilder.append("\tClass: " + classesName + "\n");
            int amount = 0;
            for (Document doc : map.get(classesName)) {
                amount += doc.getWordsInDocument().size();
            }
            //aClassifierClass.setAmountOfDocuments(map.get(classesName).size());
            classifierClasses[index] = aClassifierClass;
            index++;
            stringBuilder.append("\tAmount of documents in class: " + map.get(classesName).size() + "\n\n");
        }
        stringBuilder.append("Amount of documents in total: " + amountOfDocuments + "\n");

        List<ClassDocument> classDocuments = new ArrayList<>();

        System.out.println("amount of classifierClasses: " + classifierClasses.length);
        // Set the prior chances and add to the list of classDocuments
        for (ClassifierClass aClassifierClass : classifierClasses) {
            classDocuments.add(new ClassDocument(aClassifierClass, map.get(aClassifierClass.getName()), true));
        }

        stringBuilder.append("Time taken to prepare classifierClasses and documents: " + (System.currentTimeMillis() - timeToPrepareClassesAndDocuments) + " ms");
        this.message = stringBuilder.toString();

        return classDocuments;

    }

    public List<ClassDocument> getClassDocuments() {
        return classDocuments;
    }
}
