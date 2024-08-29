package com.vcampus.controller.login;

import com.vcampus.func.client.UserSend;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button ExitButton;

    @FXML
    private Button ImageButton;

    @FXML
    private Button RegisterButton;

    @FXML
    private Button ReturnButton;

    @FXML
    private RadioButton adminstrator;

    @FXML
    private Spinner<Integer> age;

    @FXML
    private RadioButton female;

    @FXML
    private ToggleGroup gender;

    @FXML
    private TextField id;

    @FXML
    private ImageView image;

    @FXML
    private ToggleGroup job;

    @FXML
    private RadioButton male;

    @FXML
    private TextField pwd;

    @FXML
    private RadioButton student;

    @FXML
    private RadioButton teacher;

    @FXML
    private TextField username;

    private BufferedImage photo;

    private FileChooser fileChooser = new FileChooser();

    @FXML
    void ChooseImage(MouseEvent event) {
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        Image images = new Image(selectedFile.toURI().toString());
        photo = SwingFXUtils.fromFXImage(images, null);//image类型转化为bufferedimage
        image.setImage(images);
    }


    /**
     * 前往登录界面（功能方法）
     * */
    void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RegisterController.class.getResource("/com/vcampus/view/login/login.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setTitle("登录");
            stage.setScene(new Scene(page));
            stage.show();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * 点击退出按钮退出程序
     * */
    void Exit(MouseEvent event) {
        Platform.exit();
    }

    @SuppressWarnings("unused")
    @FXML
    /**
     * 点击注册按钮，弹出提示框（是否注册成功），成功则返回登录界面
     * */
    void Register(MouseEvent event) throws SQLException, UnknownHostException, IOException {
        //true应为判断是否成功注册的函数
        Alert alert = new Alert(null);
        String nameString = username.getText();
        String genderString = gender.getSelectedToggle().equals(male) ? "male" : "female";
        String pwdString = pwd.getText();
        String idString = id.getText();
        int ageint = age.getValue();
        String roleString = job.getSelectedToggle().equals(student) ? "student" :
                (job.getSelectedToggle().equals(teacher) ? "teacher" : "admin");

        //这里调用了UserFunc来传信息
        if(UserSend.register(idString, nameString, ageint, genderString, pwdString, roleString, photo)) {
            ((Stage)ExitButton.getScene().getWindow()).close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("注册成功");
            alert.setContentText("注册成功");
            alert.showAndWait();
            goToLogin();
        }
        else {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("注册失败");
            alert.setContentText("注册失败");
            alert.showAndWait();
        }
    }

    @FXML
    /**
     * 点击返回按钮返回登录界面
     * */
    void Return(MouseEvent event) {
        ((Stage)ReturnButton.getScene().getWindow()).close();
        goToLogin();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        SpinnerValueFactory.IntegerSpinnerValueFactory intfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1,Integer.MAX_VALUE,1);
        age.setValueFactory(intfactory);
    }
}
