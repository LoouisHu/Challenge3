package com.example.noblenotebooklouis.challenge3;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 * Created by Noble Notebook Louis on 20-Sep-17.
 */

public class CSV2Arff {
    public static void main(String[] args) throws IOException {
        CSVLoader csvLoader = new CSVLoader();
        csvLoader.setSource(new File("C:\\Users\\Louis\\Documents\\Module Smart Spaces\\DataSet\\Participant_1.csv"));
        Instances data = csvLoader.getDataSet();

        ArffSaver arffSaver = new ArffSaver();
        arffSaver.setInstances(data);
        arffSaver.setFile(new File("C:\\Users\\Louis\\Documents\\Module Smart Spaces\\DataSet\\Participant_1.arff"));
        arffSaver.writeBatch();
    }
}
