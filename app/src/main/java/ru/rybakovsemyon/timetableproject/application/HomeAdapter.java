package ru.rybakovsemyon.timetableproject.application;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.model.Lesson;


class HomeAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private ArrayList<Lesson> objects = new ArrayList<>();
    private String nameSchedule = null;
    private String type = null;

    HomeAdapter(Context context, ArrayList<Lesson> parametr, String name, String t){
        nameSchedule = name;
        type = t;
        objects = parametr;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_home, parent, false);
        }
        Lesson l = getLesson(position);
        TextView tTeacher = (TextView) view.findViewById(R.id.text_teacher); //
        TextView tGroup = (TextView)view.findViewById(R.id.text_group); //
        TextView tTime = (TextView) view.findViewById(R.id.text_time); //
        TextView tType = (TextView) view.findViewById(R.id.text_type);
        TextView tSubject = (TextView)view.findViewById(R.id.text_lesson); //
        TextView tPlace = (TextView)view.findViewById(R.id.text_place); //
        String[] tags = new String[14];
        switch (type){
            case "auditory":
                tags[0] = nameSchedule;
                tags[1] = null;
                tags[2] = null;
                tags[3] = l.getTeachers().get(0).getTeacherName();
                tags[4] = l.getTeachers().get(0).getTeacherId();
                tags[5] = l.getGroups().get(0).getGroupName();
                tags[6] = l.getGroups().get(0).getGroupId();
                tags[7] = l.getGroups().get(0).getGroupType();
                break;
            case "group":
                tags[5] = nameSchedule;
                tags[6] = null;
                tags[7] = null;
                tags[0] = l.getAuditories().get(0).getAuditoryName();
                tags[1] = l.getAuditories().get(0).getAuditoryAddress();
                tags[2] = l.getAuditories().get(0).getAuditoryId();
                tags[3] = l.getTeachers().get(0).getTeacherName();
                tags[4] = l.getTeachers().get(0).getTeacherId();
                break;
            case "teacher":
                tags[3] = nameSchedule;
                tags[4] = null;
                tags[0] = l.getAuditories().get(0).getAuditoryName();
                tags[1] = l.getAuditories().get(0).getAuditoryAddress();
                tags[2] = l.getAuditories().get(0).getAuditoryId();
                tags[5] = l.getGroups().get(0).getGroupName();
                tags[6] = l.getGroups().get(0).getGroupId();
                tags[7] = l.getGroups().get(0).getGroupType();
                break;
        }
        tags[8] = l.getTimeStart() + "-"+ l.getTimeEnd();
        tags[9] = l.getSubject();
        tags[10] = l.getType();
        tags[11] = l.getDateStartString();
        tags[12] = l.getDateEndString();
        tags[13] = String.valueOf(l.getTemp_id());
        switch (tags[10]){
            case "0": //practic
                tags[10] = "Практика";
                break;
            case "1": //lab?
                tags[10] = "Лаборатория";
                break;
            case "2": //lection
                tags[10] = "Лекция";
                break;
            case "3": //seminar
                tags[10] = "Семинар";
                break;
        }
        tTeacher.setText(tags[3]);
        tTime.setText(tags[8]);
        tGroup.setText(tags[5]);
        tType.setText(tags[10]);
        tPlace.setText(tags[0]);
        tSubject.setText(tags[9]);
        tPlace.setTag(tags);
        return view;
    }

    private Lesson getLesson(int position){
        return ((Lesson) getItem(position));
    }
}
