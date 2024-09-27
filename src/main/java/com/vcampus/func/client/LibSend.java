package com.vcampus.func.client;
import java.util.List;
import java.util.ArrayList;

import com.vcampus.message.Message;
import com.vcampus.net.ClientNet;
import com.vcampus.pojo.BookBorrow;
import com.vcampus.pojo.BookPojo;
import java.io.IOException;

public class LibSend {
    public static BookPojo[] loadBookData() throws IOException {
        Message temp=new Message();
        temp.changeOperateType("GetBookData");
        BookPojo[] res= ClientNet.sendAndReceive(temp,new BookPojo[]{});
        return res;
    }

    public static BookPojo[] searchBookData(String bookname) throws IOException {

            Message temp = new Message();
            temp.addObject(bookname);
            temp.changeOperateType("SearchBookData");
            BookPojo[] res = ClientNet.sendAndReceive(temp, new BookPojo[]{});
            return res;


    }

    public static BookPojo[] searchByBookNumber(String bookId) throws IOException {

        Message temp = new Message();
        temp.addObject(bookId);
        temp.changeOperateType("SearchByBookNumber");
        BookPojo[] res = ClientNet.sendAndReceive(temp, new BookPojo[]{});
        return res;
    }

    public static BookPojo[] searchByType(String bookType) throws IOException {

        Message temp = new Message();
        temp.addObject(bookType);
        temp.changeOperateType("SearchByType");
        BookPojo[] res = ClientNet.sendAndReceive(temp, new BookPojo[]{});
        return res;
    }
    // 在书本数据库中操作借阅信息
    public static boolean borrowBook(BookPojo book) {
        try {
            // 构建请求消息
            Message temp = new Message();
            temp.addObject(book); // 将要借阅的书籍对象添加到消息中
            temp.changeOperateType("BorrowBook"); // 设置操作类型为 BorrowBook

            Boolean ok = ClientNet.sendAndReceive(temp, false);

            return ok;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 如果出现异常，返回 false
        }
    }

    // 在借阅信息数据库中操作借阅信息
    public static boolean borrowBook_show(BookBorrow bookborrow) {
        try {
            // 构建请求消息
            Message temp = new Message();
            temp.addObject(bookborrow);
            temp.changeOperateType("BorrowBook_show");

            Boolean ok = ClientNet.sendAndReceive(temp, false);

            return ok;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 如果出现异常，返回 false
        }
    }

    //显示所有借阅信息
    public static BookBorrow[] loadBookBorrowData() throws IOException {
        Message temp=new Message();
        temp.changeOperateType("LoadBookBorrowData");
        BookBorrow[] res= ClientNet.sendAndReceive(temp,new BookBorrow[]{});
        return res;
    }

    //显示某学生所有借阅信息
    public static BookBorrow[] loadStuBookBorrowData(String uId) throws IOException {
        Message temp=new Message();
        temp.addObject(uId);
        temp.changeOperateType("LoadStuBookBorrowData");
        BookBorrow[] res= ClientNet.sendAndReceive(temp,new BookBorrow[]{});
        return res;
    }

    // 在书本数据库中操作归还信息
    public static boolean returnBook(BookPojo book) {
        try {
            // 构建请求消息
            Message temp = new Message();
            temp.addObject(book); // 将要借阅的书籍对象添加到消息中
            temp.changeOperateType("ReturnBook"); // 设置操作类型为 BorrowBook

            Boolean ok = ClientNet.sendAndReceive(temp, false);

            return ok;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 如果出现异常，返回 false
        }
    }

    // 在借阅信息数据库中操作归还信息
    public static boolean returnBook_show(BookBorrow bookborrow) {
        try {
            // 构建请求消息
            Message temp = new Message();
            temp.addObject(bookborrow);
            temp.changeOperateType("ReturnBook_show");

            Boolean ok = ClientNet.sendAndReceive(temp, false);

            return ok;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 如果出现异常，返回 false
        }
    }

    public static boolean addBook(BookPojo book) throws IOException {
        Message temp = new Message();
        temp.addObject(book);
        temp.changeOperateType("AddBook");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }

    public static boolean deleteBook(BookPojo book) throws IOException {
        Message temp = new Message();
        temp.addObject(book);
        temp.changeOperateType("DeleteBook");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }

    public static boolean modifyBook(BookPojo book) throws IOException {
        Message temp = new Message();
        temp.addObject(book);
        temp.changeOperateType("ModifyBook");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }

    public static boolean renewBook(BookBorrow bookborrow) throws IOException {
        Message temp = new Message();
        temp.addObject(bookborrow);
        temp.changeOperateType("RenewBook");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }

    public static boolean returnWarning(BookBorrow bookborrow) throws IOException {
        Message temp = new Message();
        temp.addObject(bookborrow);
        temp.changeOperateType("ReturnWarning");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }
}
