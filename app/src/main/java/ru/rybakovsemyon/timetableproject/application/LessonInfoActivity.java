package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.rybakovsemyon.timetableproject.R;

public class LessonInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_info);
        Intent putIntent = getIntent();
        Button btn_add = (Button) findViewById(R.id.create_tasks);
        final String[] data = putIntent.getStringArrayExtra("data");
        String day_of_week = putIntent.getStringExtra("day_of_week");
        if (day_of_week == null){
            Intent chooseIntent = new Intent(this, ChooseActivity.class);
            startActivity(chooseIntent);
            finish();
        }
        String TEST = putIntent.getStringExtra("test");
        System.out.println(TEST + "KAK TAK?");
        if (TEST.equalsIgnoreCase("true")) {
            TextView tasks = (TextView) findViewById(R.id.text_task);
            tasks.setVisibility(View.GONE);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tasks_layout);
            linearLayout.setVisibility(View.GONE);
            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.tasks_line);
            linearLayout1.setVisibility(View.GONE);
            btn_add.setVisibility(View.GONE);
        }
        /*
            Какой индекс за что отвечает:
            0 - имя аудитории
            1 - адрес аудитории
            2 - ид аудитории
            3 - имя препода
            4 - ид препода
            5 - имя группы
            6 - ид группы
            7 - тип кластера
            8 - время пары
            9 - предмет
            10 - тип занятия
            11 - дата начала
            12 - дата конца
            13 id
            Итого 14 объектов
         */
        TextView lesson_week = (TextView)findViewById(R.id.lesson_infoTime);
        lesson_week.setText(day_of_week+", "+data[8]);
        TextView lesson_subject = (TextView)findViewById(R.id.lesson_subject);
        lesson_subject.setText(data[9]);
        TextView lesson_type = (TextView)findViewById(R.id.lesson_type);
        lesson_type.setText(data[10]);
        TextView info1 = (TextView)findViewById(R.id.lesson_info1);
        TextView info2 = (TextView)findViewById(R.id.lesson_info2);
        TextView lesson_start = (TextView)findViewById(R.id.lesson_start_end);
        String c_start = getString(R.string.date_start_end) +" " + data[11] + " - " + data[12];
        lesson_start.setText(c_start);
        if (data[2] == null){ //если это аудитория, то
            String temp = data[0]+ " "+ getString(R.string.info_auditory);
            getSupportActionBar().setTitle(temp);
            SpannableString s1 = new SpannableString(data[3]);
            s1.setSpan(new UnderlineSpan(), 0, data[3].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            info1.setText(s1); //в инфо1 преподователя и событие на клик
            SpannableString s2 = new SpannableString(data[5]);
            s2.setSpan(new UnderlineSpan(), 0, data[5].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            info2.setText(s2); //в инфо2 группу и событие на клик
            if (data[15].equalsIgnoreCase("0")) {
                info1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LessonInfoActivity.this, HomeActivity.class);
                        intent.putExtra("id", data[4]);
                        intent.putExtra("type", "teacher");
                        startActivity(intent);
                    }
                });
                info2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LessonInfoActivity.this, HomeActivity.class);
                        intent.putExtra("id", data[6]);
                        intent.putExtra("type", "group");
                        startActivity(intent);
                    }
                });
            }
        } else {
            if (data[4] == null){ //если это преподаватель, то
                getSupportActionBar().setTitle(data[3] + " "+ getString(R.string.info_teacher));
                String c_place;
                if ((data[1] == null) && (data[15].equalsIgnoreCase("1"))){
                    c_place = data[0];
                } else {
                    c_place = data[0]+", "+data[1];
                }
                SpannableString s1 = new SpannableString(c_place);
                s1.setSpan(new UnderlineSpan(), 0, c_place.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                info1.setText(s1); //в инфо1 аудиторию и событие на клик
                SpannableString s2 = new SpannableString(data[5]);
                s2.setSpan(new UnderlineSpan(), 0, data[5].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                info2.setText(s2); //в инфо2 группу и событие на клик
                if (data[15].equalsIgnoreCase("0")) {
                    info1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(LessonInfoActivity.this, HomeActivity.class);
                            intent.putExtra("id", data[2]);
                            intent.putExtra("type", "auditory");
                            startActivity(intent);
                        }
                    });
                    info2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(LessonInfoActivity.this, HomeActivity.class);
                            intent.putExtra("id", data[6]);
                            intent.putExtra("type", "group");
                            startActivity(intent);
                        }
                    });
                }
            } else {
                if (data[6]==null){ //если это группа
                    String temp = data[5] + " "+ getString(R.string.info_group);
                    getSupportActionBar().setTitle(temp);
                    String c_place;
                    if ((data[1] == null) && (data[15].equalsIgnoreCase("1"))){
                        c_place = data[0];
                    } else {
                        c_place = data[0]+", "+data[1];
                    }
                    SpannableString s1 = new SpannableString(c_place);
                    s1.setSpan(new UnderlineSpan(), 0, c_place.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    info1.setText(s1); //в инфо1 аудиторию и событие на клик
                    SpannableString s2 = new SpannableString(data[3]);
                    s2.setSpan(new UnderlineSpan(), 0, data[3].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    info2.setText(s2); //в инфо2 препода и событие на клик
                    if (data[15].equalsIgnoreCase("0")) {
                        info2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(LessonInfoActivity.this, HomeActivity.class);
                                intent.putExtra("id", data[4]);
                                intent.putExtra("type", "teacher");
                                startActivity(intent);
                            }
                        });
                        info1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(LessonInfoActivity.this, HomeActivity.class);
                                intent.putExtra("id", data[2]);
                                intent.putExtra("type", "auditory");
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        }
    }
}
