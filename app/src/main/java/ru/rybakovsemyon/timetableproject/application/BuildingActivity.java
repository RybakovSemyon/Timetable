package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.model.Building;
import ru.rybakovsemyon.timetableproject.model.Buildings;

public class BuildingActivity extends AppCompatActivity {

    private ArrayList<Building> buildingList = null;
    AdapterList buildingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building);
        Buildings ans = new Buildings(41);
        buildingList = ans.getBuildings();
        DrawingList();
    }
    private void DrawingList(){
        ProgressBar pBar;
        TextView tView;
        pBar = (ProgressBar)findViewById(R.id.progressBarBUILDING);
        tView = (TextView)findViewById(R.id.textViewBUILDING);
        pBar.setVisibility(View.GONE);
        tView.setVisibility(View.GONE);
        ListView listView = (ListView)findViewById(R.id.listviewBUILDING);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(buildingList);
        buildingAdapter = new AdapterList(this, objects, 1);
        listView.setAdapter(buildingAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                Object tag = textView.getTag();
                String puts = tag.toString();
                Intent intent = new Intent(BuildingActivity.this, AuditoryActivity.class);
                intent.putExtra("id",puts);
                startActivity(intent);
                System.out.println("."+puts+".");
            }
        });
    }
}
