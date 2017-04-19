package ru.rybakovsemyon.timetableproject.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.util.List;

@Table(name = "Lessons")
public class DLesson extends Model {
    @Column(name = "Subject")
    public String subject;

    @Column(name = "Synthetic")
    public int synthetic;

    @Column(name = "Type")
    public String type;

    @Column(name = "TimeStart")
    public String timeStart;

    @Column(name = "TimeEnd")
    public String timeEnd;

    @Column(name = "Parity")
    public String parity;

    @Column(name = "DateStart")
    public String dateStart;

    @Column(name = "DateEnd")
    public String dateEnd;

    @Column(name = "Dates")
    public String dates;

    @Column(name = "ClusterId")
    public String clusterId;

    @Column(name = "ClusterName")
    public String clusterName;

    @Column(name = "ClusterType")
    public String clusterType;

    @Column(name = "DDay", onDelete = Column.ForeignKeyAction.CASCADE, onUpdate = Column.ForeignKeyAction.CASCADE)
    public DDay dday;

    public DLesson(){
        super();
    }

    public DLesson(String subject, String type, String dateEnd, String dateStart, String timeEnd, String timeStart, String parity,
                   String clusterId, String clusterName, String clusterType, String dates, DDay dday){
        this.subject = subject;
        this.type = type;
        this.dateEnd = dateEnd;
        this.dateStart = dateStart;
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
        this.parity = parity;
        this.clusterId = clusterId;
        this.clusterName = clusterName;
        this.clusterType = clusterType;
        this.dates = dates;
        this.dday = dday;
    }

    public List<DTeacher> dteachers(){
        return getMany(DTeacher.class, "DLesson");
    }

    public List<DAuditory> dauditories(){
        return getMany(DAuditory.class, "DLesson");
    }

    public List<DGroup> dgroups(){
        return getMany(DGroup.class, "DLesson");
    }

    public List<DTask> dtasks() { return getMany(DTask.class, "DLesson"); }
}
