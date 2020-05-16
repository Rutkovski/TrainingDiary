package com.demo.trainingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends HomeActivity {
    private final ArrayList<Training> trainings = new ArrayList<>();
    private RecyclerView recyclerView;
    private TrainingAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerView = findViewById(R.id.recycler);
        adapter = new TrainingAdapter(trainings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        init();

        adapter.setClickTraining(new TrainingAdapter.ClickTraining() {
            @Override
            public void onClickTraining(int position) {
                Intent intent = new Intent(getApplicationContext(), AddTrainingActivity.class);
                long id = adapter.getTrainings().get(position).getId();
                intent.putExtra("id", id);
                startActivity(intent);
            }

            @Override
            public void onLongClickTraining(int position) {
                warningRemove(position);
            }
        });
    }

    public void onClickAddTraining(View view) {
        Intent intent = new Intent(this, AddTrainingActivity.class);
        startActivity(intent);
    }

    private void init() {
        LiveData<List<Training>> trainingsFromDB = viewModel.getTrainings();
        trainingsFromDB.observe(this, new Observer<List<Training>>() {
            @Override
            public void onChanged(List<Training> trainingsFromLiveData) {
                adapter.setTrainings(trainingsFromLiveData);
            }
        });
    }

    public void remove(int position) {
        Training training = adapter.getTrainings().get(position);
        viewModel.deleteTraining(training);
    }

    private void warningRemove(int position) {
        FragmentManager manager = getSupportFragmentManager();
        DialogRemove dialogRemove = new DialogRemove();
        Bundle bundlePosition = new Bundle();
        bundlePosition.putInt("position", position);
        dialogRemove.setArguments(bundlePosition);
        dialogRemove.show(manager, "dialog_remove");
    }
}
