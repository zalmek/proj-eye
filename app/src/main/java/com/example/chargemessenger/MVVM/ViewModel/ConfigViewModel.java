package com.example.chargemessenger.MVVM.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.MVVM.Models.Configs;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class ConfigViewModel {
    ConfigRepository repository;

    private MutableLiveData<Configs> mConfig = new MutableLiveData<>();

    @Inject
    public ConfigViewModel(@ApplicationContext Context context) {
        repository = new ConfigRepository(context);

        repository.getAllDbConfigs().observeForever(configs -> {
            if (configs.size() > 0) {
                mConfig.setValue(configs.get(0));
            }
        });
    }

    public MutableLiveData<Configs> getConfig() {
        return mConfig;
    };

    public void ChangeConfig(String uuid, Integer userId, Integer batteryLevel) {
        if (repository.getAllDbConfigs().getValue() != null) {
            Configs temp = getConfig().getValue();

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

            getConfig().setValue(temp);
            Configs finalTemp = temp;
            if (repository.getAllDbConfigs().getValue().size() == 0 || repository.getAllDbConfigs().getValue().stream().noneMatch(configs -> configs.getId().equals(finalTemp.getId()))) {
                repository.AddToDatabase(temp);

            } else {
                repository.UpdateInDatabase(temp);
            }
        }
    }
}
