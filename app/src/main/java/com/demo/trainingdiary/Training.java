package com.demo.trainingdiary;

import android.content.res.Resources;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "Trainings")
public class Training {

    @PrimaryKey(autoGenerate=true)
    private int id;
    private String title;
    private String description;
    private int level;
    private long duration;
    private long trainingDate;

    public Training(String title, String description, int level, long duration,long trainingDate) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.duration = duration;
        this.trainingDate = trainingDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public long getDuration() {
        return duration;
    }

    public long getTrainingDate() {
        return trainingDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setTrainingDate(long trainingDate) {
        this.trainingDate = trainingDate;
    }
}

