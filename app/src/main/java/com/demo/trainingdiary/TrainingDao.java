package com.demo.trainingdiary;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TrainingDao {

    @Query("SELECT * FROM trainings ORDER BY trainingDate DESC")
    LiveData<List<Training>> getAllTrainings();

    @Query("DELETE FROM trainings")
    void deleteAllTrainings();

    @Insert
    void insertTraining(Training training);

    @Delete
    void deleteTraining(Training training);

    @Update
    void update(Training training);

    @Query("SELECT * FROM trainings WHERE id = :id")
    Training getById(long id);

}
