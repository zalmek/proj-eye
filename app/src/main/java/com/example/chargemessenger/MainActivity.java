package com.example.chargemessenger;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.MVVM.Models.Configs;
import com.example.chargemessenger.MVVM.View.BatLvlFragment;
import com.example.chargemessenger.MVVM.View.EnterFragment;
import com.example.chargemessenger.Logic.MyService;
import com.example.chargemessenger.MVVM.ViewModel.ConfigViewModel;
import com.example.chargemessenger.databinding.ActivityMainBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;
    private Observer choosing;

    @Inject
    public ConfigViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopService(new Intent(this, MyService.class));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        choosing = (Observer<Configs>) config -> {
            if (config.getUuid().length() > 5) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit);
                transaction.replace(R.id.activityid, new BatLvlFragment());
                transaction.commit();
                mViewModel.getConfig().removeObserver(choosing);
            }
            else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                EnterFragment enterFragment = new EnterFragment();
                transaction.replace(R.id.activityid, enterFragment);
                transaction.commit();
                binding.progressBar.setVisibility(View.GONE);
                mViewModel.getConfig().removeObserver(choosing);
            }
        };

        mViewModel.getConfig().observeForever(choosing);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.getConfig().removeObserver(choosing);
    }
    class IThread extends Thread {
        @Override
        public void run()    //Этот метод будет выполнен в побочном потоке
        {
//            filewriter.write(getString(R.string.idfilename),"");
//            filewriter.write(getString(R.string.textfilename),"");
        }
}
}
