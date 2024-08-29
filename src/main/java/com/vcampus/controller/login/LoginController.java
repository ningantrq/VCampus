package com.vcampus.controller.login;


import com.vcampus.controller.TempUser;
import com.vcampus.func.client.UserSend;
import com.vcampus.pojo.UserPojo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private Button ExitButton;

    @FXML
    private Button ForgetButton;

    @FXML
    private Button LoginButton;

    @FXML
    private Button RegisterButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    //这个并没有实现！
    @FXML
    /**
     * 进入忘记密码
     * */
    void Forget(MouseEvent event) {
        ((Stage)ForgetButton.getScene().getWindow()).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Forget.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("忘记密码");
            stage.setScene(new Scene(page));
            stage.show();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * 退出程序
     * */
    void Exit(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    /**
     * 登录，成功则进入主菜单
     * @throws IOException
     * @throws InterruptedException
     * @throws SQLException
     * @throws UnknownHostException
     * */
    void Login(MouseEvent event) throws UnknownHostException, SQLException, InterruptedException, IOException {
        String id = username.getText();
        String pass = password.getText();
        //此处true应为判断是否成功进入的函数

        //这里用到了UserFunc，也就是向Socket发送登录请求
        UserPojo user = UserSend.login(id, pass);

        TempUser.setTUser(user);
        if(user == null) {
            //弹出错误窗口
            Alert alert = new Alert(null);
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("登录失败");
            alert.setContentText("账号密码有误，登录失败");
            alert.showAndWait();
        }else {
            //进入主界面
//            ((Stage)LoginButton.getScene().getWindow()).close();
//            try {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/view/MainMenu/MainMenu.fxml"));
//                AnchorPane page = loader.load();
//                Stage stage = new Stage();
//                stage.setResizable(false);
//                stage.setTitle("主菜单");
//                stage.setScene(new Scene(page));
//                stage.show();
//            }catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            }
        }
    }

    @FXML
    /**
     * 进入注册页面
     * */
    void Register(MouseEvent event) {
        ((Stage)RegisterButton.getScene().getWindow()).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("/com/vcampus/view/login/Register.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("注册");
            stage.setScene(new Scene(page));
            stage.show();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        };
    }
}