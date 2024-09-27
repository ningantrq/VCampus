package com.vcampus.controller.studentManagement;

import com.vcampus.controller.TempUser;
import com.vcampus.func.client.StudentSend;
import com.vcampus.pojo.StudentInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class studentModeController {
//    @FXML
//    private Button goback;

    @FXML
    private Text idText;

    @FXML
    private Text roleText;

    @FXML
    private Text nameText;

    @FXML
    private Text yearText;

    @FXML
    private Text ageText;

    @FXML
    private Text sexText;

    @FXML
    private Text acdaText;

    @FXML
    public void initialize() throws IOException {
        String id = TempUser.getTUser().getuId();
        StudentInfo stu = StudentSend.getById(id);
        idText.setText(stu.getID());
        roleText.setText(stu.getRole());
        nameText.setText(stu.getName());
        yearText.setText(String.valueOf(stu.getDate()));
        sexText.setText(stu.getSex());
        acdaText.setText(stu.getAcademy());
        ageText.setText(String.valueOf(stu.getAge()));
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
