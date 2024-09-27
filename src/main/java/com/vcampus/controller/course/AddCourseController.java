package com.vcampus.controller.course;

import com.vcampus.func.client.CourseSend;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import com.vcampus.pojo.CoursePojo;

import java.io.IOException;
import java.sql.SQLException;

public class AddCourseController {
    private Stage primaryStage;

    // 设置Stage的引用
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // 关闭Stage的方法
    public void closeStage() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }

    private ObservableList<CoursePojo> coursePojoList; // 这应该是TableView的数据源
    // 构造函数或其他方式来设置courseList
    public void setCourseList(ObservableList<CoursePojo> coursePojoList) {
        this.coursePojoList = coursePojoList;
    }

    @FXML
    public TextField idText;

    @FXML
    public TextField nameText;

    @FXML
    public TextField teacherText;

    @FXML
    public TextField creditText;

    @FXML
    public TextField timeText;

    @FXML
    public TextField locationText;

    @FXML
    public TextField capacityText;

    @FXML
    public Button confirmButton;

    @FXML
    private void confirm() throws SQLException, IOException, InterruptedException {
        String id = idText.getText();
        String name = nameText.getText();
        String teacher = teacherText.getText();
        String credit = creditText.getText();
        String time = timeText.getText();
        String location = locationText.getText();
        String capacity = capacityText.getText();
        if(id.trim().isEmpty()||name.trim().isEmpty()||teacher.trim().isEmpty()||credit.trim().isEmpty()||time.trim().isEmpty()||location.trim().isEmpty()||capacity.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("请输入正确的课程信息！");
            alert.showAndWait();
        }
        else {
            CoursePojo newCoursePojo = new CoursePojo(id, name, teacher, credit, time, location, capacity, "0");
            CourseSend.addCourse(id,name,teacher,capacity,"0", newCoursePojo.getCourseDate(), newCoursePojo.getCourseStart(), newCoursePojo.getCourseEnd(), location,credit);
            coursePojoList.add(newCoursePojo);
            closeStage();
        }
    }

    @FXML
    private void cancel() {
        closeStage();
    }

    public void showCourseInfo(int rowIndex) {
        CoursePojo coursePojo = coursePojoList.get(rowIndex);
        idText.setText(coursePojo.getCourseId());
        nameText.setText(coursePojo.getCourseName());
        teacherText.setText(coursePojo.getCourseTeacher());
        creditText.setText(coursePojo.getCourseCredit());
        timeText.setText(coursePojo.getCourseTime());
        locationText.setText(coursePojo.getCourseLocation());
        capacityText.setText(coursePojo.getCourseCapacity());
    }

}
