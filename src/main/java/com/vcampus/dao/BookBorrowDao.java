package com.vcampus.dao;

//package view.server.DAO;

//import view.client.Library.BookBorrow;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import com.vcampus.dao.BookDao;
import com.vcampus.pojo.BookBorrow;
import com.vcampus.pojo.BookPojo;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

import static com.vcampus.dao.BookDao.findBookBybId;

/**
 * 图书馆模块借书DAO
 */
public class BookBorrowDao {

    /**
     * 查找该用户目前借了那些书
     *
     * @param uId 查询用户一卡通号
     * @return 该用户的所有现存借书记录，已BookBorrow[]的形式返回
     */

    public static BookBorrow[] findBookBorrowsById(String uId) {
        String sqlString = "SELECT * FROM tblBookBorrow WHERE uId = ?";
        ArrayList<BookBorrow> bookBorrowList = new ArrayList<>(); // 使用 ArrayList 收集结果

String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";


        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, uId); // 设置参数，防止 SQL 注入

            try (ResultSet res = pstmt.executeQuery()) {
                // 遍历结果集并将每条记录添加到 ArrayList
                while (res.next()) {
                    BookBorrow bookBorrow = new BookBorrow(
                            res.getString("uId"),
                            res.getString("bookId"),
                            res.getString("borrowTime"),
                            res.getString("outdateTime")
                    );
                    bookBorrowList.add(bookBorrow);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将 ArrayList 转换为数组
        return bookBorrowList.toArray(new BookBorrow[0]);
    }
    /**
     * 借书，在BookBorrow表中新增该用户对该书本的借书记录
     *
     * @param borrowRecord 借书操作记录（包涵操作号、一卡通号、书号、操作时间、操作类型、备注）
     * @return 借书操作是否成功，如该书已被该用户借阅，则返回false，并不进行借书操作
     */
    public static boolean Borrow(BookBorrow borrowRecord) {
        String sqlString = "INSERT INTO tblBookBorrow(uId, bookId, borrowTime, outdateTime) VALUES (?, ?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            /**
             String bookId=borrowRecord.getbId();
             Book book=findBookBybId(bookId);
             if(book.getFreeNum()<1)
             {
             con.close();
             System.out.println("借书失败：当前书目已无剩余");
             return false; //
             }
             **/
            //else {
            pstmt.setString(1, borrowRecord.getUId());
            pstmt.setString(2, borrowRecord.getBId());
            pstmt.setString(3, borrowRecord.getBorrowTime());
            pstmt.setString(4, borrowRecord.getOutdateTime());

            int num = pstmt.executeUpdate();
            return num > 0;
            //}

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 还书，在BookBorrow表中删除该用户对该书本的借书记录
     * @param borrowRecord 还书操作记录（包涵操作号、一卡通号、书号、操作时间、操作类型、备注）
     * @return 还书操作是否成功，如该书还未被该用户借阅，或者操作日期晚于借阅过期时间，则返回false，并不进行还书操作
     */
    public static boolean Return(BookBorrow borrowRecord) {
        String sqlString = "DELETE FROM tblBookBorrow WHERE uId = ? AND bookId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {


            pstmt.setString(1, borrowRecord.getUId());
            pstmt.setString(2, borrowRecord.getBId());

            pstmt.executeUpdate(); // 执行删除操作

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 续借，在BookBorrow表中修改该用户对该书本的借书情况，将过期时间改为当前操作时间加10；
     *
     * @param renewRecord 续借操作记录（包涵操作号、一卡通号、书号、操作时间、操作类型、备注）
     * @return 续借操作是否成功，如该书还未被该用户借阅，则返回false，或者操作日期晚于借阅过期时间，并不进行续借操作
     */

    public static boolean Renew(BookBorrow renewRecord) {
        // 更新时分秒的日期格式
        String sqlString = "UPDATE tblBookBorrow SET outdateTime = ? WHERE uId = ? AND bookId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            // 使用包含时分秒的日期格式
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 格式化当前时间为字符串！！原先不是字符串！！
            Date date = dateFormat1.parse(renewRecord.getOutdateTime());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 7); // 延长7天
            Date newdate = calendar.getTime();

            // 将新的日期格式化为包含时分秒
            String newdate_String = dateFormat1.format(newdate);

            // 设置 SQL 语句中的参数
            pstmt.setString(1, newdate_String);
            pstmt.setString(2, renewRecord.getUId());
            pstmt.setString(3, renewRecord.getBId());

            // 执行更新
            int num = pstmt.executeUpdate();
            return num > 0; // 返回更新结果

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param borrowRecord
     * @return
     */
    public static boolean CheckRenew(BookBorrow borrowRecord) {
        String sqlString = "SELECT borrowTime, outdateTime FROM tblBookBorrow WHERE uId = ? AND bookId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, borrowRecord.getUId());
            pstmt.setString(2, borrowRecord.getBId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String borrowTime = rs.getString("borrowTime");
                    String outdateTime = rs.getString("outdateTime");

                    // 解析日期
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDate borrowDate = LocalDate.parse(borrowTime,formatter);
                    LocalDate returnDate = LocalDate.parse(outdateTime,formatter);


                    long daysBetween = ChronoUnit.DAYS.between(borrowDate, returnDate);
                    if (daysBetween <= 14) {
                        System.out.println("还书日期在允许范围内");
                        return true;
                    } else {
                        System.out.println("还书日期超过 14 天");
                        return false;
                    }
                } else {
                    System.out.println("未找到借书记录");
                    return false; // 未找到记录
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 发生异常时返回 false
        }
    }

    /**
     * 查找所有用户的当前借书情况（管理员系统可用）
     *
     * @return 所有用户的当前借书情况，以BookHol[]的形式返回
     */
    public static BookBorrow[] findAllBookBorrows() {
        String sqlString = "SELECT * FROM tblBookBorrow";
        List<BookBorrow> bookrecordlist = new ArrayList<>(); // 用于存储结果的列表
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            // 逐行读取结果集
            while (res.next()) {

                BookBorrow bookrecord = new BookBorrow(
                        res.getString("uId"),
                        res.getString("bookId"),
                        res.getString("borrowTime"),
                        res.getString("outdateTime")
                );
                bookrecordlist.add(bookrecord); // 添加到列表
            }
            //System.out.println("length " + studentInfoList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        //System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return bookrecordlist.toArray(new BookBorrow[0]);

    }

    //还书日期与现在日期相差小于两天，返回true
    public static boolean CheckReturnDateWithinTwoDays(BookBorrow borrowRecord) {
        String sqlString = "SELECT outdateTime FROM tblBookBorrow WHERE uId = ? AND bookId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, borrowRecord.getUId());
            pstmt.setString(2, borrowRecord.getBId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String outdateTime = rs.getString("outdateTime");

                    // 解析还书日期
                    LocalDate returnDate = LocalDate.parse(outdateTime);
                    LocalDate currentDate = LocalDate.now();

                    // 检查还书日期与当前日期相差是否在 2 天内
                    long daysBetween = ChronoUnit.DAYS.between(currentDate, returnDate);
                    if (Math.abs(daysBetween) <= 2) {
                        System.out.println("还书日期与当前日期相差在 2 天内");
                        return true; // 相差在 2 天内
                    } else {
                        System.out.println("还书日期与当前日期相差超过 2 天");
                        return false; // 超过 2 天
                    }
                } else {
                    System.out.println("未找到借书记录");
                    return false; // 未找到记录
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 发生异常时返回 false
        }
    }
}



