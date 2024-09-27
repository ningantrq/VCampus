package com.vcampus.controller.studentManagement;

import com.vcampus.func.client.StudentSend;
import com.vcampus.pojo.StudentInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class studentAddController {
    @FXML
    private TextField uId;

    @FXML
    private TextField name;

    @FXML
    private ComboBox sexBox;

    @FXML
    private ComboBox roleBox;

    @FXML
    private TextField age;

    @FXML
    private TextField year;

    @FXML
    private TextField acda;

    @FXML
    private Button ok;

    @FXML
    private Button goback;

    void setuId (String s) {
        uId.setText(s);
    }
    void setSex (String s) {
        sexBox.getSelectionModel().select(s);
    }
    void setName (String n) {
        name.setText(n);
    }
    void setRole (String r) {
        roleBox.getSelectionModel().select(r);
    }
    void setAge (int a) {
        age.setText(a + "");
    }
    void setYear (int y) {
        year.setText(y + "");
    }
    void setAcda (String a) {
        acda.setText(a);
    }

    private StudentInfo modifyStudent;
    public void setModifyStudent (StudentInfo s) {
        modifyStudent = s;
    }
    public void initialize() throws IOException {
        modifyStudent = null;
        sexBox.getItems().addAll("男", "女");
        sexBox.getSelectionModel().select("男");
        sexBox.setStyle("-fx-font-size: 22px;");
        roleBox.getItems().addAll("学生", "老师", "管理员");
        roleBox.getSelectionModel().select("学生");
        roleBox.setStyle("-fx-font-size: 22px;");
    }

    public void OnlyRead () {
        uId.setStyle("-fx-background-color: lightgray;");
        uId.setDisable(true);
        roleBox.setOnAction(event -> {
            roleBox.requestFocus();
        });
    }

    @FXML
    void EnterStuManagement (MouseEvent event) {
        ((Stage)goback.getScene().getWindow()).close();
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_man_menu.fxml"));
//            AnchorPane page = loader.load();
//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("学籍管理");
//            stage.setScene(new Scene(page));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void AddOrModify (MouseEvent event) throws IOException {
        if (modifyStudent == null) {
            AddStudent(event);
        } else {
            ModifyStudent(event);
        }
    }

    @FXML
    void AddStudent (MouseEvent event) throws IOException {
        StudentInfo temp = new StudentInfo();
        if (uId.getText() == "" || age.getText() == "" || acda.getText() == "" ||
        year.getText() == "" ||  name.getText() == "") {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/empty.fxml"));
                AnchorPane page = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("输入栏不能为空");
                stage.setScene(new Scene(page));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        boolean ok = true;
        StudentInfo[] allStudent = StudentSend.loadStudentData();
        if (allStudent != null) {
            for (StudentInfo stu : allStudent) {
                if (stu.getID().equals(uId.getText())) {
                    ok = false;
                    break;
                }
            }
            if (ok == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息界面");
                alert.setHeaderText("id不能相同");
                alert.showAndWait();
                return;
            }
        }


        temp.setID(uId.getText());
        temp.setAge(Integer.valueOf(age.getText()));
        temp.setAcademy(acda.getText());
        temp.setDate(Integer.valueOf(year.getText()));
        temp.setRole((String)roleBox.getSelectionModel().getSelectedItem());
        temp.setSex((String)sexBox.getSelectionModel().getSelectedItem());
        temp.setName(name.getText());
        boolean flag = StudentSend.addStudentData(temp);

        ((Stage)goback.getScene().getWindow()).close();
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_man_menu.fxml"));
//            AnchorPane page = loader.load();
//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("学籍管理");
//            stage.setScene(new Scene(page));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void ModifyStudent (MouseEvent event) throws IOException {
        StudentInfo temp = new StudentInfo();
        if (uId.getText() == "" || age.getText() == "" || acda.getText() == "" ||
                year.getText() == "" || name.getText() == "") {
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/empty.fxml"));
                AnchorPane page = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("输入栏不能为空");
                stage.setScene(new Scene(page));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        temp.setID(uId.getText());
        temp.setAge(Integer.valueOf(age.getText()));
        temp.setAcademy(acda.getText());
        temp.setDate(Integer.valueOf(year.getText()));
        temp.setRole((String) roleBox.getSelectionModel().getSelectedItem());
        temp.setSex((String) sexBox.getSelectionModel().getSelectedItem());
        temp.setName(name.getText());
        boolean flag = StudentSend.modifyStudentData(modifyStudent.getID(), temp);

        ((Stage) goback.getScene().getWindow()).close();
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_man_menu.fxml"));
//            AnchorPane page = loader.load();
//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("学籍管理");
//            stage.setScene(new Scene(page));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    }

}
