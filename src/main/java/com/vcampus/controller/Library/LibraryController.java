package com.vcampus.controller.Library;

import com.vcampus.controller.TempBook;

import com.vcampus.pojo.UserPojo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.vcampus.pojo.BookPojo;
import com.vcampus.pojo.BookBorrow;
import com.vcampus.func.client.LibSend;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Objects;

import com.vcampus.controller.TempUser;

import static com.vcampus.dao.BookDao.findBookBybId;
import static com.vcampus.func.client.LibSend.*;

public class LibraryController {

    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button showAllBooksButton;
    @FXML
    private Button borrowButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button renewButton;
    @FXML
    private Tab Booklist;
    @FXML
    private Tab Borrowlist;


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
    private TableColumn<BookPojo, String> stateColumn;
    @FXML
    private TableColumn<BookPojo, String> typeColumn;
    @FXML
    private TableColumn<BookPojo, String> placeColumn;
    @FXML
    private ComboBox<String> searchCriteriaComboBox;
    @FXML
    private TableColumn<BookBorrow, String> borrowedBookNameColumn;
    @FXML
    private TableColumn<BookBorrow, String> borrowedBookNumberColumn;
    @FXML
    private TableColumn<BookBorrow, String> borrowTimeColumn;
    @FXML
    private TableColumn<BookBorrow, String> dueDateColumn;



    private ObservableList<BookPojo> bookList = FXCollections.observableArrayList();

    private ObservableList<BookBorrow> bookborrowList = FXCollections.observableArrayList();

    private void setUpTableColumns(){
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("BName"));
        bookNumberColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("BId"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("Author"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("Type"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("Place"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, String>("IsBorrow"));
        //borrowColumn.setCellValueFactory(new PropertyValueFactory<BookPojo, Button>("borrowButton"));
    }
    private void setUpBorrowTableColumns(){
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

        // 清空列表并添加新数据
        bookList.clear();
        for (BookPojo b : temp) {
            bookList.add(b);
        }

        // 更新 TableView
        tv.setItems(bookList);
    }
    // 展示某学生所有借阅信息
    private void loadStuBookBorrows() throws IOException {
        // 设置列的单元格工厂
        setUpBorrowTableColumns();
        UserPojo user = TempUser.getTUser();
        String uid = user.getuId();
        // 加载所有书籍数据
        BookBorrow[] temp = loadStuBookBorrowData(uid);

        // 检查 temp 是否为 null
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
        loadStuBookBorrows();
        tv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    void Search(MouseEvent event) throws IOException {
        String searchText;
        String criteria = searchCriteriaComboBox.getValue();
        searchText = searchTextField.getText();
        BookPojo[] books = new BookPojo[0];
        // 根据不同的搜索条件调用相应的搜索函数
        switch (criteria) {
            case "书名":
                books = searchBookData(searchText);
                break;
            case "编号":
                books = searchByBookNumber(searchText);
                break;
            case "类型":
                books = searchByType(searchText);
                break;

        }
        if (books == null) {
            books = new BookPojo[0]; // 如果 books 为空，则初始化为空数组
        }
        TempBook.setBUser(books);
        // 检查 books 是否为 null，防止 NullPointerException

        if(books.length == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 改为明确指定 AlertType
            alert.setTitle("检索失败");
            alert.setHeaderText("未检索到该书籍！");
            alert.showAndWait();
        }else {
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
    void showAll(MouseEvent event)
    {
        try {
            loadAllBooks(); // 调用公共方法
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @FXML
//    void libraryExit(MouseEvent event)
//    {
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
    @FXML
    private void handleBorrow(MouseEvent event) {
        try {
            // 获取当前选中的书籍
            BookPojo selectedBook = tv.getSelectionModel().getSelectedItem();
            // 检查是否有选中的书籍
            if (selectedBook == null) {
                showAlert(Alert.AlertType.WARNING, "未选择书籍", "请先选择要借阅的书籍！");
                return;
            }
            String tt = selectedBook.getIsBorrow();
            String book = selectedBook.getBName();

            if(Objects.equals((String) tt, "未借出"))
            {
                boolean success = borrowBook(selectedBook);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("提示");
                alert.setContentText("借阅成功！");
                alert.showAndWait();
                loadAllBooks();


                //我的借阅中更新显示
                Date now = new Date();
                BookBorrow borrow = new BookBorrow();
                borrow.setbId(selectedBook.getBId());
                UserPojo user = TempUser.getTUser();
                borrow.setUId(user.getuId());
                borrow.setBorrowTime(borrowtimeset(now));
                borrow.setOutdateTime(returntimeset(now));

                boolean borrowsuccess = borrowBook_show(borrow);//在数据库中成功加入该跳借阅信息
                loadStuBookBorrows();
            }
            if(Objects.equals((String) tt, "已借出")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("提示");
                alert.setContentText("借阅失败！");
                alert.showAndWait();
                loadAllBooks();
            }
        }
        catch (Exception e) {}
    }

    @FXML
    private void handleReturn(MouseEvent event) {
        try {
            // 获取当前选中的书籍
            BookBorrow selectedBookBorrow = tv2.getSelectionModel().getSelectedItem();
            BookPojo[] temp2 = findBookBybId(selectedBookBorrow.getBId());
            BookPojo temp = temp2[0];
            // 检查是否有选中的书籍
            if (selectedBookBorrow == null) {
                showAlert(Alert.AlertType.WARNING, "未选择书籍", "请先选择要归还的书籍！");
                return;
            }
            String book = selectedBookBorrow.getBName();
            boolean success = returnBook(temp);
            System.out.println(success);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("提示");
                alert.setContentText("归还成功！");
                alert.showAndWait();

                boolean success2 = returnBook_show(selectedBookBorrow);
                loadAllBooks();
                loadStuBookBorrows();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText("提示");
                alert.setContentText("归还失败！");
                alert.showAndWait();
                loadAllBooks();
            }
        }
        catch (Exception e) {}
    }
    @FXML
    private void handleRenew(MouseEvent event) throws IOException {
        // 获取当前选中的书籍
        BookBorrow selectedBookBorrow = tv2.getSelectionModel().getSelectedItem();

        // 检查是否有选中的书籍
        if (selectedBookBorrow == null) {
            showAlert(Alert.AlertType.WARNING, "未选择书籍", "请先选择要续借的书籍！");
            return;
        }
        BookPojo[] temp2 = findBookBybId(selectedBookBorrow.getBId());
        BookPojo temp = temp2[0];
        String book = selectedBookBorrow.getBName();
        boolean success = renewBook(selectedBookBorrow);
        if(success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("提示");
            alert.setContentText("续借成功！");
            alert.showAndWait();
            loadStuBookBorrows();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提示");
            alert.setHeaderText("续借失败");
            alert.setContentText("还书日期超过 14 天！");
            alert.showAndWait();
            loadStuBookBorrows();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String borrowtimeset(Date now) {
        // 定义时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 格式化当前时间为字符串！！原先不是字符串！！
        return sdf.format(now);

    }

    private String returntimeset(Date now) {
        // 定义时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 格式化当前时间为字符串！！原先不是字符串！！
        String returnTime = sdf.format(now);
        //归还时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        // 格式化归还时间为字符串
        returnTime = sdf.format(calendar.getTime());
        return returnTime;
    }

}
