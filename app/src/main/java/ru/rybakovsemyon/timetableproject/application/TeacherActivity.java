package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.model.Teacher;
import ru.rybakovsemyon.timetableproject.model.Teachers;
import ru.rybakovsemyon.timetableproject.model.TimetableAPI;

public class TeacherActivity extends AppCompatActivity {

    ArrayList<Teacher> teacherList;
    Teachers ans;
    AutoCompleteTextView mAutoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher);
        TimetableAPI timetableAPI = TimetableAPI.retrofit.create(TimetableAPI.class);
        Call<Teachers> call = timetableAPI.getTeachers();
        call.enqueue(new Callback<Teachers>() {
            @Override
            public void onResponse(Call<Teachers> call, Response<Teachers> response) {
                if (response.isSuccessful()){
                    ans = response.body();
                    teacherList = ans.getTeachers();
                    if (teacherList != null) {
                        Drawing();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Teachers> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Не удалось получить данные", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

    private void Drawing(){
        final int size = teacherList.size();
        String[] nameTeacher = new String[size];
        for (int i = 0; i < size; i++){
            Teacher teacher;
            teacher = teacherList.get(i);
            nameTeacher[i] = teacher.getTeacherName();
        }
        mAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.inputSearch);
        mAutoCompleteTextView.setAdapter(new ArrayAdapter<>(TeacherActivity.this, android.R.layout.simple_dropdown_item_1line, nameTeacher));
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0;
                String ID = null;
                boolean answer = false;
                while (i < size){
                    Teacher teacher;
                    teacher = teacherList.get(i);
                    String name = teacher.getTeacherName();
                    String getText = mAutoCompleteTextView.getText().toString();
                    int comp = getText.compareToIgnoreCase(name);
                    if (comp == 0){
                        answer = true;
                        ID = teacher.getTeacherId();
                        break;
                    }
                    i++;
                }
                if (answer){
                    Intent intent = new Intent(TeacherActivity.this, HomeActivity.class);
                    intent.putExtra("id", ID);
                    intent.putExtra("type", "teacher");
                    startActivity(intent);
                    mAutoCompleteTextView.setText("");
                    System.out.println(ID);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }
        });
        ProgressBar pBar;
        TextView tView;
        pBar = (ProgressBar)findViewById(R.id.progressBarTEACHER);
        tView = (TextView)findViewById(R.id.textViewTEACHER);
        pBar.setVisibility(View.GONE);
        tView.setVisibility(View.GONE);
        Toast toast = Toast.makeText(getApplicationContext(), "Загружено", Toast.LENGTH_SHORT);
        toast.show();
    }
}
