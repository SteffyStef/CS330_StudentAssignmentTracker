
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}

