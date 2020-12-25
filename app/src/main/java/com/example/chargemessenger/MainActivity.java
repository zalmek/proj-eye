package com.example.chargemessenger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
            overwriteFile("id.txt",binding.Userid);
            overwriteFile("bot_token.txt",binding.BotToken);
            overwriteFile("text.txt",binding.Text);
        }
    }
    class OThread extends Thread
    {
        @Override
        public void run()	//Этот метод будет выполнен в побочном потоке
        {
        binding.Userid.setText(readFromFileobj("id.txt"));
        binding.BotToken.setText(readFromFileobj("bot_token.txt"));
        binding.Text.setText(readFromFileobj("text.txt"));
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
               Toast.makeText(getApplicationContext(),"Action completed. App is ready. You can quit.",Toast.LENGTH_LONG).show();
            }
        });
    }
    public String readFromFileobj(String filename){
        try {
        String rootDataDir = getApplicationContext().getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
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
        return contents;}
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void overwriteFile(String filename, EditText inObj){
        try {
        String rootDataDir = getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
        Log.i(TAG, rootDataDir.toString());
        File file = new File(rootDataDir, filename);
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.printf("%s", inObj.getText()); //Записываем текст в файл
            writer.close(); // Закрываем файл
//                Log.i(TAG, String.valueOf(file.canRead()));
//                Log.i(TAG, String.valueOf(file.canWrite()));
            Log.i(TAG, String.valueOf(file.exists()));
        } catch (IOException e) {
            e.printStackTrace();
            file.exists();
        }
    }catch (Exception e){
        e.printStackTrace();}
    }
}
