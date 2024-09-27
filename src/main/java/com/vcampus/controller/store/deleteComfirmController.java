package com.vcampus.controller.store;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class deleteComfirmController {
    @FXML
    public Button confirm;

    @FXML
    public Button cancel;

    private storeAdminMenuController mainController;

    public void setMainController(storeAdminMenuController mainController) {
        this.mainController = mainController;
    }
    @FXML
    public void ClickConfirm(MouseEvent mouseEvent) throws IOException {
        if (mainController != null) {
            mainController.delete(); // 调用主控制器的删除方法
        }
        ((Stage) confirm.getScene().getWindow()).close(); // 关闭确认窗口
    }
    @FXML
    public void ClickCancel(MouseEvent mouseEvent) {
        ((Stage) cancel.getScene().getWindow()).close(); // 关闭确认窗口
    }


}
