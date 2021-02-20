package com.example.chargemessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chargemessenger.databinding.ActivityMainBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    static IThread secondThread;
    public ActivityMainBinding binding;
    public static String id_file = "id.txt";
    public static String bot_token_file = "bot_token.txt";
    public static String text_file = "text.txt";
    @Inject
    Filewriter filewriter;
    @Inject
    Filereader filereader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        filereader.read(id_file);
        binding.UserId.getEditText().setText(filereader.getText());
        filereader.read(bot_token_file);
        binding.BotToken.getEditText().setText(filereader.getText());
        filereader.read(text_file);
        binding.Text.getEditText().setText(filereader.getText());
        startService(new Intent(this, MyService.class));
        binding.button.setOnClickListener(v -> {
            secondThread = new IThread();
            secondThread.start();
            Toast.makeText(getApplicationContext(), "Action completed. App is ready. You can quit.", Toast.LENGTH_LONG).show();
        });
    }

    class IThread extends Thread {
        @Override
        public void run()    //Этот метод будет выполнен в побочном потоке
        {
            filewriter.write(id_file, binding.UserId.getEditText());
            filewriter.write(bot_token_file, binding.BotToken.getEditText());
            filewriter.write(text_file, binding.Text.getEditText());

        }
    }
}
