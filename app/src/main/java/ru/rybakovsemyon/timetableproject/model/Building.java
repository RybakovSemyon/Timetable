package ru.rybakovsemyon.timetableproject.model;


public class Building {
    private String bdg_name;
    private String bdg_id;

    Building(String b_name, String b_id){
        bdg_name = b_name;
        bdg_id = b_id;
    }

    public String getBuildingName() {
        return bdg_name;
    }

    public String getBuildingId() {
        return bdg_id;
    }


}
