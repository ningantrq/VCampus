module com.vcampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    requires java.mail;

    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.json;
    requires qianfan;
//    requires javafx.web;
    requires fastjson;

    // 打开所有需要的包给 javafx.fxml
    opens com.vcampus.controller.login to javafx.fxml;
    opens com.vcampus.controller.menu to javafx.fxml;
    opens com.vcampus.controller.Library to javafx.fxml;
    opens com.vcampus.pojo to javafx.base , javafx.fxml;
    opens com.vcampus.controller.ai to javafx.fxml;
    opens com.vcampus.controller.bank to javafx.fxml;
    opens com.vcampus.controller.studentManagement to javafx.fxml;
    opens com.vcampus.controller.store to javafx.fxml;
    opens com.vcampus.controller.course to javafx.fxml;
//    opens com.vcampus.controller.store to javafx.fxml;
    // 如果有其他控制器包，也需要相应添加
    // opens com.vcampus.controller.[other] to javafx.fxml;

    exports com.vcampus;


}
