
package ru.rybakovsemyon.timetableproject.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesson {

    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("time_start")
    @Expose
    private String timeStart;
    @SerializedName("time_end")
    @Expose
    private String timeEnd;
    @SerializedName("parity")
    @Expose
    private String parity;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("date_end")
    @Expose
    private String dateEnd;
    @SerializedName("dates")
    @Expose
    private Object dates;
    @SerializedName("cluster_id")
    @Expose
    private String clusterId;
    @SerializedName("cluster_name")
    @Expose
    private String clusterName;
    @SerializedName("cluster_type")
    @Expose
    private String clusterType;
    @SerializedName("teachers")
    @Expose
    private ArrayList<Teacher> teachers = null;
    @SerializedName("auditories")
    @Expose
    private ArrayList<Auditory> auditories = null;
    @SerializedName("groups")
    @Expose
    private ArrayList<Group> groups = null;

    private long dday = -1;

    private long temp_id = -1;

    private int synthetic = 0;

    public int getSyntetic() { return synthetic; }
    public void setSynthetic(int synthetic) { this.synthetic = synthetic;}

    public long getDday() { return dday; }
    public void setDday(long dday) { this.dday = dday;}
    public long getTemp_id(){
        return temp_id;
    }

    public void setTemp_id(long temp_id){
        this.temp_id = temp_id;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getType() {
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(String timeEnd){
        this.timeEnd = timeEnd;
    }

    public String getParity() {
        return parity;
    }
    public void setParity(String parity){
        this.parity = parity;
    }

    public Calendar getDateStart() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar date = GregorianCalendar.getInstance();
        try {
            date.setTime(formatter.parse(dateStart));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getDateStartString(){
        return dateStart;
    }
    public void setDateStart(String dateStart){
        this.dateStart = dateStart;
    }

    public String getDateEndString(){
        return dateEnd;
    }
    public void setDateEnd(String dateEnd){
        this.dateEnd = dateEnd;
    }

    public Calendar getDateEnd() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar date = GregorianCalendar.getInstance();
        try {
            date.setTime(formatter.parse(dateEnd));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Object getDates() {
        String answer = "temp";
        dates = answer;
        return dates;
    }
    public void setDates(String dates){
        this.dates = dates;
    }

    public String getClusterId() {
        return clusterId;
    }
    public void setClusterId(String clusterId){
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }
    public void setClusterName(String clusterName){
        this.clusterName = clusterName;
    }

    public String getClusterType() {
        return clusterType;
    }
    public void setClusterType(String clusterType){
        this.clusterType = clusterType;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }
    public void setTeachers(ArrayList<Teacher> teachers){
        this.teachers = teachers;
    }

    public ArrayList<Auditory> getAuditories() {
        return auditories;
    }
    public void setAuditories(ArrayList<Auditory> auditories){
        this.auditories = auditories;
    }

    public ArrayList<Group> getGroups(){
        return groups;
    }
    public void setGroups(ArrayList<Group> groups){
        this.groups = groups;
    }
}
