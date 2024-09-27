package com.vcampus.controller.course;

import com.vcampus.controller.TempUser;
import com.vcampus.func.client.CourseSend;
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
import java.util.*;

import com.vcampus.pojo.CoursePojo;

public class ChooseCourseController {
    @FXML
    private TableView<CoursePojo> courseTableView;

    @FXML
    private Button addCourseButton;

    private String studentId;

    public void setStudentId() {
        this.studentId=TempUser.getTUser().getuId();
    }

    public boolean[] isChosen = new boolean[100];

    @FXML
    private void initialize() throws IOException {
        // 当FXML文件被加载时，这个方法会被自动调用（如果它存在的话）
        // 传入所有课程
        setStudentId();
        CoursePojo[] temp = CourseSend.showAllCourses();

        //判断课程中是否有已选的课
        CoursePojo[] chosenCourse =CourseSend.showChooseCourses(studentId);
        String[] chosenId = new String[chosenCourse.length];
        for (int i = 0; i < chosenCourse.length; i++) {
            chosenId[i] = chosenCourse[i].getCourseId();
        }
        if(chosenCourse==null)System.out.println(0);
        else {
            Set<String> cSet = new HashSet<>();
            cSet.addAll(Arrays.asList(chosenId));
            // 遍历x数组，对每个元素进行标记
            if (!cSet.isEmpty()) {
                for (int i = 0; i < temp.length; i++) {
                    if (cSet.contains(temp[i].getCourseId())) {
                        isChosen[i] = true; // 如果在chosenCourse中存在，则标记为t
                    } else {
                        isChosen[i] = false; // 否则标记为f
                    }
                }
            }
        }

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

        //选课按钮
        TableColumn<CoursePojo, Void> ChooseCourseColumn = new TableColumn<>("选课");
        ChooseCourseColumn.setCellFactory(col -> new TableCell<CoursePojo, Void>() {
            final Button cellButton = new Button();
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setGraphic(cellButton);
                    int rowIndex = getIndex();
                    if(isChosen[rowIndex])
                        cellButton.setText("退选");
                    else cellButton.setText("选课");
                    cellButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // 处理按钮点击事件
                            if ("选课".equals(cellButton.getText())) {
                                // 执行选课操作
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("确认");
                                alert.setHeaderText("确认选课");
                                alert.setContentText("您确定要选择该课程吗？");
                                // 显示对话框并等待用户响应
                                Optional<ButtonType> result = alert.showAndWait();

                                // 处理用户响应
                                if (result.get() == ButtonType.OK) {
                                int rowIndex = getIndex();
                                CoursePojo temp = getTableView().getItems().get(rowIndex);
                                int chooseCase;//1为时间冲突，2为课容量冲突，0为选课成功
                                try {
                                    chooseCase=CourseSend.chooseCourse(temp,studentId);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if(chooseCase==1) {
                                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                    alert2.setContentText("课程时间与已选课程时间冲突！");
                                    alert2.showAndWait();
                                }
                                if(chooseCase==2) {
                                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                    alert2.setContentText("课容量已满！");
                                    alert2.showAndWait();
                                }
                                // 切换按钮标签为退课
                                if (chooseCase==0) {
                                    try {
                                        CourseSend.chooseCoursePN(temp);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    int numpn = Integer.parseInt(temp.getCoursePeopleNumber());
                                    numpn+=1;
                                    String strpn = Integer.toString(numpn);
                                    temp.setCoursePeopleNumber(strpn);
                                    isChosen[rowIndex]=true;
                                    getTableView().getItems().set(rowIndex, temp);
                                    Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                                    alert4.setContentText("选课成功！");
                                    alert4.showAndWait();
                                }
                                }
                            } else {
                                // 执行退课操作
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("确认");
                                alert.setHeaderText("确认退选");
                                alert.setContentText("您确定要退选该课程吗？");

                                // 显示对话框并等待用户响应
                                Optional<ButtonType> result = alert.showAndWait();

                                // 处理用户响应
                                if (result.get() == ButtonType.OK) {
                                int rowIndex = getIndex();
                                CoursePojo temp = getTableView().getItems().get(rowIndex);
                                String courseId=temp.getCourseId();
                                try {
                                    CourseSend.dropCourse(courseId,studentId);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    CourseSend.dropCoursePN(temp);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                int numpn = Integer.parseInt(temp.getCoursePeopleNumber());
                                numpn-=1;
                                String strpn = Integer.toString(numpn);
                                temp.setCoursePeopleNumber(strpn);
                                    isChosen[rowIndex]=false;
                                getTableView().getItems().set(rowIndex, temp);
                                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                alert3.setContentText("退选成功！");
                                alert3.showAndWait();
                                getTableView().getItems().set(rowIndex, temp);
                                }
                            }
                        }
                    });
                } else {
                    setGraphic(null); // 如果没有数据，则不显示按钮
                }
            }
        });

        courseTableView.getColumns().addAll(courseIdColumn, courseNameColumn, courseTeacherColumn, courseCapacityColumn, courseCreditColumn, coursePeopleNumberColumn, courseTimeColumn, courseLocationColumn, ChooseCourseColumn);
        // 初始化表格数据，显示所有课程
        ObservableList<CoursePojo> data = FXCollections.observableArrayList();
        Collections.addAll(data, temp);
        courseTableView.setItems(data);
    }

    @FXML
    private void courseTable() {
        try {
            // 加载FXML文件

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/Course/CourseTableView.fxml"));
            Parent root = loader.load(); // 这将抛出异常，如果FXML文件有错误

            // 创建新的Stage
            Stage stage = new Stage();

            // 设置Stage的标题和场景
            stage.setTitle("我的课表");
            stage.setScene(new Scene(root));

            // 显示Stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // 错误处理
        }
    }
}