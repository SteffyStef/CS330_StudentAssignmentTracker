
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_assignment_tracker";

    public static Connection getConnection() throws SQLException {
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (username == null || password == null) {
            throw new SQLException("Database credentials are not configured.");
        }

        return DriverManager.getConnection(URL, username, password);
    }
}
