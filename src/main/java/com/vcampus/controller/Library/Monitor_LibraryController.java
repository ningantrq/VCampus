package com.vcampus.controller.Library;

import com.vcampus.controller.TempBook;
import com.vcampus.controller.TempBook2;
import com.vcampus.controller.TempUser;
import com.vcampus.func.client.LibSend;
import com.vcampus.pojo.UserPojo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.vcampus.pojo.BookPojo;
import com.vcampus.pojo.BookBorrow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Date;

import static com.vcampus.func.client.LibSend.loadBookBorrowData;
import static com.vcampus.func.client.LibSend.loadBookData;

public class Monitor_LibraryController {


    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button showAllBooksButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Tab Booklist;
    @FXML
    private Tab Borrowlist;
    @FXML
    private Button returnWarning;


    // 馆藏目录
    @FXML
    private TableView<BookPojo> tv;
    @FXML
    private TableView<BookBorrow> tv2;
    @FXML
    private TableColumn<BookPojo, String> bookNameColumn;
    @FXML
    private TableColumn<BookPojo, String> bookNumberColumn;
    @FXML
    private TableColumn<BookPojo, String> authorColumn;
    @FXML
    private TableColumn<BookPojo, Integer> totalNumColumn;  // 馆藏副本
    @FXML
    private TableColumn<BookPojo, Integer> borrowNumColumn;  // 已借副本
    @FXML
    private TableColumn<BookPojo, Integer> availNumColumn;  // 可借副本
    @FXML
    private TableColumn<BookBorrow, String> borrowedStudentIdColumn;
    @FXML
    private TableColumn<BookBorrow, String> borrowedBookNameColumn;
    @FXML
    private TableColumn<BookBorrow, String> borrowedBookNumberColumn;
    @FXML
    private TableColumn<BookBorrow, String> borrowTimeColumn;
    @FXML
    private TableColumn<BookBorrow, String> dueDateColumn;
    @FXML
    private TableColumn<BookPojo, String> stateColumn;
    @FXML
    private TableColumn<BookPojo, String> typeColumn;
    @FXML
    private TableColumn<BookPojo, String> placeColumn;


    private ObservableList<BookPojo> bookList = FXCollections.observableArrayList();

    private ObservableList<BookBorrow> bookborrowList = FXCollections.observableArrayList();

