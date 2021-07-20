package com.example.chargemessenger.MVVM.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.Logic.MyService;
import com.example.chargemessenger.MVVM.ViewModel.ConfigViewModel;
import com.example.chargemessenger.databinding.FragmentBatLvlBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatLvlFragment extends Fragment {
    private FragmentBatLvlBinding batLvlFragment;
//    WaveView waveView;
    @Inject
    ConfigViewModel mViewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getContext().startService(new Intent(getContext(), MyService.class));
        batLvlFragment = FragmentBatLvlBinding.inflate(inflater, container, false);
//        waveView=new WaveView(getContext());
//        waveView.setShowWave(true);
        mViewModel.getConfig().observe(getViewLifecycleOwner(), config -> {
            batLvlFragment.textView.setText(config.getBatteryLevel().toString());
            batLvlFragment.waveView.setWaveXAxisPositionMultiplier(1f-config.getBatteryLevel()*0.01f);
        });
//        LateProgressSet lateProgressSet = new LateProgressSet();
//        lateProgressSet.execute();

        return batLvlFragment.getRoot();
    }



    }
//    public void setProgress() {
//        batLvlFragment.waveView.setProgress(lvl);
//        batLvlFragment.textView.setText(String.valueOf(lvl));
//    }


//    class LateProgressSet extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            synchronized (MainThread) {
//                try {
//                    wait(10);
//                } catch (InterruptedException ie) {
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
////            setProgress();
//        }
//    }

