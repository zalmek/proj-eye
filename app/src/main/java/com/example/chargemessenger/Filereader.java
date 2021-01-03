package com.example.chargemessenger;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Filereader implements Readable{
    private String text = null;

    public String getText() {
        return text;
    }
    @Override
    public void read(Context context,String filename) {
        try {
            String rootDataDir = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
            File file = new File(rootDataDir, filename);
            int length = (int) file.length();

            byte[] bytes = new byte[length];

            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                in.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String contents = new String(bytes);
            text=contents;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
