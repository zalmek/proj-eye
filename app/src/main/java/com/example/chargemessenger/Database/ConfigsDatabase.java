package com.example.chargemessenger.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.chargemessenger.Database.Dao.ConfigDao;
import com.example.chargemessenger.MVVM.Models.Configs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Provides;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Database(entities = {Configs.class}, version = 1, exportSchema = false)
public abstract class ConfigsDatabase extends RoomDatabase {
    public abstract ConfigDao configsDao();

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static ConfigsDatabase instance = null;

    // CAN CAUSE MISTAKE CHANGE COMMEND NEXT TO LINES
//    @Singleton
//    @Provides
//    public static ConfigsDatabase getDatabase(final Context context) {
//                    return Room.databaseBuilder(context,
//                            ConfigsDatabase.class,
//                            "configs_database.db")
//                            .build();
//    }


    public static ConfigsDatabase getDatabase(@ApplicationContext Context context) {
        if (instance == null) {
            synchronized (ConfigsDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            ConfigsDatabase.class,
                            "configs_database.db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    databaseWriteExecutor.execute(() -> {
                                        instance.configsDao().insert(new Configs("", 0,-1));
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }
}
