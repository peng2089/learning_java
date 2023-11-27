# MySQL数据库与JDBC编程
JDBC全称是Java Database Connectivity，即Java数据库连接。

DML(Data Manipulation Language, 数据操作语言)：主要有insert、update和delete三个关键字完成。
DDL(Data Definition Language, 数据定义语言)：主要有create、alter、drop和truncate四个关键字完成。
DCL(Data Control Language, 数据控制语言)：主要有grant和revoke两个关键字完成。

## 13.3 JDBC的典型用法
### JDBC4.2 常用接口和类简介
DriverManager：用于管理JDBC驱动的服务类。主要功能是获取Connection对象。

Connection：代表数据库连接对象，每个Connection代表一个物理连接对话。想要访问数据库，必须鲜活的数据库连接。
该接口常用的方法：
1. Statement createStatement() throws SQLException：该方法返回一个Statement对象。
2. PreparedStatement prepareStatement(String sql) throws SQLException：该方法返回预编译的Statement对象，即将SQL语句提交到数据库进行编译。
3. CallableStatement prepareCall(String sql) throws SQLException：该方法返回CallableStatement对象，该对象用于调用存储过程。

JDBC编程步骤：
1. 加载数据库驱动
    ```java
    // 加载驱动
    Class.forName("com.mysql.jdbc.Driver"); // 加载MySQL驱动
    Class.forName("oracle.jdbc.driver.OracleDriver"); // 加载Oracle驱动
    ```
2. 通过DriverManager获取数据库连接。
    ```java
    DriverManager.getConnection(String url, String user, String pass);
    ```
    MySQL数据库的URL写法通常如下：
    ```java
    jdbc:mysql://hostname:port/databasename
    ```
    Oracle数据库的URl写法如下:
    ```java
    jdbc:oracle:thin:@hostname:port:databasename
    ```

3. 通过Connection对象创建Statement对象。
- createStatement()：创建基本的Statement对象。
- prepareStatement(String sql)：根据传入的SQL语句创建预编译的Statement对象。
- prepareCall(String sql)：根据传入的SQL语句创建CallableStatement对象。

4. 使用Statement执行SQL语句。
- execute()：可执行任何SQL语句，但比较麻烦。
- executeUpdate()：主要用于执行DML（插入、删除、修改）和DDL语句。执行DML语句返回受影响的行数，执行DDL语句返回0,。
- executeQuery()：只能执行查询语句，执行后返回代表查询结果的ResultSet对象。

5. 操作结果集。如果执行的SQL语句是查询语句，则执行结果将返回一个ResultSet对象，该对象里保存了SQL语句查询结果。程序可以通过操作该ResultSet对象来取出查询结果。ResultSet对象主要提供以下两类方法：
- next()、previous()、first()、last()、beforeFirst()、afterLast()、absolute()等移动记录指针的方法。
- getXxx()方法获取记录指针指向行、特定列的值。

6. 回收数据库资源，包括关闭ResultSet、Statement和Connection等资源。
```java
// 1. 加载驱动
Class.forName("com.mysql.jdbc.Driver");
// 2. 使用DriverManager获取数据库连接
Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "123456");
// 3. 使用Connection来创建一个Statement对象
Statement stmt = conn.createStatement();
// 4. 执行SQL语句
// Statement有三种执行SQL语句的方法：
// a. execute()可执行任何SQL语句，返回一个boolean值。如果执行后第一个结果是ResultSet，则返回true，否则返回false。
// b. executeQuery()执行select语句，返回查询到的结果集。
// c. executeUpdate()用于执行DML语句，返回一个整数，代表被SQL语句影响的记录条数。
ResultSet rs = stmt.executeQuery("SELECT * FROM `tbl_user`");

while(rs.next()) {
    System.out.println(
        rs.getInt(1) 
        + rs.getString(2) 
        + rs.getString(3)
    );
}
```
## 13.4 执行SQL语句的方式
### 使用Java8新增的excuteLargeUpdate方法执行DDL和DML语句
```java
public class ExecuteDDL {
    private String driver;
    private String url;
    private String user;
    private String pass;

    public void initParam(String paramFile) throws Exception {
        // 使用Properties类来加载属性文件
        Properties props = new Properties();
        props.load(new FileInputStream(paramFile));
        url = props.getProperty("url");
        user = props.getProperty("user");
        pass = props.getProperty("pass");
    }

    public void createTable(String sql) throws Exception {
        // 加载驱动
        Class.forName(driver);
        try(
        // 获取数据库连接
        Connection conn = DriverManager.getConnection(url, user, pass);
        // 使用Connection来创建一个Statement对象
        Statement stmt = conn.createStatement()) {
            // 执行DDL语句，创建数据表
            stmt.executeUpdate(sql);
        }
    }
    public static void main(String[] args) {
        ExecuteDDL ed = new ExecuteDDL();
        ed.initParam("mysql.ini");
        ed.createTable("CREATE TABLE `test` "
            + " (id int auto_increment primary key,"
            + "name varchar(255), "
            + "desp text);");
        System.out.println("建表成功");
    }
}
```

### 使用execute方法执行SQL语句
```java
public class ExecuteDDL {
    private String driver;
    private String url;
    private String user;
    private String pass;

    public void initParam(String paramFile) throws Exception {
        // 使用Properties类来加载属性文件
        Properties props = new Properties();
        props.load(new FileInputStream(paramFile));
        url = props.getProperty("url");
        user = props.getProperty("user");
        pass = props.getProperty("pass");
    }
    public void executeSql(String sql) throws Exception {
        // 加载驱动
        Class.forName(driver);
        try(
            // 获取数据库连接
            Connection conn = DriverManager.getConnection(url, user, pass);
            // 使用Connection来创建一个Statement对象
            Statement stmt = conn.createStatement()) 
        {
            // 执行SQL语句，返回boolean值表示是否包含ResultSet
            boolean hasResultSet = stmt.execute(sql);
            if (hasResultSet) {
                try(
                    // 获取结果集
                    ResultSet rs = stmt.getResultSet()) {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnCount = rsmd.getColumnCount();
                        // 迭代输出ResultSet对象
                        while(rs.next()) {
                            for (int i = 0; i < columnCount; i++) {
                                System.out.print(rs.getString(i + 1));
                            }
                        }
                    }
            }else {
                
            }
        }
        
    }
}
```
## 13.5 管理结果集
## 13.6 Java7的RowSet1.1
## 13.7 事务处理
## 13.8 分析数据库信息
## 13.9 使用连接池管理连接
