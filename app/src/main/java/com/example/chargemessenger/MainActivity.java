package com.example.chargemessenger;


import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.chargemessenger.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

import static android.view.View.INVISIBLE;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Fragment fragment = new EnterFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EnterFragment enterFragment = new EnterFragment();
        ft.replace(R.id.activityid, enterFragment);
        binding.progressBar.setVisibility(INVISIBLE);
        ft.commit();
    }
}
