package com.example.chargemessenger;

import android.content.Context;
import android.os.Environment;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Filewriter implements Writable {

    @Override
    public void write(Context context, String filename, EditText obj) {
        try {
            String rootDataDir = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
            File file = new File(rootDataDir, filename);
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.printf("%s", obj.getText());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();}
    }
}
