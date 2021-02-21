package com.example.chargemessenger;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chargemessenger.databinding.FragmentEnterBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EnterFragment extends Fragment {
    private FragmentEnterBinding fragmentbinding;
    static IThread secondThread;
    public static String id_file = "id.txt";
    public static String bot_token_file = "bot_token.txt";
    public static String text_file = "text.txt";
    @Inject Filewriter filewriter;
    @Inject Filereader filereader;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentbinding = FragmentEnterBinding.inflate(inflater, container, false);
        filereader.read(id_file);
        fragmentbinding.UserId.getEditText().setText(filereader.getText());
        filereader.read(bot_token_file);
        fragmentbinding.BotToken.getEditText().setText(filereader.getText());
        filereader.read(text_file);
        fragmentbinding.Text.getEditText().setText(filereader.getText());
        getActivity().startService(new Intent(getContext(), MyService.class));
        fragmentbinding.button.setOnClickListener(v -> {
            secondThread = new IThread();
            secondThread.start();
            Toast.makeText(getContext(), "Action completed. App is ready. You can quit.", Toast.LENGTH_LONG).show();
        });
        return fragmentbinding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentbinding = null;
    }
    class IThread extends Thread {
        @Override
        public void run()    //Этот метод будет выполнен в побочном потоке
        {
            filewriter.write(id_file, fragmentbinding.UserId.getEditText());
            filewriter.write(bot_token_file, fragmentbinding.BotToken.getEditText());
            filewriter.write(text_file, fragmentbinding.Text.getEditText());

        }
    }
}