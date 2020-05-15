package com.demo.trainingdiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddTrainingActivity extends HomeActivity {
    private TextView textViewDayOfWeek;
    private TextView textViewMonthAndYear;
    private TextView textViewDayOfMount;
    private TextView textViewDuration;

    private EditText editTextViewTitle;
    private EditText editTextDescription;
    private RadioGroup radioGroup;


    private MainViewModel viewModel;
    private Training training;
    private SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        sp = PreferenceManager.getDefaultSharedPreferences(this);


        textViewDayOfWeek = findViewById(R.id.textViewDayOfWeek);
        textViewDayOfMount = findViewById(R.id.textViewDayOfMounth);
        textViewMonthAndYear = findViewById(R.id.textViewMountAndYear);
        textViewDuration = findViewById(R.id.textViewDuration);
        editTextViewTitle = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        radioGroup = findViewById(R.id.radioGroup);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        timeOfTraining = (sp.getInt("key_number", 10)) * 60000;


        dateOfTraining = new GregorianCalendar();


        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            long id = intent.getLongExtra("id", 0);
            training = viewModel.getTraining(id);
            editTextViewTitle.setText(training.getTitle());
            editTextDescription.setText(training.getDescription());
            RadioButton radioButton = null;
            int lvl = training.getLevel();
            switch (lvl) {
                case 1:
                    radioButton = findViewById(R.id.radioButton);
                    break;
                case 2:
                    radioButton = findViewById(R.id.radioButton2);
                    break;
                case 3:
                    radioButton = findViewById(R.id.radioButton3);
                    break;
            }
            radioButton.setChecked(true);
            timeOfTraining = training.getDuration();
            dateOfTraining.setTimeInMillis(training.getTrainingDate());
        }
        setInitialCalendar();
        setInitialTime();
    }

    public void onClickAddDate(View view) {
        new DatePickerDialog(
                AddTrainingActivity.this,
                listener,
                dateOfTraining.get(Calendar.YEAR),
                dateOfTraining.get(Calendar.MONTH),
                dateOfTraining.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    public void onClickDuration(View view) {
        new TimePickerDialog(AddTrainingActivity.this,
                listenerTime,
                (int) (timeOfTraining / 3600000),
                (int) ((timeOfTraining % 3600000) / 60000),
                true
        ).show();
    }

    private void setInitialTime() {
        int hours = (int) (timeOfTraining / 3600000);
        int minute = (int) ((timeOfTraining % 3600000) / 60000);
        textViewDuration.setText(String.format(getString(R.string.training_duration), hours, minute));
    }

    private void setInitialCalendar() {
        SimpleDateFormat monthAndYear = new SimpleDateFormat("MMM yyyy");
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EEEE");
        textViewMonthAndYear.setText(monthAndYear.format(dateOfTraining.getTime()));
        textViewDayOfMount.setText(dateOfTraining.get(Calendar.DAY_OF_MONTH) + "");
        textViewDayOfWeek.setText(dayOfWeek.format(dateOfTraining.getTime()));
    }

    public void onClickButtonAdd(View view) {
        String title = editTextViewTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int idRadiobutton = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(idRadiobutton);
        int lvl = Integer.parseInt((String) radioButton.getTag());

        if (!title.isEmpty()) {
            if (training != null) {
                viewModel.deleteTraining(training);
            }
            training = new Training(title, description, lvl, timeOfTraining, dateOfTraining.getTimeInMillis());
            viewModel.insertTraining(training);
            onBackPressed();
        } else {
            Toast.makeText(this, R.string.warning_title, Toast.LENGTH_SHORT).show();
        }
    }
    private Calendar dateOfTraining;
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateOfTraining.set(year, monthOfYear, dayOfMonth);
            setInitialCalendar();
        }
    };
    private long timeOfTraining;
    TimePickerDialog.OnTimeSetListener listenerTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            timeOfTraining = i * 3600000 + i1 * 60000;
            setInitialTime();
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}
