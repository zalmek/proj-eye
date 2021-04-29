package com.example.chargemessenger.MVVM.View;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.MVVM.Models.Configs;
import com.example.chargemessenger.MVVM.ViewModel.ConfigViewModel;
import com.example.chargemessenger.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.chargemessenger.databinding.FragmentEnterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EnterFragment extends Fragment {
    private FragmentEnterBinding enterBinding;
    public static int controlSum = 0;

    private Configs config;
    @Inject
    ConfigViewModel mViewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        config = mViewModel.getConfig().getValue();
        mViewModel.getConfig().observe(getViewLifecycleOwner(), config -> {
            this.config = config;
        });

        // Telegram button onclick logic
        enterBinding = FragmentEnterBinding.inflate(inflater, container, false);
        enterBinding.button.setOnClickListener(v -> {
            try {
                if (config.getUuid().isEmpty()) {
                    Dialog dialog = new MaterialAlertDialogBuilder(getContext(),
                            R.style.ThemeOverlay_App_MaterialAlertDialog)
                            .setTitle(getString(R.string.instr))
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog12, which) -> {
                                UUID uuid = UUID.randomUUID();
                                mViewModel.ChangeConfig(uuid.toString(), null, null);

                                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(String.valueOf(uuid), String.valueOf(uuid));
                                clipboard.setPrimaryClip(clip);

                                Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Chargehelperbot"));
                                telegram.setPackage("org.telegram.messenger");
                                startActivity(telegram);

                                dialog12.cancel();

                                Toast.makeText(getContext(), "Action completed. App is ready. You can quit.", Toast.LENGTH_LONG).show();
                                FragmentManager fragmentManager = getParentFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit);
                                transaction.replace(R.id.activityid, new BatLvlFragment());
                                transaction.commit();
                            })
                            .setMessage("To make app work properly click on 'open telegram' button and paste the text from your clipboard.")
                            .create();
                    dialog.show();

                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                    if (textView != null)
                        textView.setTextSize(17);
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Telegram app is not installed", Toast.LENGTH_LONG).show();
            }
        });
        return enterBinding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        enterBinding = null;
    }
}