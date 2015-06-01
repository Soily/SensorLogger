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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class ChooseSensorActivity extends AppCompatActivity implements SensorEventListener, AdapterView.OnItemClickListener {

    public final static String EXTRA_MESSAGE = "com.app.freethinkers.MESSAGE";

    private SensorManager mSensorManager;
    private Sensor mLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sensor);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        ListView MyElement = (ListView) findViewById(R.id.listView);
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceSensors);
        MyElement.setAdapter(adapter);
        MyElement.setOnItemClickListener(this);
        /*
        for(int i = 0 ; i < deviceSensors.size();i++) {
            TextView myTextView = new TextView(getApplicationContext());
            myTextView.setTextColor(Color.BLACK);
            myTextView.setText(deviceSensors.get(i).getName());
            bgElement.addView(myTextView);
            MyElement.setSelection(i);

        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Sensor Event Listener Overrides
    //
    //
    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        //float lux = event.values[0];
        // Do something with this sensor value.
        //TextView myTextView = (TextView) findViewById(R.id.textView);
        //myTextView.setText(String.valueOf(lux));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mSensorManager.unregisterListener(this);
    }

    // ListView Item Overrides
    //
    //
    // @Override
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       viewMainActivity();
    }


    public void viewMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Test");
        startActivity(intent);
    }

}
