package com.example.chargemessenger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.chargemessenger.databinding.FragmentEnterBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EnterFragment extends Fragment {
    private FragmentEnterBinding enterBinding;
    static int controlSum = 0;
    @Inject
    Filewriter filewriter;
    @Inject
    Filereader filereader;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        File f = new File(getContext().getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath()+ "/"+getString(R.string.textfilename));
        if (!f.exists()){
            IThread iTheread = new IThread();
            iTheread.start();}
        enterBinding = FragmentEnterBinding.inflate(inflater, container, false);
        filereader.read(getString(R.string.textfilename));
        if (filereader.getText().length() > 5)
            controlSum += 1;
        if (controlSum >= 1) {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit);
            transaction.replace(R.id.activityid, new BatLvlFragment());
            transaction.commit();
        }
        enterBinding.button.setOnClickListener(v -> {
            try {
                if (filereader.getText().length() > 5) {
                    String uuid = filereader.getText();
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(String.valueOf(uuid), String.valueOf(uuid));
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getContext(), String.valueOf(uuid), Toast.LENGTH_SHORT).show();
                    Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Chargehelperbot"));
                    telegram.setPackage("org.telegram.messenger");
                    startActivity(telegram);
                } else {
                    UUID uuid = UUID.randomUUID();
                    filewriter.write(getString(R.string.textfilename), uuid);
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(String.valueOf(uuid), String.valueOf(uuid));
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getContext(), String.valueOf(uuid), Toast.LENGTH_SHORT).show();
                    Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Chargehelperbot"));
                    telegram.setPackage("org.telegram.messenger");
                    startActivity(telegram);
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Telegram app is not installed", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(getContext(), "Action completed. App is ready. You can quit.", Toast.LENGTH_LONG).show();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit);
            transaction.replace(R.id.activityid, new BatLvlFragment());
            transaction.commit();
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
//            filewriter.write(getString(R.string.idfilename),"");
            filewriter.write(getString(R.string.textfilename),"");
        }
}
}