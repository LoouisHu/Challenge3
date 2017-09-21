package nl.utwente.mod6.ai.documents_and_words;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

/**
 * This class gets documents for the training data set or testClassifier data set.
 * Created by omer on 1-12-15.
 */
public class DocumentLoader {

    private Tokenizer tokenizer;

    public DocumentLoader(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Get a list of directories under 'path'
     *
     * @param path the path to look under
     * @return a list of directories
     */
    public String[] getListOfDirectories(String path) {
        File folder = new File(path);
        String[] listOfDirs = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        return listOfDirs;
    }

    /**
     * Get a list of files under 'path'
     * @param path the path to look under
     * @return a list of files
     */
    public String[] getListOfFiles(String path) {
        File folder = new File(path);
        String[] listOfFiles = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isFile();
            }
        });
        return listOfFiles;
    }

    /**
     * Get the training data set frtrainingdatasetom under path
     * @param path the path to look under
     * @return a map with the class names as strings and a list of documents as values
     */
    public Map<String, List<Document>> getDocuments(String path) {
        String[] dirs = this.getListOfDirectories(path);
        Map<String, List<Document>> trainingsSet = new HashMap<>();
        for (String dir : dirs) {
            String[] files = this.getListOfFiles(path + "/" + dir);
            ArrayList<Document> documents = new ArrayList<>();
            for (String file : files) {
                documents.add(new Document(path + "/" + dir + "/" + file, this.tokenizer));
            }
            trainingsSet.put(dir, documents);
        }
        return trainingsSet;
    }
    
    /**
     * Get the testClassifier data set
     * @param path        the path to the testClassifier documents
     * @return a map with the documents as keys and the class names as values
     */
    public Map<Document, String> getTestDocuments(String path) {
        String[] dirs = this.getListOfDirectories(path);
        Map<Document, String> testSet = new HashMap<>();
        for (String dir : dirs) {
            String[] files = this.getListOfFiles(path + "/" + dir);
            for (String file : files) {
                testSet.put(new Document(path + "/" + dir + "/" + file, this.tokenizer), dir);
            }
        }
        return testSet;
    }


}
