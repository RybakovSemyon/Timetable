
package ru.rybakovsemyon.timetableproject.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lessons {

    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("auditory_name")
    @Expose
    private String auditoryName;
    @SerializedName("days")
    @Expose
    private ArrayList<Day> days = null;

    public String getName(String request){
        switch (request){
            case "auditory":
                return auditoryName;
            case "teacher":
                return teacherName;
            case "group":
                return groupName;
            default:
                return "error_getNameLessons";
        }
    }

    public ArrayList<Day> getDays() {
        return days;
    }


}
