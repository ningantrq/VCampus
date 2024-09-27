package com.vcampus.dao;

import java.io.File;
import java.sql.*;

public class DbHelper {
    public static Connection conn = null;

    //连接到数据库
//    public static void connect() {
//        try {
//            // 加载 SQLite JDBC 驱动
////            Class.forName("org.sqlite.JDBC"); // 确保加载驱动
//
//                        String relativePath = "VCampus/src/main/java/com/vcampus/db/my_db.db";
//            String dbPath = new File(relativePath).getAbsolutePath();
//            String url = "jdbc:sqlite:" + dbPath;
//            conn = DriverManager.getConnection(url);
//
//            if (conn != null) {
//                System.out.println("Connection to SQLite has been established.");
//            } else {
//                System.out.println("Connection failed: Connection object is null.");
//            }
//        }
//        } catch (SQLException e) {
//            System.out.println("Error connecting to the database.");
//            e.printStackTrace();
//        }
//    }
    public static void connect() {
        try {
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
//            String url="jdbc:sqlite:D:/Ning An/VCampus/VCampus/src/main/java/com/vcampus/db/my_db.db";
//            String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 检查连接是否为空，如果为空则重新连接
    private static void ensureConnection() {
        if (conn == null) {
            connect();
        }
    }

    public static ResultSet read(String sqlString)
    {
        ensureConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlString);
            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static boolean create(String sqlString)
    {
        try {
            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(sqlString);
            return rows>0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean update(String sqlString)
    {
        try {
            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(sqlString);
            return rows>0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean delete(String sqlString)
    {
        try {
            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(sqlString);
            return rows>0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param args
     *            the command line arguments
     */
    /*public static void main(String[] args) {
    	DbHelper dbhelper = new DbHelper();
        dbhelper.connect();
    }*/
}
