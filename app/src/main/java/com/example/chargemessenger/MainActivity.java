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
            Writer writer = new Writer();
            writer.write(getApplicationContext(),id_file,binding.Userid);
            writer.write(getApplicationContext(),bot_token_file,binding.BotToken);
            writer.write(getApplicationContext(),text_file,binding.Text);

        }
    }
    class OThread extends Thread
    {
        @Override
        public void run()	//Этот метод будет выполнен в побочном потоке
        {
            Reader reader = new Reader();
            reader.read(getApplicationContext(),id_file);
            binding.Userid.setText(reader.getText());
            reader.read(getApplicationContext(),bot_token_file);
            binding.BotToken.setText(reader.getText());
            reader.read(getApplicationContext(),text_file);
            binding.Text.setText(reader.getText());
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
}
