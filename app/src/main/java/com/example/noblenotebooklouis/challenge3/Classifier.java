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
        tree = (J48) SerializationHelper.read(new FileInputStream("J48Classifier.model"));
//        tree.buildClassifier(data);
    }

    public Instances listToInstance(List<Data> readings) {
        FastVector atts = new FastVector();
        FastVector fvClassVal = new FastVector();
        fvClassVal.addElement("walking");
        fvClassVal.addElement("standing");
        fvClassVal.addElement("jogging");
        fvClassVal.addElement("sitting");
        fvClassVal.addElement("biking");
        fvClassVal.addElement("upstairs");
        fvClassVal.addElement("downstairs");
        Attribute Class = new Attribute("Activity", fvClassVal);

        atts.addElement(new Attribute("Ax"));
        atts.addElement(new Attribute("Ay"));
        atts.addElement(new Attribute("Az"));
        atts.addElement(new Attribute("Lx"));
        atts.addElement(new Attribute("Ly"));
        atts.addElement(new Attribute("Lz"));
        atts.addElement(new Attribute("Gx"));
        atts.addElement(new Attribute("Gy"));
        atts.addElement(new Attribute("Gz"));
        atts.addElement(new Attribute("Mx"));
        atts.addElement(new Attribute("My"));
        atts.addElement(new Attribute("Mz"));
        atts.addElement(Class);

        Instances dataSet = new Instances("Data", atts, 0);



        for (Data d : readings) {
            double[] attValues = new double[dataSet.numAttributes()];
            attValues[0] = d.getA().getX();
            attValues[1] = d.getA().getY();
            attValues[2] = d.getA().getZ();
            attValues[3] = d.getL().getX();
            attValues[4] = d.getL().getY();
            attValues[5] = d.getL().getZ();
            attValues[6] = d.getG().getX();
            attValues[7] = d.getG().getY();
            attValues[8] = d.getG().getZ();
            attValues[9] = d.getM().getX();
            attValues[10] = d.getM().getY();
            attValues[11] = d.getM().getZ();

            Instance instance = new Instance(1.0, attValues);
            dataSet.add(instance);
            dataSet.setClassIndex(dataSet.numAttributes() - 1);

        }




        return dataSet;

    }

    public String classifyInstance(Instance instance) throws Exception {
        tree.classifyInstance(instance);
    }



}
