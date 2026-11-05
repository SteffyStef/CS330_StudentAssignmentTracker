-- INNER JOIN: Show assignments with their course names

SELECT courses.course_name, assignments.title
FROM courses
         INNER JOIN assignments
                    ON courses.course_id = assignments.course_id;


-- WHERE: Find assignments belonging to course 1

SELECT title, due_date, completed
FROM assignments
WHERE course_id = 1;


-- GROUP BY: Count assignments for each course

SELECT course_id, COUNT(*) AS assignment_count
FROM assignments
GROUP BY course_id;


-- JOIN + WHERE + GROUP BY:
-- Count incomplete assignments for each course

SELECT courses.course_name,
       COUNT(assignments.assignment_id) AS assignment_count
FROM courses
         INNER JOIN assignments
                    ON courses.course_id = assignments.course_id
WHERE assignments.completed = FALSE
GROUP BY courses.course_id, courses.course_name;


-- UPDATE: Mark an assignment as completed

UPDATE assignments
SET completed = TRUE
WHERE assignment_id = 1;
