
package ru.rybakovsemyon.timetableproject.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teachers {

    @SerializedName("teachers")
    @Expose
    private ArrayList<Teacher> teachers = null;

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

}
