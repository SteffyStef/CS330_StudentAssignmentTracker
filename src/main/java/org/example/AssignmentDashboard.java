
package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AssignmentDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label title = new Label("Student Assignment Tracker");


        title.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1E40AF;"
        );


        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(title);


        Label totalAssignments = new Label("TOTAL ASSIGNMENTS: --");

        String sql = "SELECT COUNT(*) AS total FROM assignments";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql)) {

            if (results.next()) {
                int total = results.getInt("total");
                totalAssignments.setText("TOTAL ASSIGNMENTS: " + total);
            }

        } catch (Exception e) {
            System.out.println("Error loading total assignments: " + e.getMessage());
        }


        totalAssignments.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2563EB;" +
                        "-fx-background-color: white;" +
                        "-fx-padding: 25px;" +
                        "-fx-border-color: #D1D5DB;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;"
        );

        layout.getChildren().add(totalAssignments);


        Label completedAssignments = new Label("COMPLETED: --");

        String completedSql =
                "SELECT COUNT(*) AS total FROM assignments WHERE completed = TRUE";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(completedSql)) {

            if (results.next()) {
                int completed = results.getInt("total");
                completedAssignments.setText("COMPLETED: " + completed);
            }

        } catch (Exception e) {
            System.out.println("Error loading completed assignments: "
                    + e.getMessage());
        }


        Label pendingAssignments = new Label("PENDING: --");

        String pendingSql =
                "SELECT COUNT(*) AS total FROM assignments WHERE completed = FALSE";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(pendingSql)) {

            if (results.next()) {
                int pending = results.getInt("total");
                pendingAssignments.setText("PENDING: " + pending);
            }

        } catch (Exception e) {
            System.out.println("Error loading pending assignments: "
                    + e.getMessage());
        }


        Label totalCourses = new Label("TOTAL COURSES: --");

        String coursesSql = "SELECT COUNT(*) AS total FROM courses";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(coursesSql)) {

            if (results.next()) {
                int courses = results.getInt("total");
                totalCourses.setText("TOTAL COURSES: " + courses);
            }

        } catch (Exception e) {
            System.out.println("Error loading total courses: "
                    + e.getMessage());
        }


        String cardStyle =
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2563EB;" +
                        "-fx-background-color: white;" +
                        "-fx-padding: 25px;" +
                        "-fx-border-color: #D1D5DB;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;";

        completedAssignments.setStyle(cardStyle);
        pendingAssignments.setStyle(cardStyle);
        totalCourses.setStyle(cardStyle);


        HBox cardRow = new HBox(10);
        cardRow.setAlignment(Pos.CENTER);

        cardRow.getChildren().addAll(
                totalAssignments,
                completedAssignments,
                pendingAssignments,
                totalCourses
        );

        layout.getChildren().remove(totalAssignments);
        layout.getChildren().add(cardRow);


        Label courseReportTitle = new Label("Assignments by Course");

        courseReportTitle.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1E40AF;"
        );

        layout.getChildren().add(courseReportTitle);


        VBox courseReport = new VBox(10);
        courseReport.setAlignment(Pos.CENTER);

        courseReport.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 25px;" +
                        "-fx-border-color: #D1D5DB;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;"
        );

        courseReport.setMinWidth(400);


        String reportSql = """
        SELECT courses.course_name,
               COUNT(assignments.assignment_id) AS total_assignments
        FROM courses
        LEFT JOIN assignments
        ON courses.course_id = assignments.course_id
        GROUP BY courses.course_id, courses.course_name
        ORDER BY courses.course_name
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(reportSql)) {

            while (results.next()) {
                String courseName = results.getString("course_name");
                int count = results.getInt("total_assignments");

                Label courseLabel = new Label(
                        courseName + " — " + count + " assignments"
                );

                courseLabel.setStyle(
                        "-fx-font-size: 18px;" +
                                "-fx-text-fill: #374151;"
                );

                courseReport.getChildren().add(courseLabel);
            }

        } catch (Exception e) {
            System.out.println("Error loading course report: "
                    + e.getMessage());
        }

        layout.getChildren().add(courseReport);






        Scene scene = new Scene(layout, 1200, 600);


        primaryStage.setTitle("Student Assignment Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
