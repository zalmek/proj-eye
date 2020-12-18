package com.example.chargemessenger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chargemessenger.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



//String rootDataDir = getApplicationContext().getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
//        File file = new File(rootDataDir, "id.txt");
//        int length = (int) file.length();
//
//        byte[] bytes = new byte[length];
//
//        FileInputStream in = null;
//        try {
//        in = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//        e.printStackTrace();
//        }
//        try {
//        in.read(bytes);
//        } catch (IOException e) {
//        e.printStackTrace();
//        } finally {
//        try {
//        in.close();
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//        }
//        String contents = new String(bytes);
//        Log.i(TAG,contents);
////

public class MainActivity extends AppCompatActivity {
    class IThread extends Thread
    {
        @Override
        public void run()	//Этот метод будет выполнен в побочном потоке
        {
            String rootDataDir = getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
            Log.i(TAG, rootDataDir.toString());
            File file = new File(rootDataDir, "id.txt");
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.printf("%s", binding.Userid.getText()); //Записываем текст в файл
                writer.close(); // Закрываем файл
//                Log.i(TAG, String.valueOf(file.canRead()));
//                Log.i(TAG, String.valueOf(file.canWrite()));
                Log.i(TAG, String.valueOf(file.exists()));
            } catch (IOException e) {
                e.printStackTrace();
                file.exists();
            }
        }
    }
    class OThread extends Thread
    {
        @Override
        public void run()	//Этот метод будет выполнен в побочном потоке
        { String rootDataDir = getApplicationContext().getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
        File file = new File(rootDataDir, "id.txt");
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
        Log.i(TAG,contents);
        binding.Userid.setText(contents);
        }
    }
    static IThread secondThread;
    public ActivityMainBinding binding;
    static OThread thirdThread;
    private static final String TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        thirdThread = new OThread();
        thirdThread.start();
        startService(new Intent(this,MyService.class));
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondThread = new IThread();
                secondThread.start();
               Toast.makeText(getApplicationContext(),"UserId completed. App is ready. You can quit.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
