package ru.rybakovsemyon.timetableproject.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Groups")
public class DGroup extends Model {

    @Column(name = "GroupName")
    public String groupName;

    @Column(name = "GroupId")
    public String groupID;

    @Column(name = "GroupType")
    public String groupType;

    @Column(name = "DLesson", onDelete = Column.ForeignKeyAction.CASCADE, onUpdate = Column.ForeignKeyAction.CASCADE)
    public DLesson dlesson;

    public DGroup(){
        super();
    }

    public DGroup(String groupID, String groupName, String groupType, DLesson dlesson){
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupType = groupType;
        this.dlesson = dlesson;
    }
}
