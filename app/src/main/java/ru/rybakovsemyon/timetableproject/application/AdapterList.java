package ru.rybakovsemyon.timetableproject.application;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.model.Auditory;
import ru.rybakovsemyon.timetableproject.model.Building;
import ru.rybakovsemyon.timetableproject.model.Faculty;
import ru.rybakovsemyon.timetableproject.model.Group;

class AdapterList extends BaseAdapter {
    private LayoutInflater lInflater;
    private ArrayList<Building> objectsB;
    private ArrayList<Faculty> objectsF;
    private ArrayList<Group> objectsG;
    private ArrayList<Auditory> objectsA;
    private ArrayList<Object> objects;
    private int CASE;
    // 1 - buildings, 2 - faculties, 3 - groups, 4 - auditory

    AdapterList(Context context, ArrayList<Object> obj, int c){
        objects = obj;
        CASE = c;
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
            view = lInflater.inflate(R.layout.item_list, parent, false);
        }
        TextView t = (TextView)view.findViewById(R.id.text_name);
        Object o = getItem(position);
        switch (CASE){
            case 1:
                Building b = (Building) o;
                t.setText(b.getBuildingName());
                t.setTag(b.getBuildingId());
                break;
            case 2:
                Faculty f = getFaculty(position);
                t.setText(f.getFacultyName());
                t.setTag(f.getFacultyId());
                break;
            case 3:
                Group g = getGroup(position);
                String nameOfItem = g.getGroupType() + " " + g.getGroupName();
                t.setText(nameOfItem);
                t.setTag(g.getGroupId());
                break;
            case 4:
                Auditory a = getAuditory(position);
                t.setText(a.getAuditoryName());
                t.setTag(a.getAuditoryId());
                break;
        }
        return view;
    }

    private Group getGroup(int position) {
        return ((Group) getItem(position));
    }
    private Building getBuilding(int position) {
        return ((Building) getItem(position));
    }
    private Faculty getFaculty(int position) {
        return ((Faculty) getItem(position));
    }
    private Auditory getAuditory(int position) { return ((Auditory) getItem(position));}

}
