package com.vcampus.dao;

//import view.client.CourseSelection.CourseClass;

import com.vcampus.pojo.CoursePojo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class CourseChooseDao {
        //冲突检测
        public static boolean checkCourseTimeConflict(Connection con, CoursePojo course, String uId) throws SQLException {

            try (Statement sta = con.createStatement()) {
                ResultSet studentCourses = sta.executeQuery("select * from tblCourseChoose cc " +
                        "join tblCourse c on cc.courseId = c.courseId " +
                        "where cc.uId = '" + uId + "'");

                while (studentCourses.next()) {
                    String existingCourseDate = studentCourses.getString("courseDate");
                    int existingCourseStart = studentCourses.getInt("courseStart");
                    int existingCourseEnd = studentCourses.getInt("courseEnd");
                    int courseStart = Integer.parseInt(course.getCourseStart());
                    int courseEnd = Integer.parseInt(course.getCourseEnd());

                    if (existingCourseDate.equals(course.getCourseDate()) &&
                            ((courseStart >= existingCourseStart && courseStart <= existingCourseEnd) ||
                                    (courseEnd >= existingCourseStart && courseEnd <= existingCourseEnd))) {
                        return true; // 时间冲突
                    }
                }
                return false;
            }
        }

            ///容量检测
            public static boolean checkCapacity(Connection con, CoursePojo course) throws SQLException {

                try (Statement sta = con.createStatement()) {
                    ResultSet courseResultSet = sta.executeQuery("SELECT * FROM tblCourse WHERE courseId = '" + course.getCourseId() + "'");

                    if (courseResultSet.next()) {
                        int People = courseResultSet.getInt("CoursePeopleNumber");
                        int courseCapacity= Integer.parseInt(course.getCourseCapacity());

                        // 检查课程容量是否相等
                        if (courseCapacity == People) {
                            return true; // 容量相等
                        }
                    }
                }

                return false; // 容量不相等
            }
            /**
             * 增加选课链接
             * @param course 课程编号
             * @param uId　学生编号
             * @return 是否创建成功
             */
            public static int createClassStudentLink(CoursePojo course, String uId) {


                String sqlString = "INSERT INTO tblCourseChoose(courseId, uId, courseDate, startTime, endTime) VALUES (?, ?, ?, ?, ?)";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
                try (Connection con = DriverManager.getConnection(url);
                     PreparedStatement pstmt = con.prepareStatement(sqlString)) {

                    if (checkCourseTimeConflict(con, course, uId)) {
                        //con.close();
                        System.out.println("选课失败：课程时间与已选课程时间冲突");
                        return 1; // 时间冲突，选课失败
                    }

                    if (checkCapacity(con, course)) {
                       // con.close();
                        System.out.println("选课失败：课容量已满");
                        return 2; // 选课失败
                    }

            else {
                        pstmt.setString(1, course.getCourseId());
                        pstmt.setString(2, uId);
                        pstmt.setString(3, course.getCourseDate());
                        pstmt.setString(4, course.getCourseStart());
                        pstmt.setString(5, course.getCourseEnd());

                        int num = pstmt.executeUpdate();
                        return 0;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 3;
                }
            }


            /**
             * 删除选课链接
             * @param courseId 班级编号
             * @param uId 学生编号
             * @return 是否创建成功
             */
            public static boolean deleteStudentInfoByIdAndName(String courseId, String uId) {


                String sqlString = "DELETE FROM tblCourseChoose WHERE courseId = ? AND uId = ?";

                // 数据库地址，绝对路径
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";

                try (Connection con = DriverManager.getConnection(url);
                     PreparedStatement pstmt = con.prepareStatement(sqlString)) {

                    pstmt.setString(1, courseId);
                    pstmt.setString(2, uId);

                    pstmt.executeUpdate(); // 执行删除操作

                } catch (SQLException e) {
                    e.printStackTrace();
                    return false; // 删除操作失败
                }
                return true; // 删除操作成功
            }

}


