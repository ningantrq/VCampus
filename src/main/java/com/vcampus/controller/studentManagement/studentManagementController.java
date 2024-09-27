package com.vcampus.controller.studentManagement;

import com.vcampus.func.client.StudentSend;
import com.vcampus.pojo.StudentInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.vcampus.func.client.StudentSend.loadStudentData;

public class studentManagementController {
    @FXML
    private TextField searchContext;

    @FXML
    private Button search;

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button modify;

    @FXML
    private Button display;

    @FXML
    private TableView<StudentInfo> tv;

    @FXML
    private TableColumn<StudentInfo, String> idColumn;

    @FXML
    private TableColumn<StudentInfo, String> roleColumn;

    @FXML
    private TableColumn<StudentInfo, String> nameColumn;

    @FXML
    private TableColumn<StudentInfo, String> sexColumn;

    @FXML
    private TableColumn<StudentInfo, Integer> dateColumn;

    @FXML
    private TableColumn<StudentInfo, Integer> ageColumn;

    @FXML
    private TableColumn<StudentInfo, String> academyColumn;

    private ObservableList<StudentInfo> studentList = FXCollections.observableArrayList();

    @FXML
    private void setUpTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("Role"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("Sex"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
        academyColumn.setCellValueFactory(new PropertyValueFactory<>("Academy"));
    }

    public void initialize() throws IOException {
        // 设置列的单元格工厂

        setUpTableColumns();
        // 加载数据到 TableView
        StudentInfo[] temp = loadStudentData();
        for (StudentInfo s : temp) {
            studentList.add(s);
        }
        tv.setItems(studentList);
    }

    @FXML
    public void enterAdd(MouseEvent event) {
//        ((Stage)add.getScene().getWindow()).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_add.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("添加学生信息");
            stage.setScene(new Scene(page));
            stage.showAndWait();

            loadAllStudent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() throws IOException {
        StudentInfo stu = tv.getSelectionModel().getSelectedItem();
        if (stu != null) {
            String id = stu.getID();
            studentList.remove(stu); // 从列表中删除
            StudentSend.deleteStudentData(id); // 从数据库中删除
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息界面");
            alert.setHeaderText("没有选中的学生");
            alert.showAndWait();        }
    }

    @FXML
    public void enterDelete(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/deleteComfirm.fxml"));
            AnchorPane page = loader.load();

            // 获取控制器并检查其是否为 null
            deleteComfirmController c = loader.getController();
            if (c != null) {
                c.setMainController(this); // 传递主控制器的引用
            } else {
                System.err.println("deleteComfirmController is null after loading FXML.");
            }

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("确认删除");
            stage.setScene(new Scene(page));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void enterModify(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_add.fxml"));
            AnchorPane page = loader.load();
            studentAddController c = loader.getController();
            c.OnlyRead();

            StudentInfo stu = tv.getSelectionModel().getSelectedItem();

            if (stu != null) {
//                ((Stage)modify.getScene().getWindow()).close();
                c.setModifyStudent(stu);
                c.setAge(stu.getAge());
                c.setName(stu.getName());
                c.setRole(stu.getRole());
                c.setYear(stu.getDate());
                c.setuId(stu.getID());
                c.setSex(stu.getSex());
                c.setAcda(stu.getAcademy());

                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("修改学生信息");
                stage.setScene(new Scene(page));
                stage.showAndWait();
                loadAllStudent();


            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息界面");
                alert.setHeaderText("未选中学生");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void search () throws IOException {
        String keyword = searchContext.getText().trim(); // 获取输入的搜索关键字并去除前后空格
        if (!keyword.isEmpty()) {
            // 清空当前列表
            studentList.clear();

            // 假设 StudentSend.getAllStudents() 返回所有学生的列表
            StudentInfo[] allStudents = StudentSend.loadStudentData();
            if (allStudents != null) {
                for (StudentInfo student : allStudents) {
                    // 使用模糊搜索判断是否包含关键字
                    if (student.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                            student.getID().toLowerCase().contains(keyword.toLowerCase()) ||
                            student.getAcademy().toLowerCase().contains(keyword.toLowerCase())) {
                        studentList.add(student); // 添加匹配的学生到列表
                    }
                }
            }


            // 更新 TableView 的数据
            if (!studentList.isEmpty()) {
                tv.setItems(studentList);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息界面");
                alert.setHeaderText("没有找到匹配的学生");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息界面");
            alert.setHeaderText("对话框需要填写内容");
            alert.showAndWait();
        }
    }

    @FXML
    public void enterSearch(MouseEvent event) throws IOException {
        search();
    }
    @FXML
    public void keySearch(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            search();
        }
    }

    public void displayAllStudent (MouseEvent event) throws IOException {
        StudentInfo[] stuList = StudentSend.loadStudentData();
        if (stuList != null) {

        }
        studentList.clear();
        for (StudentInfo s : stuList) {
            studentList.add(s);
        }
        tv.setItems(studentList);
    }

    public void loadAllStudent() throws IOException {
        StudentInfo[] stuList = StudentSend.loadStudentData();
        if (stuList != null) {

        }
        studentList.clear();
        for (StudentInfo s : stuList) {
            studentList.add(s);
        }
        tv.setItems(studentList);
    }

//    public void EnterMainMenu(MouseEvent mouseEvent) {
//        ((Stage)goback.getScene().getWindow()).close();
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/vcampus/view/mainMenu/mainMenu.fxml"));
//            AnchorPane page = loader.load();
//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("主菜单");
//            stage.setScene(new Scene(page));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}