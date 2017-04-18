package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.rybakovsemyon.timetableproject.R;
import ru.rybakovsemyon.timetableproject.data.DAuditory;
import ru.rybakovsemyon.timetableproject.data.DDay;
import ru.rybakovsemyon.timetableproject.data.DGroup;
import ru.rybakovsemyon.timetableproject.data.DLesson;
import ru.rybakovsemyon.timetableproject.data.DTeacher;
import ru.rybakovsemyon.timetableproject.data.DTimetable;
import ru.rybakovsemyon.timetableproject.model.Auditory;
import ru.rybakovsemyon.timetableproject.model.Day;
import ru.rybakovsemyon.timetableproject.model.Group;
import ru.rybakovsemyon.timetableproject.model.Lesson;
import ru.rybakovsemyon.timetableproject.model.Lessons;
import ru.rybakovsemyon.timetableproject.model.Teacher;
import ru.rybakovsemyon.timetableproject.model.TimetableAPI;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static String nameSchedule = "Расписание ТГУ";
    static String type = null;
    static Calendar maxDate = GregorianCalendar.getInstance();
    static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    static Calendar minDate = GregorianCalendar.getInstance();
    Calendar positionDate = GregorianCalendar.getInstance();
    int WORK;
    static boolean TEST;
    static ArrayList<Day> dayList;
    Lessons ans = null;
    int days = 0;
    int begin_position;
    static int DAY_OF_WEEK;
    long db_id;
    ArrayList<Integer> itemsID = new ArrayList<>();
    ArrayList<String> itemsTYPE = new ArrayList<>();
    ArrayList<String> itemsNAME = new ArrayList<>();
    static final private int CHOOSE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        TEST = true;
        setSupportActionBar(toolbar);
        Intent putIntent = getIntent();
        type = putIntent.getStringExtra("type");
        String req_id = putIntent.getStringExtra("id");
        if (req_id == null){
            String temp = putIntent.getStringExtra("db_id");
            nameSchedule = putIntent.getStringExtra("name");
            if (temp == null){
                List<DTimetable> all = new Select().from(DTimetable.class).execute();
                if (all.size() == 0){
                    Intent intent = new Intent(this, ChooseActivity.class);
                    startActivity(intent);
                    finish();
                }
                int i = 0;
                while (i < all.size()){
                    if(all.get(i).important == 1){
                        db_id = all.get(i).getId();
                        type = all.get(i).type;
                        nameSchedule = all.get(i).name;
                        break;
                    }
                    i++;
                }
            } else {
                db_id = Long.parseLong(temp);
            }
            WORK = 2;
            new ProgressTask().execute();
        } else {

            TimetableAPI timetableAPI = TimetableAPI.retrofit.create(TimetableAPI.class);
            Call<Lessons> call = timetableAPI.getLessonsGroup(req_id);
            switch (type) {
                case "auditory":
                    call = timetableAPI.getLessonsAuditory(req_id);
                    break;
                case "teacher":
                    call = timetableAPI.getLessonsTeacher(req_id);
                    break;
                case "group":
                    call = timetableAPI.getLessonsGroup(req_id);
                    break;
                default:
                    Toast toast = Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
            }
            call.enqueue(new Callback<Lessons>() {
                @Override
                public void onResponse(Call<Lessons> call, Response<Lessons> response) {
                    if (response.isSuccessful()) {
                        ans = response.body();
                        dayList = ans.getDays();
                        nameSchedule = ans.getName(type);
                        if (dayList.size() != 0) {
                            new ProgressTask().execute();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Расписания пока нет", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Lessons> call, Throwable t) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Не удалось получить данные", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            });
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        SubMenu sub = menu.addSubMenu(0, 1234, Menu.NONE, getText(R.string.nav_tittle));
        List<DTimetable> all = new Select().from(DTimetable.class).execute();
        if (all.size() != 0) {
            for (int i = 0; i < all.size(); i++) {
                long temp_id = all.get(i).getId();
                int _id = (int) temp_id;
                itemsID.add(_id);
                itemsTYPE.add(all.get(i).type);
                itemsNAME.add(all.get(i).name);
                switch (all.get(i).type){
                    case "auditory":
                        sub.add(0, _id, Menu.NONE, all.get(i).name).setIcon(R.drawable.home_48);
                        break;
                    case "group":
                        sub.add(0, _id, Menu.NONE, all.get(i).name).setIcon(R.drawable.group_48);
                        break;
                    case "teacher":
                        sub.add(0, _id, Menu.NONE, all.get(i).name).setIcon(R.drawable.guru_48);
                        break;
                }

            }
        }
//        for (int i = 0; i < itemsID.size(); i++){
//            MenuItem menuItem = (MenuItem) findViewById(itemsID.get(i));
//            final String temp_type = itemsTYPE.get(i);
//            final String temp_id = Integer.toString(itemsID.get(i));
//            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
//                    intent.putExtra("type", temp_type);
//                    intent.putExtra("db_id", temp_id);
//                    startActivity(intent);
//                    return true;
//                }
//            });
//        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_choose) {
            Intent intent = new Intent(this, ChooseActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_schedule) {
            List<DTimetable> all = new Select().from(DTimetable.class).execute();
            if (all.size() == 0){
                startActivity(new Intent(getApplicationContext(), ChooseActivity.class));
                finish();
            } else {
                int i = 0;
                while (i < all.size()){
                    if(all.get(i).important == 1){
                        db_id = all.get(i).getId();
                        type = all.get(i).type;
                        nameSchedule = all.get(i).name;
                        break;
                    }
                    i++;
                }
                DrawingTimetable(1);
            }
        } else if (id == R.id.nav_schedules){
            Intent intent = new Intent(this, ManagerActivity.class);
            startActivity(intent);
            finish();
        }
        for (int i = 0; i < itemsID.size(); i++){
            if (id == itemsID.get(i)){
                db_id = itemsID.get(i);
                nameSchedule = itemsNAME.get(i);
                type = itemsTYPE.get(i);
                DrawingTimetable(1);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //-----------! Fragment
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            int position = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
            Day day = new Day();
            boolean t = false;
            String s = null;
            int i = 0;
            while (i < dayList.size()){
                Day temp_day = dayList.get(i);
                int day_of_week = temp_day.getWeekday();
                s = getDayNow(position);
                int day_of_week_now = DayOfWeek(s);
                if (day_of_week_now == day_of_week){
                    day = temp_day;
                    t = true;
                    break;
                }
                i++;
            }
            if (t){
                ArrayList<Lesson> all_lessons = day.getLessons();
                ArrayList<Lesson> concrete_lessons = new ArrayList<>();
                try {
                    for (i = 0; i < all_lessons.size(); i++){
                        Lesson lesson = all_lessons.get(i);
                        Calendar date_start = lesson.getDateStart();
                        Calendar date_end = lesson.getDateEnd();
                        Calendar compare = GregorianCalendar.getInstance();
                        compare.setTime(formatter.parse(s));
                        if (compare.after(date_start) && compare.before(date_end)){
                            concrete_lessons.add(lesson);
                            //получил неотсортированные занятия в определенный день
                            //занятия могут пропасть, поэтому ниже будет проверка на пустоту
                        }
                    }
                } catch (ParseException e){
                    e.printStackTrace();
                }
                if (concrete_lessons.size() != 0) {
                    //теперь отсортируем, а потом передадим адаптеру
                    Collections.sort(concrete_lessons, new Comparator<Lesson>() {
                        @Override
                        public int compare(Lesson o1, Lesson o2) {
                            return o1.getTimeStart().compareTo(o2.getTimeStart());
                        }
                    });
                    TextView textView = (TextView) rootView.findViewById(R.id.weekend);
                    textView.setVisibility(View.GONE);
                    HomeAdapter timetableListAdapter = new HomeAdapter(rootView.getContext(),
                            concrete_lessons, nameSchedule, type);
                    ListView listView = (ListView) rootView.findViewById(R.id.listviewTIMETABLE);
                    listView.setAdapter(timetableListAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView tPlace = (TextView)view.findViewById(R.id.text_place); //
                            //вытащим данные из элемента, которые засунули в тэг одного из textView
                            String[] info = (String[]) tPlace.getTag(); //length info = 13
                            Intent intent = new Intent(getActivity(), LessonInfoActivity.class);
                            intent.putExtra("data",info);
                            intent.putExtra("day_of_week", DayOfWeekINString(1, DAY_OF_WEEK-1));
                            startActivity(intent);
                        }
                    });
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView tPlace = (TextView)view.findViewById(R.id.text_place); //
                            //вытащим данные из элемента, которые засунули в тэг одного из textView
                            String[] info = (String[]) tPlace.getTag();
                            Intent intent = new Intent(getActivity(), DeleteActivity.class);
                            intent.putExtra("_id", info[13]);
                            startActivityForResult(intent, CHOOSE);
                            return true;
                        }
                    });
                }
            }
            Button add_element = (Button) rootView.findViewById(R.id.add_element);
            if (!TEST){

                add_element.setVisibility(View.VISIBLE);
                add_element.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CreateLessonActivity.class);
                        String wd = Integer.toString(DAY_OF_WEEK-1);
                        intent.putExtra("type", type);
                        intent.putExtra("weekday", wd);
                        Date mind = minDate.getTime();
                        String minds = formatter.format(mind);
                        Date maxd = maxDate.getTime();
                        String maxds = formatter.format(maxd);
                        intent.putExtra("name", nameSchedule);
                        intent.putExtra("min_day", minds);
                        intent.putExtra("max_day", maxds);
                        startActivity(intent);
                    }
                });
            } else {
                add_element.setVisibility(View.GONE);
            }
            return rootView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            String answer = data.getStringExtra("db_id");
            long _id = Long.parseLong(answer);
            new Delete().from(DLesson.class).where("Id = ?", _id).execute();
            DrawingTimetable(1);
        }

    }

    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return days;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String s = getDayNow(position);
            String s1;
            DAY_OF_WEEK = DayOfWeek(s);
            s1 = DayOfWeekINString(0, DAY_OF_WEEK);
            return s1 +" - " + s;
        }
    }

    private class ProgressTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            switch (WORK) {
                case 0: //getDays
                    String TEST_MIN_DATE = "01.01.2050";
                    String TEST_MAX_DATE = "01.01.2000";
                    try {
                        minDate.setTime(formatter.parse(TEST_MIN_DATE));
                        maxDate.setTime(formatter.parse(TEST_MAX_DATE));
                        for (int i = 0; i < dayList.size(); i++) {
                            Day day = dayList.get(i);
                            ArrayList<Lesson> lessonList = day.getLessons();
                            for (int j = 0; j < lessonList.size(); j++) {
                                Calendar startDate = lessonList.get(j).getDateStart();
                                Calendar endDate = lessonList.get(j).getDateEnd();
                                if (startDate.before(minDate)) {
                                    positionDate = startDate;
                                    minDate = startDate;
                                }
                                if (endDate.after(maxDate)) {
                                    maxDate = endDate;
                                }
                            }
                        }
                        Date a = maxDate.getTime();
                        Date b = minDate.getTime();
                        long difference = a.getTime() - b.getTime();
                        days = (int) (difference / (24 * 60 * 60 * 1000));
                        Date now = new Date();
                        Date then = minDate.getTime();
                        long razn = now.getTime() - then.getTime();
                        begin_position = (int)(razn/(24*60*60*1000));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1: // Model to DBmodel
                    ActiveAndroid.beginTransaction();
                    try {
                        DTimetable timetable = new DTimetable();
                        timetable.name = nameSchedule;
                        timetable.type = type;
                        List<DTimetable> temp_all = new Select().from(DTimetable.class).execute();
                        boolean test = true;
                        if (temp_all.size() != 0){
                            for (int i = 0; i < temp_all.size(); i++){
                                if (temp_all.get(i).important == 1){
                                    test = false;
                                }
                            }
                        }
                        if (test){
                            timetable.important = 1;
                        } else {
                            timetable.important = 0;
                        }
                        timetable.save();
                        for (int i = 0; i < dayList.size(); i++) {
                            Day day = dayList.get(i);
                            DDay dday = new DDay();
                            dday.dtimetable = timetable;
                            dday.weekday = day.getWeekday();
                            dday.save();
                            ArrayList<Lesson> lessons = day.getLessons();
                            for (int j = 0; j < lessons.size(); j++) {
                                Lesson lesson = lessons.get(j);
                                DLesson dLesson = new DLesson();
                                dLesson.subject = lesson.getSubject();
                                dLesson.dday = dday;
                                dLesson.dates = lesson.getDates().toString();
                                dLesson.dateStart = lesson.getDateStartString();
                                dLesson.dateEnd = lesson.getDateEndString();
                                dLesson.timeStart = lesson.getTimeStart();
                                dLesson.timeEnd = lesson.getTimeEnd();
                                dLesson.parity = lesson.getParity();
                                dLesson.type = lesson.getType();
                                dLesson.clusterName = lesson.getClusterName();
                                dLesson.clusterType = lesson.getClusterType();
                                dLesson.clusterId = lesson.getClusterId();
                                dLesson.save();
                                switch (type) {
                                    case "teacher":
                                        ArrayList<Group> tgroups = lesson.getGroups();
                                        for (int k = 0; k < tgroups.size(); k++) {
                                            Group group = tgroups.get(k);
                                            DGroup dGroup = new DGroup();
                                            dGroup.dlesson = dLesson;
                                            dGroup.groupID = group.getGroupId();
                                            dGroup.groupType = group.getGroupType();
                                            dGroup.groupName = group.getGroupName();
                                            dGroup.save();
                                        }
                                        ArrayList<Auditory> tauditories = lesson.getAuditories();
                                        for (int k = 0; k < tauditories.size(); k++) {
                                            Auditory auditory = tauditories.get(k);
                                            DAuditory dAuditory = new DAuditory();
                                            dAuditory.auditoryName = auditory.getAuditoryName();
                                            dAuditory.auditoryID = auditory.getAuditoryId();
                                            dAuditory.auditoryAddress = auditory.getAuditoryAddress();
                                            dAuditory.dlesson = dLesson;
                                            dAuditory.save();
                                        }

                                        break;
                                    case "auditory":
                                        ArrayList<Group> agroups = lesson.getGroups();
                                        for (int k = 0; k < agroups.size(); k++) {
                                            Group group = agroups.get(k);
                                            DGroup dGroup = new DGroup();
                                            dGroup.dlesson = dLesson;
                                            dGroup.groupID = group.getGroupId();
                                            dGroup.groupType = group.getGroupType();
                                            dGroup.groupName = group.getGroupName();
                                            dGroup.save();
                                        }
                                        ArrayList<Teacher> ateachers = lesson.getTeachers();
                                        for (int k = 0; k < ateachers.size(); k++) {
                                            Teacher teacher = ateachers.get(k);
                                            DTeacher dTeacher = new DTeacher();
                                            dTeacher.teacherName = teacher.getTeacherName();
                                            dTeacher.teacherID = teacher.getTeacherId();
                                            dTeacher.dlesson = dLesson;
                                            dTeacher.save();
                                        }
                                        break;
                                    case "group":
                                        ArrayList<Auditory> gauditories = lesson.getAuditories();
                                        for (int k = 0; k < gauditories.size(); k++) {
                                            Auditory auditory = gauditories.get(k);
                                            DAuditory dAuditory = new DAuditory();
                                            dAuditory.auditoryName = auditory.getAuditoryName();
                                            dAuditory.auditoryID = auditory.getAuditoryId();
                                            dAuditory.auditoryAddress = auditory.getAuditoryAddress();
                                            dAuditory.dlesson = dLesson;
                                            dAuditory.save();
                                        }

                                        ArrayList<Teacher> gteachers = lesson.getTeachers();
                                        for (int k = 0; k < gteachers.size(); k++) {
                                            Teacher teacher = gteachers.get(k);
                                            DTeacher dTeacher = new DTeacher();
                                            dTeacher.teacherName = teacher.getTeacherName();
                                            dTeacher.teacherID = teacher.getTeacherId();
                                            dTeacher.dlesson = dLesson;
                                            dTeacher.save();
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        ActiveAndroid.setTransactionSuccessful();
                    } finally {
                        ActiveAndroid.endTransaction();
                    }
                    break;
                case 2: //DBmodel to Model
                    List<DDay> dDays = new Select().from(DDay.class).where("DTimetable = ?", db_id).execute();
                    ArrayList<Day> temp = new ArrayList<>();
                    for (int i = 0; i < dDays.size(); i++){
                        Day day = new Day();
                        day.setWeekday(dDays.get(i).weekday);
                        ArrayList<Lesson> lessons = new ArrayList<>();
                        List<DLesson> dLessons = new Select().from(DLesson.class).where("DDay = ?", dDays.get(i).getId()).execute();
                        for (int j = 0; j < dLessons.size(); j++){
                            switch (type){
                                case "group":
                                    Lesson glesson = new Lesson();
                                    glesson.setClusterId(dLessons.get(j).clusterId);
                                    glesson.setClusterName(dLessons.get(j).clusterName);
                                    glesson.setClusterType(dLessons.get(j).clusterType);
                                    glesson.setDateEnd(dLessons.get(j).dateEnd);
                                    glesson.setDateStart(dLessons.get(j).dateStart);
                                    glesson.setDates(dLessons.get(j).dates);
                                    glesson.setParity(dLessons.get(j).parity);
                                    glesson.setSubject(dLessons.get(j).subject);
                                    glesson.setTimeEnd(dLessons.get(j).timeEnd);
                                    glesson.setTimeStart(dLessons.get(j).timeStart);
                                    glesson.setType(dLessons.get(j).type);
                                    glesson.setTemp_id(dLessons.get(j).getId());
                                    ArrayList<Teacher> gteachers = new ArrayList<>();
                                    List<DTeacher> dTeachers = new Select().from(DTeacher.class).where("DLesson = ?", dLessons.get(j).getId()).execute();
                                    for (int k = 0; k < dTeachers.size(); k++){
                                        Teacher gteacher = new Teacher();
                                        gteacher.setTeacherId(dTeachers.get(k).teacherID);
                                        gteacher.setTeacherName(dTeachers.get(k).teacherName);
                                        gteachers.add(gteacher);
                                    }
                                    glesson.setTeachers(gteachers);
                                    ArrayList<Auditory> gauditories = new ArrayList<>();
                                    List<DAuditory> dAuditories = new Select().from(DAuditory.class).where("DLesson = ?", dLessons.get(j).getId()).execute();
                                    for (int n = 0; n < dAuditories.size(); n++){
                                        Auditory gauditory = new Auditory();
                                        gauditory.setAuditoryAddress(dAuditories.get(n).auditoryAddress);
                                        gauditory.setAuditoryId(dAuditories.get(n).auditoryID);
                                        gauditory.setAuditoryName(dAuditories.get(n).auditoryName);
                                        gauditories.add(gauditory);
                                    }
                                    glesson.setAuditories(gauditories);
                                    lessons.add(glesson);
                                    break;
                                case "auditory":
                                    Lesson alesson = new Lesson();
                                    alesson.setDateEnd(dLessons.get(j).dateEnd);
                                    alesson.setDateStart(dLessons.get(j).dateStart);
                                    alesson.setDates(dLessons.get(j).dates);
                                    alesson.setParity(dLessons.get(j).parity);
                                    alesson.setSubject(dLessons.get(j).subject);
                                    alesson.setTimeEnd(dLessons.get(j).timeEnd);
                                    alesson.setTimeStart(dLessons.get(j).timeStart);
                                    alesson.setType(dLessons.get(j).type);
                                    alesson.setTemp_id(dLessons.get(j).getId());
                                    ArrayList<Teacher> ateachers = new ArrayList<>();
                                    List<DTeacher> aTeachers = new Select().from(DTeacher.class).where("DLesson = ?", dLessons.get(j).getId()).execute();
                                    for (int k = 0; k < aTeachers.size(); k++){
                                        Teacher ateacher = new Teacher();
                                        ateacher.setTeacherId(aTeachers.get(k).teacherID);
                                        ateacher.setTeacherName(aTeachers.get(k).teacherName);
                                        ateachers.add(ateacher);
                                    }
                                    alesson.setTeachers(ateachers);
                                    ArrayList<Group> agroups = new ArrayList<>();
                                    List<DGroup> dGroups = new Select().from(DGroup.class).where("DLesson = ?", dLessons.get(j).getId()).execute();
                                    for (int n = 0; n < dGroups.size(); n++){
                                        Group group = new Group();
                                        group.setGroupId(dGroups.get(n).groupID);
                                        group.setGroupName(dGroups.get(n).groupName);
                                        group.setGroupType(dGroups.get(n).groupType);
                                        agroups.add(group);
                                    }
                                    alesson.setGroups(agroups);
                                    lessons.add(alesson);
                                    break;
                                case "teacher":
                                    Lesson tlesson = new Lesson();
                                    tlesson.setDateEnd(dLessons.get(j).dateEnd);
                                    tlesson.setDateStart(dLessons.get(j).dateStart);
                                    tlesson.setDates(dLessons.get(j).dates);
                                    tlesson.setParity(dLessons.get(j).parity);
                                    tlesson.setSubject(dLessons.get(j).subject);
                                    tlesson.setTimeEnd(dLessons.get(j).timeEnd);
                                    tlesson.setTimeStart(dLessons.get(j).timeStart);
                                    tlesson.setType(dLessons.get(j).type);
                                    tlesson.setTemp_id(dLessons.get(j).getId());
                                    ArrayList<Group> tgroups = new ArrayList<>();
                                    List<DGroup> dtGroups = new Select().from(DGroup.class).where("DLesson = ?", dLessons.get(j).getId()).execute();
                                    for (int n = 0; n < dtGroups.size(); n++){
                                        Group group = new Group();
                                        group.setGroupId(dtGroups.get(n).groupID);
                                        group.setGroupName(dtGroups.get(n).groupName);
                                        group.setGroupType(dtGroups.get(n).groupType);
                                        tgroups.add(group);
                                    }
                                    tlesson.setGroups(tgroups);
                                    ArrayList<Auditory> tauditories = new ArrayList<>();
                                    List<DAuditory> dtAuditories = new Select().from(DAuditory.class).where("DLesson = ?", dLessons.get(j).getId()).execute();
                                    for (int n = 0; n < dtAuditories.size(); n++){
                                        Auditory gauditory = new Auditory();
                                        gauditory.setAuditoryAddress(dtAuditories.get(n).auditoryAddress);
                                        gauditory.setAuditoryId(dtAuditories.get(n).auditoryID);
                                        gauditory.setAuditoryName(dtAuditories.get(n).auditoryName);
                                        tauditories.add(gauditory);
                                    }
                                    tlesson.setAuditories(tauditories);
                                    lessons.add(tlesson);
                                    break;
                                default:
                                    break;
                            }
                        }
                        day.setLessons(lessons);
                        temp.add(day);
                    }
                    dayList = temp;
                    break;


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            switch (WORK){
                case 0:
                    DrawingTimetable(0);
                    break;
                case 1:
                    Toast toast = Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case 2:
                    TEST = false;
                    ToDBstart();
                    break;
            }

        }
    }

    private void ToDBstart(){
        WORK = 0;
        new ProgressTask().execute();
    }

    private void DrawingTimetable (int check){
        ProgressBar pbar = (ProgressBar) findViewById(R.id.pb_home);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.c_home);
        PagerTabStrip ptab = (PagerTabStrip) findViewById(R.id.pts_home);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (check == 1){
            fab.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            pbar.setVisibility(View.GONE);
            pbar.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(R.string.app_name);
            WORK = 2;
            new ProgressTask().execute();
            return;
        }
        mViewPager.setVisibility(View.VISIBLE);
        ptab.setVisibility(View.VISIBLE);
//        TextView name = (TextView) findViewById(R.id.name_schedule);
//        name.setText(nameSchedule);
        pbar.setVisibility(View.GONE);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        getSupportActionBar().setTitle(nameSchedule);

//        mViewPager.post(new Runnable() {
//            @Override
//            public void run() {
//                mViewPager.setCurrentItem(begin_position);
//            }
//        });
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(begin_position);
        if (TEST) {

            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WORK = 1;
                    new ProgressTask().execute();
                }
            });
        }
    }

    private static String DayOfWeekINString(int request, int i){
        String answer = null;
        switch (request){
            case 0:
                switch (i){
                    case 1:
                        answer = "пн";
                        break;
                    case 2:
                        answer = "вт";
                        break;
                    case 3:
                        answer = "ср";
                        break;
                    case 4:
                        answer = "чт";
                        break;
                    case 5:
                        answer = "пт";
                        break;
                    case 6:
                        answer = "сб";
                        break;
                    case 7:
                        answer = "вс";
                        break;
                }
                break;
            case 1:
                switch (i){
                    case 1:
                        answer = "Понедельник";
                        break;
                    case 2:
                        answer = "Вторник";
                        break;
                    case 3:
                        answer = "Среда";
                        break;
                    case 4:
                        answer = "Четверг";
                        break;
                    case 5:
                        answer = "Пятница";
                        break;
                    case 6:
                        answer = "Суббота";
                        break;
                    case 7:
                        answer = "Воскресенье";
                        break;
                }
                break;
        }
        return answer;
    }

    private static String getDayNow(int position){
        Calendar calendar = Calendar.getInstance();
        String formated = formatter.format(minDate.getTime());
        try {
            calendar.setTime(formatter.parse(formated));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_YEAR, position);
        return formatter.format(calendar.getTime());
    }

    private static int DayOfWeek(String fakeDate) {
        Calendar c = GregorianCalendar.getInstance();
        try {
            c.setTime(formatter.parse(fakeDate));
        } catch (ParseException e){
            e.printStackTrace();
        }
        return   7 - (8 - c.get(GregorianCalendar.DAY_OF_WEEK))%7;
    }

}
