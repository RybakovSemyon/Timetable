package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.model.Faculties;
import ru.rybakovsemyon.timetableproject.model.Faculty;
import ru.rybakovsemyon.timetableproject.model.TimetableAPI;

public class FacultyActivity extends AppCompatActivity {

    Faculties ans = null;
    AdapterList facultyAdapter;
    ArrayList<Faculty> facultyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty);
        TimetableAPI timetableAPI = TimetableAPI.retrofit.create(TimetableAPI.class);
        Call<Faculties> call = timetableAPI.getFaculty();
        call.enqueue(new Callback<Faculties>() {
            @Override
            public void onResponse(Call<Faculties> call, Response<Faculties> response) {
                if (response.isSuccessful()){
                    ans = response.body();
                    facultyList = ans.getFaculties();
                    if (facultyList != null) {
                        DrawingList();
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
            public void onFailure(Call<Faculties> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Не удалось получить данные", Toast.LENGTH_SHORT);
                System.out.println(t.toString());
                toast.show();
                finish();
            }
        });


    }

    private void DrawingList(){
        ProgressBar pBar;
        TextView tView;
        pBar = (ProgressBar)findViewById(R.id.progressBarFACULTY);
        tView = (TextView)findViewById(R.id.textViewFACULTY);
        pBar.setVisibility(View.GONE);
        tView.setVisibility(View.GONE);
        ListView listView = (ListView)findViewById(R.id.listviewFACULTY);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(facultyList);
        facultyAdapter = new AdapterList(this, objects, 2);
        listView.setAdapter(facultyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                Object tag = textView.getTag();
                String puts = tag.toString();
                Intent intent = new Intent(FacultyActivity.this, GroupActivity.class);
                intent.putExtra("id",puts);
                startActivity(intent);
                System.out.println("."+puts+".");
            }
        });
    }
}
