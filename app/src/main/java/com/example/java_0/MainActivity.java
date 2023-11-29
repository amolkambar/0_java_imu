package com.example.java_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.result);
        String display = "Start scanning";
        textView.setText(display);
        Switch switchButton = findViewById(R.id.switch1);
        final String TAG = "MainApp";
        switchButton.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            //@Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Perform actions based on switch state (isChecked)
                if (isChecked) {
                    textView.setText("Starting IMU Scan...");
                    SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    //Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

                    if (accelerometer != null) {
                        SensorEventListener accelerometerListener = new SensorEventListener() {
                            @Override
                            public void onSensorChanged(SensorEvent event) {
                                // Handle accelerometer measurements
                                float x = event.values[0];
                                float y = event.values[1];
                                float z = event.values[2];
                                String displayText = String.format("X: %.2f Y: %.2f Z: %.2f", x, y, z);
                                textView.setText(displayText);
                                Log.i(TAG, "IMU:" + displayText);
                            }
                            @Override
                            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                                // Handle changes in sensor accuracy
                            }
                        };
                    } else {
                        textView.setText("No IMU available / Unsupported format");
                    }

                } else {
                    textView.setText("Switch is OFF");
                    // Perform additional actions for OFF state
                }
            }
        });

    }
}