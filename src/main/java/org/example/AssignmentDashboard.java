
package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

        Label assignmentsTitle = new Label("All Assignments");

        assignmentsTitle.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1E40AF;"
        );


        TableView<Assignment> assignmentsTable = new TableView<>();

        TableColumn<Assignment, String> assignmentColumn =
                new TableColumn<>("Assignment");

        assignmentColumn.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );

        TableColumn<Assignment, Integer> idColumn =
                new TableColumn<>("ID");
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<Assignment, String> courseColumn =
                new TableColumn<>("Course");
        courseColumn.setCellValueFactory(
                new PropertyValueFactory<>("course")
        );

        TableColumn<Assignment, String> dueDateColumn =
                new TableColumn<>("Due Date");
        dueDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("dueDate")
        );

        TableColumn<Assignment, Boolean> completedColumn =
                new TableColumn<>("Completed");
        completedColumn.setCellValueFactory(
                new PropertyValueFactory<>("completed")
        );

        completedColumn.setCellFactory(column -> new javafx.scene.control.TableCell<Assignment, Boolean>() {
            @Override
            protected void updateItem(Boolean completed, boolean empty) {
                super.updateItem(completed, empty);


                if (empty || completed == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(completed ? "Completed" : "Pending");

                    if (completed) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }

            }
        });




        assignmentsTable.getColumns().addAll(
                idColumn,
                courseColumn,
                assignmentColumn,
                dueDateColumn,
                completedColumn
        );

        assignmentsTable.setPrefHeight(200);

        assignmentsTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );


        ObservableList<Assignment> assignmentData =
                FXCollections.observableArrayList();

        String assignmentsSql = """
        SELECT assignments.assignment_id,
               courses.course_name,
               assignments.title,
               assignments.due_date,
               assignments.completed
        FROM assignments
        INNER JOIN courses
        ON assignments.course_id = courses.course_id
        ORDER BY assignments.assignment_id
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(assignmentsSql)) {

            while (results.next()) {
                Assignment assignment = new Assignment(
                        results.getInt("assignment_id"),
                        results.getString("course_name"),
                        results.getString("title"),
                        results.getString("due_date"),
                        results.getBoolean("completed")
                );

                assignmentData.add(assignment);
            }

        } catch (Exception e) {
            System.out.println("Error loading assignments: "
                    + e.getMessage());
        }

        assignmentsTable.setItems(assignmentData);


        layout.getChildren().addAll(
                assignmentsTitle,
                assignmentsTable
        );








        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        layout.setStyle("-fx-padding: 30px;");

        Scene scene = new Scene(scrollPane, 1200, 600);



        primaryStage.setTitle("Student Assignment Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
