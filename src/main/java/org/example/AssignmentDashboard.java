
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





        Scene scene = new Scene(layout, 1200, 600);


        primaryStage.setTitle("Student Assignment Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
