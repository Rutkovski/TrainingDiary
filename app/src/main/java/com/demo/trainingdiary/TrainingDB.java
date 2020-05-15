package com.demo.trainingdiary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Training.class},version = 1,exportSchema = false)
public abstract class TrainingDB extends RoomDatabase {

   private static final String nameDB = "trainings.db";
   private static TrainingDB trainingDB;
   private static final Object lock=new Object();

    public static TrainingDB getInstance (Context context) {
        synchronized (lock) {
            if (trainingDB == null) {
                trainingDB = Room.databaseBuilder(context, TrainingDB.class, nameDB)
                        .build();
            }
        }
        return trainingDB;
    }

    public abstract TrainingDao trainingsDao();
}
