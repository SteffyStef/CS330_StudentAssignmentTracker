
CREATE TABLE courses (
                         course_id INT PRIMARY KEY AUTO_INCREMENT,
                         course_name VARCHAR(100) NOT NULL
);


CREATE TABLE assignments (
                             assignment_id INT PRIMARY KEY AUTO_INCREMENT,
                             course_id INT NOT NULL,
                             title VARCHAR(100) NOT NULL,
                             due_date DATE,
                             completed BOOLEAN NOT NULL DEFAULT FALSE,
                             FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
