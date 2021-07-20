package com.example.chargemessenger.MVVM.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Configs {

    @PrimaryKey
    @NotNull
    @ColumnInfo
    private Long id;

    @NotNull
    @ColumnInfo
    private String uuid;

    @ColumnInfo
    @NotNull
    private Integer userid;

    @NotNull
    @ColumnInfo
    private Integer batteryLevel;

    public Configs(@NotNull String uuid, @NotNull Integer userid, @NotNull Integer batteryLevel) {
        this.uuid = uuid;
        this.userid = userid;
        this.batteryLevel = batteryLevel;
    }

    @NotNull
    public String getUuid() {
        return uuid;
    }

    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public void setUuid(@NotNull String uuid) {
        this.uuid = uuid;
    }

    @NotNull
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(@NotNull Integer userid) {
        this.userid = userid;
    }

    @NotNull
    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(@NotNull Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userid=" + userid +
                '}';
    }
}
