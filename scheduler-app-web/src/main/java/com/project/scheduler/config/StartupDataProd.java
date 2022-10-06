package com.project.scheduler.config;

import com.project.scheduler.entity.*;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.repository.PostponeLessonRepository;
import com.project.scheduler.service.impl.AdminServiceImpl;
import com.project.scheduler.service.impl.StudentServiceImpl;
import com.project.scheduler.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Profile("prod")
@Component
public class StartupDataProd implements CommandLineRunner {

    private final StudentServiceImpl studentService;
    private final TeacherServiceImpl teacherService;
    private final AdminServiceImpl adminService;
    private final CourseRepository courseRepository;
    private final GroupCourseRepository groupCourseRepository;
    private final PostponeLessonRepository postponeLessonRepository;
    private final LessonRepository lessonRepository;

    private static final Random RANDOM =  new Random();
    private static final List<Integer> LECTURES = Arrays.asList(5, 8, 17, 23, 27, 33, 34, 39, 43, 44, 49);
    private static final List<Integer> LABS = Arrays.asList(21, 22, 30, 31);
    private static final List<Integer> SEMS = Arrays.asList(11, 12, 13, 14, 15, 16, 24, 25, 26, 32, 35, 36, 37, 38, 45, 46, 47, 48);


    @Autowired
    public StartupDataProd(StudentServiceImpl studentService,
                       TeacherServiceImpl teacherService,
                       AdminServiceImpl adminService, CourseRepository courseRepository, GroupCourseRepository groupCourseRepository, PostponeLessonRepository postponeLessonRepository, LessonRepository lessonRepository) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.adminService = adminService;
        this.courseRepository = courseRepository;
        this.groupCourseRepository = groupCourseRepository;
        this.postponeLessonRepository = postponeLessonRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void run(String... args) {
        //LEAVE EMPTY( DATABASE IS FULL)
    }

    private void initLessons() {
        List<GroupCourse> groups = groupCourseRepository.findAll();
        for (GroupCourse group : groups){
            initLessonsForGroup(group);
        }
    }
    private void initLessonsForGroup(GroupCourse group) {
        int weekStart = RANDOM.nextInt(4) + 1;
        int weekEnd = RANDOM.nextInt(6) + 10;
        LessonType lessonType = getLessonType(group.getId());
        WeekDay weekDay = initWeekDay();
        LessonOrder lessonOrder = initLessonOrder();
        String place = (RANDOM.nextInt(200) + 200) + "";
        for (int i = weekStart; i <= weekEnd; i++) {
            ScheduleDate date = new ScheduleDate(weekDay, lessonOrder, i);
            Lesson lesson = new Lesson(lessonType, place, date, group);
            System.out.println(lesson);
            lessonRepository.save(lesson);
        }
    }

    private LessonOrder initLessonOrder() {
        int lesson = RANDOM.nextInt(7) + 1;
        return switch (lesson) {
            case 1 -> LessonOrder.FIRST;
            case 2 -> LessonOrder.SECOND;
            case 3 -> LessonOrder.THIRD;
            case 4 -> LessonOrder.FORTH;
            case 5 -> LessonOrder.FIFTH;
            case 6 -> LessonOrder.SIXTH;
            case 7 -> LessonOrder.SEVENTH;
            default -> LessonOrder.SEVENTH;
        };
    }

    private WeekDay initWeekDay() {
        int day = RANDOM.nextInt(5) + 1;
        return switch (day) {
            case 1 -> WeekDay.MONDAY;
            case 2 -> WeekDay.TUESDAY;
            case 3 -> WeekDay.WEDNESDAY;
            case 4 -> WeekDay.THURSDAY;
            case 5 -> WeekDay.FRIDAY;
            default -> WeekDay.WEDNESDAY;
        };
    }

    private LessonType getLessonType(Long id) {
        Integer id1 = id.intValue();
        if (LECTURES.contains(id1)) {
            return LessonType.LECTURE;
        }
        if (LABS.contains(id1)) {
            return LessonType.LAB;
        }
        if (SEMS.contains(id1)) {
            return LessonType.SEMINAR;
        }
        return LessonType.PRACTICE;
    }

    private void initGroupsWithTeachers() {
        List<GroupCourse> groups = groupCourseRepository.findAll();
        List<Teacher> teachers = teacherService.findAll();
        for (Teacher teacher : teachers){
            System.err.println(teacher);
            Set<GroupCourse> groupCourses = teacher.getGroupCourse();
            for (int i = 0; i < 9; i++) {
                int g = RANDOM.nextInt(groups.size());
                groupCourses.add(groups.get(g));
                groups.remove(g);
            }
            teacher.setGroupCourse(groupCourses);
            teacherService.save(teacher);
        }
    }

    private void initGroupsWithStudents() {
        List<GroupCourse> groups = groupCourseRepository.findAll();
        List<Student> students = studentService.findAll();
        for (Student student : students){
            System.err.println(student);
            Set<GroupCourse> groupCourses = student.getGroupCourse();
            for (GroupCourse group : groups){
                groupCourses.add(group);
                student.setGroupCourse(groupCourses);
            }
            studentService.save(student);
        }
    }

