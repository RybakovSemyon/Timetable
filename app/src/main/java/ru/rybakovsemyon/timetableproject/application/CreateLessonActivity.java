package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.rybakovsemyon.timetableproject.R;

public class CreateLessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createlesson);
        Intent intentGet = getIntent();
        String weekday = intentGet.getStringExtra("weekday");
        String min_date = intentGet.getStringExtra("min_day");
        String max_date = intentGet.getStringExtra("max_day");
        String nameSchedule = intentGet.getStringExtra("name");
        String type = intentGet.getStringExtra("type");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar minDate = GregorianCalendar.getInstance();
        Calendar maxDate = GregorianCalendar.getInstance();
        ArrayList<String> dates = new ArrayList<>();
        System.out.println(type + " " + min_date + " "+ max_date + " "+ weekday);
        final Spinner begin_lesson = (Spinner) findViewById(R.id.ed_time);
        ArrayAdapter<?> adapter_time = ArrayAdapter.createFromResource(this, R.array.timesStart, android.R.layout.simple_spinner_item);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        begin_lesson.setAdapter(adapter_time);
        EditText edit_info1 = (EditText) findViewById(R.id.ed_info1);
        EditText edit_info2 = (EditText) findViewById(R.id.ed_info2);
        TextView label1 = (TextView) findViewById(R.id.cl_info1);
        TextView label2 = (TextView) findViewById(R.id.cl_info2);
        EditText edit_subject = (EditText) findViewById(R.id.ed_subject);
        System.out.println(edit_info1.getText());
        switch (type){
            case "auditory":
                getSupportActionBar().setTitle(nameSchedule + " " + getString(R.string.info_auditory));
                label1.setText(R.string.cl_infoTeacher);
                label2.setText(R.string.cl_infoGroup);
                edit_info1.setHint(getString(R.string.edit_teacher));
                edit_info2.setHint(getString(R.string.edit_group));
                break;
            case "group":
                getSupportActionBar().setTitle(nameSchedule + " " + getString(R.string.info_group));
                label1.setText(R.string.cl_infoTeacher);
                label2.setText(R.string.cl_infoAud);
                edit_info1.setHint(getString(R.string.edit_teacher));
                edit_info2.setHint(getString(R.string.edit_auditory));
                break;
            case "teacher":
                getSupportActionBar().setTitle(nameSchedule + " "+ getString(R.string.info_teacher));
                label1.setText(R.string.cl_infoGroup);
                label2.setText(R.string.cl_infoAud);
                edit_info1.setHint(getString(R.string.edit_group));
                edit_info2.setHint(getString(R.string.edit_auditory));
                break;
        }
        String[] typeLesson = new String[4];
        typeLesson[0] = "Практика";
        typeLesson[1] = "Лаборатория";
        typeLesson[2] = "Лекция";
        typeLesson[3] = "Семинар";
        ArrayAdapter<String> adapter_type = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeLesson);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner type_lesson = (Spinner) findViewById(R.id.ed_type);
        type_lesson.setAdapter(adapter_type);
        try {
            minDate.setTime(formatter.parse(min_date));
            maxDate.setTime(formatter.parse(max_date));
            int beginWeekday = 7 - (8 - minDate.get(GregorianCalendar.DAY_OF_WEEK))%7;
            int needWeekday = Integer.parseInt(weekday);
            while (beginWeekday != needWeekday){
                minDate.add(Calendar.DATE, 1);
                beginWeekday = 7 - (8 - minDate.get(GregorianCalendar.DAY_OF_WEEK))%7;
            }
            while (minDate.before(maxDate)){
                Date date = minDate.getTime();
                dates.add(formatter.format(date));
                minDate.add(Calendar.DATE, 7);
            }
//            Date now = new Date();
//            Date then = minDate.getTime();
//            long razn = now.getTime() - then.getTime();
//            begin_position = (int)(razn/(24*60*60*1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] stringDates = dates.toArray(new String[0]);
        ArrayAdapter<String> adapter_date = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringDates);
        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner date_start = (Spinner) findViewById(R.id.ed_start);
        Spinner date_end = (Spinner) findViewById(R.id.ed_end);
        date_start.setAdapter(adapter_date);
        date_end.setAdapter(adapter_date);
        Button button = (Button) findViewById(R.id.new_lesson);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = type_lesson.getSelectedItem().toString();
                String azaza = begin_lesson.getSelectedItem().toString();
            }
        });




    }

}
