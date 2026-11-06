
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Date;
import java.time.LocalDate;


public class AssignmentManager {

    public static void markCompleted(int assignmentId) {

        String sql = """
                UPDATE assignments
                SET completed = TRUE
                WHERE assignment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, assignmentId);

            int rowsUpdated = statement.executeUpdate();

            System.out.println(
                    rowsUpdated + " assignment(s) marked completed."
            );

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    public static void addAssignment(int courseId, String title,
                                     LocalDate dueDate) {

        String sql = """
            INSERT INTO assignments
                (course_id, title, due_date, completed)
            VALUES (?, ?, ?, FALSE)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, courseId);
            statement.setString(2, title);
            statement.setDate(3, Date.valueOf(dueDate));

            int rowsInserted = statement.executeUpdate();

            System.out.println(
                    rowsInserted + " assignment(s) added successfully."
            );

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

}

