package com.example.chargemessenger.Database;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.chargemessenger.Database.Dao.ConfigDao;
import com.example.chargemessenger.MVVM.Models.Configs;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class ConfigRepository {
    private ConfigDao mConfigDao;
    private LiveData<List<Configs>> mAllDbConfigs;
    private Context context;

    public ConfigRepository(Context context) {
        this.context = context;

        ConfigsDatabase db = ConfigsDatabase.getDatabase(context);
        mConfigDao = db.configsDao();
        mAllDbConfigs = mConfigDao.getAllConfigs();
    }

    public LiveData<List<Configs>> getAllDbConfigs() {
        return mAllDbConfigs;
    }

    public void AddToDatabase(Configs config) {
        ConfigsDatabase.databaseWriteExecutor.execute(() -> {
            mConfigDao.insert(config);
        });
    }

    public void UpdateInDatabase(Configs config) {
        ConfigsDatabase.databaseWriteExecutor.execute(() -> {
            mConfigDao.update(config);
        });
    }
}
