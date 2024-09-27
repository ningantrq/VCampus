package com.vcampus.controller.course;

import com.vcampus.controller.TempUser;
import com.vcampus.func.client.CourseSend;
import com.vcampus.func.client.StudentSend;
import com.vcampus.pojo.StudentInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class TeacherCourseController {
    @FXML
    private TableView<CoursePojo> courseTableView;

    private String teacherName;
    public void setTeacherName() throws IOException {
        String teacherId= TempUser.getTUser().getuId();
        this.teacherName = StudentSend.getById(teacherId).getName();;
    }

    @FXML
    private void initialize() throws IOException {
        // 当FXML文件被加载时，这个方法会被自动调用（如果它存在的话）
        // 你可以在这里设置初始的文本，但也可以在需要的时候从其他方法中调用
        setTeacherName();

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

        courseTableView.getColumns().addAll(courseIdColumn, courseNameColumn, courseTeacherColumn, courseCapacityColumn, courseCreditColumn, coursePeopleNumberColumn, courseTimeColumn, courseLocationColumn);
        // 初始化表格数据
        ObservableList<CoursePojo> data = FXCollections.observableArrayList();

        CoursePojo[] temp = CourseSend.showTeacherCourses(teacherName);
        Collections.addAll(data, temp);
        courseTableView.setItems(data);
    }
}

