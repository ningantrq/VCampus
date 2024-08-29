package com.vcampus.dao;

import java.sql.*;

public class DbHelper {
    public static Connection conn = null;

    //连接到数据库
    public static void connect() {
        try {
            String url = "jdbc:sqlite:D:\\Ning An\\VCampus\\src\\main\\java\\com\\vcampus\\db\\vCampus.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet read(String sqlString)
    {

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
