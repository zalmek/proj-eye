package com.example.chargemessenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.chargemessenger.databinding.FragmentBatLvlBinding;

import org.jetbrains.annotations.NotNull;

public class BatLvlFragment extends Fragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        com.example.chargemessenger.databinding.FragmentBatLvlBinding batLvlBinding = FragmentBatLvlBinding.inflate(inflater, container, false);
        batLvlBinding.extendedFab.setOnClickListener(v ->
        {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.activityid,new EnterFragment() );
            transaction.commit();
        });
        batLvlBinding.waveView.setProgress(23);
        return batLvlBinding.getRoot();
    }
}