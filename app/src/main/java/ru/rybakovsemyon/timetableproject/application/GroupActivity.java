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
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.model.Group;
import ru.rybakovsemyon.timetableproject.model.Groups;
import ru.rybakovsemyon.timetableproject.model.TimetableAPI;

public class GroupActivity extends AppCompatActivity {

    Groups ans = null;
    AdapterList groupAdapter;
    ArrayList<Group> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        Intent req = getIntent();
        String req_id = req.getStringExtra("id");
        TimetableAPI timetableAPI = TimetableAPI.retrofit.create(TimetableAPI.class);
        Call<Groups> call = timetableAPI.getGroups(req_id);
        call.enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(Call<Groups> call, Response<Groups> response) {
                if (response.isSuccessful()) {
                    ans = response.body();
                    groupList = ans.getGroups();
                    if (groupList != null) {
                        DrawingList();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "У данного факультета нет групп", Toast.LENGTH_SHORT);
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
            public void onFailure(Call<Groups> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

    private void DrawingList() {
        ProgressBar pBar;
        TextView tView;
        pBar = (ProgressBar) findViewById(R.id.progressBarGROUP);
        tView = (TextView) findViewById(R.id.textViewGROUP);
        pBar.setVisibility(View.GONE);
        tView.setVisibility(View.GONE);
        Collections.sort(groupList, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                return o1.getGroupName().compareTo(o2.getGroupName());
            }
        });
        ListView listView = (ListView) findViewById(R.id.listviewGROUP);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(groupList);
        groupAdapter = new AdapterList(this, objects, 3);
        listView.setAdapter(groupAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                Object tag = textView.getTag();
                String puts = tag.toString();
                Intent intent = new Intent(GroupActivity.this, HomeActivity.class);
                intent.putExtra("type", "group");
                intent.putExtra("id", puts);
                startActivity(intent);
            }
        });
    }
}
