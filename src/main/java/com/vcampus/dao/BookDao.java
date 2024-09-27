package com.vcampus.dao;

import com.vcampus.pojo.BookPojo;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * 图书馆模块书籍DAO
 */
public class BookDao {

    // Database URL
    static String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
    private static final String DB_URL = url;

    /**
     * 根据书号查询书
     * @param bookId 书号
     * @return 查询到的书本对象
     */
    public static BookPojo[] findBookBybId(String bookId) {

        String sql = "SELECT * FROM tblBook WHERE bookId = ?";
        BookPojo[] books = new BookPojo[0];

        try (Connection con = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, bookId);
            ResultSet res = pstmt.executeQuery();

            books = extractBooksFromResultSet(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
    /**
     * 根据名字查询书
     *
     * @param bookname
     * @return book
     */
    public static BookPojo[] findBookByBookName(String bookname) {
        String sql = "SELECT * FROM tblBook WHERE bookname = ?";
        BookPojo[] books = new BookPojo[0];

        try (Connection con = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, bookname);
            ResultSet res = pstmt.executeQuery();

            books = extractBooksFromResultSet(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
    private static BookPojo[] extractBooksFromResultSet(ResultSet res) throws SQLException {
        // 使用列表动态存储结果
        List<BookPojo> books = new ArrayList<>();

        // 直接遍历结果集
        while (res.next()) {
            // 创建 BookPojo 对象并添加到列表中
            BookPojo book = new BookPojo(
                    res.getString("bookId"),
                    res.getString("bookname"),
                    res.getString("type"),
                    res.getString("author"),
                    res.getString("place"),
                    res.getString("isBorrow")
            );
            books.add(book);
        }

        // 将列表转换为数组
        return books.toArray(new BookPojo[0]);
    }
    /**
     * 根据类别查询书
     *
     * @param type
     * @return book
     */
    public static BookPojo[] findBookByType(String type) {
        String sql = "SELECT * FROM tblBook WHERE type = ?";
        BookPojo[] books = new BookPojo[0];

        try (Connection con = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, type);
            ResultSet res = pstmt.executeQuery();

            books = extractBooksFromResultSet(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }



    /**
     * 向数据库中新增插入书本
     *
     * @param book 需新增的书本
     * @return 新增插入是否成功, 若数据库中原本已存在该书籍，则返回false，不执行新增插入操作
     */
    public static boolean addBook(BookPojo book) {
        String sqlString = "INSERT INTO tblBook(bookId,bookname,type,author,place,isBorrow) VALUES (?, ?, ?, ?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {


            pstmt.setString(1, book.getBId());
            pstmt.setString(2, book.getBName());
            pstmt.setString(3, book.getType());
            pstmt.setString(4, book.getAuthor());
            pstmt.setString(5, book.getPlace());
            pstmt.setString(6, book.getIsBorrow());

            int num = pstmt.executeUpdate();
            return num > 0;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据书号删除书
     *
     * @param bId 书号
     * @return 删除操作是否成功，如该书原本在数据库中不存在，则返回false，并不进行删除操作
     */
    public static boolean deleteBook(String bId) {
        String sqlString = "DELETE FROM tblBook WHERE bookId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        // 数据库地址，绝对路径

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, bId);

            pstmt.executeUpdate(); // 执行删除操作

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 修改书籍信息()
     *
     * @param book 修改的书籍（存储的是修改后新书的信息）
     * @return 修改操作是否成功，如该书原本在数据库中不存在，则返回false，并不进行修改操作
     */
    public static boolean modifyBook(BookPojo book) {
        String sqlString = "UPDATE tblBook SET bookname = ?, type = ?, author = ?, place = ? WHERE bookid = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, book.getBName());
            pstmt.setString(2, book.getType());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPlace());
            pstmt.setString(5, book.getBId());



            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据书号借书，将该书籍在馆册数减一，被借次数加一
     *
     * @param book 书号
     * @return 借书操作是否成功，如该书原本在数据库中不存在，或在馆册数不足以借阅则返回false，并不进行借书操作
     */
    public static boolean borrowBook(BookPojo book) {
        String sqlString = "UPDATE tblBook SET isBorrow = ? WHERE bookId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {


            String s="已借出";
            //  else {
            pstmt.setString(1, s);
            pstmt.setString(2, book.getBId());
            // }
            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据书号还书，将该书籍在馆册数加一
     *
     * @param book 书号
     * @return 还书操作是否成功，如该书原本在数据库中不存在，或还书后在馆册数大于馆藏册数，则返回false，并不进行借书操作
     */
    public static boolean returnBook(BookPojo book) {
        String sqlString = "UPDATE tblBook SET isBorrow = ? WHERE bookId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            /**
             if(book.getFreeNum()<1)
             {
             con.close();
             System.out.println("借书失败：当前书目已无剩余");
             return false; //
             }
             **/

            String s="未借出";
            //  else {
            pstmt.setString(1, s);
            pstmt.setString(2, book.getBId());
            // }
            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 查询所有书籍
     *
     * @return 查询到的所有书本对象，返回一个Book类的数组
     */
    public static BookPojo[] findAllBooks() {
        String sqlString = "SELECT * FROM tblBook";
        List<BookPojo> booklist = new ArrayList<>(); // 用于存储结果的列表
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            // 逐行读取结果集
            while (res.next()) {

                BookPojo book = new BookPojo(
                        res.getString("bookId"),
                        res.getString("bookname"),
                        res.getString("type"),
                        res.getString("author"),
                        res.getString("place"),
                        res.getString("isBorrow")

                );
                booklist.add(book); // 添加到列表
            }
            //System.out.println("length " + studentInfoList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        //System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return booklist.isEmpty() ? null : booklist.toArray(new BookPojo[0]);
    }


}