package com.app.freethinkers.sensorlogger;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    static final String LOG_INT = "LoggingInterval";
    static final String SENSOR_TYPE = "SensorType";
    static final String SENSOR_TYPE_POS = "SensorTypePos";
    static String message_SensorType = "";
    static String message_Log_Int = "";
    static int message_SensorTypePos = 0;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private List<Sensor> deviceSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String CheckStringNotNull;

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            message_SensorType = savedInstanceState.getString(LOG_INT);
            message_Log_Int = savedInstanceState.getString(SENSOR_TYPE);
            message_SensorTypePos =savedInstanceState.getInt(SENSOR_TYPE_POS);
        } else {
            // Probably initialize members with default values for a new instance
        }

        /* Get Message from ChooseSensorActivity and Display */
        Intent intent = getIntent();
        CheckStringNotNull = intent.getStringExtra(ChooseSensorActivity.EXTRA_MESSAGE_SENSOR_TYPE);
        if(CheckStringNotNull != null)
        {
            message_SensorType = CheckStringNotNull;
            CheckStringNotNull = null;
        }
        CheckStringNotNull = intent.getStringExtra(SettingsLessActivity.EXTRA_MESSAGE_LOG_INT);
        if(CheckStringNotNull != null)
        {
            message_Log_Int = CheckStringNotNull;
            CheckStringNotNull = null;
        }

        message_SensorTypePos = intent.getIntExtra(ChooseSensorActivity.EXTRA_MESSAGE_SENSOR_TYPE_POS,0);


        if(message_SensorType != null)
        {
            TextView myTextView =(TextView)findViewById(R.id.textView7);
            myTextView.setText(message_SensorType);
        }
        if(message_Log_Int != null)
        {
            TextView myTextView =(TextView)findViewById(R.id.textView8);
            myTextView.setText(message_Log_Int);
        }
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

    public void StartStopClicked(View view){
        ToggleButton myToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        if(myToggleButton.isChecked())
        {
            mSensor = deviceSensors.get(message_SensorTypePos);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else
        {
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
            myFileOperations.saveDataToFile(myFileHandle,LogData);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(LOG_INT, message_Log_Int);
        savedInstanceState.putString(SENSOR_TYPE, message_SensorType);
        savedInstanceState.putInt(SENSOR_TYPE_POS, message_SensorTypePos);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    // Sensor Event Listener Overrides
    //
    //
    @Override
    public final void onSensorChanged(SensorEvent event) {
        // Get Sensor Value 1
        float lux = event.values[0];
        String SensorValue1 = String.valueOf(lux);
        // Add Sensor Value 1 to UI
        TextView SensorValue1_View = (TextView) findViewById(R.id.textView11);
        SensorValue1_View.setText(SensorValue1);
        // Do something with this sensor value.
        FileOperations myFileOperations = new FileOperations();
        String ts = myFileOperations.getCurrentTimeStamp();
        writeLogToDisc("LogFile1.txt", ts + ": "+ SensorValue1 + "\n");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
