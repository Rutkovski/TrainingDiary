package com.demo.trainingdiary;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> {

    private List<Training> trainings;
    private ClickTraining clickTraining;

    public TrainingAdapter(ArrayList<Training> trainings) {
        this.trainings = trainings;
    }

    public void setClickTraining(ClickTraining clickTraining) {
        this.clickTraining = clickTraining;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_item_layout, parent, false);
        return new TrainingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        Resources resources = holder.itemView.getResources();
        Training training = trainings.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(training.getTrainingDate());


        String trainingDateDayOfWeek = new SimpleDateFormat("E").format(calendar.getTime());
        String trainingDateDayOfMonth = new SimpleDateFormat("d").format(calendar.getTime());
        String trainingDateMonthAndYear = new SimpleDateFormat("M.yy").format(calendar.getTime());





        int hours = (int) (training.getDuration() / 3600000);
        int minute = (int) ((training.getDuration() % 3600000) / 60000);
        String duration = String.format(resources.getString(R.string.training_duration), hours, minute);
        String level = "";
        switch (training.getLevel()) {
            case 1:
                level = resources.getString(R.string.lvl_easy);
                break;
            case 2:
                level = resources.getString(R.string.lvl_medium);
                break;
            case 3:
                level = resources.getString(R.string.lvl_hard);
                break;
        }


        holder.textViewTitle.setText(training.getTitle());
        holder.textViewDescription.setText(training.getDescription());
        holder.textViewLevel.setText(level);
        holder.textViewTrainingDateDayOfWeek.setText(trainingDateDayOfWeek);

        holder.textViewTrainingDateOfMonth.setText(trainingDateDayOfMonth);
        holder.textViewTrainingDateMonthAndYear.setText(trainingDateMonthAndYear);

        holder.textViewDuration.setText(duration);

    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public interface ClickTraining {
        void onClickTraining (int position);
        void onLongClickTraining(int position);
    }

    class TrainingViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewLevel;
        TextView textViewDuration;
        TextView textViewTrainingDateDayOfWeek;
        TextView textViewTrainingDateOfMonth;
        TextView textViewTrainingDateMonthAndYear;


        public TrainingViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewLevel = itemView.findViewById(R.id.textViewlvl);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);

            textViewTrainingDateDayOfWeek = itemView.findViewById(R.id.textViewTrainingDateDayOfWeek);
            textViewTrainingDateOfMonth = itemView.findViewById(R.id.textViewTrainingDateDayOfMonth);
            textViewTrainingDateMonthAndYear = itemView.findViewById(R.id.textViewTrainingDateMonthAndYear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (clickTraining != null) {
                        clickTraining.onClickTraining(getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (clickTraining != null) {
                        clickTraining.onLongClickTraining(getAdapterPosition());
                    }
                    return true;
                }
            });

        }
    }
}
