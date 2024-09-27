package com.vcampus.controller.menu;

import com.vcampus.controller.TempUser;
import com.vcampus.func.client.StudentSend;
import com.vcampus.pojo.StudentInfo;
import com.vcampus.pojo.UserPojo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Text mainIdText;

    @FXML
    private VBox SceneBox;

    @FXML
    private Button studentManagementButton;

    @FXML
    private Button courseSelectionButton;

    @FXML
    private Button storeButton;

    @FXML
    private Button libraryButton;

    @FXML
    private Button bankButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button aiButton;

    @FXML
    void initialize() {
        // 绑定各个按钮的事件
        studentManagementButton.setOnAction(event -> handleStudentManagement());
        courseSelectionButton.setOnAction(event -> handleCourseSelection());
        storeButton.setOnAction(event -> handleStore());
        libraryButton.setOnAction(event -> handleLibrary());
        bankButton.setOnAction(event -> handleBank());
        logoutButton.setOnAction(event -> handleLogout());
        aiButton.setOnAction(event -> handleAi());

        String id = TempUser.getTUser().getuId();
        mainIdText.setText(id);
    }

    private void handleStudentManagement() {
        try {
            FXMLLoader loader = new FXMLLoader();
            if (TempUser.getTUser().getuRole().equals("admin")) {
                loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_man_menu.fxml"));
            } else {
                loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/studentMode.fxml"));
                //loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_man_menu.fxml"));
            }
            //loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_man_menu.fxml"));

            Pane page = loader.load();

            Pane originalPane = (Pane) SceneBox.getChildren().get(0);
            //加载界面
            SceneBox.getChildren().add(page);
            //清除掉HBox中原来的界面
            if(this.SceneBox !=null) {
                SceneBox.getChildren().remove(originalPane);
            }

//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("学籍管理");
//            stage.setScene(new Scene(page));
//
//            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCourseSelection() {
        // 处理选课系统的逻辑
        try {
            FXMLLoader loader = new FXMLLoader();
            if (TempUser.getTUser().getuRole().equals("admin")) {
                loader.setLocation(getClass().getResource("/com/vcampus/view/Course/CourseView.fxml"));
            }
            else if (TempUser.getTUser().getuRole().equals("student"))
            {
                loader.setLocation(getClass().getResource("/com/vcampus/view/Course/ChooseCourseView.fxml"));
            }
            else {
                loader.setLocation(getClass().getResource("/com/vcampus/view/Course/TeacherCourseView.fxml"));
                //loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_man_menu.fxml"));
            }
            //loader.setLocation(getClass().getResource("/com/vcampus/view/studentManagement/stu_man_menu.fxml"));

            Pane page = loader.load();

            Pane originalPane = (Pane) SceneBox.getChildren().get(0);
            //加载界面
            SceneBox.getChildren().add(page);
            //清除掉HBox中原来的界面
            if(this.SceneBox !=null) {
                SceneBox.getChildren().remove(originalPane);
            }

//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("学籍管理");
//            stage.setScene(new Scene(page));
//
//            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleStore(){
        // 处理商店的逻辑
        try {
            FXMLLoader loader = new FXMLLoader();
            if (TempUser.getTUser().getuRole().equals("admin")) {
                loader.setLocation(getClass().getResource("/com/vcampus/view/store/storeAdminMenu.fxml"));
            } else {
                loader.setLocation(getClass().getResource("/com/vcampus/view/store/storeStuMenu.fxml"));
            }
            Pane page = loader.load();

            Pane originalPane = (Pane) SceneBox.getChildren().get(0);
            //加载界面
            SceneBox.getChildren().add(page);
            //清除掉HBox中原来的界面
            if (this.SceneBox != null) {
                SceneBox.getChildren().remove(originalPane);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
//        Stage stage = new Stage();
//
//        stage.setResizable(false);
//        stage.setTitle("商店");
//        stage.setScene(new Scene(page));
//
//        stage.show();
    }

    private void handleLibrary() {
        // 处理图书馆的逻辑
        //System.out.println("图书馆");
        try {
            // 加载LibraryView.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/vcampus/view/library/Library.fxml"));
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/com/vcampus/view/library/Monitor_Library.fxml"));
            UserPojo temp = TempUser.getTUser();
            //学生教师图书馆界面
            if(temp.getURole().equals("student")||temp.getURole().equals("teacher")) {
                Pane lib = fxmlLoader.load();

//                // 获取当前窗口并关闭
//                ((Stage) libraryButton.getScene().getWindow()).close();

//                // 创建一个新的Stage窗口
//                Stage stage = new Stage();
//                stage.setTitle("图书馆");
//                stage.setScene(new Scene(lib));
//                stage.show();


                Pane originalPane = (Pane) SceneBox.getChildren().get(0);
                //加载界面
                SceneBox.getChildren().add(lib);
                //清除掉HBox中原来的界面
                if(this.SceneBox !=null) {
                    SceneBox.getChildren().remove(originalPane);
                }
            }
            else if(temp.getURole().equals("admin")){
                Pane lib = fxmlLoader2.load();
//                // 获取当前窗口并关闭
//                ((Stage) libraryButton.getScene().getWindow()).close();
//                // 创建一个新的Stage窗口
//                Stage stage = new Stage();
//                stage.setTitle("图书馆");
//                stage.setScene(new Scene(lib));
//                stage.show();

                Pane originalPane = (Pane) SceneBox.getChildren().get(0);
                //加载界面
                SceneBox.getChildren().add(lib);
                //清除掉HBox中原来的界面
                if(this.SceneBox !=null) {
                    SceneBox.getChildren().remove(originalPane);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("加载错误");
            alert.setHeaderText(null);
            alert.setContentText("无法加载图书馆界面！");
            alert.showAndWait();
        }
    }

    private void handleAi() {
        try {
//            ((Stage) aiButton.getScene().getWindow()).close();
            // 加载LibraryView.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/vcampus/view/ai/ai.fxml"));

            Pane aiview = fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setTitle("AI助手");
//            stage.setScene(new Scene(aiview));
//            stage.show();
            Pane originalPane = (Pane) SceneBox.getChildren().get(0);
            //加载界面
            SceneBox.getChildren().add(aiview);
            //清除掉HBox中原来的界面
            if(this.SceneBox !=null) {
                SceneBox.getChildren().remove(originalPane);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("加载错误");
            alert.setHeaderText(null);
            alert.setContentText("无法加载AI助手界面！");
            alert.showAndWait();
        }
    }

    private void handleBank() {
        try{// 加载LibraryView.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/vcampus/view/bank/bank.fxml"));
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/com/vcampus/view/bank/Monitor_Bank.fxml"));
            UserPojo temp = TempUser.getTUser();
            if(temp.getURole().equals("student")||temp.getURole().equals("teacher")) {
                Pane bank = fxmlLoader.load();
                Pane originalPane = (Pane) SceneBox.getChildren().get(0);
                //加载界面
                SceneBox.getChildren().add(bank);
                //清除掉HBox中原来的界面
                if(this.SceneBox !=null) {
                    SceneBox.getChildren().remove(originalPane);
                }

                // 获取当前窗口并关闭
//                ((Stage) bankButton.getScene().getWindow()).close();
//
//                // 创建一个新的Stage窗口
//                Stage stage = new Stage();
//                stage.setTitle("银行");
//                stage.setScene(new Scene(bank));
//                stage.show();
            }
            else if(temp.getURole().equals("admin")){

                AnchorPane bank = fxmlLoader2.load();
                Pane originalPane = (Pane) SceneBox.getChildren().get(0);
                //加载界面
                SceneBox.getChildren().add(bank);
                //清除掉HBox中原来的界面
                if(this.SceneBox !=null) {
                    SceneBox.getChildren().remove(originalPane);
                }

//                // 获取当前窗口并关闭
//                ((Stage) bankButton.getScene().getWindow()).close();
//                // 创建一个新的Stage窗口
//                Stage stage = new Stage();
//                stage.setTitle("银行");
//                stage.setScene(new Scene(bank));
//                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("加载错误");
            alert.setHeaderText(null);
            alert.setContentText("无法加载银行界面！");
            alert.showAndWait();
        }


    }

    private void handleLogout() {

        try {
            ((Stage)logoutButton.getScene().getWindow()).close();

            // 创建FXML加载器
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/login/login.fxml"));
            Parent root = loader.load();

            // 创建新窗口并设置场景
            Stage stage = new Stage();
            stage.setTitle("主菜单");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
