package com.example.noblenotebooklouis.challenge3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
        try {
            classifier = new Classifier();
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
                readings.clear();
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Nothing
    }
}
