package com.vcampus;

import com.vcampus.func.server.*;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ServerThread extends Thread{

    private Socket clientSocket=null;
    private boolean isDone=false;

    ServerThread(Socket clientSocket)
    {
        this.clientSocket=clientSocket;
    }

    public void run()
    {
        while(!isDone)
        {
            try {
                threadRun(this.clientSocket);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void threadRun(Socket clientSocket) throws SQLException, IOException {
        System.out.println("已连接客户端" + clientSocket);
        Message receivedMessage= ServerNet.receiveObject(new Message(),clientSocket);//把message对象接收
        switch(receivedMessage.getOperationType())//比对message的操作类型
        {
            case Login://登录
                UserRespond.login(receivedMessage, clientSocket);//调用对应的响应函数
                break;

            case Register://注册
                UserRespond.register(receivedMessage, clientSocket);
                //对应的响应函数
                break;

            case ChangePassword://修改密码
                UserRespond.ChangePassword(receivedMessage,clientSocket);
                break;

            case Changepassword_helper:
                UserRespond.changepassword_helper(receivedMessage,clientSocket);
                break;

            case GetBookData:
                LibRespond.loadBookData(receivedMessage,clientSocket);
                break;

            case SearchBookData:
                LibRespond.searchBookData(receivedMessage, clientSocket);
                break;

            case BorrowBook:
                LibRespond.borrowBook(receivedMessage, clientSocket);
                break;

            case BorrowBook_show:
                LibRespond.borrowBook_show(receivedMessage, clientSocket);
                break;

            case LoadBookBorrowData:
                LibRespond.loadBookBorrowData(receivedMessage, clientSocket);
                break;

            case ReturnBook:
                LibRespond.returnBook(receivedMessage, clientSocket);
                break;

            case ReturnBook_show:
                LibRespond.returnBook_show(receivedMessage, clientSocket);
                break;

            case AddBook:
                LibRespond.addBook(receivedMessage, clientSocket);
                break;

            case DeleteBook:
                LibRespond.deleteBook(receivedMessage, clientSocket);
                break;

            case ModifyBook:
                LibRespond.modifyBook(receivedMessage, clientSocket);
                break;

            case RenewBook:
                LibRespond.renewBook(receivedMessage, clientSocket);
                break;

            case ReturnWarning:
                LibRespond.returnWarning(receivedMessage, clientSocket);
                break;

            case LoadStuBookBorrowData:
                LibRespond.loadStuBookBorrowData(receivedMessage, clientSocket);
                break;

            case SearchByBookNumber:
                LibRespond.searchByBookNumber(receivedMessage,clientSocket);
                break;

            case SearchByType:
                LibRespond.searchByType(receivedMessage,clientSocket);
                break;

            case GetStudentData://获取学生信息
                StudentRespond.loadStudentData(receivedMessage,clientSocket);
                break;

            case AddStudentData://添加学生信息
                StudentRespond.AddStudentData(receivedMessage, clientSocket);
                break;

            case DeleteStudentData:// 删除学生信息
                StudentRespond.DeleteStudentData(receivedMessage, clientSocket);
                break;

            case ModifyStudentData://修改学生信息
                StudentRespond.ModifyStudentData(receivedMessage, clientSocket);
                break;

            case GetById://根据学生id找
                StudentRespond.GetById(receivedMessage, clientSocket);
                break;

            case GetGoodData:
                GoodRespond.getGoodData(receivedMessage, clientSocket);
                break;

            case DeleteGoodData:
                GoodRespond.deleteGoodData(receivedMessage, clientSocket);
                break;

            case ModifyGoodData:
                GoodRespond.ModifyGoodData(receivedMessage, clientSocket);
                break;

            case AddGoodData:
                GoodRespond.addGoodData(receivedMessage, clientSocket);
                break;

            case GetOrderData:
                OrderRespond.getOrderData(receivedMessage, clientSocket);
                break;

            case AddOrder:
                OrderRespond.addOrder(receivedMessage, clientSocket);
                break;

            case CheckAndDeductFunds:
                OrderRespond.buyAndCheck(receivedMessage, clientSocket);
                break;

            case GetStuOrderData:
                OrderRespond.getStuOrderData(receivedMessage, clientSocket);
                break;

            case ShowAllCourses:
                CourseRespond.showAllCourses(receivedMessage, clientSocket);
                break;

            case AddCourse:
                CourseRespond.addCourse(receivedMessage, clientSocket);
                break;

            case DeleteCourse:
                CourseRespond.deleteCourse(receivedMessage, clientSocket);
                break;

            case UpdateCourse:
                CourseRespond.updateCourse(receivedMessage, clientSocket);
                break;

            case ChooseCourse:
                CourseRespond.chooseCourse(receivedMessage, clientSocket);
                break;

            case DropCourse:
                CourseRespond.dropCourse(receivedMessage, clientSocket);
                break;

            case ChooseCoursePN:
                CourseRespond.chooseCoursePN(receivedMessage, clientSocket);
                break;

            case DropCoursePN:
                CourseRespond.dropCoursePN(receivedMessage, clientSocket);
                break;

            case ShowChooseCourses:
                CourseRespond.showChooseCourses(receivedMessage, clientSocket);
                break;

            case ShowTeacherCourses:
                CourseRespond.showTeacherCourses(receivedMessage, clientSocket);
                break;

            case ChangeMoney:
                BankRespond.changeMoney(receivedMessage,clientSocket);
                break;

            case ChangeFroze:
                BankRespond.changeFroze(receivedMessage,clientSocket);
                break;

            case GetBankData:
                BankRespond.loadBankData(receivedMessage,clientSocket);
                break;

            case SearchBankData:
                BankRespond.searchBankData(receivedMessage,clientSocket);
                break;
            case CreateRechargeOrder:
                BankRespond.createRechargeOrder(receivedMessage,clientSocket);
                break;
            case GetOrderData2:
                BankRespond.loadOrderData(receivedMessage,clientSocket);
                break;

            case FindOrder:
                BankRespond.findOrder(receivedMessage,clientSocket);
                break;

            case FindAllOrders:
                BankRespond.findAllOrders(receivedMessage,clientSocket);
                break;

            default://错误类型的处理
                System.out.println("wrong operationType");
                break;
        }
        close();
    }

    public void close() throws IOException {
        isDone=true;
        clientSocket.close();
    }





}
