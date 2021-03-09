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
    private  FragmentBatLvlBinding batLvlFragment;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        batLvlFragment.waveView.setVisibility(View.VISIBLE);
        filereader.read(getString(R.string.batlvl_filename));
        batLvlFragment = FragmentBatLvlBinding.inflate(inflater, container, false);
        batLvlFragment.extendedFab.setOnClickListener(v ->
        {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_open_enter,R.anim.fragment_fade_exit);
            transaction.replace(R.id.activityid,new EnterFragment() );
            transaction.commit();
        });
        return batLvlFragment.getRoot();
    }
}