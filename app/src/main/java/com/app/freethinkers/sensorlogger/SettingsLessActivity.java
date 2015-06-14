package com.app.freethinkers.sensorlogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class SettingsLessActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_LOG_INT_IN_MS = "com.app.freethinkers.MESSAGE_LOG_INT_IN_MS";
    static final String SETTINGS_LOG_INT_MS = "SettingsLoggingIntervalPos";

    private static int LogIntervalInMs = 0;
    private static int SettingsLogIntPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_less);

        // Restore value in radio group (UI)
        RadioGroup myRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        myRadioGroup.check(SettingsLogIntPos);
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current configuration
        savedInstanceState.putInt(SETTINGS_LOG_INT_MS, SettingsLogIntPos);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        //Restore values
        SettingsLogIntPos = savedInstanceState.getInt(SETTINGS_LOG_INT_MS);
    }

    public void viewMainActivity(int myLogIntervalInMs) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE_LOG_INT_IN_MS,myLogIntervalInMs);
        startActivity(intent);
    }

    public void onRadioButtonsClicked(View v){

        // Get position in Radio Group
        RadioGroup myRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        SettingsLogIntPos = myRadioGroup.getCheckedRadioButtonId();

        // Get RadioButton
        RadioButton myRadioButton = (RadioButton) findViewById(SettingsLogIntPos);

        switch(myRadioButton.getId()) {
            case R.id.radioButton:
                // 100 ms
                LogIntervalInMs = 100;
                break;
            case R.id.radioButton2:
                // 500 ms
                LogIntervalInMs = 500;
                break;
            case R.id.radioButton3:
                // 1 second
                LogIntervalInMs = 1000;
                break;
            case R.id.radioButton4:
                // 5 seconds
                LogIntervalInMs = 5000;
                break;
            case R.id.radioButton5:
                // 1 Minute
                LogIntervalInMs = 60000;
                break;
        }

        viewMainActivity(LogIntervalInMs);
    }



}
