package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.rybakovsemyon.timetableproject.R;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);
    }

    public void btn_faculty(View view) {
        Intent intent = new Intent(this, FacultyActivity.class);
        startActivity(intent);
    }

    public void btn_teacher(View view) {
        Intent intent = new Intent(this, TeacherActivity.class);
        startActivity(intent);
    }

    public void btn_auditory(View view) {
        Intent intent = new Intent(this, BuildingActivity.class);
        startActivity(intent);
    }
}
