package com.vcampus.controller.store;

import com.vcampus.func.client.OrderSend;
import com.vcampus.pojo.OrderPojo;
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

public class orderMenuController {

    @FXML
    private TextField searchContext;

    @FXML
    private Button search;

    @FXML
    private Button goback;

    @FXML
    private TableView<OrderPojo> tv;

    @FXML
    private TableColumn<OrderPojo, String> oidColumn;

    @FXML
    private TableColumn<OrderPojo, String> uidColumn;

    @FXML
    private TableColumn<OrderPojo, String> gidColumn;

    @FXML
    private TableColumn<OrderPojo, String> nameColumn;

    @FXML
    private TableColumn<OrderPojo, Double> priceColumn;

    @FXML
    private TableColumn<OrderPojo, Double> totalColumn;

    @FXML
    private TableColumn<OrderPojo, Integer> cntColumn;

    @FXML
    private TableColumn<OrderPojo, Integer> isGoodColumn;

    private ObservableList<OrderPojo> orderList = FXCollections.observableArrayList();

    @FXML
    private void setUpTableColumns() {
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("uId"));
        oidColumn.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("gName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("Total")); // 注意大小写
        cntColumn.setCellValueFactory(new PropertyValueFactory<>("count")); // 注意大小写
        gidColumn.setCellValueFactory(new PropertyValueFactory<>("gId"));
        isGoodColumn.setCellValueFactory(new PropertyValueFactory<>("isGood")); // 使用 "isGood"
    }


    public void initialize() throws IOException {
        // 设置列的单元格工厂

        setUpTableColumns();

        // 加载数据到 TableView
        OrderPojo[] temp = OrderSend.getOrderData();
        for (OrderPojo s : temp) {
            orderList.add(s);
        }
        tv.setItems(orderList);
    }

    private void search () throws IOException {
        String keyword = searchContext.getText().trim(); // 获取输入的搜索关键字并去除前后空格
        if (!keyword.isEmpty()) {
            // 清空当前列表
            orderList.clear();

            OrderPojo[] allOrder = OrderSend.getOrderData();
            for (OrderPojo op : allOrder) {
                // 使用模糊搜索判断是否包含关键字
                if (op.getGName().toLowerCase().contains(keyword.toLowerCase()) ||
                        op.getGId().toLowerCase().contains(keyword.toLowerCase()) ||
                        op.getUId().toLowerCase().contains(keyword.toLowerCase())) {
                    orderList.add(op); // 添加匹配的商品到列表
                }
            }

            // 更新 TableView 的数据
            if (!orderList.isEmpty()) {
                tv.setItems(orderList);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息界面");
            alert.setHeaderText("框里面需要有内容");
            alert.showAndWait();
        }
    }

    @FXML
    public void enterSearch(MouseEvent mouseEvent) throws IOException {
        search();
    }

    @FXML
    public void keySearch(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            search();
        }
    }

    @FXML
    public void EnterStoreMenu(MouseEvent mouseEvent) {
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
    public void displayAllOrder(MouseEvent mouseEvent) throws IOException {
        OrderPojo[] gList = OrderSend.getOrderData();
        orderList.clear();
        for (OrderPojo s : gList) {
            orderList.add(s);
        }
        tv.setItems(orderList);
    }
}
