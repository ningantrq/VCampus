package com.vcampus.dao;

import com.vcampus.pojo.BankPojo;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.vcampus.pojo.OrderPojo;
import java.sql.*;
import java.io.File;




public class BankDao {
    /**
     * 通过一卡通号查询账户信息
     *
     * @param aId 一卡通号
     * @return 该uId对应的一个 BankPojo
     */

    public static BankPojo findBankByuId(String aId) {

        String sqlString = "SELECT * FROM tblBank WHERE aId = ?";
        BankPojo banker = null;

        //数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        //连接数据库
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, aId);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                banker = new BankPojo();
                banker.setAId(res.getString("aId"));
                banker.setAName(res.getString("aName"));
                banker.setAMoney(res.getDouble("aMoney"));
                banker.setIsFroze(res.getString("isFroze"));
            } else {
                throw new SQLException("not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return banker;

    }

    /**
     * 增加账户信息
     *
     * @param banker 想要添加的信息
     * @return 是否成功
     */
    public static boolean AddBankData(BankPojo banker) {


        String sqlString = "INSERT INTO tblBank(aId, aName, aMoney, isFroze) VALUES (?, ?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, banker.getAId());
            pstmt.setString(2, banker.getAName());
            pstmt.setDouble(3, banker.getAMoney());
            pstmt.setString(4, banker.getIsFroze());

            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据一卡通号删除信息
     *
     * @param aId 一卡通号
     * @return 是否成功删除
     */
    public boolean deleteBankAccountById(String aId) {


        String sqlString = "DELETE FROM tblBank WHERE aId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, aId);

            pstmt.executeUpdate(); // 执行删除操作

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 查询所有信息
     *
     * @return banklsit[]
     */
    public static BankPojo[] findallBankAccounts() {
        String sqlString = "SELECT * FROM tblBank";
        List<BankPojo> bankList = new ArrayList<>(); // 用于存储结果的列表
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            // 逐行读取结果集
            while (res.next()) {
                // 创建 StudentInfo 对象并添加到列表中
                BankPojo banker = new BankPojo(
                        res.getString("aId"),
                        res.getString("aName"),
                        res.getDouble("aMoney"),
                        res.getString("isFroze")

                );
                bankList.add(banker); // 添加到列表
            }
            //System.out.println("length " + studentInfoList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        //System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return bankList.isEmpty() ? null : bankList.toArray(new BankPojo[0]);

    }

    /**
     * 充值
     *
     * @param banker
     * @param addMoney,充值的钱数，当它为负值时即为消费。
     * @return 是否修改成功
     */
    public static boolean changeMoney(BankPojo banker, Double addMoney, String password) {
        String sqlString = "UPDATE tblBank SET aMoney = ? WHERE aId = ?";
        String getUserPasswordQuery = "SELECT uPwd FROM tblUser WHERE uId = ?";
        String getIsFrozeQuery = "SELECT isFroze FROM tblBank WHERE aId = ?"; // 使用 aId 作为条件

        String userPassword = "";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            // 获取用户输入的密码
            String enteredPassword = password;

            // 查询数据库获取用户的密码
            try (PreparedStatement passwordStmt = con.prepareStatement(getUserPasswordQuery)) {
                passwordStmt.setString(1, banker.getAId());
                ResultSet passwordResult = passwordStmt.executeQuery();
                if (passwordResult.next()) {
                    userPassword = passwordResult.getString("uPwd");
                }
            }

            // 验证密码
            if (!enteredPassword.equals(userPassword)) {
                return false; // 密码错误
            }

            // 查询账户状态
            String isFroze = "";
            try (PreparedStatement frozeStmt = con.prepareStatement(getIsFrozeQuery)) {
                frozeStmt.setString(1, banker.getAId());
                ResultSet frozeResult = frozeStmt.executeQuery();
                if (frozeResult.next()) {
                    isFroze = frozeResult.getString("isFroze");
                }
            }

            // 检查账户是否可用
            if (!"可用".equals(isFroze)) {
                return false; // 账户被冻结
            }

            // 更新账户余额
            double cMoney = banker.getAMoney();
            double MONEY = cMoney + addMoney;

            pstmt.setDouble(1, MONEY); // 使用 setDouble 设置金额
            pstmt.setString(2, banker.getAId());

            int num = pstmt.executeUpdate();
            return num > 0; // 返回更新结果

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 发生异常，返回 false
        }
    }

    /**
     * 更改isFroze
     * @param bank
     * @return
     */
    public static boolean changeFroze(BankPojo bank) {
        String sqlString = "UPDATE tblBank SET isFroze = ? WHERE aId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            // 获取当前状态
            String currentStatus = bank.getIsFroze();
            String newStatus = "";

            if (currentStatus.equals("可用")) {
                newStatus = "不可用";
            } else  {
                newStatus = "可用";
            }

            pstmt.setString(1, newStatus);
            pstmt.setString(2, bank.getAId());

            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 购买，检测，并扣费
     * @param money 要消费的金额
     * @param uId 当前账户的uid
     * @return true
     */

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

    public static boolean CreateRechargeOrder(String uId,  double price, String name) {

        String gId = "财务操作";
        String gName = name;

        int count = 1;

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
                newOId = Integer.parseInt(maxOId) + 1; // 生成新 OrderId
            }

            // 格式化 OrderId 为六位字符串
            String OIdFormatted = String.format("%06d", newOId);
            int notgood=0;

            // 插入新商品
            try (PreparedStatement pstmt = con.prepareStatement(insertSql)) {
                pstmt.setString(1, OIdFormatted);
                pstmt.setString(2, uId);
                pstmt.setString(3, gId);
                pstmt.setString(4, gName);
                pstmt.setDouble(5, price);
                pstmt.setInt(6, count);
                pstmt.setDouble(7, Total);
                pstmt.setInt(8, notgood);

                int num = pstmt.executeUpdate();
                return num > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询所有订单
     *
     * @return 查询到的所有订单对象，返回一个Bank类的数组
     */
    public static OrderPojo[] findAllOrders() {
        String sqlString = "SELECT * FROM tblOrder";
        List<OrderPojo> orderlist = new ArrayList<>(); // 用于存储结果的列表
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
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
                        res.getDouble("total"),
                        res.getInt("isGood")

                );
                orderlist.add(order); // 添加到列表
            }
            //System.out.println("length " + studentInfoList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        //System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return orderlist.isEmpty() ? null : orderlist.toArray(new OrderPojo[0]);
    }
    /**返回当前ID下的订单
     *
     * @param id
     * @return
     */


    public static OrderPojo[] ReturnOrdersByuId(String id) {
        //System.out.println("orderdao");
        String sql = "SELECT * FROM tblOrder WHERE uId = ?";
        OrderPojo[] s = new OrderPojo[0];

String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet res = pstmt.executeQuery();

            s = extractOrderFromResultSet(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }
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
                od.setIsGood(res.getInt("total"));

            } else {
                throw new SQLException("not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return od;

    }
}
