package com.example.chargemessenger;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chargemessenger.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.Thread.sleep;


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
    static IThread secondThread;
    public ActivityMainBinding binding;
    private static final String TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getApplicationContext().registerReceiver(new MyBroadcastReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondThread = new IThread();
                secondThread.start();
                try {
                    sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                MainActivity.this.startMyService();
//                Toast.makeText(getApplicationContext(),"buttonclicked",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
