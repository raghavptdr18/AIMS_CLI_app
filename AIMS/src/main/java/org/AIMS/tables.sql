
DROP TABLE users;
DROP TABLE user_details;
DROP TABLE user_details_hidden;
DROP TABLE course_catalog_admin;
DROP TABLE course_offerings;
DROP TABLE instructors;
DROP TABLE student_registration;
DROP TABLE grades;

//////////////////////////////////////////////

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE admin(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE course_catalog_admin (
  offering_id serial PRIMARY KEY,
  course_id varchar(255) NOT NULL,
  course_name varchar(255) NOT NULL,
  batch integer NOT NULL,
  department varchar(255) NOT NULL,
  semester integer NOT NULL,
  ce varchar(255) NOT NULL,
  credit REAL NOT NULL,
  prerequisites varchar(255) NOT NULL
);

CREATE TABLE course_offerings (
  offering_id serial PRIMARY KEY,
  course_id varchar(255) NOT NULL ,
  instructor varchar(255) NOT NULL,
  cgpa_constraint REAL NOT NULL
);

CREATE TABLE grades (
  grade_id serial PRIMARY KEY,
  username varchar(255) NOT NULL,
  course_id varchar(255) NOT NULL, 
  grade varchar(5) NOT NULL
);

CREATE TABLE user_details(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    semester integer NOT NULL,
    joining_date VARCHAR(255) NOT NULL,
    batch integer NOT NULL,
    contact VARCHAR(255) NOT NULL,
    cgpa REAL NOT NULL,
    current_sem_credits REAL NOT NULL
    department varchar(255) NOT NULL
);

CREATE TABLE student_registration (
  registration_id serial PRIMARY KEY,
  username varchar(255) NOT NULL,  
  course_id varchar(255) NOT NULL,
  semester integer NOT NULL,
  stat varchar(255) NOT NULL
);


CREATE TABLE instructors (
  instructor_id SERIAL PRIMARY KEY,
  username varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL
);

CREATE Table flags(
  sem integer NOT NULL,
  course_registration_instructor integer NOT NULL,
  course_registration_student integer NOT NULL,
  upload_grade integer NOT NULL
);

CREATE TABLE l(
  logged integer NOT NULL
);

CREATE TABLE graduated(
  username varchar(255) NOT NULL,
  total_core_credit REAL NOT NULL,
  total_elective_credit REAL NOT NULL
);

/////////////////////////////////////////////////

SELECT * FROM users ; 
SELECT * FROM course_offerings ; 
SELECT * FROM instructors ; 
SELECT * FROM student_registration ; 
SELECT * FROM grades ; 
SELECT * FROM user_details;
SELECT * FROM course_catalog_admin;
SELECT * FROM flags;
SELECT * FROM l;
SELECT * FROM graduated;


INSERT INTO users (username,email,password) VALUES ('dd', 'deanoffice@iitrpr.ac.in', '1234');

INSERT INTO admin (username,password) VALUES ('aa', '1234');

INSERT INTO flags (sem,course_registration_instructor,course_registration_student,upload_grade) VALUES (0,0,0,0);


SELECT grades.grade, course_catalog_admin.credit FROM student_registration INNER JOIN course_catalog_admin ON student_registration.course_id = course_catalog_admin.course_id INNER JOIN grades ON student_registration.course_id = grades.course_id AND grades.username = student_registration.username WHERE student_registration.username = 'rrr' AND student_registration.semester=1;










SELECT grades.grade , course_catalog_admin.credit
FROM course_catalog_admin , student_registration , grades
WHERE course_catalog_admin.course_id=student_registration.course_id AND student_registration.course_id=grades.course_id AND grades.username='"+username+"' AND student_registration.semester='"+sem+"'



SELECT grades.username, course_offerings.course_id , grades.grade
FROM course_offerings, grades
WHERE course_offerings.instructor = '"+username+"' AND course_offerings.course_id=grades.course_id



SELECT SUM(l_t_p) 
FROM student_registration , course_catalog
WHERE student_registration.course_id=course_catalog.id AND student_id=username AND ( student_registration.semester=sem-1 || student_registration.semester=sem-2);

SELECT current_sem_credits
FROM user_details_hidden
WHERE username = username AND semester=sem 

SELECT * 
FROM student_registration 
WHERE course_id=id

SELECT course_id,instructor,credit,cgpa_constraint,ce,prerequisites
FROM course_catalog_admin,course_offerings
WHERE batch=batch AND semester=sem AND course_catalog_admin.course_id=course_offerings.course_id

SELECT course_catalog_admin.course_id, course_offerings.instructor, course_catalog_admin.credit, course_offerings.cgpa_constraint, course_catalog_admin.ce, course_catalog_admin.prerequisites
FROM course_catalog_admin, course_offerings
WHERE course_catalog_admin.course_id = course_offerings.course_id AND course_catalog_admin.batch = batch AND course_catalog_admin.semester = sem;


SELECT SUM(course_catalog_admin.credit) FROM student_registration, course_catalog_admin WHERE student_registration.course_id = course_catalog_admin.course_id AND student_registration.username = ? AND (student_registration.semester = ? OR student_registration.semester = ?)

SELECT * FROM course_offerings , course_catalog_admin WHERE course_catalog_admin.course_id = course_offerings.course_id AND course_catalog_admin.batch = AND course_catalog_admin.semester=


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


INSERT INTO users (username,email,password) VALUES ('ppp', '2020csb1115@iitrpr.ac.in', '1234');
INSERT INTO user_details (username,semester,joining_date,batch,contact,cgpa,current_sem_credits) VALUES('ppp',1,'10/10/10',2020,'99926514',0,0);
INSERT INTO users (username,email,password) VALUES ('qqq', '2020csb1115@iitrpr.ac.in', '1234');
INSERT INTO user_details (username,semester,joining_date,batch,contact,cgpa,current_sem_credits) VALUES('qqq',1,'10/10/10',2020,'99926514',0,0);
INSERT INTO users (username,email,password) VALUES ('www', '2020csb1115@iitrpr.ac.in', '1234');
INSERT INTO user_details (username,semester,joining_date,batch,contact,cgpa,current_sem_credits) VALUES('www',1,'10/10/10',2020,'99926514',0,0);
INSERT INTO users (username,email,password) VALUES ('eee', '2020csb1115@iitrpr.ac.in', '1234');
INSERT INTO user_details (username,semester,joining_date,batch,contact,cgpa,current_sem_credits) VALUES('eee',1,'10/10/10',2020,'99926514',0,0);
INSERT INTO users (username,email,password) VALUES ('ttt', '2020csb1115@iitrpr.ac.in', '1234');
INSERT INTO user_details (username,semester,joining_date,batch,contact,cgpa,current_sem_credits) VALUES('ttt',1,'10/10/10',2020,'99926514',0,0);
INSERT INTO users (username,email,password) VALUES ('yyy', '2020csb1115@iitrpr.ac.in', '1234');
INSERT INTO user_details (username,semester,joining_date,batch,contact,cgpa,current_sem_credits) VALUES('yyy',1,'10/10/10',2020,'99926514',0,0);
INSERT INTO users (username,email,password) VALUES ('uuu', '2020csb1115@iitrpr.ac.in', '1234');
INSERT INTO user_details (username,semester,joining_date,batch,contact,cgpa,current_sem_credits) VALUES('uuu',1,'10/10/10',2020,'99926514',0,0);

INSERT INTO course_catalog_admin (course_id,course_name,batch,department,semester,ce,credit,prerequisites) VALUES ('3cs000','wgrf',2020,'cse',3,'c',1,'-1');


INSERT INTO course_offerings (course_id,instructor,cgpa_constraint) VALUES ('cs203','nitin','6');
INSERT INTO course_offerings (course_id,instructor,cgpa_constraint) VALUES ('cs101','nitin','6');
INSERT INTO course_offerings (course_id,instructor,cgpa_constraint) VALUES ('hs302','nitin','6');
INSERT INTO course_offerings (course_id,instructor,cgpa_constraint) VALUES ('hs101','nitin','6');
INSERT INTO course_offerings (course_id,instructor,cgpa_constraint) VALUES ('cs345','nitin','6');
INSERT INTO course_offerings (course_id,instructor,cgpa_constraint) VALUES ('3cs000','nitin','6');