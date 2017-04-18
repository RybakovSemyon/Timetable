package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.data.DTimetable;

public class ManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager);
        ActionBar actionBar = this.getSupportActionBar();
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        Button btn_important = (Button) findViewById(R.id.btn_important);
        List<DTimetable> all = new Select().from(DTimetable.class).execute();
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.manager_group);
        RadioButton radioButton;
        if (all.size() != 0){
            radioGroup.setVisibility(View.VISIBLE);
            btn_important.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
            TextView info_manager1 = (TextView) findViewById(R.id.info_manager1);
            info_manager1.setVisibility(View.GONE);
            TextView info_manager2 = (TextView) findViewById(R.id.info_manager2);
            info_manager2.setVisibility(View.VISIBLE);
            for (int i = 0; i < all.size(); i++){
                radioButton = new RadioButton(this);
                String type = null;
                String[] tag = new String[2];
                tag[0] = "0";
                switch (all.get(i).type){
                    case "auditory":
                        type = "(Аудитория)";
                        break;
                    case "group":
                        type = "(Группа)";
                        break;
                    case "teacher":
                        type = "(Преподаватель)";
                        break;
                }
                if (all.get(i).important == 1){
                    tag[0] = "1";
                    String label = "Основное: " + all.get(i).name +" " + type;
                    radioButton.setTextColor(getResources().getColor(R.color.light_blue));
                    if (actionBar != null) {
                        actionBar.setTitle(label);
                    }
                }
                type = all.get(i).name + " " + type;
                radioButton.setText(type);
                tag[1] = all.get(i).getId().toString();
                radioButton.setTag(tag);
                radioGroup.addView(radioButton);
            }
        }
        Button button = (Button) findViewById(R.id.aschedule);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, ChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    System.out.println(checkedRadioButtonId);
                    if (checkedRadioButtonId == -1){
                        Toast toast = Toast.makeText(getApplicationContext(), "Выберите расписание", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        RadioButton myRadioButton = (RadioButton) findViewById(checkedRadioButtonId);
                        System.out.println(myRadioButton.getTag().toString() + " "+ myRadioButton.getText());
                        String[] info = (String[]) myRadioButton.getTag();
                        long _id = Long.parseLong(info[1]);
                        System.out.println(_id);
                        if (info[0] == "1"){
                            new Delete().from(DTimetable.class).where("Id = ?", _id).execute();
                            List<DTimetable> all = new Select().from(DTimetable.class).execute();
                            if (all.size() != 0){
                                new Update(DTimetable.class).set("Important = 1").where("Id = ?", all.get(0).getId()).execute();
                            }
                        } else {
                            new Delete().from(DTimetable.class).where("Id = ?", _id).execute();
                        }
                        Toast toast = Toast.makeText(getApplicationContext(), "Удалено", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(ManagerActivity.this, ManagerActivity.class);
                        startActivity(intent);
                        finish();
                    }


            }
        });
        btn_important.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                System.out.println(checkedRadioButtonId);
                if (checkedRadioButtonId == -1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Выберите расписание", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    RadioButton myRadioButton = (RadioButton) findViewById(checkedRadioButtonId);
                    System.out.println(myRadioButton.getTag().toString() + " "+ myRadioButton.getText());
                    String[] info = (String[]) myRadioButton.getTag();
                    long _id = Long.parseLong(info[1]);
                    System.out.println(_id);
                    if (info[0] == "1"){
                        Toast toast = Toast.makeText(getApplicationContext(), "Оно и так основное", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        List<DTimetable> all = new Select().from(DTimetable.class).execute();
                        if (all.size() !=0){
                            for (int i = 0; i < all.size(); i++){
                                new Update(DTimetable.class).set("Important = 0").where("Id = ?", all.get(i).getId()).execute();
                            }
                            new Update(DTimetable.class).set("Important = 1").where("Id = ?", _id).execute();
                        }
                        Toast toast = Toast.makeText(getApplicationContext(), "Сделано :-)", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(ManagerActivity.this, ManagerActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
