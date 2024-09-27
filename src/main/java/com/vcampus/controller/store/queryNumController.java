package com.vcampus.controller.store;

import com.vcampus.func.client.GoodSend;
import com.vcampus.func.client.StudentSend;
import com.vcampus.pojo.GoodPojo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.*;


import java.io.IOException;

public class queryNumController {
    @FXML
    private Button ok;

    @FXML
    private TextField numText;

    @FXML
    private TextField passwordText;

    private storeStuMenu pTable;

    public void setStoreStuMenu (storeStuMenu t) {
        pTable = t;
    }

    @FXML
    public void clickOk(MouseEvent mouseEvent) throws IOException {
        if (!numText.getText().equals("")) {
            int cnt = Integer.valueOf(numText.getText());
            String password = passwordText.getText();
            pTable.BuyGoods(cnt, password);
            ((Stage)ok.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息界面");
            alert.setHeaderText("没有输入数字");
            alert.showAndWait();
        }
    }
}