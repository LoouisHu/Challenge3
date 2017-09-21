package com.example.noblenotebooklouis.challenge3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.SparseInstance;
import weka.core.converters.ConverterUtils;

/**
 * Created by Noble Notebook Louis on 21-Sep-17.
 */

public class Classifier {

    private Instances data;
    private J48 tree;

    public Classifier() throws Exception {
//        ConverterUtils.DataSource source = new ConverterUtils.DataSource("C:\\Users\\Louis\\Documents\\Module Smart Spaces\\DataSet\\Participant_1.csv");
//        data = source.getDataSet();
        tree = (J48) SerializationHelper.read(new FileInputStream("C:\\Users\\Louis\\AndroidStudioProjects\\Challenge3\\app\\src\\main\\java\\J48Classifier.model"));
//        tree.buildClassifier(data);
    }

    /**
     * turns the read data into Instances
     * @param readings
     * @return
     */

    public Instances listToInstance(List<Data> readings) {
        List<Attribute> atts = new ArrayList<>();
        List<String> classVal = new ArrayList<>();

        Attribute Ax = new Attribute("Ax");
        Attribute Ay = new Attribute("Ay");
        Attribute Az = new Attribute("Az");
        Attribute Lx = new Attribute("Lx");
        Attribute Ly = new Attribute("Ly");
        Attribute Lz = new Attribute("Lz");
        Attribute Gx = new Attribute("Gx");
        Attribute Gy = new Attribute("Gy");
        Attribute Gz = new Attribute("Gz");
        Attribute Mx = new Attribute("Mx");
        Attribute My = new Attribute("My");
        Attribute Mz = new Attribute("Mz");

        atts.add(Ax);
        atts.add(Ay);
        atts.add(Az);
        atts.add(Lx);
        atts.add(Ly);
        atts.add(Lz);
        atts.add(Gx);
        atts.add(Gy);
        atts.add(Gz);
        atts.add(Mx);
        atts.add(My);
        atts.add(Mz);
        atts.add(new Attribute("@@class@@", classVal));

        classVal.add("walking");
        classVal.add("standing");
        classVal.add("jogging");
        classVal.add("sitting");
        classVal.add("biking");
        classVal.add("upstairs");
        classVal.add("downstairs");

//        Attribute Class = new Attribute("Activity", classVal);

        Instances dataSet = new Instances("TestInstances", (ArrayList<Attribute>) atts, 0);
        dataSet.setClassIndex(dataSet.numAttributes() - 1);

        for (Data d : readings) {
            Instance instCo = new DenseInstance(dataSet.numAttributes());
            dataSet.add(instCo);
            instCo.setValue(Ax, d.getA().getX());
            instCo.setValue(Ay, d.getA().getY());
            instCo.setValue(Az, d.getA().getZ());
            instCo.setValue(Lx, d.getL().getX());
            instCo.setValue(Ly, d.getL().getY());
            instCo.setValue(Lz, d.getL().getZ());
            instCo.setValue(Gx, d.getG().getX());
            instCo.setValue(Gy, d.getG().getY());
            instCo.setValue(Gz, d.getG().getZ());
            instCo.setValue(Mx, d.getM().getX());
            instCo.setValue(My, d.getM().getY());
            instCo.setValue(Mz, d.getM().getZ());

        }

        return dataSet;

    }

    /**
     * Classifies in an instance. Prefer to have multiple instances.
     *
     * @param instance
     * @return the class attribute, e.g. walking, jogging etc.
     * @throws Exception
     */
    public String classify(Instance instance) throws Exception {
        double result = tree.classifyInstance(instance);
        return data.classAttribute().value((int) result);
    }



}
