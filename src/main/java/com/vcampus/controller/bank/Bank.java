package com.vcampus.controller.bank;

import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.vcampus.controller.TempOrder;
import com.vcampus.controller.TempUser;
import com.vcampus.func.client.BankSend;
import com.vcampus.pojo.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
//import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import static com.vcampus.dao.BankDao.CreateRechargeOrder;
import static com.vcampus.dao.BankDao.findBankByuId;
import static com.vcampus.func.client.BankSend.*;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;
import com.vcampus.controller.MD5Utils;


public class Bank {

    @FXML
    private TextField rechargeAmountField;

    @FXML
    private PasswordField rechargePasswordField;

    @FXML
    private TextField searchField;

    @FXML
    private TextField cardStatusField;

    @FXML
    private PasswordField freezePasswordField;

    @FXML
    private Button search;
//    @FXML
//    private Button exitButton;
    @FXML
    private TextField savings;
    @FXML
    private Button exit;



    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField accountBalanceField;
    @FXML
    private TextField freezeStatusField;
    @FXML
    private TableView<OrderPojo> billsTable;
    @FXML
    private TableColumn<OrderPojo, String> billIdColumn;
    @FXML
    private TableColumn<OrderPojo, String> accountColumn;
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




    public void loadmoney() throws IOException{
        UserPojo temp = TempUser.getTUser();
        String id = temp.getuId();
        BankPojo bank = findBankByuId(id);
        String amoneyString = String.valueOf(bank.getAMoney());

        // 设置 savings TextField 的值
        savings.setText(amoneyString);
    }
    public void initialize() throws IOException {
        UserPojo temp = TempUser.getTUser();
        String id = temp.getuId();
        BankPojo bank = findBankByuId(id);

        String name = bank.getAName();
        String amoneyString = String.valueOf(bank.getAMoney());
        String state = bank.getIsFroze();

        cardNumberField.setText(id);
        nameField.setText(name);
        accountBalanceField.setText(amoneyString);
        freezeStatusField.setText(state);
        cardStatusField.setText(state);
        loadmoney();

        loadAllOrders();
    }
    private void loadAllOrders() throws IOException{
        UserPojo temp = TempUser.getTUser();
        String id = temp.getuId();
        BankPojo bank = findBankByuId(id);

        setUpTableColumns();
        OrderPojo[] tempOrder = loadOrderData(id);
        orderList.clear();
        for(OrderPojo tempOrderPojo : tempOrder){
            orderList.add(tempOrderPojo);
        }
        billsTable.setItems(orderList);
    }
    private void setUpTableColumns(){
        billIdColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, String>("OrderId"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, String>("UId"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, String>("GId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, String>("GName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, Double>("Price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, Integer>("Count"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, Double>("Total"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<OrderPojo, Integer>("IsGood"));
        //borrowColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, Button>("borrowButton"));
    }
    /*
    @FXML
    private void handleRecharge() throws IOException {
        Double amount = Double.valueOf(rechargeAmountField.getText());
        String password = rechargePasswordField.getText();

        UserPojo temp = TempUser.getTUser();
        String id = temp.getuId();
        String password_right = temp.getUPwd();
        BankPojo bank = findBankByuId(temp.getUId());




        boolean success = changeMoney(bank,amount,password);

        if(success && bank.getIsFroze() == "可用") {
            boolean success2 =  CreateRechargeOrder(id,amount,"充值");
            if(success2) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("提示");
                alert.setHeaderText("充值成功！");
                alert.showAndWait();
                loadmoney();
                initialize();
                rechargeAmountField.clear();
                rechargePasswordField.clear();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("充值失败！");
            alert.showAndWait();
            rechargeAmountField.clear();
            rechargePasswordField.clear();
        }
    }
*/
//    @FXML
//    private WebView webView;
    public String testZhifuFM() {
        String attch = ""; // 附加参数
        Double amount = Double.valueOf(rechargeAmountField.getText());

        String merchantNum ="427635523302342656";
        String orderNo = "1";
        String notifyUrl = "https://www.zhifux.com/success.txt";
        String returnUrl = "https://www.baidu.com/";
        String payType = "alipay";
        String secretKey = "0e151d89a69034d2280ebc39003ff3a0";
        String sign = merchantNum + orderNo + amount + notifyUrl + secretKey;
        sign = MD5Utils.md5(sign);// md5签名
        System.out.println(sign);
        String url = "http://api-38yo53ge12bl.zhifu.fm.it88168.com/api/startOrder";
        Map<String, String> paramMap = new HashMap<>();// post请求的参数
        paramMap.put("merchantNum", merchantNum);
        paramMap.put("orderNo", orderNo);
        paramMap.put("amount", String.valueOf(amount));
        paramMap.put("notifyUrl", notifyUrl);
        paramMap.put("returnUrl", returnUrl);
        paramMap.put("payType", payType);
        paramMap.put("attch", attch);
        paramMap.put("sign", sign);
        paramMap.put("subject", "测试subject");
        paramMap.put("body", "测试商品body");
        System.out.println(JSONObject.toJSON(paramMap));
//		String result = HttpUtil.post(url, paramMap); //JDK11
//		System.out.println(result);
        String result;
        JSONObject ret = new JSONObject();
        try {
            String paramStr = toParams(paramMap);
            System.out.println(paramStr);
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httpost = new HttpPost(url + "?" + paramStr); // 设置响应头信息
            String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";
            httpost.setHeader("User-Agent",userAgent); //防止被防火墙拦截 Apache httpclient

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000)
                    .build();
            httpost.setConfig(requestConfig);

            HttpResponse retResp = httpclient.execute(httpost);
            result = EntityUtils.toString(retResp.getEntity(), "UTF-8");
            System.out.println(result);//json
            if(result.startsWith("{")) { //为了您的业务健壮性，建议简单判断或者增加下容错逻辑
                ret = JSONObject.parseObject(result);
                //直接跳转到支付页面 或者自己前端打开 data里面的 payUrl 内容，
                if(null != ret.getJSONObject("data").getString("payUrl")){
                    ret.getJSONObject("data").getString("payUrl");
                }else{
                    System.out.println(ret.getString("msg"));
                    return "";
                }
                return ret.getJSONObject("data").getString("payUrl");
            }else{
                return "";
            }
        }
        catch (ClientProtocolException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }catch (ParseException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    @FXML
    private void handleRecharge() throws IOException {
//        Double amount = Double.valueOf(rechargeAmountField.getText());
//        String password = rechargePasswordField.getText();
//
//        UserPojo temp = TempUser.getTUser();
//        String id = temp.getuId();
//        String password_right = temp.getUPwd();
//        BankPojo bank = findBankByuId(temp.getUId());
//
//        String payUrl = testZhifuFM();
//        if (!"".equals(payUrl)) {
//            // 动态创建 WebView
//            WebView webView = new WebView();
//            WebEngine webEngine = webView.getEngine();
//            webEngine.load(payUrl);
//
//            // 监听 WebView 加载状态
//            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
//                if (newState == Worker.State.SUCCEEDED) {
//                    String currentUrl = webEngine.getLocation();
//                    System.out.println("当前加载的 URL: " + currentUrl);  // 打印当前 URL 进行调试
//
//                    // 判断是否跳转到支付成功的 URL
//                    if (!currentUrl.equals(payUrl)) {  // 这里检测是否跳转到 returnUrl
//                        Platform.runLater(() -> {
//                            // 关闭支付页面
//                            Stage stage = (Stage) webView.getScene().getWindow();
//                            stage.close();
//
//                            try {
//                                boolean success = changeMoney(bank, amount, password);
//
//                                if (success && "可用".equals(bank.getIsFroze())) {
//                                    boolean success2 = CreateRechargeOrder(id, amount, "充值");
//                                    System.out.println(success2);
//                                    if (success2) {
//                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                                        alert.setTitle("提示");
//                                        alert.setHeaderText("充值成功！");
//                                        alert.showAndWait();
//                                        loadmoney(); // 可能抛出异常
//                                        initialize(); // 可能抛出异常
//                                        rechargeAmountField.clear();
//                                        rechargePasswordField.clear();
//                                    } else {
//                                        Alert alert = new Alert(Alert.AlertType.WARNING);
//                                        alert.setTitle("提示");
//                                        alert.setHeaderText("充值失败！");
//                                        alert.showAndWait();
//                                        rechargeAmountField.clear();
//                                        rechargePasswordField.clear();
//                                    }
//                                } else {
//                                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                                    alert.setTitle("提示");
//                                    alert.setHeaderText("充值失败！");
//                                    alert.showAndWait();
//                                    rechargeAmountField.clear();
//                                    rechargePasswordField.clear();
//                                }
//                            } catch (Exception e) {  // 捕获所有异常
//                                e.printStackTrace();
//                                Alert alert = new Alert(Alert.AlertType.WARNING);
//                                alert.setTitle("提示");
//                                alert.setHeaderText("充值失败！");
//                                alert.showAndWait();
//                                rechargeAmountField.clear();
//                                rechargePasswordField.clear();
//                            }
//                        });
//                    }
//                }
//            });

//            // 显示 WebView 在新的窗口中
//            Stage stage = new Stage();
//            stage.setTitle("支付页面");
//            stage.setScene(new Scene(new StackPane(webView), 1000, 800));
//
//            // 设置窗口关闭事件监听器
//            stage.setOnCloseRequest((WindowEvent event) -> {
//                // 当用户关闭支付窗口时弹出“支付失败”的提示
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("提示");
//                alert.setHeaderText("充值失败！");
//                alert.showAndWait();
//                rechargeAmountField.clear();
//                rechargePasswordField.clear();
//            });
//
//            stage.show();
//        }
    }
    public String toParams(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            value = URLEncoder.encode(value, "UTF-8");
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            }
            else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }



    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleSearch() throws IOException {
        String searchText;
        searchText = searchField.getText();

        OrderPojo order= BankSend.findOrder(searchText);


        TempOrder.setOrder(order);
        if (order == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING); // 改为明确指定 AlertType
            alert.setTitle("检索失败");
            alert.setHeaderText("未检索到该流水！");
            alert.showAndWait();
            searchField.clear();
        } else {
            // 清空现有的书籍列表并添加检索到的书籍
            orderList.clear();
            orderList.addAll(order);
            billsTable.setItems(orderList);  // 更新 TableView

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("检索成功");
            alert.setHeaderText("已成功检索到流水！");
            alert.showAndWait();
            searchField.clear();
        }
    }

    @FXML
    private void handleShowAll(){
        try {
            loadAllOrders();// 调用公共方法
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleFreeze() throws IOException {
        String password = freezePasswordField.getText();

        UserPojo temp = TempUser.getTUser();
        String password_right = temp.getUPwd();
        BankPojo bank = findBankByuId(temp.getUId());
        if(password.equals(password_right)) {
            boolean success = changeFroze(bank);
            if(success) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("提示");
                alert.setHeaderText("修改成功！");
                alert.showAndWait();
                freezePasswordField.clear();
                initialize();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("密码错误，修改失败！");
                alert.showAndWait();
                freezePasswordField.clear();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("密码错误，修改失败！");
            alert.showAndWait();
            freezePasswordField.clear();
        }

    }

    // Helper method to validate recharge input
    private boolean validateRechargeInput(String amount, String password) {
        // Add your validation logic here
        return amount != null && !amount.isEmpty() && password != null && !password.isEmpty();
    }

    // Helper method to validate freeze input
    private boolean validateFreezeInput(String password) {
        // Add your validation logic here
        return password != null && !password.isEmpty();
    }

//    @FXML
//    private void Exit() {
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
}
/**
 <!-- 账单 Tab -->
                <Tab text="账单">
                    <AnchorPane>
                        <children>
                            <VBox spacing="10" prefWidth="600.0" prefHeight="400.0" AnchorPane.topAnchor="10.0">
                                <HBox spacing="10">
                                    <Label text="搜索交易记录:"/>
                                    <TextField fx:id="searchField" promptText="输入订单编号、一卡通号或商品名称"/>
                                    <Button fx:id="search" text="搜索" onAction="#handleSearch"/>
                                </HBox>
                                <TableView fx:id="billTable" prefHeight="350.0">
                                    <columns>
                                        <TableColumn fx:id="orderidcolumn" text="订单编号" prefWidth="100"/>
                                        <TableColumn fx:id="uidcolumn" text="一卡通号" prefWidth="100"/>
                                        <TableColumn fx:id="gldcolumn" text="商品编号" prefWidth="100"/>
                                        <TableColumn fx:id="gnamecolumn" text="商品名称" prefWidth="150"/>
                                        <TableColumn fx:id="pricecolumn" text="单价" prefWidth="50"/>
                                        <TableColumn fx:id="countcolumn" text="数量" prefWidth="50"/>
                                        <TableColumn fx:id="totalcolumn" text="总价" prefWidth="50"/>
                                    </columns>
                                </TableView>
                            </VBox>
                        </children>
                    </AnchorPane>
                </Tab>
**/
