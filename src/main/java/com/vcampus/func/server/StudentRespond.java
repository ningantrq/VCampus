package com.vcampus.func.server;

import com.vcampus.dao.StudentInfoDao;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;
import com.vcampus.pojo.StudentInfo;

import java.io.IOException;
import java.net.Socket;

public class StudentRespond {
    public static void loadStudentData(Message message, Socket clientSocket) throws IOException {
        StudentInfo[] temp = StudentInfoDao.showAllStudentInfo();
        //System.out.println("StudentRespond : " + temp.length);
        ServerNet.sendObject(temp, clientSocket);
    }

    public static void AddStudentData(Message message, Socket clientSocket) throws IOException {
        StudentInfo stu = (StudentInfo) message.getObjectList().get(0);
        boolean ok = StudentInfoDao.AddStudentInfo(stu);
        ServerNet.sendObject(ok, clientSocket);
    }

    public static void DeleteStudentData(Message message, Socket clientSocket) throws IOException {
        String id = (String) message.getObjectList().get(0);
        boolean ok = StudentInfoDao.deleteStudentInfoById(id);
        ServerNet.sendObject(ok, clientSocket);
    }

    public static void ModifyStudentData(Message message, Socket clientSocket) throws IOException {
        String id = (String) message.getObjectList().get(0);
        StudentInfo stu = (StudentInfo) message.getObjectList().get(1);
        boolean ok = StudentInfoDao.deleteStudentInfoById(id);
        ok &= StudentInfoDao.AddStudentInfo(stu);
        ServerNet.sendObject(ok, clientSocket);
    }

    public static void GetById(Message message, Socket clientSocket) throws IOException {
        String id = (String) message.getObjectList().get(0);
        StudentInfo stu = StudentInfoDao.findStudentInfoById(id);
        ServerNet.sendObject(stu, clientSocket);
    }
}
