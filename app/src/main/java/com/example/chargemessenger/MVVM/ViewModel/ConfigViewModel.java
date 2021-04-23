package com.example.chargemessenger.MVVM.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.MVVM.Models.Configs;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class ConfigViewModel {
    @Inject
    ConfigRepository repository;

    private MutableLiveData<Configs> mConfig;

    @Inject
    public ConfigViewModel(@ApplicationContext Context context) {
        mConfig = new MutableLiveData<>();
        
        mConfig.observe();
    }

    public MutableLiveData<Configs> getLiveDataConfig() {
        if (mAllDbConfigs.getValue() != null && mAllDbConfigs.getValue().size() > 0) {
            mConfig.setValue(mAllDbConfigs.getValue().get(0));
        }
        return mConfig;
    };

    public Configs getConfig() {
        if (getLiveDataConfig().getValue() == null) {
            ChangeConfig(null, null, null);
        }

        return mConfig.getValue();
    };

    public void ChangeConfig(String uuid, Integer userId, Integer batteryLevel) {
        Configs temp = getLiveDataConfig().getValue();

        if (temp != null) {
            if (uuid != null) {
                temp.setUuid(uuid);
            }
            if (userId != null) {
                temp.setUserid(userId);
            }
            if (batteryLevel != null) {
                temp.setBatteryLevel(batteryLevel);
            }
        } else {
            temp = new Configs(
                    uuid == null ? "" : uuid,
                    userId == null ? 0 : userId,
                    batteryLevel == null ? -1 : batteryLevel
            );
        }

        getLiveDataConfig().setValue(temp);
        if (repository.getAllDbConfigs().getValue() == null || mAllDbConfigs.getValue().size() == 0) {
            repository.AddToDatabase(temp);

        } else {
            repository.UpdateInDatabase(temp);
        }
    }
}
