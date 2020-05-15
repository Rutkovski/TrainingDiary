package com.demo.trainingdiary;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainViewModel extends AndroidViewModel {
    private static TrainingDB trainingDB;
    private LiveData <List<Training>> trainings;


    public MainViewModel(@NonNull Application application) {
        super(application);
        trainingDB=TrainingDB.getInstance(getApplication());
        trainings=trainingDB.trainingsDao().getAllTrainings();
    }

    public LiveData<List<Training>> getTrainings() {
        return trainings;
    }

    public Training getTraining(Long l){
       GetByIdtTask getByIdtTask =  new GetByIdtTask();
       getByIdtTask.execute(l);
        try {
            return getByIdtTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertTraining (Training training){
        new InsertTask().execute(training);
    }

    public void deleteTraining (Training training){
        new DeleteTask().execute(training);
    }

    public void update (Training training){
        new UpdateTask().execute(training);
    }

    public void deleteAllTraining (){
        new DeleteAllTask().execute();
    }




    private static class InsertTask extends AsyncTask<Training,Void,Void>{
        @Override
        protected Void doInBackground(Training... trainings) {
            if (trainings!=null&&trainings.length>0){
                trainingDB.trainingsDao().insertTraining(trainings[0]);
            }
            return null;
        }
    }

    private static class GetByIdtTask extends AsyncTask<Long,Void,Training>{
        @Override
        protected Training doInBackground(Long... longs) {
            return trainingDB.trainingsDao().getById(longs[0]);
        }

        @Override
        protected void onPostExecute(Training training) {
            super.onPostExecute(training);

        }
    }


    private static class UpdateTask extends AsyncTask<Training,Void,Void>{
        @Override
        protected Void doInBackground(Training... trainings) {
            if (trainings!=null&&trainings.length>0){
                trainingDB.trainingsDao().update(trainings[0]);
            }
            return null;
        }
    }



    private static class DeleteTask extends AsyncTask<Training,Void,Void>{
        @Override
        protected Void doInBackground(Training... trainings) {
            if (trainings!=null&&trainings.length>0){
                trainingDB.trainingsDao().deleteTraining(trainings[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            trainingDB.trainingsDao().deleteAllTrainings();
            return null;
        }
    }




}
