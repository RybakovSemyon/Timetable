package ru.rybakovsemyon.timetableproject.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.util.List;

@Table(name = "Days")
public class DDay extends Model {
    @Column(name = "Weekday")
    public int weekday;

    @Column(name = "DTimetable", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public DTimetable dtimetable;

    public DDay(){
        super();
    }

    public DDay(int weekday, DTimetable dtimetable){
        super();
        this.weekday = weekday;
        this.dtimetable = dtimetable;
    }

    public List<DLesson> lessons(){
        return getMany(DLesson.class, "DDay");
    }
}
