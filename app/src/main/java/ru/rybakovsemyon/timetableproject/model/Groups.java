
package ru.rybakovsemyon.timetableproject.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Groups {

    @SerializedName("groups")
    @Expose
    private ArrayList<Group> groups = null;

    public ArrayList<Group> getGroups() {
        return groups;
    }
}
