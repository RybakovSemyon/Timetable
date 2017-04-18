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
import ru.rybakovsemyon.timetableproject.model.Auditories;
import ru.rybakovsemyon.timetableproject.model.Auditory;
import ru.rybakovsemyon.timetableproject.model.Group;
import ru.rybakovsemyon.timetableproject.model.Groups;
import ru.rybakovsemyon.timetableproject.model.TimetableAPI;

public class AuditoryActivity extends AppCompatActivity {

    Auditories ans = null;
    AdapterList auditoryAdapter;
    ArrayList<Auditory> auditoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auditory);
        Intent req = getIntent();
        String req_id = req.getStringExtra("id");
        TimetableAPI timetableAPI = TimetableAPI.retrofit.create(TimetableAPI.class);
        Call<Auditories> call = timetableAPI.getAuditories(req_id);
        call.enqueue(new Callback<Auditories>() {
            @Override
            public void onResponse(Call<Auditories> call, Response<Auditories> response) {
                if (response.isSuccessful()) {
                    ans = response.body();
                    auditoryList = ans.getAuditories();
                    if (auditoryList != null) {
                        DrawingList();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "В данном корпусе нет аудиторий", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Не удалось получить данные", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Auditories> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

    private void DrawingList() {
        ProgressBar pBar;
        TextView tView;
        pBar = (ProgressBar) findViewById(R.id.progressBarAuditory);
        tView = (TextView) findViewById(R.id.textViewAuditory);
        pBar.setVisibility(View.GONE);
        tView.setVisibility(View.GONE);
        ListView listView = (ListView) findViewById(R.id.listviewAuditory);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(auditoryList);
        auditoryAdapter = new AdapterList(this, objects, 4);
        listView.setAdapter(auditoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                Object tag = textView.getTag();
                String puts = tag.toString();
                Intent intent = new Intent(AuditoryActivity.this, HomeActivity.class);
                intent.putExtra("type", "auditory");
                intent.putExtra("id", puts);
                startActivity(intent);
            }
        });
    }
}
