package com.project.scheduler.config;

import com.project.scheduler.dto.CourseDTO;
import com.project.scheduler.dto.LessonRequestDTO;
import com.project.scheduler.entity.*;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.List;

@Profile("dev")
@Component
public class StartupData implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(StartupData.class);

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final AdminService adminService;
    private final CourseService courseService;
    private final GroupCourseRepository groupCourseRepository;

    @Autowired
    public StartupData(StudentService studentService,
                       TeacherService teacherService,
                       AdminService adminService, CourseService courseService, GroupCourseRepository groupCourseRepository) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.adminService = adminService;
        this.courseService = courseService;
        this.groupCourseRepository = groupCourseRepository;
    }

    @Override
    public void run(String... args) {
        MDC.put("Starting application at:", java.time.LocalTime.now().toString());
        logger.trace("This is TRACE");
        logger.debug("This is DEBUG");
        logger.info("This is INFO");
        logger.warn("This is WARN");
        logger.error("This is ERROR");
        adminAccount();
        studentAccount();
        initDatabaseCourse();
    }

    public void initDatabaseCourse() {
        CourseDTO course1 = new CourseDTO("Computing", (byte) 2);
        CourseDTO course2 = new CourseDTO("English", (byte) 3);
        CourseDTO course3 = new CourseDTO("Algorithms", (byte) 4);
        courseService.saveCourse(course1);
        courseService.saveCourse(course2);
        courseService.saveCourse(course3);

        List<GroupCourse> groupCourseList = groupCourseRepository.findAll();

        for (int i = 0; i < groupCourseList.size(); i++) {
            LessonRequestDTO lesson1 = LessonRequestDTO.builder()
                    .type(LessonType.LECTURE)
                    .place("20" + i)
                    .day(WeekDay.MONDAY)
                    .time(LessonOrder.FIFTH)
                    .weekStart(1 + i)
                    .weekEnd(15 - i + i/2)
                    .groupId(groupCourseList.get(i).getId())
                    .build();
            courseService.addLessons(lesson1);
        }

        for (int i = 0; i < groupCourseList.size(); i++) {
            LessonRequestDTO lesson1 = LessonRequestDTO.builder()
                    .type(LessonType.PRACTICE)
                    .place("30" + i)
                    .day(WeekDay.TUESDAY)
                    .time(LessonOrder.FIRST)
                    .weekStart(1 + i)
                    .weekEnd(15 - i + i/2)
                    .groupId(groupCourseList.get(i).getId())
                    .build();
            courseService.addLessons(lesson1);
        }
    }

    private void studentAccount() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //-------------------
        Student student = new Student();
        student.setEmail("student@ukma.edu.ua");
        student.setFirstName("Ivan");
        student.setLastName("Boyko");
        student.setPassword(encoder.encode("student"));
        student.setFaculty("FI");
        student.setStudTicketSeries("T1");
        student.setYearAdmission(2019);
        student.setSpecialty("SE");
        student.setRole(Role.STUDENT);
        student.setAuthorized(true);

        Teacher teacher = new Teacher();

        teacher.setEmail("teacher@ukma.edu.ua");
        teacher.setPassword(encoder.encode("teacher"));
        teacher.setAcademicDegree("Master");
        teacher.setFirstName("Alina");
        teacher.setLastName("Petrivna");
        teacher.setDepartment("FI");
        teacher.setRole(Role.TEACHER);
        teacher.setAuthorized(true);
        teacherService.save(teacher);

        Teacher teacher2 = new Teacher();
        teacher2.setEmail("teacher2@ukma.edu.ua");
        teacher2.setPassword(encoder.encode("teacher2"));
        teacher2.setAcademicDegree("Master");
        teacher2.setFirstName("Maksym");
        teacher2.setLastName("Demchenko");
        teacher2.setDepartment("FI");
        teacher2.setRole(Role.TEACHER);
        teacher2.setAuthorized(true);
        teacherService.save(teacher2);
        teacherService.updateAcademicDegree(teacher2.getUserId(), "PhD");
        teacherService.updateDepartment(teacher2.getUserId(), "Department of Computer Science");

        studentService.save(student);
        //----------------------------------------

        Student student2 = new Student();
        student2.setEmail("student2@ukma.edu.ua");
        student2.setFirstName("Ivan");
        student2.setLastName("Tolkunov");
        student2.setPassword(encoder.encode("student2"));
        student2.setFaculty("FI");
        student2.setStudTicketSeries("T2");
        student2.setYearAdmission(2020);
        student2.setSpecialty("CS");
        student2.setRole(Role.STUDENT);
        student2.setAuthorized(true);
        studentService.save(student2);

        Student student3 = new Student();
        student3.setEmail("student3@ukma.edu.ua");
        student3.setFirstName("Anna");
        student3.setLastName("Kovalenko");
        student3.setPassword(encoder.encode("student3"));
        student3.setFaculty("FI");
        student3.setStudTicketSeries("T3");
        student3.setYearAdmission(2019);
        student3.setSpecialty("CS");
        student3.setRole(Role.STUDENT);
        student3.setAuthorized(true);
        studentService.save(student3);

        Student student4 = new Student();
        student4.setEmail("student3@ukma.edu.ua");
        student4.setFirstName("Anna");
        student4.setLastName("Diana");
        student4.setPassword(encoder.encode("Melnyk"));
        student4.setFaculty("FI");
        student4.setSpecialty("SE");
        student4.setRole(Role.STUDENT);
        student4.setAuthorized(true);
        studentService.save(student4);

        studentService.delete(student4);
        studentService.updateFaculty(student3, "FH");
        studentService.updateTicketNumber(student2, "121");
        studentService.updateSpeciality(student, "AM");

    }

    private void adminAccount() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Admin admin = new Admin();
        admin.setFirstName("Admin`s first name");
        admin.setLastName("Admin`s last name");
        admin.setRole(Role.ADMIN);
        admin.setEmail("admin@ukma.edu.ua");
        admin.setPassword(encoder.encode("admin"));
        admin.setAuthorized(true);
        adminService.save(admin);
    }
}