    public void initDatabaseCourse() {
        Course course1 = new Course("OOP");
        courseRepository.save(course1);
        initGroups(course1, (byte) 5);
        Course course2 = new Course("English");
        courseRepository.save(course2);
        initGroups(course2, (byte) 6);
        Course course3 = new Course("Databases");
        courseRepository.save(course3);
        initGroups(course3, (byte) 4);
        Course course4 = new Course("Anatomy");
        courseRepository.save(course4);
        initGroups(course4, (byte) 2);
        Course course5 = new Course("Sociology");
        courseRepository.save(course5);
        initGroups(course5, (byte) 4);
        Course course6 = new Course("Functional programming");
        courseRepository.save(course6);
        initGroups(course6, (byte) 3);
        Course course7 = new Course("Physics");
        courseRepository.save(course7);
        initGroups(course7, (byte) 2);
        Course course8 = new Course("Logic");
        courseRepository.save(course8);
        initGroups(course8, (byte) 1);
        Course course9 = new Course("History of Ukraine");
        courseRepository.save(course9);
        initGroups(course9, (byte) 6);
        Course course10 = new Course("Android programming");
        courseRepository.save(course10);
        initGroups(course10, (byte) 4);
        Course course11 = new Course("Philosophy");
        courseRepository.save(course11);
        initGroups(course11, (byte) 6);
    }

    private void initGroups(Course course, byte num){
        for (byte i = 1; i <= num; i++){
            GroupCourse group = new GroupCourse(course, i);
            groupCourseRepository.save(group);
        }
    }

    private void studentAccount() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //-------------------
        Student student = new Student();
        student.setEmail("student4@ukma.edu.ua");
        student.setFirstName("Oleg");
        student.setLastName("Turko");
        student.setPassword(encoder.encode("password"));
        student.setFaculty("FSNST");
        student.setStudTicketSeries("RG12461");
        student.setYearAdmission(2024);
        student.setSpecialty("Social work");
        student.setRole(Role.STUDENT);
        student.setAuthorized(true);
        studentService.save(student);
        //----------------------------------------

        Student student2 = new Student();
        student2.setEmail("student4@ukma.edu.ua");
        student2.setFirstName("Ann");
        student2.setLastName("Kolodiy");
        student2.setPassword(encoder.encode("password"));
        student2.setFaculty("FPRN");
        student2.setStudTicketSeries("RD19204");
        student2.setYearAdmission(2023);
        student2.setSpecialty("Biology");
        student2.setRole(Role.STUDENT);
        student2.setAuthorized(true);
        studentService.save(student2);

        Student student3 = new Student();
        student3.setEmail("student5@ukma.edu.ua");
        student3.setFirstName("Iryna");
        student3.setLastName("Bodnarchuk");
        student3.setPassword(encoder.encode("password"));
        student3.setFaculty("FI");
        student3.setStudTicketSeries("FE538923");
        student3.setYearAdmission(2022);
        student3.setSpecialty("SE");
        student3.setRole(Role.STUDENT);
        student3.setAuthorized(true);
        studentService.save(student3);

        Student student4 = new Student();
        student4.setEmail("student6@ukma.edu.ua");
        student4.setFirstName("Lilia");
        student4.setLastName("Schevchenko");
        student4.setPassword(encoder.encode("password"));
        student4.setFaculty("FI");
        student4.setSpecialty("SE");
        student4.setRole(Role.STUDENT);
        student4.setAuthorized(true);
        studentService.save(student4);
    }

    private void teacherAccount() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Teacher teacher = new Teacher();

        teacher.setEmail("teacher1@ukma.edu.ua");
        teacher.setPassword(encoder.encode("password"));
        teacher.setAcademicDegree("Master");
        teacher.setFirstName("Alina");
        teacher.setLastName("Petrivna");
        teacher.setDepartment("FI");
        teacher.setRole(Role.TEACHER);
        teacher.setAuthorized(true);
        teacherService.save(teacher);

        Teacher teacher2 = new Teacher();
        teacher2.setEmail("teacher2@ukma.edu.ua");
        teacher2.setPassword(encoder.encode("password"));
        teacher2.setAcademicDegree("Master");
        teacher2.setFirstName("Maksym");
        teacher2.setLastName("Demchenko");
        teacher2.setDepartment("FI");
        teacher2.setRole(Role.TEACHER);
        teacher2.setAuthorized(true);
        teacherService.save(teacher2);

        Teacher teacher3 = new Teacher();
        teacher3.setEmail("teacher3@ukma.edu.ua");
        teacher3.setPassword(encoder.encode("password"));
        teacher3.setAcademicDegree("Assistant");
        teacher3.setFirstName("Igor");
        teacher3.setLastName("Chick");
        teacher3.setDepartment("FI");
        teacher3.setRole(Role.TEACHER);
        teacher3.setAuthorized(true);
        teacherService.save(teacher3);

        Teacher teacher4 = new Teacher();
        teacher4.setEmail("teacher4@ukma.edu.ua");
        teacher4.setPassword(encoder.encode("password"));
        teacher4.setAcademicDegree("Master");
        teacher4.setFirstName("Halyna");
        teacher4.setLastName("Pushyk");
        teacher4.setDepartment("FSNST");
        teacher4.setRole(Role.TEACHER);
        teacher4.setAuthorized(true);
        teacherService.save(teacher4);

        Teacher teacher5 = new Teacher();
        teacher5.setEmail("teacher5@ukma.edu.ua");
        teacher5.setPassword(encoder.encode("password"));
        teacher5.setAcademicDegree("Master");
        teacher5.setFirstName("Maryna");
        teacher5.setLastName("Kucher");
        teacher5.setDepartment("FPRN");
        teacher5.setRole(Role.TEACHER);
        teacher5.setAuthorized(true);
        teacherService.save(teacher5);
    }
}