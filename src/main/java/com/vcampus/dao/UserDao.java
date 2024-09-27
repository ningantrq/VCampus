////package xxxx.xxxx.DAO;
//package com.vcampus.dao;
///**DAO文件的路径，可以参考demo里的格式**/
//import com.vcampus.pojo.User;
////import xxxx.xxxx.User;
///**导入该DAO所需要的类(例如UserDao、studentInfoDao需要user类，BookDao、BookBorrowDao需要Book类等），格式参考demo**/
//
//import java.sql.*;
//
//public class UserDao {
//
//    /**
//     * 通过用户ID查找用户类
//     *
//     * @param uId 用户ID
//     * @return 对应的User类，否则返回null
//     */
//    public static User findUserByuId(String uId) {
//
//        String sqlString = "select * from tblUser where uId = '" + uId + "'";
//        User user = new User();
////sqlite方法
//        Connection con = null;
//        try {
//            String url = "jdbc:sqlite:D:\Ning An\VCampus\VCampus\VCampus\src\main\java\com\vcampus\db\my_db.db";
//            // create a connection to the database
//            con = DriverManager.getConnection(url);
//
//            System.out.println("Connection to SQLite has been established.");
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        //sqlite方法结束
///**
// try {
// Class.forName("com.hxtt.sql.access.AccessDriver");//导入Access驱动文件，本质是.class文件
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// }
//        try {
// Connection con = DriverManager.getConnection("jdbc:Access:///.\\Database\\vCampus.mdb", "", "");
// //与数据库建立连接，getConnection()方法第一个参数为jdbc:Access:///+文件总路径,第二个参数是用户名，第三个参数是密码（Access是没有用户名和密码此处为空字符串）
// **/
//        try {
//            Statement sta = con.createStatement();
//            ResultSet res = sta.executeQuery(sqlString);
//
//            if (!res.next()) {
//                return null;
//            }
//
//            res.next();
//
//
//            //根据User类修改函数名字
//            user.setuId(res.getString(1));
//            user.setuPwd(res.getString(2));
//            user.setuRole(res.getString(3));
//
//            con.close();//关闭数据库连接
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return user;
//    }
//
///**
// * 传入用户的ID，密码，角色，创建用户
// * @param user 创建用户的信息
// * @return 是否创建成功
// */
//    public static boolean CreateUser(User user) {
//
//
//        //后续.根据user类修改函数名
//        String sqlString = "insert into tblUser(uId,uPwd,uRole) values('" + user.getuId() + "','" + user.getuPwd() + "','" + user.getuRole() + "')";
//
//        try {
//            Class.forName("com.hxtt.sql.access.AccessDriver");//导入Access驱动文件，本质是.class文件
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            Connection con = DriverManager.getConnection("jdbc:Access:///.\\Database\\vCampus.mdb", "", "");
//            //与数据库建立连接，getConnection()方法第一个参数为jdbc:Access:///+文件总路径,第二个参数是用户名 ，第三个参数是密码（Access是没有用户名和密码此处为空字符串）
//            Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            int num = sta.executeUpdate(sqlString);
//
//            con.close();//关闭数据库连接
//            if (num == 0) return false;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
///**
// * 修改用户信息
// * @return 是否修改成功
// **/
//    public static boolean updateUser(User user) {
//        String sqlString = "update tblUser set uPwd = '" + user.getuPwd() + "', uRole = '" + user.getuRole() + "' whrere uId = '" + user.getuId() + "'";
//
//        try {
//            Class.forName("com.hxtt.sql.access.AccessDriver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Connection con = DriverManager.getConnection("jdbc:Access:///.\\Database\\vCampus.mdb", "", "");
//            Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//            int num = sta.executeUpdate(sqlString);
//            if (num == 0) return false; // 如果没有更新任何行，则返回false
//
//            con.close();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//}
//
///**
// * 查找全部老师，用于下拉框
// * @return 全部老师的User[]
// */
//
//    /*
//    public User[] findAllTeachers(){
//        String sqlString = "select * from tblUser where uRole = 'TC'";
//        User[] allTeachers = new User[10];
//
//        try {
//            Class.forName("com.hxtt.sql.access.AccessDriver");//导入Access驱动文件，本质是.class文件
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            Connection con = DriverManager.getConnection("jdbc:Access:///.\\Database\\vCampus.mdb", "", "");
//            //与数据库建立连接，getConnection()方法第一个参数为jdbc:Access:///+文件总路径,第二个参数是用户名，第三个参数是密码（Access是没有用户名和密码此处为空字符串）
//            Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            ResultSet res = sta.executeQuery(sqlString);
//
//            int count = 0;
//            while (res.next()) {
//                count++;
//            }//统计一共查找到多少条数据
//            res.beforeFirst();
//
//            if (count == 0) {
//                return null;
//            }//若未找教师账户，则返回null
//
//            allTeachers = new User[count];
//            int index = 0;
//            while (res.next()) {//不断的移动光标到下一个数据
//                allTeachers[index] = new User(res.getString(1), res.getString(2), res.getString(3));
//                index++;
//            }
//
//            con.close();//关闭数据库连接
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return allTeachers;
//    }
//*/
///**
// * 查找全部学生，用于下拉框
// * @return 全部学生的User[]
// */
//
///*
//    public User[] findAllStudents(){
//        String sqlString = "select * from tblUser where uRole = 'ST'";
//        User[] allStudents = new User[10];
//
//        try {
//            Class.forName("com.hxtt.sql.access.AccessDriver");//导入Access驱动文件，本质是.class文件
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            Connection con = DriverManager.getConnection("jdbc:Access:///.\\Database\\vCampus.mdb", "", "");
//            //与数据库建立连接，getConnection()方法第一个参数为jdbc:Access:///+文件总路径,第二个参数是用户名，第三个参数是密码（Access是没有用户名和密码此处为空字符串）
//            Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            ResultSet res = sta.executeQuery(sqlString);
//
//            int count = 0;
//            while (res.next()) {
//                count++;
//            }//统计一共查找到多少条数据
//            res.beforeFirst();
//
//            if (count == 0) {
//                return null;
//            }//若未找教师账户，则返回null
//
//            allStudents = new User[count];
//            int index = 0;
//            while (res.next()) {//不断的移动光标到下一个数据
//                allStudents[index] = new User(res.getString(1), res.getString(2), res.getString(3));
//                index++;
//            }
//
//            con.close();//关闭数据库连接
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return allStudents;
//    }
//
//}
//
// */
package com.vcampus.dao;

