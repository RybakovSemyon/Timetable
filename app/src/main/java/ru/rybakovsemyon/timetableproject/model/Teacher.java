
package ru.rybakovsemyon.timetableproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teacher {

    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;


    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName){
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(String teacherId){
        this.teacherId = teacherId;
    }
}
