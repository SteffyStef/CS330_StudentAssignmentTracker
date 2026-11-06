
# Student Assignment Tracker

## Project Overview
The Student Assignment Tracker is a Java console application
that helps students organize and manage their assignments.

The application connects to a MySQL database to store
course information, assignment details, due dates,
and completion status.

## Features
- View all assignments
- Mark assignments as completed
- Add new assignments
- View pending assignments
- Delete assignments with confirmation
- Validate menu choices, IDs, and due dates
- Store assignment data in MySQL

## Technologies Used
- Java
- MySQL
- JDBC (MySQL Connector/J)
- Maven
- IntelliJ IDEA
- Git and GitHub

## Database Structure

The application uses two tables:

### Courses
- course_id (Primary Key)
- course_name

### Assignments
- assignment_id (Primary Key)
- course_id (Foreign Key)
- title
- due_date
- completed

Each assignment belongs to a course.

## How to Run
1. Install Java, MySQL, and IntelliJ IDEA.
2. Clone or download this repository.
3. Create a MySQL database named student_assignment_tracker.
4. Run the SQL migration in migrations/V1_create_initial_tables.sql.
5. Configure the DB_USER and DB_PASSWORD environment variables.
6. Open the project in IntelliJ IDEA.
7. Allow Maven to download the required dependencies.
8. Run org.example.Main.
9. Use the console menu to manage assignments.

## SQL Concepts Demonstrated
- CREATE TABLE
- PRIMARY KEY and FOREIGN KEY
- SELECT
- INSERT
- UPDATE
- DELETE
- INNER JOIN
- WHERE
- ORDER BY
- Prepared statements

## Project Status
Core application features implemented and tested.
