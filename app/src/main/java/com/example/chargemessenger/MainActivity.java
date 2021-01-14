package com.example.chargemessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chargemessenger.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static String id_file = "id.txt";
    public static String bot_token_file = "bot_token.txt";
    public static String text_file = "text.txt";
    class IThread extends Thread
    {
        @Override
        public void run()	//Этот метод будет выполнен в побочном потоке
        {
            Filewriter filewriter = new Filewriter();
            filewriter.write(getApplicationContext(),id_file,binding.UserId.getEditText());
            filewriter.write(getApplicationContext(),bot_token_file,binding.BotToken.getEditText());
            filewriter.write(getApplicationContext(),text_file,binding.Text.getEditText());

        }
    }
    static IThread secondThread;
    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Filereader filereader = new Filereader();
        filereader.read(getApplicationContext(),id_file);
        binding.UserId.getEditText().setText(filereader.getText());
        filereader.read(getApplicationContext(),bot_token_file);
        binding.BotToken.getEditText().setText(filereader.getText());
        filereader.read(getApplicationContext(),text_file);
        binding.Text.getEditText().setText(filereader.getText());
        startService(new Intent(this,MyService.class));
        binding.button.setOnClickListener(v -> {
            secondThread = new IThread();
            secondThread.start();
           Toast.makeText(getApplicationContext(),"Action completed. App is ready. You can quit.",Toast.LENGTH_LONG).show();
        });
    }
}
