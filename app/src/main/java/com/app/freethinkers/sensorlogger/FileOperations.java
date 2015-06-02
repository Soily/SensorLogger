package com.app.freethinkers.sensorlogger;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Patrick on 02.06.2015.
 */
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
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), LoggingName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    public void saveDateToFile(File MyHandle, String AppendString){
        try {

            OutputStream os = new FileOutputStream(MyHandle);
            os.write(AppendString.getBytes());
            os.close();


        }catch(IOException e){
            Log.w("ExternalStorage", "Error writing " + MyHandle, e);
        }

    }
}
