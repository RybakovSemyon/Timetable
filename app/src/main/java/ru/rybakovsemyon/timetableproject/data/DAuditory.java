package ru.rybakovsemyon.timetableproject.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Auditories")
public class DAuditory extends Model {

    @Column(name = "DLesson", onDelete = Column.ForeignKeyAction.CASCADE, onUpdate = Column.ForeignKeyAction.CASCADE)
    public DLesson dlesson;

    @Column(name = "AuditoryId")
    public String auditoryID;

    @Column(name = "AuditoryAddress")
    public String auditoryAddress;

    @Column(name = "AuditoryName")
    public String auditoryName;

    public DAuditory(){
        super();
    }

    public DAuditory(String auditoryAddress, String auditoryID, String auditoryName, DLesson dlesson){
        this.auditoryAddress = auditoryAddress;
        this.auditoryID = auditoryID;
        this.auditoryName = auditoryName;
        this.dlesson = dlesson;
    }
}
