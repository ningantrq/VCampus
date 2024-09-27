package com.vcampus.dao;//package xxxx.xxxx.DAO;

import com.vcampus.pojo.StudentInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class StudentInfoDao {
    /**
     * 通过一卡通号查询学籍信息
     *
     * @param uId 一卡通号
     * @return 该uId对应的一个 StudentInfo类的学籍信息数据
     */
    public static StudentInfo findStudentInfoById(String uId) {
        /*
            通过一卡通号查询学籍信息
            传入参数为一卡通号uId
            返回值为该uId对应的一个 StudentInfo类的学籍信息数据
         */

        String sqlString = "SELECT * FROM tblstudentInfo WHERE uId = ?";

        //数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        StudentInfo studentInfo = new StudentInfo();

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, uId);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                studentInfo = new StudentInfo();
                studentInfo.setID(res.getString("uId"));
                studentInfo.setRole(res.getString("uRole"));
                studentInfo.setName(res.getString("uName"));
                studentInfo.setSex(res.getString("uSex"));
                studentInfo.setDate(res.getInt("uDate"));
                studentInfo.setAge(res.getInt("uAge"));
                studentInfo.setAcademy(res.getString("uAcademy"));
            } else {
                throw new SQLException("student not found for uId: " + uId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentInfo;

    }

    /**
     * 增加学生学籍信息
     *
     * @param stuInfo 想要添加的学生学籍信息
     * @return 是否成功
     */
    public static boolean AddStudentInfo(StudentInfo stuInfo) {
    /*
        增加学生学籍信息
        传入参数为想要添加的学生学籍信息
     */

        String sqlString = "INSERT INTO tblStudentInfo(uId, uRole, uName, uSex, uDate, uAge, uAcademy) VALUES (?, ?, ?, ?, ?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            // 使用 stuInfo 对象的 getter 方法来设置参数
            pstmt.setString(1, stuInfo.getID());          // 获取学生ID
            pstmt.setString(2, stuInfo.getRole());        // 获取学生角色
            pstmt.setString(3, stuInfo.getName());        // 获取学生姓名
            pstmt.setString(4, stuInfo.getSex());         // 获取学生性别
            pstmt.setInt(5, stuInfo.getDate());        // 获取学生出生日期
            pstmt.setInt(6, stuInfo.getAge());            // 获取学生年龄
            pstmt.setString(7, stuInfo.getAcademy());     // 获取学生学院

            int num = pstmt.executeUpdate();
            return num > 0; // 如果更新的行数大于0，返回true

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 出现异常时返回false
        }
    }



    /**
     * 根据一卡通号删除学生学籍信息
     *
     * @param uId 一卡通号
     * @return 是否成功删除
     */
    public static boolean deleteStudentInfoById(String uId) {
        /*
         * 通过一卡通号删除学生信息
         * 传入参数为一卡通号uId
         */

        String sqlString = "DELETE FROM tblstudentInfo WHERE uId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, uId);

            pstmt.executeUpdate(); // 执行删除操作

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 查询所有学生学籍信息
     *
     * @return 所有学生的StudentInfo[]
     */
    public static StudentInfo[] showAllStudentInfo() {
        String sqlString = "SELECT * FROM tblStudentInfo";
        List<StudentInfo> studentInfoList = new ArrayList<>(); // 用于存储结果的列表
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            // 逐行读取结果集
            while (res.next()) {
                // 创建 StudentInfo 对象并添加到列表中
                StudentInfo studentInfo = new StudentInfo(
                        res.getString("uId"),
                        res.getString("uRole"),
                        res.getString("uName"),
                        res.getString("uSex"),
                        res.getInt("uAge"),
                        res.getInt("uDate"),
                        res.getString("uAcademy")
                );
                studentInfoList.add(studentInfo); // 添加到列表
            }
            //System.out.println("length " + studentInfoList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return studentInfoList.toArray(new StudentInfo[0]);
    }

    /**
     * 修改学生信息
     *
     * @param studentInfo
     * @return 是否修改成功
     */
    public boolean ModifyStudentInfo(StudentInfo studentInfo) {

        String sqlString = "UPDATE tblStudentInfo SET uRole = ?, uName = ?, uSex = ?, uDate = ?, uAge = ?, uAcademy = ? WHERE uId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {


            pstmt.setString(1, studentInfo.getRole());
            pstmt.setString(2, studentInfo.getName());
            pstmt.setString(3, studentInfo.getSex());
            pstmt.setInt(4, studentInfo.getDate());
            pstmt.setInt(5, studentInfo.getAge());
            pstmt.setString(6, studentInfo.getAcademy());
            pstmt.setString(7, studentInfo.getID());

            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
