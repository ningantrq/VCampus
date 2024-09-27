package com.vcampus.controller.store;

import com.vcampus.controller.TempUser;
import com.vcampus.func.client.GoodSend;
import com.vcampus.func.client.OrderSend;
import com.vcampus.pojo.GoodPojo;
import com.vcampus.pojo.UserPojo;
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

public class storeStuMenu {
    @FXML
    public TextField searchContext;

    @FXML
    private Button buy;

    @FXML
    private Button display;

    @FXML
    private Button search;

    @FXML
    private Button order;

    @FXML
    private TableView<GoodPojo> tv;

    @FXML
    private TableColumn<GoodPojo, String> idColumn;

    @FXML
    private TableColumn<GoodPojo, String> nameColumn;

    @FXML
    private TableColumn<GoodPojo, Double> priceColumn;

    @FXML
    private TableColumn<GoodPojo, String> categoryColumn;

    @FXML
    private TableColumn<GoodPojo, Integer> stockColumn;

    private ObservableList<GoodPojo> goodsList = FXCollections.observableArrayList();

    @FXML
    private void setUpTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("GId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("GName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("GPrice"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("GStock"));
    }

    public void initialize() throws IOException {
        // 设置列的单元格工厂

        setUpTableColumns();

        // 加载数据到 TableView
        GoodPojo[] temp = GoodSend.getGoodData();
        for (GoodPojo s : temp) {
            goodsList.add(s);
        }
        tv.setItems(goodsList);
    }


    private void search () throws IOException {
        String keyword = searchContext.getText().trim(); // 获取输入的搜索关键字并去除前后空格
        if (!keyword.isEmpty()) {
            // 清空当前列表
            goodsList.clear();

            GoodPojo[] allGoods = GoodSend.getGoodData();
            for (GoodPojo student : allGoods) {
                // 使用模糊搜索判断是否包含关键字
                if (student.getGName().toLowerCase().contains(keyword.toLowerCase()) ||
                        student.getGId().toLowerCase().contains(keyword.toLowerCase()) ||
                        student.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                    goodsList.add(student); // 添加匹配的商品到列表
                }
            }

            // 更新 TableView 的数据
            if (!goodsList.isEmpty()) {
                tv.setItems(goodsList);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息界面");
            alert.setHeaderText("对话框不能为空");
            alert.showAndWait();
        }
    }
    @FXML
    public void enterSearch(MouseEvent event) throws IOException {
        search();
    }

    @FXML
    public void keySearch(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            search();
        }
    }

    @FXML
    public void displayAllGoods(MouseEvent mouseEvent) throws IOException {
        GoodPojo[] gList = GoodSend.getGoodData();
        goodsList.clear();
        for (GoodPojo s : gList) {
            goodsList.add(s);
        }
        tv.setItems(goodsList);
    }

//    @FXML
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


    @FXML
    public void clickBuyGoods(MouseEvent event) throws IOException {
        GoodPojo g = tv.getSelectionModel().getSelectedItem();
        if (g != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/store/queryNum.fxml"));

            // 不需要手动设置控制器，直接加载
            AnchorPane page = loader.load();

            // 获取控制器实例
            queryNumController controller = loader.getController();
            controller.setStoreStuMenu(this); // 传递数据

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("询问");
            stage.setScene(new Scene(page));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息界面");
            alert.setHeaderText("没有选中商品");
            alert.showAndWait();
        }
    }


    public void BuyGoods(int cnt, String password) throws IOException {
        int c = cnt;
        GoodPojo g = tv.getSelectionModel().getSelectedItem();
        UserPojo cur = TempUser.getTUser();
        if (g.getGStock() >= cnt) {

            String id = g.getGId();
            boolean ok = OrderSend.buyAndCheck(cnt * g.getGPrice(), cur.getUId(), password);
            if (ok) {
                goodsList.remove(g);
                g.setGStock(g.getGStock() - cnt);
                GoodSend.modifyGoodData(id, g);
                goodsList.add(g);
                OrderSend.addOrder(cur.getUId(), g.getGId(), g.getGName(), g.getGPrice(), cnt);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息界面");
                alert.setHeaderText("密码错误/余额不足/找不到用户");
                alert.showAndWait();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("消息界面");
            alert.setHeaderText("商品数量不够");
            alert.showAndWait();
        }
    }


    @FXML
    public void openOrder(MouseEvent mouseEvent) {
//        ((Stage)order.getScene().getWindow()).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/store/stuOrderMenu.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("订单");
            stage.setScene(new Scene(page));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
