package com.vcampus.controller.store;

import com.vcampus.func.client.GoodSend;
import com.vcampus.pojo.GoodPojo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class goodAddController {
    @FXML
    public TextField id;

    @FXML
    public TextField name;

    @FXML
    public TextField price;

    @FXML
    public TextField category;

    @FXML
    public TextField stock;

    @FXML
    public Button goback;

    void setId (String s) {
        id.setText(s);
    }
    void setName (String s) {
        name.setText(s);
    }
    void setCategory (String n) {
        category.setText(n);
    }
    void setPrice (Double r) {
        price.setText(r.toString());
    }
    void setStock (Integer r) {
        stock.setText(r.toString());
    }

    private GoodPojo modifyGoods;
    public void initialize() throws IOException {
        modifyGoods = null;
    }

    public void setModifyGoods (GoodPojo s) {
        modifyGoods = s;
    }

    public void OnlyRead () {

    }

    public void EnterGoodMenu(MouseEvent mouseEvent) {
        ((Stage)goback.getScene().getWindow()).close();
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/vcampus/view/store/storeAdminMenu.fxml"));
//            AnchorPane page = loader.load();
//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("商店");
//            stage.setScene(new Scene(page));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void AddOrModify (MouseEvent event) throws IOException {
        if (modifyGoods == null) {
            AddGood(event);
        } else {
            ModifyGood(event);
        }
    }

    void AddGood (MouseEvent event) throws IOException {
        GoodPojo temp = new GoodPojo();
        if (id.getText() == "" || name.getText() == "" || price.getText() == "" ||
                category.getText() == "" ||  stock.getText() == "") {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息界面");
                alert.setHeaderText("对话框不能为空");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        boolean ok = true;
        GoodPojo[] allGood = GoodSend.getGoodData();
        for (GoodPojo g : allGood) {
            if (g.getGId().equals(id.getText())) {
                ok = false;
                break;
            }
        }
        if (ok == false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息界面");
            alert.setHeaderText("id不能相同");
            alert.showAndWait();
        }
        temp.setGId(id.getText());
        temp.setGPrice(Double.valueOf(price.getText()));
        temp.setGName(name.getText());
        temp.setGStock(Integer.valueOf(stock.getText()));
        temp.setCategory(category.getText());
        boolean flag = GoodSend.addGoodData(temp);

        ((Stage)goback.getScene().getWindow()).close();
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/vcampus/view/store/storeAdminMenu.fxml"));
//            AnchorPane page = loader.load();
//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("商店");
//            stage.setScene(new Scene(page));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void ModifyGood (MouseEvent event) throws IOException {
        GoodPojo temp = new GoodPojo();
        if (id.getText() == "" || name.getText() == "" || price.getText() == "" ||
                category.getText() == "" ||  stock.getText() == "") {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息界面");
                alert.setHeaderText("对话框不能为空");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        temp.setGId(id.getText());
        temp.setGPrice(Double.valueOf(price.getText()));
        temp.setGName(name.getText());
        temp.setGStock(Integer.valueOf(stock.getText()));
        temp.setCategory(category.getText());
        boolean flag = GoodSend.modifyGoodData(modifyGoods.getGId(), temp);

        ((Stage)goback.getScene().getWindow()).close();
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/vcampus/view/store/storeAdminMenu.fxml"));
//            AnchorPane page = loader.load();
//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("商店");
//            stage.setScene(new Scene(page));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
