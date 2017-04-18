
package ru.rybakovsemyon.timetableproject.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Faculties {

    @SerializedName("faculties")
    @Expose
    private ArrayList<Faculty> faculties = null;

    public ArrayList<Faculty> getFaculties() {
        return faculties;
    }

}
