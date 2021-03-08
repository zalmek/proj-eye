package com.example.chargemessenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.chargemessenger.databinding.FragmentEnterBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EnterFragment extends Fragment {
    private FragmentEnterBinding enterBinding;
    static IThread secondThread;
    @Inject Filewriter filewriter;
    @Inject Filereader filereader;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        enterBinding = FragmentEnterBinding.inflate(inflater, container, false);
        filereader.read(getString(R.string.idfilename));
        enterBinding.UserId.getEditText().setText(filereader.getText());
        filereader.read(getString(R.string.bot_tokenfilename));
        enterBinding.BotToken.getEditText().setText(filereader.getText());
        filereader.read(getString(R.string.textfilename));
        enterBinding.Text.getEditText().setText(filereader.getText());
        enterBinding.button.setOnClickListener(v -> {
            secondThread = new IThread();
            secondThread.start();
            Toast.makeText(getContext(), "Action completed. App is ready. You can quit.", Toast.LENGTH_LONG).show();
        });
        return enterBinding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        enterBinding = null;
    }
    class IThread extends Thread {
        @Override
        public void run()    //Этот метод будет выполнен в побочном потоке
        {
            filewriter.write(getString(R.string.idfilename), enterBinding.UserId.getEditText());
            filewriter.write(getString(R.string.bot_tokenfilename), enterBinding.BotToken.getEditText());
            filewriter.write(getString(R.string.textfilename), enterBinding.Text.getEditText());

        }
    }
}