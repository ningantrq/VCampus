package com.vcampus.func.server;

import com.vcampus.controller.menu.MainMenuController;
import com.vcampus.dao.BookBorrowDao;
import com.vcampus.dao.BookDao;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;
import com.vcampus.pojo.BookPojo;
import com.vcampus.pojo.BookBorrow;
import com.vcampus.pojo.UserPojo;

import java.io.IOException;
import java.net.Socket;

public class LibRespond {
    public static void loadBookData(Message message, Socket clientsocket) throws IOException {
        BookPojo[] temp = BookDao.findAllBooks();
        ServerNet.sendObject(temp,clientsocket);
    }

    public static void searchBookData(Message message, Socket clientsocket) throws IOException {
        String bookname=(String)message.getObjectList().get(0);
        BookPojo[] temp = BookDao.findBookByBookName(bookname);
        ServerNet.sendObject(temp,clientsocket);
    }

    public static void searchByBookNumber(Message message, Socket clientsocket) throws IOException {
        String bookid=(String)message.getObjectList().get(0);
        BookPojo[] temp = BookDao.findBookBybId(bookid);
        ServerNet.sendObject(temp,clientsocket);
    }

    public static void searchByType(Message message, Socket clientsocket) throws IOException {
        String booktype=(String)message.getObjectList().get(0);
        BookPojo[] temp = BookDao.findBookByType(booktype);
        ServerNet.sendObject(temp,clientsocket);
    }
    public static void borrowBook(Message message, Socket clientsocket) throws IOException {
        BookPojo book=(BookPojo) message.getObjectList().get(0);
        boolean ok = BookDao.borrowBook(book);
        ServerNet.sendObject(ok,clientsocket);
    }

    public static void borrowBook_show(Message message, Socket clientsocket) throws IOException {
        BookBorrow borrow=(BookBorrow) message.getObjectList().get(0);
        boolean ok = BookBorrowDao.Borrow(borrow);
        ServerNet.sendObject(ok,clientsocket);
    }

    public static void loadBookBorrowData(Message message, Socket clientSocket)
    {
        BookBorrow[] temp = BookBorrowDao.findAllBookBorrows();
        ServerNet.sendObject(temp,clientSocket);
    }

    public static void loadStuBookBorrowData(Message message, Socket clientSocket)
    {
        String uid =(String)message.getObjectList().get(0);
        BookBorrow[] temp = BookBorrowDao.findBookBorrowsById(uid);
        ServerNet.sendObject(temp,clientSocket);
    }

    public static void returnBook(Message message, Socket clientsocket) throws IOException {
        BookPojo book=(BookPojo) message.getObjectList().get(0);
        boolean ok = BookDao.returnBook(book);
        ServerNet.sendObject(ok,clientsocket);
    }

    public static void returnBook_show(Message message, Socket clientsocket) throws IOException {
        BookBorrow borrow=(BookBorrow) message.getObjectList().get(0);
        boolean ok = BookBorrowDao.Return(borrow);
        ServerNet.sendObject(ok,clientsocket);
    }

    public static void addBook(Message message, Socket clientsocket) throws IOException {
        BookPojo book=(BookPojo) message.getObjectList().get(0);
        boolean ok = BookDao.addBook(book);
        ServerNet.sendObject(ok,clientsocket);
    }

    public static void deleteBook(Message message, Socket clientsocket) throws IOException {
        BookPojo book=(BookPojo) message.getObjectList().get(0);
        String bid = book.getBId();
        boolean ok = BookDao.deleteBook(bid);
        ServerNet.sendObject(ok,clientsocket);
    }

    public static void modifyBook(Message message, Socket clientsocket) throws IOException {
        BookPojo book=(BookPojo) message.getObjectList().get(0);
        boolean ok = BookDao.modifyBook(book);
        ServerNet.sendObject(ok,clientsocket);
    }

    public static void renewBook(Message message, Socket clientsocket) throws IOException {
        BookBorrow borrow=(BookBorrow) message.getObjectList().get(0);
        boolean ok;
        if(BookBorrowDao.CheckRenew(borrow))
        {ok = BookBorrowDao.Renew(borrow);}
        else{ok= false;}
        ServerNet.sendObject(ok,clientsocket);
    }

    public static void returnWarning(Message message, Socket clientsocket) throws IOException {
        BookBorrow borrow=(BookBorrow) message.getObjectList().get(0);
        boolean ok = BookBorrowDao.Renew(borrow);
        ServerNet.sendObject(ok,clientsocket);
    }



}
