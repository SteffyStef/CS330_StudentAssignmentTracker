
package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Student Assignment Tracker");
            System.out.println("--------------------------");
            System.out.println("1. View assignments");
            System.out.println("2. Mark assignment completed");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            if (choice == 1) {


                String sql = """
        
                        SELECT courses.course_name,
               assignments.title,
               assignments.due_date,
               assignments.completed
        FROM courses
        INNER JOIN assignments
        ON courses.course_id = assignments.course_id
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
                String
                    course = results.getString("course_name");
                String title = results.getString("title");
                String dueDate = results.getString("due_date");
                boolean completed = results.getBoolean("completed");

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
                System.out.println("Goodbye!");
                break;
            }


        }
}
}
