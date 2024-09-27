package com.vcampus.controller.login;

import com.vcampus.dao.DbHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

import com.vcampus.pojo.UserPojo;
import com.vcampus.dao.UserDao;
import com.vcampus.func.client.UserSend;
import java.io.IOException;
import java.sql.SQLException;

public class ForgetController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField codeField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button sendCodeButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    private String generatedCode;
    @FXML
    void handleCancel(MouseEvent event) {
        closeWindow(event);
        try {
            ((Stage)cancelButton.getScene().getWindow()).close();

            // 创建FXML加载器
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/login/login.fxml"));
            Parent root = loader.load();

            // 创建新窗口并设置场景
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow(MouseEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    void handleSendCode() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "请输入您的邮箱地址");
            return;
        }

        generatedCode = generateVerificationCode();
        boolean isEmailSent = sendVerificationEmail(email, generatedCode);

        if (isEmailSent) {
            showAlert(Alert.AlertType.INFORMATION, "验证码已发送", "验证码已发送到您的邮箱，请查收。");
        } else {
            showAlert(Alert.AlertType.ERROR, "发送失败", "发送验证码失败，请检查邮箱地址是否正确或稍后再试。");
        }
    }
    @FXML
    void Submit(MouseEvent event) {
        try {
            // 获取用户输入的账号、验证码和密码
            String id = usernameField.getText();
            String inputCode = codeField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // 检查用户输入是否为空
            if (id.isEmpty() || inputCode.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "输入错误", "请填写所有字段");
                return;
            }

            // 检查两次输入的密码是否一致
            if (!newPassword.equals(confirmPassword)) {
                showAlert(Alert.AlertType.WARNING, "密码不匹配", "两次输入的密码不一致，请重新输入");
                return;
            }

            // 检查验证码是否正确
            if (!inputCode.equals(generatedCode)) {
                showAlert(Alert.AlertType.WARNING, "验证码错误", "输入的验证码不正确，请检查后再试");
                return;
            }

            // 检查用户是否存在
            UserPojo user = UserSend.changepassword_helper(id); // 假设该方法用于检查用户是否存在
            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "用户不存在", "找不到该账号对应的用户，请检查账号信息");
                return;
            }

            // 修改用户密码
            boolean updateSuccess = UserSend.changePassword(user, newPassword);
            if (updateSuccess) {
                showAlert(Alert.AlertType.INFORMATION, "密码重置成功", "用户 " + id + " 的密码已成功重置");
                System.out.println("用户 " + id + " 的密码已重置为: " + newPassword);
            } else {
                showAlert(Alert.AlertType.ERROR, "密码更新失败", "无法更新密码，请稍后再试");
                System.out.println("密码更新失败");
            }
        } catch (InterruptedException e) {
            // 处理 InterruptedException，例如显示错误消息或记录错误
            e.printStackTrace(); // 打印堆栈信息以便调试
            showAlert(Alert.AlertType.ERROR, "错误", "操作被中断，请重试");
        } catch (Exception e) {
            // 处理其他可能的异常
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "未知错误", "发生了未知错误，请稍后再试");
        }
    }

    private boolean sendVerificationEmail(String email, String code) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        final String username = "1304549157@qq.com";
        final String password = "wggdyqwidlzyjghf";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("您的验证码");
            message.setText("您的验证码是: " + code);

            Transport.send(message);

            System.out.println("验证码邮件发送成功！");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /*
    @FXML
    void handleSendCode() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "请输入您的邮箱地址");
            return;
        }

        generatedCode = generateVerificationCode();
        boolean isEmailSent = sendVerificationEmail(email, generatedCode);

        if (isEmailSent) {
            showAlert(Alert.AlertType.INFORMATION, "验证码已发送", "验证码已发送到您的邮箱，请查收。");
        } else {
            showAlert(Alert.AlertType.ERROR, "发送失败", "发送验证码失败，请检查邮箱地址是否正确或稍后再试。");
        }
    }

    @FXML
    void handleSubmit(MouseEvent event) {
        String username = usernameField.getText();
        String code = codeField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || code.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "所有字段均为必填项");
            return;
        }

        if (!code.equals(generatedCode)) {
            showAlert(Alert.AlertType.WARNING, "验证码错误", "验证码不正确，请重新输入");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.WARNING, "密码不匹配", "两次输入的密码不一致，请重新输入");
            return;
        }

        boolean isPasswordReset = resetPassword(event);
        if (isPasswordReset) {
            showAlert(Alert.AlertType.INFORMATION, "重置成功", "密码已成功重置，请使用新密码登录。");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "重置失败", "重置密码失败，请检查账号是否正确或稍后再试。");
        }
    }

    @FXML
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private boolean sendVerificationEmail(String email, String code) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        final String username = "1304549157@qq.com";
        final String password = "wggdyqwidlzyjghf";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("您的验证码");
            message.setText("您的验证码是: " + code);

            Transport.send(message);

            System.out.println("验证码邮件发送成功！");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resetPassword(MouseEvent event) {
        try {
            String id = usernameField.getText();
            String newPassword= newPasswordField.getText();
            UserPojo user = UserSend.changepassword_helper(id); // 这一行可能抛出 InterruptedException
            if (user == null) {
                System.out.println("用户未找到");
                return false;
            }

            boolean updateSuccess = UserSend.changePassword(user, newPassword);
            if (updateSuccess) {
                System.out.println("用户 " + id + " 的密码已重置为: " + newPassword);
                return true;
            } else {
                System.out.println("密码更新失败");
                return false;
            }

        } catch (InterruptedException e) {
            // 处理 InterruptedException，例如显示错误消息或记录错误
            e.printStackTrace(); // 打印堆栈信息以便调试
            // 你也可以选择在这里给用户展示错误消息或者记录日志
            return false; // 如果发生错误则返回 false
        } catch (Exception e) {
            // 处理其他可能的异常
            e.printStackTrace();
            return false;
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    */

}

