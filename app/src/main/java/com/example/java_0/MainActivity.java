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
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView arrow = findViewById(R.id.upArrow);
        final TextView textView = findViewById(R.id.result);
        final String TAG = "MainApp";

        Switch switchButton = findViewById(R.id.switch1);
        switchButton.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            private SensorManager sensorManager;
            private SensorEventListener accelerometerListener;
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    textView.setText("Starting IMU Scan...");
                    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

                    if (accelerometer != null) {
                        accelerometerListener = new SensorEventListener() {
                            @Override
                            public void onSensorChanged(SensorEvent event) {
                                // Handle accelerometer measurements
                                float x = event.values[0];
                                float y = event.values[1];
                                float z = event.values[2];
                                String displayText = String.format("X: %.2f Y: %.2f Z: %.2f", x, y, z);
                                textView.setText(displayText);
                                float absX = Math.abs(x);
                                float absY = Math.abs(y);
                                float absZ = Math.abs(z);
                                if (absX > absY && absX > absZ){
                                    if (x > 0) {
                                        arrow.setRotation(0);
                                    }
                                    else{
                                        arrow.setRotation((180));
                                    }
                                }
                                else if (absY > absX && absY > absZ){
                                    if (y > 0){
                                        arrow.setRotation(-90);
                                    }
                                    else{
                                        arrow.setRotation(90);
                                    }
                                }
                                else if (absZ > absX & absZ > absY){
                                    if (z > 0) {
                                        arrow.setRotation(-90);
                                    }
                                    else {
                                        arrow.setRotation(90);
                                    }
                                }
                                Log.i(TAG, "IMU:" + displayText);
                            }

                            @Override
                            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                                // Handle changes in sensor accuracy
                            }
                        };

                        // Register the listener with the SensorManager
                        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                    } else {
                        textView.setText("No IMU available / Unsupported format");
                    }

                } else {
                    textView.setText("Switch is OFF");
                    sensorManager.unregisterListener(accelerometerListener);
                }
            }
        });
    }
}
