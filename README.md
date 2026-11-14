
# Student Assignment Tracker

## Project Overview

The Student Assignment Tracker is a Java application that
helps students organize and manage their coursework.

The project includes two interfaces:
- A console-based application with an interactive menu.
- A JavaFX graphical dashboard for managing assignments.

Both interfaces connect to a MySQL relational database
using JDBC.

Students can add assignments, view due dates, track
completion status, and delete assignments.

The JavaFX dashboard also displays assignment statistics
and reports showing the number of assignments per course.

The database uses primary keys, foreign keys, and
relational tables to maintain data integrity.


## Features

### Console Application
- View all assignments
- Mark assignments as completed
- Add new assignments
- View pending assignments
- Delete assignments with confirmation
- Validate menu choices, assignment IDs, and due dates

### JavaFX Dashboard
- Display assignments in an interactive table
- View assignment titles, courses, due dates, and completion status
- Display total, completed, and pending assignment counts
- Display total courses
- Show assignment counts grouped by course
- Add assignments using a graphical form
- Validate required form fields
- Clear or cancel assignment entry
- Mark selected assignments as completed
- Prevent duplicate completion updates
- Delete selected assignments with confirmation
- Automatically refresh the dashboard after changes

### Database
- Store courses and assignments in MySQL
- Use primary and foreign keys to maintain relationships
- Perform CRUD operations using JDBC



## Technologies Used

- Java — Application programming language
- JavaFX — Graphical user interface (GUI)
- MySQL — Relational database management system
- JDBC (MySQL Connector/J) — Database connectivity
- Maven — Dependency management and project builds
- IntelliJ IDEA — Integrated development environment
- Git — Version control
- GitHub — Source code repository



## Database Structure

The application uses two related tables:

### Courses
- course_id (Primary Key)
- course_name

### Assignments
- assignment_id (Primary Key)
- course_id (Foreign Key referencing courses.course_id)
- title
- due_date
- completed

### Table Relationship

One course can have many assignments, but each assignment
belongs to one course.

This is a one-to-many (1:M) relationship.

The foreign key connects assignments.course_id to
courses.course_id and helps maintain referential integrity.



## How to Run

### Prerequisites
- Java JDK
- MySQL Server
- IntelliJ IDEA
- Maven

### Database Setup
1. Clone or download this repository.
2. Start MySQL Server.
3. Create a MySQL database named `student_assignment_tracker`.
4. Run `migrations/V1_create_initial_tables.sql` to create
   the database tables.
5. Configure the `DB_USER` and `DB_PASSWORD` environment
   variables with your MySQL credentials.

### Running the Console Application
1. Open the project in IntelliJ IDEA.
2. Allow Maven to download the required dependencies.
3. Run `org.example.Main`.
4. Use the console menu to manage assignments.

### Running the JavaFX Dashboard
1. Open the same project in IntelliJ IDEA.
2. Ensure the database is running and the environment
   variables are configured.
3. Run the `Student Assignment Dashboard` configuration.
4. Use the graphical interface to view, add, complete,
   and delete assignments.

Both interfaces connect to the same MySQL database.



## SQL Concepts Demonstrated

- CREATE TABLE — Create database tables
- PRIMARY KEY — Uniquely identify records
- FOREIGN KEY — Connect related tables
- SELECT — Retrieve data
- WHERE — Filter records
- INSERT — Add new assignments
- UPDATE — Mark assignments completed
- DELETE — Remove assignments
- INNER JOIN — Combine assignments with their courses
- LEFT JOIN — Include courses with no assignments
- GROUP BY — Group assignment data by course
- COUNT(*) — Count records
- ORDER BY — Sort query results
- Prepared Statements — Safely pass values into SQL queries
- Database Normalization — Organize related data into separate tables



## Project Status

The core application development is complete.

Implemented and tested:
- Java console application
- JavaFX graphical dashboard
- MySQL database integration
- Assignment creation and viewing
- Assignment completion tracking
- Assignment deletion with confirmation
- Dashboard statistics and course reports
- Input validation
- Automatic dashboard updates

The project is undergoing final documentation and
submission review.

