package com.project.scheduler;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.Student;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.service.UserService;
import com.project.scheduler.service.impl.AdminServiceImpl;
import com.project.scheduler.service.impl.StudentServiceImpl;
import com.project.scheduler.service.impl.TeacherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartupData implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(StartupData.class);


    private final StudentServiceImpl studentService;
    private final TeacherServiceImpl teacherService;
    private final AdminServiceImpl adminService;
    private final CourseRepository courseRepository;


    @Autowired
    public StartupData(StudentServiceImpl studentService,
                       TeacherServiceImpl teacherService,
                       AdminServiceImpl adminService, CourseRepository courseRepository) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.adminService = adminService;
        this.courseRepository = courseRepository;
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
        teacherAccount();
        initDatabaseCourse();
    }

    public void initDatabaseCourse() {
        courseRepository.save(new Course("Computing"));
        courseRepository.save(new Course("English"));
        courseRepository.save(new Course("Algorithms"));
    }

        private void studentAccount() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Student student = new Student();
        student.setEmail("student@ukma.edu.ua");
        student.setFirstName("Ivan");
        student.setLastName("Boyko");
        student.setPassword(encoder.encode("student"));
        student.setFaculty("FI");
        student.setStudTicketSeries("T1");
        student.setYearAdmission(2019);
        student.setSpecialty("SE");
        student.setRole("STUDENT");
        student.setAuthorized(true);
        studentService.save(student);

        Student student2 = new Student();
        student2.setEmail("student2@ukma.edu.ua");
        student2.setFirstName("Ivan");
        student2.setLastName("Tolkunov");
        student2.setPassword(encoder.encode("student2"));
        student2.setFaculty("FI");
        student2.setStudTicketSeries("T2");
        student2.setYearAdmission(2020);
        student2.setSpecialty("CS");
        student2.setRole("STUDENT");
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
        student3.setRole("STUDENT");
        student3.setAuthorized(true);
        studentService.save(student3);

        Student student4 = new Student();
        student4.setEmail("student3@ukma.edu.ua");
        student4.setFirstName("Anna");
        student4.setLastName("Diana");
        student4.setPassword(encoder.encode("Melnyk"));
        student4.setFaculty("FI");
        student4.setSpecialty("SE");
        student4.setRole("STUDENT");
        student4.setAuthorized(true);
        studentService.save(student4);

        studentService.delete(student4);
        studentService.updateFaculty(student3, "FH");
        studentService.updateTicketNumber(student2, "121");
        studentService.updateSpeciality(student, "AM");

    }

    private void teacherAccount() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Teacher teacher = new Teacher();

        teacher.setEmail("teacher@ukma.edu.ua");
        teacher.setPassword(encoder.encode("teacher"));
        teacher.setAcademicDegree("Master");
        teacher.setFirstName("Alina");
        teacher.setLastName("Petrivna");
        teacher.setDepartment("FI");
        teacher.setRole("TEACHER");
        teacher.setAuthorized(true);
        teacherService.save(teacher);

        Teacher teacher2 = new Teacher();
        teacher2.setEmail("teacher2@ukma.edu.ua");
        teacher2.setPassword(encoder.encode("teacher2"));
        teacher2.setAcademicDegree("Master");
        teacher2.setFirstName("Maksym");
        teacher2.setLastName("Demchenko");
        teacher2.setDepartment("FI");
        teacher2.setRole("TEACHER");
        teacher2.setAuthorized(true);
        teacherService.save(teacher2);
        teacherService.updateAcademicDegree(teacher2.getUserId(), "PhD");
        teacherService.updateDepartment(teacher2.getUserId(), "Department of Computer Science");
    }

    private void adminAccount() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Admin admin = new Admin();
        admin.setFirstName("Admin`s first name");
        admin.setLastName("Admin`s last name");
        admin.setRole("ADMIN");
        admin.setEmail("admin@ukma.edu.ua");
        admin.setPassword(encoder.encode("admin"));
        admin.setAuthorized(true);
        adminService.save(admin);
    }
}