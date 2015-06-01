package com.app.freethinkers.sensorlogger;

import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;


public class SettingsLessActivity extends ActionBarActivity {

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

                break;
            case R.id.radioButton2:
                // 500 ms

                break;
            case R.id.radioButton3:
                // 1 second

                break;
            case R.id.radioButton4:
                // 5 seconds

                break;
            case R.id.radioButton5:
                // 1 Minute

                break;
        }
    }
}
