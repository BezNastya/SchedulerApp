
create table USER
(
    user_id int,
    authorized boolean,
    email varchar,
    first_name varchar,
    last_name varchar,
    password varchar,
    role varchar
);

create table ADMIN
(
    user_id int
);
create table TEACHER
(
    academic_degree varchar,
    department varchar,
    user_id int
);
create table COURSE
(
    id int,
    name varchar
);
create table GROUP_COURSE
(
    id int,
    group_num int,
    course_id int
);

create table LESSON
(
    lesson_id int,
    schedule_date_day_of_the_week int,
    schedule_date_lesson_order int,
    schedule_date_week int,
    place varchar,
    type varchar,
    group_course_id int
);
create table POSTPONE_LESSON
(
    id int,
    description varchar,
    day_of_week int,
    lesson_order int,
    week int,
    new_place varchar,
    canceled_lesson int
);
create table STUDENT
(
    faculty varchar,
    specialty varchar,
    stud_ticket_series varchar,
    year_admission int,
    user_id int
);



