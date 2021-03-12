package com.example.chargemessenger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.chargemessenger.databinding.FragmentBatLvlBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BatLvlFragment extends Fragment {
    private  FragmentBatLvlBinding batLvlFragment;
    @Inject Filereader filereader;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        batLvlFragment = FragmentBatLvlBinding.inflate(inflater, container, false);
        filereader.read("batlvl.txt");
//        Toast.makeText(getContext(),filereader.getText(), Toast.LENGTH_SHORT).show();
        LateProgressSet lateProgressSet = new LateProgressSet();
        lateProgressSet.execute();
        batLvlFragment.extendedFab.setOnClickListener(v ->
        {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_open_enter,R.anim.fragment_fade_exit);
            transaction.replace(R.id.activityid,new EnterFragment() );
            transaction.commit();
        });
        return batLvlFragment.getRoot();
    }

     void setProgress(){
         batLvlFragment.waveView.setProgress(Integer.parseInt(filereader.getText()));
    }


    class LateProgressSet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            synchronized(this) {
                try {
                    wait(10);
                } catch(InterruptedException ie){}
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setProgress();
        }
    }

}