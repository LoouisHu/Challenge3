package com.example.noblenotebooklouis.challenge3;

import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private List<Data> readings;
    private Coordinate a, l, g, m;
    private final int WINDOW = 500; //500 is around 5 seconds.
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor linearAcceleration;
    private Sensor gravity;
    private Sensor magneticField;
    private boolean running;
    private Button button;
    private TextView status;
    private Classifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager assetManager = getAssets();
        try {
            classifier = (Classifier) SerializationHelper.read(assetManager.open("Participant1.model"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        running = false;
        initView();
        initButton();
        initSensors();
        readings = new ArrayList<>();
    }

    private void initView() {
        status = (TextView) findViewById(R.id.status);
    }

    private void initButton() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running == false) {
                    running = true;
                    button.setText("Stop");
                } else {
                    running = false;
                    button.setText("Start");
                }
            }
        });
    }

    private void initSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        linearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, linearAcceleration, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running) {
            double aX, aY, aZ, lX, lY, lZ, gX, gY, gZ, mX, mY, mZ;
            switch(sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    aX = sensorEvent.values[0];
                    aY = sensorEvent.values[1];
                    aZ = sensorEvent.values[2];
                    a = new Coordinate(aX, aY, aZ);
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    lX = sensorEvent.values[0];
                    lY = sensorEvent.values[1];
                    lZ = sensorEvent.values[2];
                    l = new Coordinate(lX, lY, lZ);
                    break;
                case Sensor.TYPE_GRAVITY:
                    gX = sensorEvent.values[0];
                    gY = sensorEvent.values[1];
                    gZ = sensorEvent.values[2];
                    g = new Coordinate(gX, gY, gZ);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    mX = sensorEvent.values[0];
                    mY = sensorEvent.values[1];
                    mZ = sensorEvent.values[2];
                    m = new Coordinate(mX, mY, mZ);
                    break;
            }
            if ( a != null && l != null && g != null && m != null) {
                Data data = new Data(a, l, g, m);
                readings.add(data);
                a = null;
                l = null;
                g = null;
                m = null;
            }

            // If readings exceed 2000 values, then put it into the classifier and get an
            // indication back what we were doing; walking, standing, jogging, sitting,
            // biking, going upstairs and going downstairs. After that clear the readings
            // list.

            if (readings.size() > WINDOW) {
                // send window of readings to classifier, get a string back
                // status.setText("RUNNING");
                classify(readings);
                readings.clear();
            }

        }
    }

    public String classify(List<Data> readings) {
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

        classVal.add("Walking");    //0
        classVal.add("Standing");   //1
        classVal.add("Jogging");    //2
        classVal.add("Sitting");    //3
        classVal.add("Biking");     //4
        classVal.add("Upstairs");   //5
        classVal.add("Downstairs"); //6

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



//        Attribute Class = new Attribute("Activity", classVal);

        Instances dataSet = new Instances("TestInstances", (ArrayList<Attribute>) atts, 1);
        dataSet.setClassIndex(dataSet.numAttributes() - 1);


        Instance instCo = new DenseInstance(dataSet.numAttributes());

        Data d = getAverage(readings);
        instCo.setValue(Ax, d.getA().getX());
        instCo.setValue(Az, d.getA().getZ());
        instCo.setValue(Lx, d.getL().getX());
        instCo.setValue(Ay, d.getA().getY());
        instCo.setValue(Ly, d.getL().getY());
        instCo.setValue(Lz, d.getL().getZ());
        instCo.setValue(Gx, d.getG().getX());
        instCo.setValue(Gy, d.getG().getY());
        instCo.setValue(Gz, d.getG().getZ());
        instCo.setValue(Mx, d.getM().getX());
        instCo.setValue(My, d.getM().getY());
        instCo.setValue(Mz, d.getM().getZ());

        instCo.setDataset(dataSet);

        String className = "";
        try {
            double result = classifier.classifyInstance(instCo);
            className = classVal.get(new Double(result).intValue());
            status.setText(className);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return className;

    }


    private Data getAverage(List<Data> data) {
        double aX = 0;
        double aY = 0;
        double aZ = 0;
        double lX = 0;
        double lY = 0;
        double lZ = 0;
        double gX = 0;
        double gY = 0;
        double gZ = 0;
        double mX = 0;
        double mY = 0;
        double mZ = 0;
        for (Data d : data) {
            aX = aX + d.getA().getX();
            aY = aY + d.getA().getY();
            aZ = aZ + d.getA().getZ();
            lX = lX + d.getL().getX();
            lY = lY + d.getL().getY();
            lZ = lZ + d.getL().getZ();
            gX = gX + d.getG().getX();
            gY = gY + d.getG().getY();
            gZ = gZ + d.getG().getZ();
            mX = mX + d.getM().getX();
            mY = mY + d.getM().getY();
            mZ = mZ + d.getM().getZ();
        }
        aX = aX / data.size();
        aY = aY / data.size();
        aZ = aZ / data.size();
        lX = lX / data.size();
        lY = lY / data.size();
        lZ = lZ / data.size();
        gX = gX / data.size();
        gY = gY / data.size();
        gZ = gZ / data.size();
        mX = mX / data.size();
        mY = mY / data.size();
        mZ = mZ / data.size();
        return new Data(new Coordinate(aX, aY, aZ), new Coordinate(lX, lY, lZ), new Coordinate(gX, gY, gZ), new Coordinate(mX, mY, mZ));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Nothing
    }
}
