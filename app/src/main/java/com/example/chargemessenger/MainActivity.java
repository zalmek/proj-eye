package com.example.chargemessenger;


import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.chargemessenger.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;
    private static final int NOTIFY_ID = 229;
    // Идентификатор канала
    private static String CHANNEL_ID = "Chargemes channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Fragment fragment = new EnterFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EnterFragment enterFragment = new EnterFragment();
        ft.replace(R.id.activityid, enterFragment);
        binding.progressBar.setVisibility(View.GONE);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
