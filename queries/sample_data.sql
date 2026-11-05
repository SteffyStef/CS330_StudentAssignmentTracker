
-- Sample data for Student Assignment Tracker
-- Run after V1__create_initial_tables.sql
-- Use on a fresh, empty database

INSERT INTO courses (course_id, course_name)
VALUES (1, 'CS330 Databases');

INSERT INTO assignments
(course_id, title, due_date)
VALUES
    (1, 'Database Final Project', '2026-12-10');
