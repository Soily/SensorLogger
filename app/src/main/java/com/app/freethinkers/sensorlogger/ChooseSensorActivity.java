package com.app.freethinkers.sensorlogger;

import android.app.LauncherActivity;
import android.content.ClipData;
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


public class ChooseSensorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public final static String EXTRA_MESSAGE_SENSOR_TYPE = "com.app.freethinkers.MESSAGE_SENSOR_TYPE";
    public final static String EXTRA_MESSAGE_SENSOR_TYPE_POS = "com.app.freethinkers.MESSAGE_SENSOR_TYPE_POS";

    private SensorManager mSensorManager;
    private String SensorType;
    private int SensorTypePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sensor);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

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
        ListView MyElement = (ListView) findViewById(R.id.listView);
        String MyItem = MyElement.getItemAtPosition(position).toString();
        SensorTypePos = position;
        SensorType = MyItem;
        viewMainActivity(SensorType);
    }


    public void viewMainActivity(String SensorType) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE_SENSOR_TYPE,SensorType);
        intent.putExtra(EXTRA_MESSAGE_SENSOR_TYPE_POS,SensorTypePos);
        startActivity(intent);
    }

}
