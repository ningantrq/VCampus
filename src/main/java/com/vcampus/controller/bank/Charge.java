package com.vcampus.controller.bank;

import com.vcampus.controller.TempBank;
import com.vcampus.pojo.BankPojo;
import com.vcampus.pojo.UserPojo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import com.vcampus.dao.BankDao;
import com.vcampus.dao.UserDao;

import java.io.IOException;

import static com.vcampus.dao.BankDao.CreateRechargeOrder;
import static com.vcampus.dao.BankDao.changeMoney;
import static com.vcampus.dao.UserDao.findUserByuId;

public class Charge {
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField moneyInput;

    @FXML
    private Button submit;

    @FXML
    private Button exit;

    // 初始化方法
    @FXML
    private void initialize() {
        // 初始化下拉菜单
        comboBox.setItems(FXCollections.observableArrayList("学费", "水电费", "网费"));
    }

    // 处理确定按钮点击
    @FXML
    private void handleConfirmAction(ActionEvent event) {
        String feeType = comboBox.getValue();
        Double amount = Double.valueOf(moneyInput.getText());
        //System.out.println("扣费项目: " + feeType + ", 金额: " + amount + "元");
        // 这里可以添加更多的业务逻辑，例如更新数据库等
        BankPojo bankPojo = TempBank.getBank();
        String id = bankPojo.getAId();
        UserPojo user = findUserByuId(id);
        String password = user.getuPwd();
        Double amount2 = -amount;boolean success = changeMoney(bankPojo,amount2,password);


        if(success && bankPojo.getIsFroze()=="可用") {
            boolean success2 = CreateRechargeOrder(id,amount,feeType);
            if(success2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("扣费");
                alert.setContentText("扣费成功！");
                alert.showAndWait();
                moneyInput.clear();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("扣费");
            alert.setContentText("扣费失败！");
            alert.showAndWait();
        }
    }


    // 处理退出按钮点击
    @FXML
    private void handleExitAction() {
//        try {
            ((Stage)exit.getScene().getWindow()).close();

//            // 创建FXML加载器
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/bank/Monitor_Bank.fxml"));
//            Parent root = loader.load();
//
//            // 创建新窗口并设置场景
//            Stage stage = new Stage();
//            stage.setTitle("银行");
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
