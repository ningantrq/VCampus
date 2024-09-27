package com.vcampus.dao;

import com.vcampus.pojo.CoursePojo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class TeacherCourseDao {
    /**
     * 显示传入teachername的所有的课程
     *
     * @return 所有的课程Course[]
     */
    public static CoursePojo[] showTeacherCourses(String Tname) {
        String sqlString = "SELECT * FROM tblCourse WHERE courseTeacher = ?";
        List<CoursePojo> courselist = new ArrayList<>(); // 用于存储结果的列表
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, Tname); // 先设置参数
            ResultSet res = pstmt.executeQuery(); // 然后执行查询

            // 逐行读取结果集
            while (res.next()) {
                CoursePojo course = new CoursePojo(
                        res.getString("courseId"),
                        res.getString("courseName"),
                        res.getString("courseTeacher"),
                        res.getString("credit"),
                        res.getString("courseDate"),
                        res.getString("courseStart"),
                        res.getString("courseEnd"),
                        res.getString("courseLocation"),
                        res.getString("courseCapacity"),
                        res.getString("coursePeopleNumber")
                );
                courselist.add(course); // 添加到列表
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        //System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return courselist.isEmpty() ? null : courselist.toArray(new CoursePojo[0]);

    }

}