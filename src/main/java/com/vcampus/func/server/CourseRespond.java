package com.vcampus.func.server;

import com.vcampus.dao.CourseChooseDao;
import com.vcampus.dao.CourseDao;
import com.vcampus.dao.TeacherCourseDao;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;
import com.vcampus.pojo.CoursePojo;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class CourseRespond {

    public static void showAllCourses(Message message, Socket clientSocket) throws IOException {
        CoursePojo[] temp = CourseDao.showAllCourses();
        //System.out.println("StudentRespond : " + temp.length);
        ServerNet.sendObject(temp, clientSocket);
    }
    //新增课程
    public static void addCourse(Message message, Socket clientSocket) throws SQLException, IOException {
        CoursePojo tempUser = (CoursePojo) message.getObjectList().get(0);
        boolean ok = CourseDao.createCourse(tempUser);
        if (ok == true)
            ServerNet.sendObject(true, clientSocket);
        else
            ServerNet.sendObject(false, clientSocket);
    }
    //删除课程
    public static void deleteCourse(Message message,Socket clientSocket) throws SQLException
    {
        String id=(String)message.getObjectList().get(0);
        boolean ok=CourseDao.deleteCourseById(id);
        if(ok==true)
            ServerNet.sendObject(true,clientSocket);
        else
            ServerNet.sendObject(false,clientSocket);
    }

    //修改课程信息
    public static void updateCourse(Message message,Socket clientSocket)throws SQLException
    {
        CoursePojo tempUser = (CoursePojo) message.getObjectList().get(0);
        boolean ok = CourseDao.updateCourse(tempUser);
        if (ok == true)
            ServerNet.sendObject(true, clientSocket);
        else
            ServerNet.sendObject(false, clientSocket);
    }

    public static void chooseCourse(Message message,Socket clientSocket)throws SQLException
    {
        CoursePojo tempUser = (CoursePojo) message.getObjectList().get(0);
        String id=(String)message.getObjectList().get(1);
        int ok= CourseChooseDao.createClassStudentLink(tempUser,id);
        ServerNet.sendObject(ok, clientSocket);
    }

    public static void dropCourse(Message message,Socket clientSocket)throws SQLException
    {
        String courseId = (String) message.getObjectList().get(0);
        String id=(String)message.getObjectList().get(1);
        boolean ok= CourseChooseDao.deleteStudentInfoByIdAndName(courseId,id);
        if (ok == true)
            ServerNet.sendObject(true, clientSocket);
        else
            ServerNet.sendObject(false, clientSocket);
    }

    public static void chooseCoursePN(Message message,Socket clientSocket)throws SQLException
    {
        CoursePojo course = (CoursePojo) message.getObjectList().get(0);
        boolean ok= CourseDao.chooseCoursePN(course);
        if (ok == true)
            ServerNet.sendObject(true, clientSocket);
        else
            ServerNet.sendObject(false, clientSocket);
    }

    public static void dropCoursePN(Message message,Socket clientSocket)throws SQLException
    {
        CoursePojo course = (CoursePojo) message.getObjectList().get(0);
        boolean ok= CourseDao.dropCoursePN(course);
        if (ok == true)
            ServerNet.sendObject(true, clientSocket);
        else
            ServerNet.sendObject(false, clientSocket);
    }

    public static void showTeacherCourses(Message message, Socket clientSocket) throws IOException {
        String teacherName = (String) message.getObjectList().get(0);
        CoursePojo[] temp = TeacherCourseDao.showTeacherCourses(teacherName);
        ServerNet.sendObject(temp, clientSocket);
    }

    public static void showChooseCourses(Message message, Socket clientSocket) throws IOException {
        String uId = (String) message.getObjectList().get(0);
        CoursePojo[] temp = CourseDao.showChooseCourses(uId);
        ServerNet.sendObject(temp, clientSocket);
    }
}

