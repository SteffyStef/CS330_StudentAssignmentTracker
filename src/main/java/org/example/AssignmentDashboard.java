
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
import java.sql.PreparedStatement;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import java.time.LocalDate;

public class AssignmentDashboard extends Application {
    private Label totalAssignments;
    private Label completedAssignments;
    private Label pendingAssignments;
    private Label totalCourses;
    private VBox courseReport;
    private ObservableList<Assignment> assignmentData;
    private String currentFilter = "All Assignments";
    private String currentSort = "Assignment ID";

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


        totalAssignments = new Label("TOTAL ASSIGNMENTS: --");

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


        completedAssignments = new Label("COMPLETED: --");

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


        pendingAssignments = new Label("PENDING: --");

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


        totalCourses = new Label("TOTAL COURSES: --");

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


        courseReport = new VBox(10);
        courseReport.setAlignment(Pos.CENTER);

        courseReport.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 25px;" +
                        "-fx-border-color: #D1D5DB;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;"
        );

        courseReport.setMinWidth(400);




        layout.getChildren().add(courseReport);
        loadCourseReport();

        Label assignmentsTitle = new Label("All Assignments");

        assignmentsTitle.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1E40AF;"
        );

        ComboBox<String> filterComboBox = new ComboBox<>();

        filterComboBox.getItems().addAll(
                "All Assignments",
                "Pending Only",
                "Completed Only"
        );

        filterComboBox.setValue("All Assignments");
        filterComboBox.setOnAction(event -> {
            currentFilter = filterComboBox.getValue();
            loadAssignments();
        });
        filterComboBox.setPrefWidth(200);

        layout.getChildren().add(filterComboBox);
        ComboBox<String> sortComboBox = new ComboBox<>();

        sortComboBox.getItems().addAll(
                "Assignment ID",
                "Due Date (Earliest First)",
                "Due Date (Latest First)"
        );

        sortComboBox.setValue("Assignment ID");
        sortComboBox.setOnAction(event -> {
            currentSort = sortComboBox.getValue();
            loadAssignments();
        });
        sortComboBox.setPrefWidth(220);

        layout.getChildren().add(sortComboBox);
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


        assignmentData = FXCollections.observableArrayList();


        loadAssignments();
        assignmentsTable.setItems(assignmentData);


        layout.getChildren().addAll(
                assignmentsTitle,
                assignmentsTable
        );
        Button markCompletedButton = new Button("✓ Mark Completed");

        markCompletedButton.setStyle(
                "-fx-background-color: #16A34A;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-background-radius: 8px;"
        );

        layout.getChildren().add(markCompletedButton);

        Button deleteAssignmentButton = new Button("Delete Assignment");

        deleteAssignmentButton.setStyle(
                "-fx-background-color: #DC2626;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-background-radius: 8px;"
        );

        layout.getChildren().add(deleteAssignmentButton);
        deleteAssignmentButton.setOnAction(event -> {
            Assignment selectedAssignment =
                    assignmentsTable.getSelectionModel().getSelectedItem();

            if (selectedAssignment == null) {
                new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING,
                        "Please select an assignment to delete."
                ).showAndWait();
                return;
            }

            javafx.scene.control.Alert confirmation =
                    new javafx.scene.control.Alert(
                            javafx.scene.control.Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to delete this assignment?",
                            javafx.scene.control.ButtonType.YES,
                            javafx.scene.control.ButtonType.NO
                    );

            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Delete Assignment");

            java.util.Optional<javafx.scene.control.ButtonType> result =
                    confirmation.showAndWait();

            if (result.isEmpty() ||
                    result.get() != javafx.scene.control.ButtonType.YES) {
                return;
            }

            int assignmentId = selectedAssignment.getId();

            AssignmentManager.deleteAssignment(assignmentId);

            loadAssignments();
            refreshStatistics();
            loadCourseReport();

        });

        markCompletedButton.setOnAction(event -> {
            Assignment selectedAssignment =
                    assignmentsTable.getSelectionModel().getSelectedItem();

            if (selectedAssignment == null) {
                new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING,
                        "Please select an assignment first."
                ).showAndWait();
                return;
            }
            if (selectedAssignment.isCompleted()) {
                new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION,
                        "This assignment is already completed."
                ).showAndWait();
                return;
            }

            int assignmentId = selectedAssignment.getId();

            AssignmentManager.markCompleted(assignmentId);

            loadAssignments();
            refreshStatistics();
            loadCourseReport();
        });

        Button editAssignmentButton = new Button("✎ Edit Assignment");

        editAssignmentButton.setStyle(
                "-fx-background-color: #F59E0B;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-background-radius: 8px;"
        );

        layout.getChildren().add(editAssignmentButton);
        editAssignmentButton.setOnAction(event -> {
            Assignment selectedAssignment =
                    assignmentsTable.getSelectionModel().getSelectedItem();

            if (selectedAssignment == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Assignment Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select an assignment to edit.");
                alert.showAndWait();
                return;
            }
            Dialog<ButtonType> editDialog = new Dialog<>();
            editDialog.setTitle("Edit Assignment");
            editDialog.setHeaderText("Update Assignment Details");

            TextField editTitleField = new TextField(selectedAssignment.getTitle());

            DatePicker editDueDatePicker = new DatePicker();

            if (selectedAssignment.getDueDate() != null &&
                    !selectedAssignment.getDueDate().isEmpty()) {
                editDueDatePicker.setValue(
                        LocalDate.parse(selectedAssignment.getDueDate())
                );
            }

            VBox editForm = new VBox(10);
            editForm.getChildren().addAll(
                    new Label("Assignment Title:"),
                    editTitleField,
                    new Label("Due Date:"),
                    editDueDatePicker
            );

            editDialog.getDialogPane().setContent(editForm);

            editDialog.getDialogPane().getButtonTypes().addAll(
                    ButtonType.OK,
                    ButtonType.CANCEL
            );

            editDialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    String newTitle = editTitleField.getText().trim();
                    LocalDate newDueDate = editDueDatePicker.getValue();

                    if (newTitle.isEmpty() || newDueDate == null) {
                        new Alert(
                                Alert.AlertType.WARNING,
                                "Please enter a title and select a due date."
                        ).showAndWait();
                        return;
                    }

                    String updateSql = """
                UPDATE assignments
                SET title = ?, due_date = ?
                WHERE assignment_id = ?
                """;

                    try (Connection connection = DatabaseConnection.getConnection();
                         PreparedStatement statement =
                                 connection.prepareStatement(updateSql)) {

                        statement.setString(1, newTitle);
                        statement.setDate(2, java.sql.Date.valueOf(newDueDate));
                        statement.setInt(3, selectedAssignment.getId());

                        int rowsUpdated = statement.executeUpdate();

                        if (rowsUpdated > 0) {
                            loadAssignments();
                            refreshStatistics();
                            loadCourseReport();

                            new Alert(
                                    Alert.AlertType.INFORMATION,
                                    "Assignment updated successfully!"
                            ).showAndWait();
                        }

                    } catch (Exception e) {
                        new Alert(
                                Alert.AlertType.ERROR,
                                "Error updating assignment: " + e.getMessage()
                        ).showAndWait();
                    }
                }
            });
        });

        Button addAssignmentButton = new Button("+ Add Assignment");

        addAssignmentButton.setStyle(
                "-fx-background-color: #2563EB;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-background-radius: 8px;"
        );

        layout.getChildren().add(addAssignmentButton);

        VBox assignmentForm = new VBox(10);
        assignmentForm.setAlignment(Pos.CENTER);
        assignmentForm.setVisible(false);
        assignmentForm.setManaged(false);

        Label formTitle = new Label("New Assignment");
        formTitle.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
        );

        TextField assignmentTitleField = new TextField();
        assignmentTitleField.setPromptText("Enter assignment title");
        assignmentTitleField.setMaxWidth(350);

        ComboBox<String> courseComboBox = new ComboBox<>();

        courseComboBox.setPromptText("Select a course");
        courseComboBox.setMaxWidth(350);

        courseComboBox.getItems().add("CS330 Databases");

        DatePicker dueDatePicker = new DatePicker();

        dueDatePicker.setPromptText("Select due date");
        dueDatePicker.setMaxWidth(350);



        Button saveAssignmentButton = new Button("Save Assignment");

        saveAssignmentButton.setStyle(
                "-fx-background-color: #16A34A;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-background-radius: 8px;"
        );

        Button cancelAssignmentButton = new Button("Cancel");

        cancelAssignmentButton.setStyle(
                "-fx-background-color: #6B7280;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-background-radius: 8px;"
        );

        HBox buttonRow = new HBox(10);
        buttonRow.setAlignment(Pos.CENTER);

        buttonRow.getChildren().addAll(
                saveAssignmentButton,
                cancelAssignmentButton
        );

        assignmentForm.getChildren().addAll(
                formTitle,
                assignmentTitleField,
                courseComboBox,
                dueDatePicker,
                buttonRow
        );




        layout.getChildren().add(assignmentForm);

        addAssignmentButton.setOnAction(event -> {
            assignmentForm.setVisible(true);
            assignmentForm.setManaged(true);
        });

        cancelAssignmentButton.setOnAction(event -> {
            assignmentTitleField.clear();
            courseComboBox.setValue(null);
            dueDatePicker.setValue(null);

            assignmentForm.setVisible(false);
            assignmentForm.setManaged(false);
        });


        saveAssignmentButton.setOnAction(event -> {
            String assignmentTitle = assignmentTitleField.getText().trim();
            String selectedCourse = courseComboBox.getValue();
            java.time.LocalDate dueDate = dueDatePicker.getValue();

            if (assignmentTitle.isEmpty() || selectedCourse == null || dueDate == null) {
                new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING,
                        "Please enter a title, select a course, and choose a due date."
                ).showAndWait();
                return;
            }

            int courseId = 1; // Temporary: CS330 Databases

            AssignmentManager.addAssignment(courseId, assignmentTitle, dueDate);
            loadAssignments();
            refreshStatistics();
            loadCourseReport();

            assignmentTitleField.clear();
            courseComboBox.setValue(null);
            dueDatePicker.setValue(null);

            assignmentForm.setVisible(false);
            assignmentForm.setManaged(false);
        });






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
    private void loadAssignments() {
        assignmentData.clear();

        String assignmentsSql = """
        SELECT assignments.assignment_id,
               courses.course_name,
               assignments.title,
               assignments.due_date,
               assignments.completed
        FROM assignments
        INNER JOIN courses
        ON assignments.course_id = courses.course_id
        """;

        if (currentFilter.equals("Pending Only")) {
            assignmentsSql += " WHERE assignments.completed = FALSE";
        } else if (currentFilter.equals("Completed Only")) {
            assignmentsSql += " WHERE assignments.completed = TRUE";
        }

        if (currentSort.equals("Due Date (Earliest First)")) {
            assignmentsSql += " ORDER BY assignments.due_date ASC, assignments.assignment_id ASC";
        } else if (currentSort.equals("Due Date (Latest First)")) {
            assignmentsSql += " ORDER BY assignments.due_date DESC, assignments.assignment_id ASC";
        } else {
            assignmentsSql += " ORDER BY assignments.assignment_id ASC";
        }

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


    }
    private void refreshStatistics() {
        String sql = "SELECT COUNT(*) AS total FROM assignments";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql)) {

            if (results.next()) {
                int total = results.getInt("total");
                totalAssignments.setText("TOTAL ASSIGNMENTS: " + total);
            }

        } catch (Exception e) {
            System.out.println("Error refreshing statistics: " + e.getMessage());
        }
        String completedSql = """
        SELECT COUNT(*) AS total
        FROM assignments
        WHERE completed = TRUE
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(completedSql)) {

            if (results.next()) {
                int completed = results.getInt("total");
                completedAssignments.setText("COMPLETED: " + completed);
            }

        } catch (Exception e) {
            System.out.println("Error refreshing completed count: "
                    + e.getMessage());
        }
        String pendingSql = """
        SELECT COUNT(*) AS total
        FROM assignments
        WHERE completed = FALSE
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(pendingSql)) {

            if (results.next()) {
                int pending = results.getInt("total");
                pendingAssignments.setText("PENDING: " + pending);
            }

        } catch (Exception e) {
            System.out.println("Error refreshing pending count: "
                    + e.getMessage());
        }
        String coursesSql = """
        SELECT COUNT(*) AS total
        FROM courses
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(coursesSql)) {

            if (results.next()) {
                int courses = results.getInt("total");
                totalCourses.setText("TOTAL COURSES: " + courses);
            }

        } catch (Exception e) {
            System.out.println("Error refreshing courses count: "
                    + e.getMessage());
        }
    }

    private void loadCourseReport() {
        courseReport.getChildren().clear();

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
        }
}
