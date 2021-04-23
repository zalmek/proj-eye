package com.example.chargemessenger.MVVM.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.Logic.MyService;
import com.example.chargemessenger.databinding.FragmentBatLvlBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatLvlFragment extends Fragment {
    private FragmentBatLvlBinding batLvlFragment;
    @Inject
    ConfigRepository repository;
    MutableLiveData<String> batlvl = new MutableLiveData<>();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getContext().startService(new Intent(getContext(), MyService.class));
        batLvlFragment = FragmentBatLvlBinding.inflate(inflater, container, false);

        repository.getLiveDataConfig().observe(getViewLifecycleOwner(), config -> {
            batLvlFragment.textView.setText(config.getBatteryLevel());
            batLvlFragment.waveView.setProgress(config.getBatteryLevel());
        });

        LateProgressSet lateProgressSet = new LateProgressSet();
        lateProgressSet.execute();
        return batLvlFragment.getRoot();
    }

//    public void setProgress() {
//        batLvlFragment.waveView.setProgress(lvl);
//        batLvlFragment.textView.setText(String.valueOf(lvl));
//    }


    class LateProgressSet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (this) {
                try {
                    wait(10);
                } catch (InterruptedException ie) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            setProgress();
        }
    }
}
