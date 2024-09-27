package com.vcampus.controller.course;

import com.vcampus.controller.TempUser;
import com.vcampus.func.client.CourseSend;
import com.vcampus.pojo.CoursePojo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CourseTableController {

    @FXML
    private GridPane timetableGrid;

    private String studentId;

    public void setStudentId() {
        this.studentId= TempUser.getTUser().getUId();
    }

    private List<CoursePojo> courses ;

    // 初始化方法，可以在FXML加载后自动调用
    @FXML
    private void initialize() throws IOException {
        // 调用添加课程信息的方法
        setStudentId();
        addCoursesToTable();
    }

    // 动态添加课程信息到GridPane的方法
    private void addCoursesToTable() throws IOException {
//        timetableGrid.setStyle("-fx-border-color: black; " +
//                        "-fx-border-width: 1; " +
//                        "-fx-grid-lines-visible: true;");
        CoursePojo[] temp=CourseSend.showChooseCourses(studentId);
        courses = Arrays.asList(temp);
        for(int i=1;i<=13;++i)
        {
            Label label = new Label("第"+i+"节");
            GridPane.setRowIndex(label, i);
            GridPane.setColumnIndex(label, 0);
            timetableGrid.getChildren().add(label);
        }
        int index=0;
        for (CoursePojo course : courses) {
            // 创建Label来显示课程信息
            Label courseLabel = new Label(course.getCourseName() +"\n周" + course.getCourseDate() + " " + course.getCourseStart()+"-"+course.getCourseEnd());

            // 设置Label在GridPane中的位置
            int day=Integer.parseInt(course.getCourseDate());
            int start = Integer.parseInt(course.getCourseStart());
            int end = Integer.parseInt(course.getCourseEnd());

            //设置颜色数组
            Color[] colors = {
                    Color.rgb(255, 153, 153), // 淡红色 (RGB: 255, 153, 153)
                    Color.rgb(173, 255, 173), // 淡绿色 (RGB: 173, 255, 173)
                    Color.rgb(173, 173, 255), // 淡蓝色 (RGB: 173, 173, 255)
                    Color.rgb(255, 255, 204), // 淡黄色 (RGB: 255, 255, 204)
                    Color.rgb(204, 153, 255), // 淡紫色 (RGB: 204, 153, 255)
                    Color.rgb(255, 204, 153), // 淡橙色 (RGB: 255, 204, 153)
                    Color.rgb(173, 255, 255), // 淡青色 (一个更淡的青色版本，因为标准的`Color.CYAN`已经是青色了)
                    Color.rgb(255, 153, 255), // 淡品红色 (一个更淡的品红色版本，因为标准的`Color.MAGENTA`已经是品红色了)
            };
            Color color = colors[index % colors.length];
            //设置Region
            Region colorRegion = new Region();
            // 设置Region的大小（根据需要调整）
            colorRegion.setPrefSize(100, 30);
            // 设置Region的背景色
            colorRegion.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
            timetableGrid.add(colorRegion, day, start,1,end-start+1);

            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(courseLabel);
            timetableGrid.add(stackPane, day, start, 1, end-start+1);
            index++;
//            GridPane.setRowIndex(courseLabel, start);
//            GridPane.setColumnIndex(courseLabel, day);
//            // 将Label添加到GridPane中
//            timetableGrid.getChildren().add(courseLabel);
        }
    }
}