package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Select;
import java.util.List;
import ru.rybakovsemyon.timetableproject.data.DAuditory;
import ru.rybakovsemyon.timetableproject.data.DBHelper;
import ru.rybakovsemyon.timetableproject.data.DDay;
import ru.rybakovsemyon.timetableproject.data.DGroup;
import ru.rybakovsemyon.timetableproject.data.DLesson;
import ru.rybakovsemyon.timetableproject.data.DTask;
import ru.rybakovsemyon.timetableproject.data.DTeacher;
import ru.rybakovsemyon.timetableproject.data.DTimetable;


public class SplashActivity extends AppCompatActivity{

    public boolean test = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        DBHelper dbHelper = new DBHelper(this);
        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("Timetables.db")
                .addModelClass(DTimetable.class)
                .addModelClass(DDay.class)
                .addModelClass(DLesson.class)
                .addModelClass(DTeacher.class)
                .addModelClass(DAuditory.class)
                .addModelClass(DGroup.class)
                .addModelClass(DTask.class)
                .create();
        ActiveAndroid.initialize(dbConfiguration);
        List<DTimetable> all = new Select().from(DTimetable.class).execute();
        if (all.size() == 0){
            Intent intent = new Intent(this, ChooseActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            int i = 0;
            String type =  null;
            String name = null;
            long t_id = -1;
            while (i < all.size()){
                if (all.get(i).important == 1){
                    type = all.get(i).type;
                    name = all.get(i).name;
                    t_id = all.get(i).getId();
                    break;
                }
                i++;
            }
            String _id = Long.toString(t_id);
            intent.putExtra("type", type);
            intent.putExtra("db_id", _id);
            intent.putExtra("name", name);
            startActivity(intent);
            finish();
        }
    }
}