    private void setUpTableColumns() {
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("BName"));
        bookNumberColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("BId"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("Author"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("Type"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("Place"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("IsBorrow"));
        //borrowColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, Button>("borrowButton"));
    }

    private void setUpBorrowTableColumns() {
        borrowedStudentIdColumn.setCellValueFactory(new PropertyValueFactory<BookBorrow, String>("uId"));
        borrowedBookNameColumn.setCellValueFactory(new PropertyValueFactory<BookBorrow, String>("bName"));
        borrowedBookNumberColumn.setCellValueFactory(new PropertyValueFactory<BookBorrow, String>("bId"));
        borrowTimeColumn.setCellValueFactory(new PropertyValueFactory<BookBorrow, String>("BorrowTime"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<BookBorrow, String>("OutdateTime"));
    }

    //展示所有书本信息
    private void loadAllBooks() throws IOException {
        // 设置列的单元格工厂
        setUpTableColumns();

        // 加载所有书籍数据
        BookPojo[] temp = loadBookData();
        if (temp == null) {
            // 如果 temp 为 null，初始化为空数组，避免空指针异常
            temp = new BookPojo[0];
        }
        // 清空列表并添加新数据
        bookList.clear();
        for (BookPojo b : temp) {
            bookList.add(b);
        }

        // 更新 TableView
        tv.setItems(bookList);
    }

    //展示所有借阅信息
    private void loadAllBookBorrows() throws IOException {
        // 设置列的单元格工厂
        setUpBorrowTableColumns();

        // 加载所有书籍数据
        BookBorrow[] temp = loadBookBorrowData();
        if (temp == null) {
            // 如果 temp 为 null，初始化为空数组，避免空指针异常
            temp = new BookBorrow[0];
        }
        // 清空列表并添加新数据
        bookborrowList.clear();
        for (BookBorrow b : temp) {
            bookborrowList.add(b);
        }

        // 更新 TableView
        tv2.setItems(bookborrowList);
    }

    public void initialize() throws IOException {
        loadAllBooks();
        loadAllBookBorrows();
        tv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    void Search(MouseEvent event) throws IOException {
        String searchText;
        searchText = searchTextField.getText();
        BookPojo[] books = LibSend.searchBookData(searchText);

        TempBook.setBUser(books);
        if (books.length == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING); // 改为明确指定 AlertType
            alert.setTitle("检索失败");
            alert.setHeaderText("未检索到该书籍！");
            alert.showAndWait();
        } else {
            // 清空现有的书籍列表并添加检索到的书籍
            bookList.clear();
            bookList.addAll(books);
            tv.setItems(bookList);  // 更新 TableView

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("检索成功");
            alert.setHeaderText("已成功检索到书籍！");
            alert.setContentText("共检索到 " + books.length + " 本书籍。");
            alert.showAndWait();
        }


    }

    @FXML
    void showAll(MouseEvent event) {
        try {
            loadAllBooks(); // 调用公共方法
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    void libraryExit(MouseEvent event) {
//        try {
//            ((Stage) exitButton.getScene().getWindow()).close();
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

    @FXML
    void handleAdd(MouseEvent event) {
//        ((Stage) addButton.getScene().getWindow()).close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/library/AddBook.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("图书入库");
            stage.setScene(new Scene(page));
            stage.showAndWait();

            loadAllBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDelete(MouseEvent event) {
        try {
            // 获取当前选中的书籍
            BookPojo selectedBook = tv.getSelectionModel().getSelectedItem();

            // 检查是否有选中的书籍
            if (selectedBook == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("未选择书籍");
                alert.setContentText("请先选择要出库的书籍！");
                alert.showAndWait();
                return;
            }
            if (selectedBook.getBorrowNum() != 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("出库失败");
                alert.setContentText("该书仍正被借阅中，无法出库！");
                alert.showAndWait();
                return;
            }
            String book = selectedBook.getBName();
            boolean success = LibSend.deleteBook(selectedBook);
            System.out.println(success);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("提示");
                alert.setContentText("出库成功！");
                alert.showAndWait();
                loadAllBooks();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("提示");
                alert.setContentText("出库失败！");
                alert.showAndWait();
                loadAllBooks();
            }
        } catch (Exception e) {
        }
    }

    @FXML
    void handleUpdate(MouseEvent event) {

        try {
            // 获取当前选中的书籍
            BookPojo selectedBook = tv.getSelectionModel().getSelectedItem();
            TempBook2.setSelectedBook(selectedBook);


            // 检查是否有选中的书籍
            if (selectedBook == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("未选择书籍");
                alert.setContentText("请先选择要更正的书籍！");
                alert.showAndWait();
                return;
            }
//            ((Stage) updateButton.getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/vcampus/view/library/UpdateBook.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("图书信息更正");
            stage.setScene(new Scene(page));

            stage.showAndWait();

            loadAllBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //展示所有借阅信息
    @FXML
    private void handleWarning(MouseEvent event) throws IOException {
// 获取当前时间
        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();

        // 日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 遍历所有借书记录
        for (BookBorrow borrow : bookborrowList) {
            try {
                // 解析截止时间
                Date dueDate = dateFormat.parse(borrow.getOutdateTime());

                // 计算剩余天数
                long diffInMillis = dueDate.getTime() - currentDate.getTime();
                long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);

                // 如果剩余天数小于等于 2 天，发送提醒邮件
                if (diffInDays <= 2) {
                    String userEmail = borrow.getUId() + "@qq.com"; // 构建邮箱地址
                    sendReminderEmail(userEmail, borrow); // 发送邮件提醒
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("提示");
                    alert.setContentText("还书提醒邮件（到期剩余2天内）已发送至学生邮箱！");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 发送邮件的辅助方法
    private void sendReminderEmail(String toEmail, BookBorrow borrow) {
        // 配置邮件服务器
        String fromEmail = "1304549157@qq.com"; // 替换为你的邮箱
        String password = "wggdyqwidlzyjghf"; // 替换为你的邮箱密码

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.qq.com"); // 使用QQ邮箱的SMTP服务器
        props.put("mail.smtp.port", "587");

        // 创建邮件会话
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // 创建邮件对象
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("图书归还提醒");

            // 邮件内容
            String content = "亲爱的用户，您借阅的书籍 \"" + borrow.getBName() + "\" 即将到期，请及时归还。截止时间: " + borrow.getOutdateTime() + "。";
            message.setText(content);

            // 发送邮件
            Transport.send(message);

            System.out.println("提醒邮件已发送至: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
