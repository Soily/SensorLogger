package com.app.freethinkers.sensorlogger;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    static final String LOG_INT_MS = "LoggingIntervalInMs";
    static final String SENSOR_TYPE_POS = "SensorTypePos";

    static int message_SensorTypePos = 0;
    static int message_Log_Int_In_Ms = 1000;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private List<Sensor> deviceSensors;
    private static String mSensorValue1;

    static Timer timer;
    static TimerTask timerTask;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Sensor Manager and sensor device list
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras !=null) {
            if (extras.containsKey(SettingsLessActivity.EXTRA_MESSAGE_LOG_INT_IN_MS)) {
                message_Log_Int_In_Ms = intent.getIntExtra(SettingsLessActivity.EXTRA_MESSAGE_LOG_INT_IN_MS, 100);
            }
            if (extras.containsKey(ChooseSensorActivity.EXTRA_MESSAGE_SENSOR_TYPE_POS)) {
                message_SensorTypePos = intent.getIntExtra(ChooseSensorActivity.EXTRA_MESSAGE_SENSOR_TYPE_POS, 0);
            }
        }

        // Display Values (SensorType and Log-Interval) in UI
        // SensorType
        TextView SensorTypeTextView = (TextView) findViewById(R.id.textView7);
        SensorTypeTextView.setText(deviceSensors.get(message_SensorTypePos).getName());

        // Log Interval
        TextView LogTextView = (TextView) findViewById(R.id.textView8);
        LogTextView.setText(String.valueOf(message_Log_Int_In_Ms));

    }

    @Override
    protected void onResume() {
        super.onResume();

        //onResume we start our timer so it can start when the app comes from the background
        //startTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Start Settings Activity
            viewSettings();
            return true;
        }
        if (id == R.id.action_chooseSensor) {
            //Start Settings Activity
            viewSensorTypeChooser();
            return true;
        }
        if (id == R.id.action_startLogging){
            startLoggingInMenu();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void viewSettings() {
        Intent intent = new Intent(this, SettingsLessActivity.class);
        startActivity(intent);
    }

    public void viewSensorTypeChooser() {
        Intent intent = new Intent(this, ChooseSensorActivity.class);
        startActivity(intent);
    }

    public void startLoggingInMenu(){
        startService(new Intent(this,LoggingService.class));
    }

    public void StartStopClicked(View view){
        ToggleButton myToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        if(myToggleButton.isChecked())
        {
            // Register Sensor and Listener
            mSensor = deviceSensors.get(message_SensorTypePos);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

            // Apply log interval to Timer --> Reschedule!
            //startTimer();
            if(timer != null){
                timer.cancel();
            }

            //re-schedule timer here
            //otherwise, IllegalStateException of
            //"TimerTask is scheduled already"
            //will be thrown
            timer = new Timer();
            timerTask = new MyTimerTask();
            //delay 10ms, repeat in <LogInterval> ms
            timer.schedule(timerTask, 10, (long)message_Log_Int_In_Ms);
        }else
        {
            // Stop Timer for Logging
            //stopTimerTask();
            if (timer!=null){
                timer.cancel();
                timer = null;
            }
            mSensorManager.unregisterListener(this);
        }

    }

    private void writeLogToDisc(String Filename, String LogData){
        // Create FileOperations Helper Class
        FileOperations myFileOperations = new FileOperations();
        // Write to Disc in case External Storage is available
        if(myFileOperations.isExternalStorageWritable())
        {
            //Create File Handle
            File myFileHandle;
            //Create Logging Directory
            myFileHandle = myFileOperations.getLoggingStorageDir(this,Filename);
            myFileOperations.saveDataToFile(myFileHandle, LogData);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current configuration
        savedInstanceState.putInt(LOG_INT_MS, message_Log_Int_In_Ms);
        savedInstanceState.putInt(SENSOR_TYPE_POS, message_SensorTypePos);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        //Restore values
        message_Log_Int_In_Ms = savedInstanceState.getInt(LOG_INT_MS);
        message_SensorTypePos = savedInstanceState.getInt(SENSOR_TYPE_POS);
    }

    // Sensor Event Listener Overrides
    //
    //
    @Override
    public final void onSensorChanged(SensorEvent event) {
        // Get Sensor Value 1
        float lux = event.values[0];
        mSensorValue1 = String.valueOf(lux);
        // Add Sensor Value 1 to UI
        TextView SensorValue1_View = (TextView) findViewById(R.id.textView11);
        SensorValue1_View.setText(mSensorValue1);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Implementations for Timer
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable(){

                @Override
                public void run() {

                }});
        }

        private void runOnUiThread(Runnable runnable) {
            // Do something with this sensor value. //Add Comment Test
            FileOperations myFileOperations = new FileOperations();
            String ts = myFileOperations.getCurrentTimeStamp();
            writeLogToDisc("LogFile1.txt", ts + ": " + mSensorValue1 + "\n");

        }

    }
}

