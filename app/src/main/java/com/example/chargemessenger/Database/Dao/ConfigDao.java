package com.example.chargemessenger.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chargemessenger.MVVM.Models.Configs;

import java.util.List;

@Dao
public interface ConfigDao {
    @Insert
    void insert(Configs configs);

    @Update
    void update(Configs config);

    @Query("SELECT * FROM configs")
    LiveData<List<Configs>> getAllConfigs();
}
