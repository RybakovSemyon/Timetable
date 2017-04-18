
package ru.rybakovsemyon.timetableproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auditory {

    @SerializedName("auditory_name")
    @Expose
    private String auditoryName;
    @SerializedName("auditory_address")
    @Expose
    private String auditoryAddress;
    @SerializedName("auditory_id")
    @Expose
    private String auditoryId;


    public String getAuditoryName() {
        return auditoryName;
    }
    public void setAuditoryName(String auditoryName){
        this.auditoryName = auditoryName;
    }

    public String getAuditoryAddress() {
        return auditoryAddress;
    }
    public void setAuditoryAddress(String auditoryAddress){
        this.auditoryAddress = auditoryAddress;
    }

    public String getAuditoryId() {
        return auditoryId;
    }
    public void setAuditoryId(String auditoryId){
        this.auditoryId = auditoryId;
    }


}
