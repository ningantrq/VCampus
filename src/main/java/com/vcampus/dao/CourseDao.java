package com.vcampus.dao;

import com.vcampus.pojo.CoursePojo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class CourseDao {
    /**
     * 通过课程编号查找课程
     *
     * @param courseId 课程编号
     * @return 查找到的课程Course
     */
    public CoursePojo findCourseByNum(String courseId) {

        String sqlString = "SELECT * FROM tblCourse WHERE courseId = ?";
        CoursePojo course = null;

        //数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        //连接数据库
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {
            System.out.println("dao");

            pstmt.setString(1, courseId);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                course = new CoursePojo();

                course.setCourseId(res.getString("courseId"));
                course.setCourseName(res.getString("courseName"));
                course.setCourseTeacher(res.getString("courseTeacher"));
                course.setCourseCapacity(res.getString("courseCapacity"));
                course.setCoursePeopleNumber(res.getString("coursePeopleNumber"));
                course.setCourseDate(res.getString("courseDate"));
                course.setCourseStart(res.getString("courseStart"));
                course.setCourseEnd(res.getString("courseEnd"));
                course.setCourseLocation(res.getString("courseLocation"));
                course.setCourseCredit(res.getString("credit"));
            }
            //若未查找到
            else {
                throw new SQLException("not Found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    /**
     * 显示所有的课程
     *
     * @return 所有的课程Course[]
     */
    public static CoursePojo[] showAllCourses() {
        String sqlString = "SELECT * FROM tblCourse";
        List<CoursePojo> courselist = new ArrayList<>(); // 用于存储结果的列表
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString);
             ResultSet res = pstmt.executeQuery()) {

            // 逐行读取结果集
            while (res.next()) {
                // 创建 StudentInfo 对象并添加到列表中
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
            //System.out.println("length " + studentInfoList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        //System.out.println("length = " + studentInfoList.toArray(new StudentInfo[0]).length);
        return courselist.isEmpty() ? null : courselist.toArray(new CoursePojo[0]);
    }

    /**
     * 添加课程
     *
     * @param course 用户输入的信息
     * @return 是否添加成功
     */
    public static boolean createCourse(CoursePojo course) {
        String sqlString = "INSERT INTO tblcourse(courseId, courseName, courseTeacher, courseCapacity, credit, coursePeopleNumber, courseDate, courseStart, courseEnd, courseLocation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getCourseName());
            pstmt.setString(3, course.getCourseTeacher());
            pstmt.setString(4, course.getCourseCapacity());
            pstmt.setString(5, course.getCourseCredit());
            pstmt.setString(6, course.getCoursePeopleNumber());
            pstmt.setString(7, course.getCourseDate());
            pstmt.setString(8, course.getCourseStart());
            pstmt.setString(9, course.getCourseEnd());
            pstmt.setString(10, course.getCourseLocation());


            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 更新课程
     *
     * @param course 用户输入的信息
     * @return 是否更新成功
     */
    public static boolean updateCourse(CoursePojo course) {
        String sqlString = "UPDATE tblCourse SET courseName = ?, courseTeacher = ?, courseCapacity = ?, credit = ?, " +
                "coursePeopleNumber = ?, courseDate = ?,courseStart = ?, courseEnd = ?, courseLocation = ? WHERE courseId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseTeacher());
            pstmt.setString(3, course.getCourseCapacity());
            pstmt.setString(4, course.getCourseCredit());
            pstmt.setString(5, course.getCoursePeopleNumber());
            pstmt.setString(6, course.getCourseDate());
            pstmt.setString(7, course.getCourseStart());
            pstmt.setString(8, course.getCourseEnd());
            pstmt.setString(9, course.getCourseLocation());
            pstmt.setString(10, course.getCourseId());


            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除课程
     *
     * @param courseId 要删除的课程的编号
     * @return 是否删除成功
     */
    public static boolean deleteCourseById(String courseId) {
        String sqlString = "DELETE FROM tblCourse WHERE courseId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, courseId);

            pstmt.executeUpdate(); // 执行删除操作

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    /**
     * 选课成功时，选课人数加1
     *
     * @param course
     * @return
     */

    /**
     * 选课成功时，选课人数加1
     * @param course
     * @return
     */

    public static boolean chooseCoursePN(CoursePojo course) {
        String sqlString = "UPDATE tblCourse SET coursePeopleNumber = ? WHERE courseId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {


            int NUM = Integer.parseInt(course.getCoursePeopleNumber());

            pstmt.setInt(1, NUM + 1);
            pstmt.setString(2, course.getCourseId());

            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 退课成功时，选课人数减1
     * @param course
     * @return
     */

    public static boolean dropCoursePN(CoursePojo course) {
        String sqlString = "UPDATE tblCourse SET coursePeopleNumber = ? WHERE courseId = ?";

        // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            int NUM = Integer.parseInt(course.getCoursePeopleNumber());

            pstmt.setInt(1, NUM- 1);
            pstmt.setString(2, course.getCourseId());

            int num = pstmt.executeUpdate();
            return num > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**通过uid获取当前选课的courseID列表
     *
     * @param uId
     * @return
     */

    public static String[] getChooseIdByUId(String uId) {
        String sqlString = "SELECT courseId FROM tblCourseChoose WHERE uId = ?";
        List<String> courseIdList = new ArrayList<>(); // 用于存储课程 ID 的列表

String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

        try (Connection con =  DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, uId);
            ResultSet res = pstmt.executeQuery();

            // 逐行读取结果集
            while (res.next()) {
                String courseId = res.getString("courseId");
                courseIdList.add(courseId); // 添加课程 ID 到列表
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        return courseIdList.toArray(new String[0]);
    }


    /**传入uid，调用getChooseIdByUId(uid)获取当前uid选择的所有课程id，据此查找所有已选的CoursePojo
     *
     * @param uId
     * @return
     */

    public static CoursePojo[] showChooseCourses(String uId) {

        String[] courseIds = getChooseIdByUId(uId);
        if (courseIds == null || courseIds.length == 0) {
            return null; // 如果输入数组为空或为null，返回null
        }

        StringBuilder sqlString = new StringBuilder("SELECT * FROM tblCourse WHERE courseId IN (");
        for (int i = 0; i < courseIds.length; i++) {
            sqlString.append("?");
            if (i < courseIds.length - 1) {
                sqlString.append(", ");
            }
        }
        sqlString.append(")");

        List<CoursePojo> courseList = new ArrayList<>();
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString.toString())) {

            // 设置每个课程ID的参数
            for (int i = 0; i < courseIds.length; i++) {
                pstmt.setString(i + 1, courseIds[i]);
            }

            try (ResultSet res = pstmt.executeQuery()) {
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
                    courseList.add(course); // 将课程添加到列表中
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList.toArray(new CoursePojo[0]);
    }
}


