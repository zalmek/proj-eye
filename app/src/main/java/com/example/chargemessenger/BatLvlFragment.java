package com.example.chargemessenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.chargemessenger.databinding.FragmentBatLvlBinding;

public class BatLvlFragment extends Fragment {
    private FragmentBatLvlBinding batLvlBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        batLvlBinding = FragmentBatLvlBinding.inflate(inflater, container, false);
        batLvlBinding.waveView.setProgress(23);
        return batLvlBinding.getRoot();
    }
}