package com.vcampus.dao;

import com.vcampus.pojo.GoodPojo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;


public class GoodDao {
    /**
     * 查找商品by_good_ID（管理员＋客户端用
     *
     * @param gId 商品ID
     * @return 该gId对应的一个 GoodPojo
     */

    public static GoodPojo findGoodBygId(String gId) {

        String sqlString = "SELECT * FROM tblGood WHERE good_id = ?";
        GoodPojo good = null;

        //数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        //连接数据库
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, gId);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                good = new GoodPojo();
                good.setGId(res.getString("gId"));
                good.setGName(res.getString("gName"));
                good.setGId(res.getString("gPrice"));
                good.setCategory(res.getString("category"));
                good.setGStock(res.getInt("gStock"));
            } else {
                throw new SQLException("not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return good;

    }

    /**
     * find by gname
     * @param name
     * @return
     */

    public static GoodPojo findGoodByName(String name) {

        String sqlString = "SELECT * FROM tblGood WHERE gName = ?";
        GoodPojo good = null;

        //数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        //连接数据库
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, name);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                good = new GoodPojo();
                good.setGId(res.getString("gId"));
                good.setGName(res.getString("gName"));
                good.setGId(res.getString("gPrice"));
                good.setCategory(res.getString("category"));
                good.setGStock(res.getInt("gStock"));
            } else {
                throw new SQLException("not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return good;

    }
    /**
     * 增加商品（该商品原本不存在，即增加一种商品类型，管理员用）
     *
     * @param good 想要添加的学生学籍信息
     * @return 是否成功
     */
    public static boolean AddGood(GoodPojo good) {


        String sqlString = "INSERT INTO tblGood(gId, gName, gPrice, category, gStock) VALUES (?, ?, ?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, good.getGId());
            pstmt.setString(2, good.getGName());
            pstmt.setDouble(3, good.getGPrice());
            pstmt.setString(4, good.getCategory());
            pstmt.setInt(5, good.getGStock());


            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除某种商品（管理员用
     *
     * @param gId 商品id
     * @return 是否成功删除
     */
    public static boolean deleteGoodById(String gId) {


        String sqlString = "DELETE FROM tblGood WHERE gId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, gId);

            pstmt.executeUpdate(); // 执行删除操作

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 查询所有商品信息（管理员和客户端用
     *
     * @return allGoods[]
     */
    public static GoodPojo[] showAllGoods() {
        String sqlString = "SELECT * FROM tblGood";
        List<GoodPojo> goodlist = new ArrayList<>(); // 用于存储结果的列表
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            // 逐行读取结果集
            while (res.next()) {

                GoodPojo good = new GoodPojo(
                        res.getString("gId"),
                        res.getString("gName"),
                        res.getDouble("gPrice"),
                        res.getString("category"),
                        res.getInt("gStock")

                );
                goodlist.add(good); // 添加到列表
            }
            //System.out.println("length " + studentInfoList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        //System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return goodlist.isEmpty() ? null : goodlist.toArray(new GoodPojo[0]);

    }

    /**
     * 修改商品信息（管理员用
     *
     * @param good
     * @return 是否修改成功
     */
    public boolean ModifyGood(GoodPojo good) {

        String sqlString = "UPDATE tblGood SET gName = ?, gPrice = ?, category = ?, gStock = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {


            pstmt.setString(1, good.getGId());
            pstmt.setString(2, good.getGName());
            pstmt.setDouble(3, good.getGPrice());
            pstmt.setString(4, good.getCategory());
            pstmt.setInt(5, good.getGStock());


            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改商品数量（购买成功时调用）
     * @param goodId
     * @param quantityChange 增加的数量（购买数量为x时，传入-x）
     * @return
     */
    public boolean ModifyGood(String goodId, int quantityChange) {

        String sqlString = "UPDATE tblGood SET gStock = gStock + ? WHERE gId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setInt(1, quantityChange);
            pstmt.setString(2, goodId);

            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkAndDeductFunds(double money, String uId, String password) {
        String selectSql = "SELECT aMoney FROM tblBank WHERE aId = ?";
        String updateSql = "UPDATE tblBank SET aMoney = aMoney - ? WHERE aId = ?";
        String getUserPasswordQuery = "SELECT uPwd FROM tblUser WHERE uId = ?";

        String userPassword = "";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement selectPstmt = con.prepareStatement(selectSql)) {

            String enteredPassword = password;

            // 查询数据库获取用户的密码
            try (PreparedStatement passwordStmt = con.prepareStatement(getUserPasswordQuery)) {
                passwordStmt.setString(1, uId);
                ResultSet passwordResult = passwordStmt.executeQuery();
                if (passwordResult.next()) {
                    userPassword = passwordResult.getString("uPwd");
                }
            }

            // 验证密码
            if (!enteredPassword.equals(userPassword)) {
                return false; // 密码错误
            }


            selectPstmt.setString(1, uId);

            try (ResultSet rs = selectPstmt.executeQuery()) {
                if (rs.next()) {
                    double aMoney = rs.getDouble("aMoney"); // 获取 aMoney 值

                    if (money <= aMoney) {
                        // 更新 aMoney
                        try (PreparedStatement updatePstmt = con.prepareStatement(updateSql)) {
                            updatePstmt.setDouble(1, money);
                            updatePstmt.setString(2, uId);
                            updatePstmt.executeUpdate(); // 执行更新
                        }

                        System.out.println("购买成功");
                        return true; // 余额足够，返回 true
                    } else {
                        System.out.println("余额不足");
                        return false; // 余额不足，返回 false
                    }
                } else {
                    System.out.println("未找到用户");
                    return false; // 如果没有找到用户，返回 false
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 发生异常时返回 false
        }
    }
}