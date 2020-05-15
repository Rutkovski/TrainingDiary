package com.demo.trainingdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.item_settings):
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case (R.id.item_about_app):
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("TrainingDiary");
                builder.setMessage("version.0.3\n15.05.2020");
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case (R.id.item_contact_the_developer):

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:rutkovski.dp@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TrainingsDiary");
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(emailIntent, "Send feedback"));
                }else {
                    Log.i("Test","Hmmm");
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
