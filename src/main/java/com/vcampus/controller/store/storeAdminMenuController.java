package com.vcampus.controller.store;

import com.vcampus.func.client.GoodSend;
import com.vcampus.func.client.StudentSend;
import com.vcampus.pojo.GoodPojo;
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



public class storeAdminMenuController {
    @FXML
    private TextField searchContext;

    @FXML
    public Button add;

    @FXML
    public Button delete;

    @FXML
    public Button modify;

    @FXML
    public Button display;


    @FXML
    public Button order;

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

    @FXML
    public void enterAdd(MouseEvent event) {
//        ((Stage)add.getScene().getWindow()).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/store/goodAdd.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("添加商品信息");
            stage.setScene(new Scene(page));

            stage.showAndWait();
            loadAllgoods();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() throws IOException {
        GoodPojo stu = tv.getSelectionModel().getSelectedItem();
        String id = stu.getGId();
        goodsList.remove(stu); // 从列表中删除
        GoodSend.deleteGoodData(id); // 从数据库中删除
    }

    @FXML
    public void enterDelete(MouseEvent event) throws IOException {
        try {
            GoodPojo stu = tv.getSelectionModel().getSelectedItem();
            if (stu != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("确认界面");
                alert.setHeaderText("是否删除该商品");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            delete();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息界面");
                alert.setHeaderText("没有选中商品");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void enterModify(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/store/goodAdd.fxml"));
            AnchorPane page = loader.load();
            goodAddController c = loader.getController();
            c.OnlyRead();

            GoodPojo goods = tv.getSelectionModel().getSelectedItem();

            if (goods != null) {
//                ((Stage)modify.getScene().getWindow()).close();
                c.setModifyGoods(goods);
                c.setId(goods.getGId());
                c.setName(goods.getGName());
                c.setStock(goods.getGStock());
                c.setCategory(goods.getCategory());
                c.setPrice(goods.getGPrice());

                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("修改商品信息");
                stage.setScene(new Scene(page));
                stage.showAndWait();
                loadAllgoods();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息界面");
                alert.setHeaderText("没有选中的商品可以修改");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
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
            alert.setHeaderText("框里面需要有内容");
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

    public void displayAllGoods (MouseEvent event) throws IOException {
        GoodPojo[] gList = GoodSend.getGoodData();
        goodsList.clear();
        for (GoodPojo s : gList) {
            goodsList.add(s);
        }
        tv.setItems(goodsList);
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

    @FXML
    public void openOrder(MouseEvent mouseEvent) {
//        ((Stage)order.getScene().getWindow()).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/store/orderMenu.fxml"));
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

    public void loadAllgoods() throws IOException {
        GoodPojo[] gList = GoodSend.getGoodData();
        goodsList.clear();
        for (GoodPojo s : gList) {
            goodsList.add(s);
        }
        tv.setItems(goodsList);
    }
}
