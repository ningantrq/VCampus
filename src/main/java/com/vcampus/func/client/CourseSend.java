package com.vcampus.func.client;

import com.vcampus.message.Message;
import com.vcampus.net.ClientNet;
import com.vcampus.pojo.UserPojo;
import com.vcampus.pojo.CoursePojo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class CourseSend {

    //显示所有课程信息
    public static CoursePojo[] showAllCourses() throws IOException {
        Message temp = new Message();
        temp.changeOperateType("ShowAllCourses");
        CoursePojo[] res = ClientNet.sendAndReceive(temp, new CoursePojo[]{});
        //System.out.println("StudentSend : " + res.length);
        return res;
    }
    // 增加课程
    public static boolean addCourse(String id, String name, String teacher,String capacity,String peopleNumber,String date,String start,String end,String location,String credit) throws SQLException, InterruptedException, UnknownHostException, IOException {
        CoursePojo tempCourse= new CoursePojo(id,name,teacher,credit,date,start,end, location, capacity, peopleNumber);
        Message temp = new Message();
        temp.addObject(tempCourse);
        temp.changeOperateType("AddCourse");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    // 删除课程
    public static boolean deleteCourse(String id) throws SQLException, UnknownHostException, IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.changeOperateType("DeleteCourse");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    // 修改课程信息
    public static boolean updateCourse(CoursePojo course) throws SQLException, UnknownHostException, IOException {
        Message temp = new Message();
        temp.addObject(course);
        temp.changeOperateType("UpdateCourse");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static int chooseCourse(CoursePojo course, String studentId) throws SQLException, UnknownHostException, IOException {
        Message temp = new Message();
        temp.addObject(course);
        temp.addObject(studentId);
        temp.changeOperateType("ChooseCourse");
        int ok = ClientNet.sendAndReceive(temp, 0);
        return ok;
    }

    public static boolean dropCourse(String courseId, String studentId) throws SQLException, UnknownHostException, IOException {
        Message temp = new Message();
        temp.addObject(courseId);
        temp.addObject(studentId);
        temp.changeOperateType("DropCourse");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static boolean chooseCoursePN(CoursePojo course) throws SQLException, UnknownHostException, IOException {
        Message temp = new Message();
        temp.addObject(course);
        temp.changeOperateType("ChooseCoursePN");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static boolean dropCoursePN(CoursePojo course) throws SQLException, UnknownHostException, IOException {
        Message temp = new Message();
        temp.addObject(course);
        temp.changeOperateType("DropCoursePN");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    //教师端显示自己所有授课信息
    public static CoursePojo[] showTeacherCourses(String teacherName) throws IOException {
        Message temp = new Message();
        temp.addObject(teacherName);
        temp.changeOperateType("ShowTeacherCourses");
        CoursePojo[] res = ClientNet.sendAndReceive(temp, new CoursePojo[]{});
        //System.out.println("StudentSend : " + res.length);
        return res;
    }

    public static CoursePojo[] showChooseCourses(String uId) throws IOException {
        Message temp = new Message();
        temp.addObject(uId);
        temp.changeOperateType("ShowChooseCourses");
        CoursePojo[] res = ClientNet.sendAndReceive(temp, new CoursePojo[]{});
        return res;
    }
}