import com.vcampus.pojo.UserPojo;

import java.sql.*;
import java.io.File;

public class UserDao {

    public static UserPojo findUserByuId(String uId) {
        String sqlString = "SELECT * FROM tblUser WHERE uId = ?";
        UserPojo user = null;

String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, uId);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                user = new UserPojo();
                user.setuId(res.getString("uId"));
                user.setuPwd(res.getString("uPwd"));
                user.setuRole(res.getString("uRole"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean createUser(UserPojo user) {
        String sqlString = "INSERT INTO tblUser(uId, uPwd, uRole) VALUES (?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, user.getuId());
            pstmt.setString(2, user.getuPwd());
            pstmt.setString(3, user.getuRole());

            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUser(UserPojo user) {
        String sqlString = "UPDATE tblUser SET uPwd = ?, uRole = ? WHERE uId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, user.getuPwd());
            pstmt.setString(2, user.getuRole());
            pstmt.setString(3, user.getuId());

            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserPojo[] findAllTeachers() {
        String sqlString = "SELECT * FROM tblUser WHERE uRole = 'TC'";
        UserPojo[] allTeachers = null;
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            res.last(); // 移动到最后一行以获取行数
            int count = res.getRow(); // 获取行数
            res.beforeFirst(); // 移动到结果集的开始

            if (count == 0) {
                return null; // 没有找到教师
            }

            allTeachers = new UserPojo[count];
            int index = 0;
            while (res.next()) {
                allTeachers[index] = new UserPojo(res.getString("uId"), res.getString("uPwd"), res.getString("uRole"));
                index++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allTeachers;
    }

    // 示例：查找全部学生
    public UserPojo[] findAllStudents() {
        String sqlString = "SELECT * FROM tblUser WHERE uRole = 'ST'";
        UserPojo[] allStudents = null;
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            res.last(); // 移动到最后一行以获取行数
            int count = res.getRow(); // 获取行数
            res.beforeFirst(); // 移动到结果集的开始

            if (count == 0) {
                return null; // 没有找到学生
            }

            allStudents = new UserPojo[count];
            int index = 0;
            while (res.next()) {
                allStudents[index] = new UserPojo(res.getString("uId"), res.getString("uPwd"), res.getString("uRole"));
                index++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allStudents;
    }
}