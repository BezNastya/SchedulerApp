
INSERT INTO USER (user_id, authorized, email, first_name, last_name, password, role) VALUES
(1,true,'student@ukma.edu.ua','Andrii', 'Serdiuk','password','STUDENT' ),
(1,true,'admin@ukma.edu.ua','Anastasiia', 'Bezruka','password','ADMIN' ),
(1,true,'teacher@ukma.edu.ua','Denis', 'Tsabut','password','TEACHER' );

INSERT INTO ADMIN(user_id)VALUES
(4);
INSERT INTO TEACHER(academic_degree,department,user_id)VALUES
('master','fi',5);

INSERT INTO COURSE(id,name)VALUES
(1,'bob');
INSERT INTO GROUP_COURSE(id ,group_num,course_id)VALUES
(1,1,1);
INSERT INTO LESSON(lesson_id,schedule_date_day_of_the_week,schedule_date_lesson_order,schedule_date_week,place,type,group_course_id)VALUES
(5,1,1,1,20,'LECTURE',3);
INSERT INTO POSTPONE_LESSON(id,description,day_of_week,lesson_order,week,new_place,canceled_lesson)VALUES
(19,'desc',2,2,2,'new place',15  );
INSERT INTO STUDENT(faculty,specialty,stud_ticket_series,year_admission,user_id)VALUES
('FI','CS','T1',2019,2  );