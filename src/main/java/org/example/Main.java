
package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;

import java.time.LocalDate;



public class Main {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Student Assignment Tracker");
            System.out.println("--------------------------");


            System.out.println("1. View all assignments");
            System.out.println("2. Mark assignment completed");
            System.out.println("3. Add new assignment");
            System.out.println("4. View pending assignments");
            System.out.println("5. Exit");


            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            if (choice == 1) {



                String sql = """
        SELECT assignments.assignment_id,
               courses.course_name,
               assignments.title,
               assignments.due_date,
               assignments.completed
        FROM courses
        INNER JOIN assignments
        ON courses.course_id = assignments.course_id
        ORDER BY assignments.assignment_id
        """;



                try (Connection connection = DatabaseConnection.getConnection
                 ();
             Statement statement = connection.
                 createStatement();
             ResultSet results = statement.executeQuery(sql)) {

            System.out.
                println("Student Assignment Tracker");


                System.out.println("--------------------------");


                while (results.next()) {
                    int assignmentId = results.getInt("assignment_id");

                    String
                    course = results.getString("course_name");
                String title = results.getString("title");
                String dueDate = results.getString("due_date");
                boolean completed = results.getBoolean("completed");


                    System.out.println("Assignment ID: " + assignmentId);

                System.out.println("Course: " + course);
                System.out.println("Assignment: " + title);
                System.out.println("Due Date: " + dueDate);
                System.out.println(

            "Completed: " + completed);
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

        else if (choice == 2) {

            System.out.print("Enter assignment ID: ");
            int assignmentId = scanner.nextInt();

            AssignmentManager.markCompleted(assignmentId);

            System.out.println("Returning to program...");
        }


            else if (choice == 3) {

                System.out.print("Enter course ID: ");
                int courseId = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter assignment title: ");
                String title = scanner.nextLine();

                System.out.print("Enter due date (YYYY-MM-DD): ");
                String dateInput = scanner.nextLine();

                LocalDate dueDate = LocalDate.parse(dateInput);

                AssignmentManager.addAssignment(courseId, title, dueDate);
            }


            else if (choice == 4) {

                String sql = """
            SELECT assignments.assignment_id,
                   courses.course_name,
                   assignments.title,
                   assignments.due_date,
                   assignments.completed
            FROM courses
            INNER JOIN assignments
            ON courses.course_id = assignments.course_id
            WHERE assignments.completed = FALSE
            ORDER BY assignments.due_date
            """;

                try (Connection connection = DatabaseConnection.getConnection();
                     Statement statement = connection.createStatement();
                     ResultSet results = statement.executeQuery(sql)) {

                    System.out.println("Pending Assignments");
                    System.out.println("-------------------");

                    boolean found = false;

                    while (results.next()) {
                        found = true;

                        int assignmentId = results.getInt("assignment_id");
                        String course = results.getString("course_name");
                        String title = results.getString("title");
                        String dueDate = results.getString("due_date");

                        System.out.println("Assignment ID: " + assignmentId);
                        System.out.println("Course: " + course);
                        System.out.println("Assignment: " + title);
                        System.out.println("Due Date: " + dueDate);
                        System.out.println();
                    }

                    if (!found) {
                        System.out.println("No pending assignments!");
                    }

                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }


            }


            else if (choice == 5) {
                System.out.println("Goodbye!");
                break;
            }


        }
}
}
