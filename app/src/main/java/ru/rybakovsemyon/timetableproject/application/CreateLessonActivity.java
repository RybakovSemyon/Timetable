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
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.data.DAuditory;
import ru.rybakovsemyon.timetableproject.data.DDay;
import ru.rybakovsemyon.timetableproject.data.DGroup;
import ru.rybakovsemyon.timetableproject.data.DLesson;
import ru.rybakovsemyon.timetableproject.data.DTeacher;

public class CreateLessonActivity extends AppCompatActivity {

    private int count = 0;
    int needWeekday = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createlesson);
        Intent intentGet = getIntent();
        String weekday = intentGet.getStringExtra("weekday");
        String min_date = intentGet.getStringExtra("min_day");
        final String max_date = intentGet.getStringExtra("max_day");
        String nameSchedule = intentGet.getStringExtra("name");
        final String type = intentGet.getStringExtra("type");
        final long db_id = Long.parseLong(intentGet.getStringExtra("db_id"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        final Calendar minDate = GregorianCalendar.getInstance();
        final Calendar maxDate = GregorianCalendar.getInstance();
        ArrayList<String> dates = new ArrayList<>();
        final Spinner begin_lesson = (Spinner) findViewById(R.id.ed_time);
        ArrayAdapter<?> adapter_time = ArrayAdapter.createFromResource(this, R.array.timesStart, android.R.layout.simple_spinner_item);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        begin_lesson.setAdapter(adapter_time);
        final EditText edit_info1 = (EditText) findViewById(R.id.ed_info1);
        final EditText edit_info2 = (EditText) findViewById(R.id.ed_info2);
        TextView label1 = (TextView) findViewById(R.id.cl_info1);
        TextView label2 = (TextView) findViewById(R.id.cl_info2);
        final EditText edit_subject = (EditText) findViewById(R.id.ed_subject);
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
        final String[] typeLesson = new String[4];
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
            needWeekday = Integer.parseInt(weekday);
            while (beginWeekday != needWeekday){
                minDate.add(Calendar.DATE, 1);
                beginWeekday = 7 - (8 - minDate.get(GregorianCalendar.DAY_OF_WEEK))%7;
            }
            while (minDate.before(maxDate)){
                Date date = minDate.getTime();
                dates.add(formatter.format(date));
                minDate.add(Calendar.DATE, 7);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] stringDates = dates.toArray(new String[0]);
        ArrayAdapter<String> adapter_date = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringDates);
        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner date_start = (Spinner) findViewById(R.id.ed_start);
        final Spinner date_end = (Spinner) findViewById(R.id.ed_end);
        date_start.setAdapter(adapter_date);
        date_end.setAdapter(adapter_date);
        Button button = (Button) findViewById(R.id.new_lesson);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edit_info1.getText().toString() == null) && (edit_info2.getText().toString() == null) && (edit_subject.getText().toString() == null)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (minDate.before(maxDate)) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Дата начала не может быть\nбольше даты конца", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    ActiveAndroid.beginTransaction();
                    try {
                        DLesson dLesson = new DLesson();
                        dLesson.subject = edit_subject.getText().toString();
                        List<DDay> dDayList = new Select().from(DDay.class).where("DTimetable = ? and Weekday = ?", db_id, needWeekday).execute();
                        dLesson.dday = dDayList.get(0); //peredat'
                        dLesson.dates = "stub";
                        dLesson.dateStart = date_start.getSelectedItem().toString();
                        dLesson.dateEnd = date_end.getSelectedItem().toString();
                        String[] timesE = getResources().getStringArray(R.array.timesEnd);
                        String[] timesS = getResources().getStringArray(R.array.timesStart);
                        dLesson.timeStart = timesS[begin_lesson.getSelectedItemPosition()];
                        dLesson.timeEnd = timesE[begin_lesson.getSelectedItemPosition()];
                        dLesson.parity = "stub";
                        String typeL = null;
                        switch (type_lesson.getSelectedItem().toString()){
                            case "Практика":
                                typeL = "0";
                                break;
                            case "Лаборатория":
                                typeL = "1";
                                break;
                            case "Лекция":
                                typeL = "2";
                                break;
                            case "Семинар":
                                typeL = "3";
                                break;
                        }
                        dLesson.type = typeL;
                        dLesson.synthetic = 1;
                        dLesson.clusterName = "stub";
                        dLesson.clusterType = "stub";
                        dLesson.clusterId = "stub";
                        dLesson.save();
                        System.out.println(type);
                        switch (type) {
                            case "teacher":
                                    DGroup dGroup = new DGroup();
                                    dGroup.dlesson = dLesson;
                                    dGroup.groupID = "stub";
                                    dGroup.groupType = "stub";
                                    dGroup.groupName = edit_info1.getText().toString();
                                    dGroup.save();
                                    DAuditory dAuditory = new DAuditory();
                                    dAuditory.auditoryName = edit_info2.getText().toString();
                                    dAuditory.auditoryID = "stub";
                                    dAuditory.auditoryAddress = "stub";
                                    dAuditory.dlesson = dLesson;
                                    dAuditory.save();
                                break;
                            case "auditory":
                                    DGroup aGroup = new DGroup();
                                    aGroup.dlesson = dLesson;
                                    aGroup.groupID = "stub";
                                    aGroup.groupType = "(Кластер)";
                                    aGroup.groupName = edit_info2.getText().toString();
                                    aGroup.save();
                                    DTeacher dTeacher = new DTeacher();
                                    dTeacher.teacherName = edit_info1.getText().toString();
                                    dTeacher.teacherID = "stub";
                                    dTeacher.dlesson = dLesson;
                                    dTeacher.save();
                                break;
                            case "group":
                                    DAuditory gAuditory = new DAuditory();
                                    gAuditory.auditoryName = edit_info2.getText().toString();
                                    gAuditory.auditoryID = "stub";
                                    gAuditory.auditoryAddress = "stub";
                                    gAuditory.dlesson = dLesson;
                                    gAuditory.save();
                                    DTeacher gTeacher = new DTeacher();
                                    gTeacher.teacherName = edit_info1.getText().toString();
                                    gTeacher.teacherID = "stub";
                                    gTeacher.dlesson = dLesson;
                                    gTeacher.save();
                                break;
                            default:
                                break;
                        }
                    } finally {
                        ActiveAndroid.setTransactionSuccessful();
                    }
                    ActiveAndroid.endTransaction();
                }
                System.out.println("ok____________________________________ok");
            }
                });
            }

    @Override
    public void onBackPressed() {
        count++;
        if (count == 1){
            Toast toast = Toast.makeText(getApplicationContext(), "Нажмите еще раз, что бы\nвыйти и не сохранить изменения", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            super.onBackPressed();
        }

    }

}
