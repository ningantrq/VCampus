package com.vcampus.controller.bank;


import com.vcampus.controller.*;
import com.vcampus.func.client.BankSend;
import com.vcampus.pojo.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.vcampus.dao.BankDao.findAllOrders;
import static com.vcampus.dao.BankDao.findBankByuId;
import static com.vcampus.func.client.BankSend.*;

public class Monitor_Bank {

    @FXML
    private TextField searchField;
    @FXML
    private TextField billSearchField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<BankPojo> accountTable;

    @FXML
    private TableColumn<BankPojo, String> accountColumn;
    @FXML
    private TableColumn<OrderPojo, String> billAccountColumn;
    @FXML
    private TableColumn<BankPojo, String> nameColumn;

    @FXML
    private TableColumn<BankPojo, Double> balanceColumn;

    @FXML
    private TableColumn<BankPojo, String> statusColumn;

    @FXML
    private Button freezeUnfreezeButton;

    @FXML
    private Button chargeButton;

//    @FXML
//    private Button exitButton;
    @FXML
    private TableView<OrderPojo> billsTable;
    @FXML
    private TableColumn<OrderPojo, String> billIdColumn;
    @FXML
    private TableColumn<BankPojo, String> billNameColumn;
    @FXML
    private TableColumn<OrderPojo, String> productIdColumn;
    @FXML
    private TableColumn<OrderPojo, String> productNameColumn;
    @FXML
    private TableColumn<OrderPojo, Double> priceColumn;
    @FXML
    private TableColumn<OrderPojo, Integer> quantityColumn;
    @FXML
    private TableColumn<OrderPojo, Double> totalPriceColumn;
    @FXML
    private TableColumn<OrderPojo, Integer> productTypeColumn;
    private ObservableList<OrderPojo> orderList = FXCollections.observableArrayList();
    private ObservableList<BankPojo> accountList = FXCollections.observableArrayList();
    @FXML
    public void setUpTableColumns() {
        accountColumn.setCellValueFactory(new PropertyValueFactory<BankPojo, String>("aId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<BankPojo, String>("aName"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<BankPojo, Double>("aMoney"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<BankPojo, String>("IsFroze"));

    }

    // 加载表格数据（示例数据，可替换为实际数据获取逻辑）
    private void loadAccountData() throws IOException {
        setUpTableColumns();
        BankPojo[] bankPojo = loadBankData();
        if (bankPojo == null) {
            bankPojo = new BankPojo[0];
        }
        accountList.clear();
        for (BankPojo bankPojo1 : bankPojo) {
            accountList.add(bankPojo1);
        }
        accountTable.setItems(accountList);
    }

    public void initialize() throws IOException {
        loadAccountData();
        loadAllOrders();
        accountTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        billsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    @FXML
    private void handleAll() {
        try {
            loadAccountData(); // 调用公共方法
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() throws IOException {

        String searchText;
        searchText = searchField.getText();
        BankPojo bank= BankSend.searchBankData(searchText);


        TempBank.setBank(bank);
        if (bank == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING); // 改为明确指定 AlertType
            alert.setTitle("检索失败");
            alert.setHeaderText("未检索到该账户！");
            alert.showAndWait();
            searchField.clear();
        } else {
            // 清空现有的书籍列表并添加检索到的书籍
            accountList.clear();
            accountList.addAll(bank);
            accountTable.setItems(accountList);  // 更新 TableView

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("检索成功");
            alert.setHeaderText("已成功检索到账户！");
            alert.showAndWait();
            searchField.clear();
        }

    }

    @FXML
    private void handleFreezeUnfreeze() throws IOException {
        BankPojo bank = accountTable.getSelectionModel().getSelectedItem();
        //TempBook2.setSelectedBook(selectedBook);


        // 检查是否有选中的书籍
        if (bank == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("未选择账户");
            alert.setContentText("请先选择账户！");
            alert.showAndWait();
            return;
        }
        boolean success = changeFroze(bank);
        if(success) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("提示");
            alert.setHeaderText("修改成功！");
            alert.showAndWait();
            initialize();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("修改失败！");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleCharge() {
        BankPojo selectedBank = accountTable.getSelectionModel().getSelectedItem();
        TempBank.setBank(selectedBank);
        // 检查是否有选中的书籍
        if (selectedBank == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("未选择账户");
            alert.setContentText("请先选择要扣费的账户！");
            alert.showAndWait();
            return;
        }



//        ((Stage) chargeButton.getScene().getWindow()).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/bank/charge.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("扣费");
            stage.setScene(new Scene(page));

            stage.showAndWait();

            loadAccountData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    private void handleExit() {
//        try {
//            ((Stage)exitButton.getScene().getWindow()).close();
//
//            // 创建FXML加载器
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/MainMenu/MainMenu.fxml"));
//            Parent root = loader.load();
//
//            // 创建新窗口并设置场景
//            Stage stage = new Stage();
//            stage.setTitle("主菜单");
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // 显示提示信息
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleBillSearch() throws IOException {
        String searchText;
        searchText = billSearchField.getText();

        OrderPojo order= BankSend.findOrder(searchText);

        System.out.println(order.getOrderId());
        TempOrder.setOrder(order);
        if (order == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING); // 改为明确指定 AlertType
            alert.setTitle("检索失败");
            alert.setHeaderText("未检索到该流水！");
            alert.showAndWait();
            billSearchField.clear();
        } else {
            // 清空现有的书籍列表并添加检索到的书籍
            orderList.clear();
            orderList.addAll(order);
            billsTable.setItems(orderList);  // 更新 TableView

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("检索成功");
            alert.setHeaderText("已成功检索到流水！");
            alert.showAndWait();
            billSearchField.clear();
        }
    }
    private void loadAllOrders() throws IOException{
        UserPojo temp = TempUser.getTUser();
        String id = temp.getuId();


        setUpTableColumns2();
        OrderPojo[] tempOrder = findAllOrders();
        orderList.clear();
        for(OrderPojo tempOrderPojo : tempOrder){
            orderList.add(tempOrderPojo);
        }
        billsTable.setItems(orderList);
    }
    private void setUpTableColumns2(){
        billIdColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, String>("OrderId"));
        billAccountColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, String>("UId"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, String>("GId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, String>("GName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, Double>("Price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, Integer>("Count"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, Double>("Total"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, Integer>("IsGood"));
        //borrowColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, Button>("borrowButton"));
    }






    @FXML
    private void handleShowAllBills() throws IOException {
        try {loadAllOrders();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
