package com.example.chargemessenger;

import android.content.Context;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class Filewriter implements Writable {
    private final Context context;
    private static final String TAG = "FilewriterTag";



    @Inject
    Filewriter(@ApplicationContext Context context) {
        this.context = context;
    }

    @Override
    public void write(String filename, EditText obj) {
        try {
            String rootDataDir = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(new File(rootDataDir, filename)));
                writer.printf("%s", obj.getText());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String filename, String string) {
        try {
            String rootDataDir = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(new File(rootDataDir, filename)));
                writer.printf("%s", string);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String filename, UUID uuid) {
        try {
            String rootDataDir = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(new File(rootDataDir, filename)));
                writer.printf("%s", uuid.toString());
                writer.close();
                Log.i(TAG,"writed");
            } catch (IOException e) {
                Log.i(TAG,"not writed");
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.i(TAG,"not writed2");
            e.printStackTrace();
        }
    }

}
