
package ru.rybakovsemyon.timetableproject.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auditories {

    @SerializedName("auditories")
    @Expose
    private ArrayList<Auditory> auditories = null;

    public ArrayList<Auditory> getAuditories() {
        return auditories;
    }


}
