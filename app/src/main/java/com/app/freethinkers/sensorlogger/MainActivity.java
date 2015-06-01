package com.app.freethinkers.sensorlogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get Message from ChooseSensorActivity and Display */
        Intent intent = getIntent();
        String message_SensorType = intent.getStringExtra(ChooseSensorActivity.EXTRA_MESSAGE_SENSOR_TYPE);
        String message_Log_Int = intent.getStringExtra(SettingsLessActivity.EXTRA_MESSAGE_LOG_INT);

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

}
