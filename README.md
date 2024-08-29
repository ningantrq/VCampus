## 整体代码结构

```
src/main
    ├── java/com/vcampus
              ├── controller
              ├── dao
              ├── db
              ├── func
              			├── client
              			├── server
              ├── message
              ├── net
              			├── ClientNet.java
              			├── ServerNet.java
              ├── pojo
              ├── util
              ├── ClientApplication.java
              ├── ServerApplication.java
     ├── resources/com/vcampus
              ├── css
              ├── image
              ├── view

```

服务端和客户端在一个工程文件内（为什么？因为如果想要通过socket传输message对象，message对象所在文件位置必须是同一个工程下面）

##### 客户端与服务器共用层：

- message:下有Message文件，实现了统一的消息格式，具体格式其实是一个对象，其包含两个成员：theOperationType（消息种类，即操作的种类，如登录/注册）和一个Object list（消息数据，以列表的方式存储）
- net:网络层，实现统一化的收发信息接口

- pojo:实例化类，比如UserPojo，就是用户的一些基本信息的封装类
- util:可能需要的工具，如果是一些想要实现的零散函数可以往这里放

##### 客户端层：

- controller:前端的控制文件，与\resource\com\vcampus\view中的.fxml文件一一对应，实现前端的各种事件
- func/client:下有user功能文件夹，下有UserSend类，实现了基本需要的登录功能（登录、注册、修改密码）,向服务器发送相关信息
- view：位于resources\com\vcampus，存储JavaFx的.fxml文件（可以使用SceneBuilder打开编辑）,与controller层中的文件一一对应

##### 服务器层

- dao:对数据库做基本的操作
- db:数据库
- func/server:实现服务器端的各种操作（使用dao层的接口）,接收客户端请求并进行响应

##### 程序启动接口

- ClientApplication：客户端启动接口
- ServerApplication：服务器启动接口

## 添加一个功能需要做什么：

对于客户端：

1. 在message\Message中的`public enum operationType`中添加你想要添加的功能名称，可以参考TraningGroup-main那个工程
2. 在pojo中添加一个你需要的信息封装类
3. 在func/client中，如果是一个全新的大功能模块（如：图书馆/选课），新建一个package，在里面创建类，命名为...Func，这里可以参考已有代码，调用你之前写的pojo和message，以及net，来实现比较底层的功能。
4. 在resource\com.vcampus.client\view中，添加你想要的前端.fxml文件，之后去client\com.vcampus.client\controller中，实现前端的事件逻辑。

对于服务器：

1. 新建数据表
2. 在dao层中添加文件，实现对数据库的操作
3. 在func层中添加文件，调用dao，pojo，message，net中的接口实现服务器端的功能

