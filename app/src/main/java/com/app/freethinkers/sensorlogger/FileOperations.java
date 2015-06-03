package com.app.freethinkers.sensorlogger;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileOperations {
    private final static String LOG_TAG = "FileOperationsClass";

    static boolean ExternalStorageWritable;
    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;

    }

    public File getLoggingStorageDir(Context context, String LoggingName) {

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);

        // Get the directory for the app's private documents directory.
        File file = new File(path + "/" + LoggingName);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public void saveDataToFile(File MyHandle, String AppendString){
        try {

            FileOutputStream os = new FileOutputStream(MyHandle,true);
            os.write(AppendString.getBytes());
            os.close();


        }catch(IOException e){
            Log.w("ExternalStorage", "Error writing " + MyHandle, e);
        }

    }

    /**
     *
     * @return yyyy-MM-dd HH:mm:ss formate date as string
     */
    public String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
