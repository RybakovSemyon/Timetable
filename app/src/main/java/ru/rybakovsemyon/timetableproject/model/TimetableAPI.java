package ru.rybakovsemyon.timetableproject.model;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TimetableAPI {
    @GET("m2/get_faculties")
    Call<Faculties> getFaculty();

    @GET("/m2/get_teachers")
    Call<Teachers> getTeachers();

    @GET("/m2/get_groups")
    Call<Groups> getGroups(@Query("faculty_id") String resourceName);

    @GET("/m2/get_auditories")
    Call<Auditories> getAuditories(@Query("building_id") String resourceName);

    @GET("/m2/get_schedule")
    Call<Lessons> getLessonsGroup(@Query("group_id") String resourceName);

    @GET("/m2/get_tschedule")
    Call<Lessons> getLessonsTeacher(@Query("teacher_id") String resourceName);

    @GET("/m2/get_aschedule")
    Call<Lessons> getLessonsAuditory(@Query("auditory_id") String resourceName);
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://timetable.tsu.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
