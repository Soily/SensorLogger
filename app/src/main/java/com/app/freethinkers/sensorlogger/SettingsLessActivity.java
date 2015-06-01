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
import android.widget.TextView;


public class SettingsLessActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_LOG_INT = "com.app.freethinkers.MESSAGE_LOG_INT";

    enum enLoggingInterval{
        LogInt_100ms,
        LogInt_500ms,
        LogInt_1s,
        LogInt_5s,
        LogInt_60s
    }

    private static enLoggingInterval myLogInterval = enLoggingInterval.LogInt_100ms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_less);
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

    public void viewMainActivity(enLoggingInterval myLogInterval) {
        Intent intent = new Intent(this, MainActivity.class);
        String LoggingInterval = "";
        switch(myLogInterval){
            case LogInt_100ms:
                LoggingInterval = "100 ms";
                break;
            case LogInt_500ms:
                LoggingInterval = "500 ms";
                break;
            case LogInt_1s:
                LoggingInterval = "1 seconds";
                break;
            case LogInt_5s:
                LoggingInterval = "5 seconds";
                break;
            case LogInt_60s:
                LoggingInterval = "1 minute";
                break;
        }

        intent.putExtra(EXTRA_MESSAGE_LOG_INT,LoggingInterval);
        startActivity(intent);
    }

    public void onRadioButtonsClicked(View v){
        int RadioButton = v.getId();

        RadioButton Button1 = (RadioButton) findViewById(R.id.radioButton);
        RadioButton Button2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton Button3 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton Button4 = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton Button5 = (RadioButton) findViewById(R.id.radioButton5);

        // Set all Radio Buttons to false
        Button1.setChecked(false);
        Button2.setChecked(false);
        Button3.setChecked(false);
        Button4.setChecked(false);
        Button5.setChecked(false);

        // Set clicked Radio Button to true
        RadioButton myButton = (RadioButton) findViewById(RadioButton);
        myButton.setChecked(true);

        switch(RadioButton) {
            case R.id.radioButton:
                // 100 ms
                myLogInterval = enLoggingInterval.LogInt_100ms;
                break;
            case R.id.radioButton2:
                // 500 ms
                myLogInterval = enLoggingInterval.LogInt_500ms;
                break;
            case R.id.radioButton3:
                // 1 second
                myLogInterval = enLoggingInterval.LogInt_1s;
                break;
            case R.id.radioButton4:
                // 5 seconds
                myLogInterval = enLoggingInterval.LogInt_5s;
                break;
            case R.id.radioButton5:
                // 1 Minute
                myLogInterval = enLoggingInterval.LogInt_60s;
                break;
        }

        viewMainActivity(myLogInterval);
    }
}
