
package ru.rybakovsemyon.timetableproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Faculty {

    @SerializedName("faculty_name")
    @Expose
    private String facultyName;
    @SerializedName("faculty_id")
    @Expose
    private String facultyId;

    public String getFacultyName() {
        return facultyName;
    }

    public String getFacultyId() {
        return facultyId;
    }
}
