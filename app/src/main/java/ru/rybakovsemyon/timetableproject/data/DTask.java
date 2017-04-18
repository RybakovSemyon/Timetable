package ru.rybakovsemyon.timetableproject.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tasks")
public class DTask extends Model {

    @Column(name = "Task")
    public String task;

    @Column(name = "DLesson", onDelete = Column.ForeignKeyAction.CASCADE, onUpdate = Column.ForeignKeyAction.CASCADE)
    public DLesson dlesson;

    @Column(name = "DataTask")
    public String dataTask;

    public DTask(){
        super();
    }
}
