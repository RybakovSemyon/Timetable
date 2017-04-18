
package ru.rybakovsemyon.timetableproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("group_type")
    @Expose
    private String groupType;

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId){
        this.groupId = groupId;
    }

    public String getGroupType() {
        return groupType;
    }
    public void setGroupType(String groupType){
        this.groupType = groupType;
    }
}
