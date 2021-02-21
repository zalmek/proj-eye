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
    @Inject Filewriter filewriter;
    @Inject Filereader filereader;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentbinding = FragmentEnterBinding.inflate(inflater, container, false);
        filereader.read(getString(R.string.idfilename));
        fragmentbinding.UserId.getEditText().setText(filereader.getText());
        filereader.read(getString(R.string.bot_tokenfilename));
        fragmentbinding.BotToken.getEditText().setText(filereader.getText());
        filereader.read(getString(R.string.textfilename));
        fragmentbinding.Text.getEditText().setText(filereader.getText());
        getActivity().startService(new Intent(getActivity().getApplicationContext(), MyService.class));
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
            filewriter.write(getString(R.string.idfilename), fragmentbinding.UserId.getEditText());
            filewriter.write(getString(R.string.bot_tokenfilename), fragmentbinding.BotToken.getEditText());
            filewriter.write(getString(R.string.textfilename), fragmentbinding.Text.getEditText());

        }
    }
}