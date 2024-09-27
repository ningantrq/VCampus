package com.vcampus.controller;
import com.vcampus.pojo.BookPojo;
public class TempBook2 {
    private static BookPojo selectedBook; // 静态变量

    public static void setSelectedBook(BookPojo book) {
        selectedBook = book;
    }

    public static BookPojo getSelectedBook() {
        return selectedBook;
    }
}
