package com.example.chargemessenger;

import android.content.Context;
import android.os.Environment;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class Filewriter implements Writable {
    private final Context context;

    @Inject Filewriter(@ApplicationContext Context context) {
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
        }catch (Exception e){
            e.printStackTrace();}
    }
}
