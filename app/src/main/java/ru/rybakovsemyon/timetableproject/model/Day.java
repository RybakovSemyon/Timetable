
package ru.rybakovsemyon.timetableproject.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("weekday")
    @Expose
    private int weekday;
    @SerializedName("lessons")
    @Expose
    private ArrayList<Lesson> lessons = null;

    public int getWeekday() {
        return weekday;
    }
    public void setWeekday(int weekday){
        this.weekday = weekday;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }
    public void setLessons(ArrayList<Lesson> lessons){
        this.lessons = lessons;
    }


}
