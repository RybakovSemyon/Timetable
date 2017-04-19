package ru.rybakovsemyon.timetableproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public  DBHelper(Context context){
        super(context, "Timetables", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Timetables ( _id INTEGER PRIMARY KEY, Name TEXT, Type TEXT, Important INTEGER);");
        db.execSQL("CREATE TABLE Days ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Weekday INTEGER, DTimetable INTEGER, FOREIGN KEY (DTimetable) REFERENCES Timetable(_id));");
        db.execSQL("CREATE TABLE Lessons ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Synthetic INTEGER, Subject TEXT, TimeStart TEXT, TimeEnd TEXT, Type TEXT, Parity TEXT, DateStart TEXT, DateEnd TEXT, Dates TEXT, ClusterId TEXT, ClusterName TEXT, ClusterType TEXT, DDay INTEGER, FOREIGN KEY (DDay) REFERENCES Days(_id));");
        db.execSQL("CREATE TABLE Auditories ( _id INTEGER PRIMARY KEY AUTOINCREMENT, AuditoryId TEXT, AuditoryName TEXT, AuditoryAddress TEXT, DLesson INTEGER, FOREIGN KEY (DLesson) REFERENCES Lessons(_id));");
        db.execSQL("CREATE TABLE Teachers ( _id INTEGER PRIMARY KEY AUTOINCREMENT, TeachersId TEXT, TeachersName TEXT, DLesson INTEGER, FOREIGN KEY (DLesson) REFERENCES Lessons(_id));");
        db.execSQL("CREATE TABLE Groups ( _id INTEGER PRIMARY KEY AUTOINCREMENT, GroupName TEXT, GroupId TEXT, GroupType TEXT, DLesson INTEGER, FOREIGN KEY (DLesson) REFERENCES Lessons(_id));");
        db.execSQL("CREATE TABLE Tasks ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Task TEXT, DataTask TEXT, DLesson INTEGER, FOREIGN KEY (DLesson) REFERENCES Lessons(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
