package com.app.freethinkers.sensorlogger;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class ChooseSensorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public final static String EXTRA_MESSAGE_SENSOR_TYPE_POS = "com.app.freethinkers.MESSAGE_SENSOR_TYPE_POS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sensor);

        SensorManager mySensorManager;
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mySensorManager.getSensorList(Sensor.TYPE_ALL);

        ListView MyElement = (ListView) findViewById(R.id.listView);
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceSensors);
        MyElement.setAdapter(adapter);
        MyElement.setOnItemClickListener(this);
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
        int SensorTypePos;
        SensorTypePos = position;
        viewMainActivity(SensorTypePos);
    }


    public void viewMainActivity(int SensorTypePos) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE_SENSOR_TYPE_POS,SensorTypePos);
        startActivity(intent);
    }

}
