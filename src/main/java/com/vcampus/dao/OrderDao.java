//package xxxx.xxxx.DAO;
package com.vcampus.dao;

import com.vcampus.pojo.OrderPojo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;


public class OrderDao {
static String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
    private static final String DB_URL = url;


    /**
     * 查找订单
     *
     * @param OId
     * @return Order
     */

    public static OrderPojo findOrder(String OId) {

        String sqlString = "SELECT * FROM tblOrder WHERE OrderId = ?";
        OrderPojo od = null;

        //数据库地址，绝对路径
        String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        //连接数据库
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, OId);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                od = new OrderPojo();
                od.setOrderId(res.getString("OrderId"));
                od.setUId(res.getString("uId"));
                od.setGId(res.getString("gId"));
                od.setGName(res.getString("gName"));
                od.setPrice(res.getDouble("price"));
                od.setCount(res.getInt("count"));
                od.setTotal(res.getDouble("total"));
                od.setIsGood(res.getInt("isGood"));

            } else {
                throw new SQLException("not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return od;

    }

    /**
     * 增加Order,只允许顾客添加，订单ID自动按序生成，管理员无生成订单权限
     *
     * @param uId, gId, gName, price, count
     * @return 是否成功
     */
    /**
     * 增加Order,只允许顾客添加，订单ID自动按序生成，管理员无生成订单权限
     *
     * @param uId, gId, gName, price, count
     * @return 是否成功
     */
    public static boolean AddOrder(String uId, String gId, String gName, double price, int count) {

        double Total = price * count;

        String getMaxIdSql = "SELECT MAX(OrderId) FROM tblOrder";
        String insertSql = "INSERT INTO tblOrder(OrderId, uId, gId, gName, price, count, total, isGood) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement getIdStmt = con.prepareStatement(getMaxIdSql);
             ResultSet rs = getIdStmt.executeQuery()) {

            // 获取当前最大 OrderId
            int newOId = 1; // 默认值
            if (rs.next() && rs.getString(1) != null) {
                String maxOId = rs.getString(1);
                newOId = Integer.parseInt(maxOId.trim()) + 1; // 生成新 OrderId
            }

            // 格式化 OrderId 为六位字符串
            String OIdFormatted = String.format("%06d", newOId);

            int isgood = 1;
            // 插入新商品
            try (PreparedStatement pstmt = con.prepareStatement(insertSql)) {
                pstmt.setString(1, OIdFormatted);
                pstmt.setString(2, uId);
                pstmt.setString(3, gId);
                pstmt.setString(4, gName);
                pstmt.setDouble(5, price);
                pstmt.setInt(6, count);
                pstmt.setDouble(7, Total);
                pstmt.setInt(8, isgood);

                int num = pstmt.executeUpdate();
                return num > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除某条订单
     *
     * @param OId 商品id
     * @return 是否成功删除
     */
    public boolean deleteOrderById(String OId) {


        String sqlString = "DELETE FROM tblOrder WHERE OrderId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, OId);

            pstmt.executeUpdate(); // 执行删除操作

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
/*
    public static OrderPojo[] showAllOrders() {
        String sqlString = "SELECT * FROM tblOrder";
        List<OrderPojo> orderlist = new ArrayList<>(); // 用于存储结果的列表

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db");
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            // 逐行读取结果集
            while (res.next()) {

                OrderPojo order = new OrderPojo(
                        res.getString("OrderId"),
                        res.getString("uId"),
                        res.getString("gId"),
                        res.getString("gName"),
                        res.getDouble("price"),
                        res.getInt("count"),
                        res.getDouble("total")
                );
                goodlist.add(good); // 添加到列表
            }
            //System.out.println("length " + studentInfoList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        //System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return orderlist.isEmpty() ? null : orderlist.toArray(new OrderPojo[0]);

    }*/

/**
 * 返回数组的辅助函数
 */

    private static OrderPojo[] extractOrderFromResultSet(ResultSet res) throws SQLException {
        // 使用列表动态存储结果
        List<OrderPojo> s = new ArrayList<>();

        // 直接遍历结果集
        while (res.next()) {
            // 创建 BookPojo 对象并添加到列表中
            OrderPojo bank = new OrderPojo(
                    res.getString("OrderId"),
                    res.getString("uId"),
                    res.getString("gId"),
                    res.getString("gName"),
                    res.getDouble("price"),
                    res.getInt("count"),
                    res.getDouble("Total"),
                    res.getInt("isGood")
            );
            s.add(bank);
        }

        // 将列表转换为数组
        return s.toArray(new OrderPojo[0]);
    }

    /**返回所有商品订单
     *
     * @return
     */

    public static OrderPojo[] ReturnGoodsOrders() {
        System.out.println("orderdao");
        String sql = "SELECT * FROM tblOrder WHERE isGood = ?";
        OrderPojo[] s = new OrderPojo[0];
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, "1");
            ResultSet res = pstmt.executeQuery();

            s = extractOrderFromResultSet(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    /**
     * 查找/返回所有订单
     * @return
     */
    public static OrderPojo[] ReturnAllOrders() {
        String sql = "SELECT * FROM tblOrder";
        OrderPojo[] s = new OrderPojo[0];

        try (Connection con = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            ResultSet res = pstmt.executeQuery();

            s = extractOrderFromResultSet(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    /**
     * 返回当前uid下的所有在商店的购买订单
     * @return
     */
    public static OrderPojo[] showOrdersByuId(String uId) {
        String sqlString = "SELECT * FROM tblOrder WHERE uId = ? AND isGood = 1";
        List<OrderPojo> orderlist = new ArrayList<>(); // 用于存储结果的列表

        // 先声明资源
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;

        try {
            // 初始化资源
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

            con = DriverManager.getConnection(url);
            pstmt = con.prepareStatement(sqlString);
            pstmt.setString(1, uId);
            res = pstmt.executeQuery();

            // 逐行读取结果集
            while (res.next()) {
                OrderPojo order = new OrderPojo(
                        res.getString("OrderId"),
                        res.getString("uId"),
                        res.getString("gId"),
                        res.getString("gName"),
                        res.getDouble("price"),
                        res.getInt("count"),
                        res.getDouble("total"),
                        res.getInt("isGood")
                );
                orderlist.add(order); // 添加到列表
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 确保资源被关闭
            try {
                if (res != null) {
                    res.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 将列表转换为数组并返回
        return orderlist.isEmpty() ? null : orderlist.toArray(new OrderPojo[0]);
    }



}



