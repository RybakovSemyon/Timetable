package ru.rybakovsemyon.timetableproject.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Teachers")
public class DTeacher extends Model {

    @Column(name = "DLesson", onDelete = Column.ForeignKeyAction.CASCADE, onUpdate = Column.ForeignKeyAction.CASCADE)
    public DLesson dlesson;

    @Column(name = "TeacherName")
    public String teacherName;

    @Column(name = "TeacherId")
    public String teacherID;

    public DTeacher(){
        super();
    }

    public DTeacher(DLesson dlesson, String teacherID, String teacherName){
        this.dlesson = dlesson;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
    }

}
