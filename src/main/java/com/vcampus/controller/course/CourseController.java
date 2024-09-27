package com.vcampus.controller.course;

import com.vcampus.func.client.CourseSend;
import com.vcampus.dao.CourseDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.EventHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import com.vcampus.pojo.CoursePojo;

public class CourseController {
    @FXML
    private TableView<CoursePojo> courseTableView;

    @FXML
    private Button addCourseButton;

    @FXML
    private void initialize() throws IOException {
        // 当FXML文件被加载时，这个方法会被自动调用（如果它存在的话）
        // 你可以在这里设置初始的文本，但也可以在需要的时候从其他方法中调用

        TableColumn<CoursePojo, String> courseIdColumn = new TableColumn<>("课程编号");
        courseIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseId()));

        TableColumn<CoursePojo, String> courseNameColumn = new TableColumn<>("课程名称");
        courseNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseName()));

        TableColumn<CoursePojo, String> courseTeacherColumn = new TableColumn<>("授课教师");
        courseTeacherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseTeacher()));

        TableColumn<CoursePojo, String> courseCreditColumn = new TableColumn<>("课程学分");
        courseCreditColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseCredit()));

        TableColumn<CoursePojo, String> courseTimeColumn = new TableColumn<>("课程时间");
        courseTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseTime()));

        TableColumn<CoursePojo, String> courseLocationColumn = new TableColumn<>("课程地点");
        courseLocationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseLocation()));

        TableColumn<CoursePojo, String> courseCapacityColumn = new TableColumn<>("课程容量");
        courseCapacityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseCapacity()));

        TableColumn<CoursePojo, String> coursePeopleNumberColumn = new TableColumn<>("已选人数");
        coursePeopleNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoursePeopleNumber()));

        //修改按钮
        TableColumn<CoursePojo, Void> updateCourseColumn = new TableColumn<>("修改信息");
        updateCourseColumn.setCellFactory(col -> new TableCell<CoursePojo, Void>() {
            final Button cellButton = new Button("修改");
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setGraphic(cellButton);
                    cellButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // 处理按钮点击事件
                            int rowIndex = getIndex();
                            updateCourse(rowIndex);
                        }
                    });
                } else {
                    setGraphic(null); // 如果没有数据，则不显示按钮
                }
            }
        });

        //删除按钮
        TableColumn<CoursePojo, Void> deleteCourseColumn = new TableColumn<>("删除课程");
        deleteCourseColumn.setCellFactory(col -> new TableCell<CoursePojo, Void>() {
            final Button cellButton = new Button("删除");
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setGraphic(cellButton);
                    cellButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // 处理按钮点击事件
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("确认");
                            alert.setHeaderText("确认删除");
                            alert.setContentText("您确定要删除该课程吗？");

                            // 显示对话框并等待用户响应
                            Optional<ButtonType> result = alert.showAndWait();

                            // 处理用户响应
                            if (result.get() == ButtonType.OK) {
                                // 用户点击了确认，执行删除操作
                                int rowIndex = getIndex();
                                if (rowIndex != -1 && getTableView() != null && getTableView().getItems() != null) {
                                    CoursePojo temp = getTableView().getItems().get(rowIndex);
                                    String tempId = temp.getCourseId();
                                    getTableView().getItems().remove(rowIndex);
                                    try {
                                        CourseSend.deleteCourse(tempId);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    });
                } else {
                    setGraphic(null); // 如果没有数据，则不显示按钮
                }
            }
        });

        courseTableView.getColumns().addAll(courseIdColumn, courseNameColumn, courseTeacherColumn, courseCapacityColumn, courseCreditColumn, coursePeopleNumberColumn, courseTimeColumn, courseLocationColumn, updateCourseColumn,deleteCourseColumn);
        // 初始化表格数据
        ObservableList<CoursePojo> data = FXCollections.observableArrayList();

        CoursePojo[] temp = CourseSend.showAllCourses();
        Collections.addAll(data, temp);
        courseTableView.setItems(data);
    }

    @FXML
    private void addCourse() {
        try {
            // 加载FXML文件

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/Course/AddCourseView.fxml"));
            Parent root = loader.load(); // 这将抛出异常，如果FXML文件有错误

            // 创建新的Stage
            Stage stage = new Stage();

            // 设置Stage的标题和场景
            stage.setTitle("新增课程");
            stage.setScene(new Scene(root));

            // 如果newWindow.fxml有Controller，并且你需要访问它，可以使用loader.getController()
            AddCourseController controller = loader.getController();
            controller.setPrimaryStage(stage);
            controller.setCourseList(courseTableView.getItems());

            // 显示Stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // 错误处理
        }
    }

    private void updateCourse(int rowIndex) {
        try {
            // 加载FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/Course/AddCourseView.fxml"));
            Parent root = loader.load(); // 这将抛出异常，如果FXML文件有错误
            // 创建新的Stage
            Stage stage = new Stage();

            // 设置Stage的标题和场景
            stage.setTitle("修改信息");
            stage.setScene(new Scene(root));

            // 如果newWindow.fxml有Controller，并且你需要访问它，可以使用loader.getController()
            AddCourseController controller = loader.getController();
            controller.setPrimaryStage(stage);
            controller.setCourseList(courseTableView.getItems());
            controller.showCourseInfo(rowIndex);
            controller.confirmButton.setOnAction(event -> {
                String id = controller.idText.getText();
                String name = controller.nameText.getText();
                String teacher = controller.teacherText.getText();
                String credit = controller.creditText.getText();
                String time = controller.timeText.getText();
                String location = controller.locationText.getText();
                String capacity = controller.capacityText.getText();
                if(id.trim().isEmpty()||name.trim().isEmpty()||teacher.trim().isEmpty()||credit.trim().isEmpty()||time.trim().isEmpty()||location.trim().isEmpty()||capacity.trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("请输入正确的课程信息！");
                    alert.showAndWait();
                }
                else {
                    CoursePojo coursePojo =courseTableView.getItems().get(rowIndex);
                    CoursePojo newCoursePojo = new CoursePojo(id, name, teacher, credit, time, location, capacity, coursePojo.getCoursePeopleNumber());
                    courseTableView.getItems().set(rowIndex, newCoursePojo);
                    try {
                        CourseSend.updateCourse(newCoursePojo);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    controller.closeStage();
                }
            });
            // 显示Stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // 错误处理
        }
    }
}