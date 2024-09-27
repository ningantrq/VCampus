package com.vcampus.controller.Library;
import com.vcampus.controller.TempBook;
import com.vcampus.controller.TempBook2;
import com.vcampus.func.client.UserSend;
import com.vcampus.pojo.UserPojo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.vcampus.pojo.BookPojo;
import com.vcampus.pojo.BookBorrow;
import com.vcampus.func.client.LibSend;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import com.vcampus.dao.BookDao;
import java.awt.print.Book;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import com.vcampus.controller.TempUser;
import static com.vcampus.dao.BookDao.findBookBybId;
import static com.vcampus.func.client.LibSend.loadBookData;
import static com.vcampus.func.client.LibSend.loadBookBorrowData;

public class UpdateBook {
    @FXML
    public Button update;
    @FXML
    private Button exit;
    @FXML
    private TextField tfBookId;
    @FXML
    private TextField tfBookName;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfPlace;
    @FXML
    private TextField tfType;


    public void initialize() throws IOException {
        BookPojo b = TempBook2.getSelectedBook();

        tfBookId.setText(b.getBId());
        tfBookName.setText(b.getBName());
        tfAuthor.setText(b.getAuthor());
        tfPlace.setText(b.getPlace());
        tfType.setText(b.getType());

    }

    @FXML
    void libraryExit(MouseEvent event)
    {
        ((Stage)exit.getScene().getWindow()).close();

        // 创建FXML加载器
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/library/Monitor_Library.fxml"));
//            Parent root = loader.load();
//
//            // 创建新窗口并设置场景
//            Stage stage = new Stage();
//            stage.setTitle("主菜单");
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.show();
    }

    @FXML
    void librarySubmit(MouseEvent event) throws IOException {
        // 获取用户输入
        String idText = tfBookId.getText().trim(); // 修正：获取正确的 TextField
        String nameText = tfBookName.getText().trim();
        String authorText = tfAuthor.getText().trim();
        String placeText = tfPlace.getText().trim();
        String typeText = tfType.getText().trim();



        // 输入验证
        if (idText.isEmpty() || nameText.isEmpty() || authorText.isEmpty() || placeText.isEmpty() || typeText.isEmpty()) {
            // 如果有空字段，显示警告
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("输入错误");
            alert.setHeaderText("所有字段都是必填项，请填写所有信息。");
            alert.showAndWait();
            return; // 结束方法执行
        }
/**
        int numText;
        try {
            // 尝试将副本数转换为整数
            numText = Integer.parseInt(numTextStr);
            if (numText < TempBook2.getSelectedBook().getBorrowNum()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("输入错误");
                alert.setHeaderText("馆藏副本数必须不小于被借阅的数量。");
                alert.showAndWait();
                return; // 结束方法执行
            }
        } catch (NumberFormatException e) {
            // 如果副本数无法转换为整数，显示错误提示
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("输入错误");
            alert.setHeaderText("馆藏副本数必须是一个有效的整数。");
            alert.showAndWait();
            return; // 结束方法执行
        }**/

        // 创建书籍对象并设置属性
        BookPojo book = new BookPojo();
        book.setAuthor(authorText);
        book.setbId(idText);
        book.setbName(nameText);
        book.setPlace(placeText);
        book.setType(typeText);


        // 尝试添加书籍
        boolean flag = LibSend.modifyBook(book);

        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("更正信息");
            alert.setHeaderText("更正成功！");
            alert.showAndWait();
            ((Stage) update.getScene().getWindow()).close();
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view/library/Monitor_Library.fxml"));
//                AnchorPane page = loader.load();
//                Stage stage = new Stage();
//                stage.setResizable(false);
//                stage.setTitle("图书馆");
//                stage.setScene(new Scene(page));
//
//                stage.show();
//
//            }catch (IOException e) {e.printStackTrace();}
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("更正信息");
            alert.setHeaderText("更正失败");
            alert.showAndWait();
        }
    }

    }


