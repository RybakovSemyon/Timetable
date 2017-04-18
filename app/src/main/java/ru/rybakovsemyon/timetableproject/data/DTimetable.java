package ru.rybakovsemyon.timetableproject.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.util.List;

@Table(name = "Timetables")
public class DTimetable extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Type")
    public String type;

    @Column(name = "Important")
    public int important;

    public DTimetable(){
        super();
    }


    public List<DDay> days(){
        return getMany(DDay.class, "DTimetable");
    }
}

