# Java基础类库

## 7.1 与用户互动

运行Java程序的参数
```java
public Class ArgsTest {
    public static void main(String[] args) {
        // 输出args数组的长度
        System.out.println(args.length);
        // 遍历args数组的每个元素
        for (String arg: args) {
            System.out.println(arg);
        }
    }
}
```

使用Scanner获取键盘输入
```java
public class ScannerKeyBoardTest {
    public static void main(String[] args) {
        // System.in 代表标准输入，也就是键盘输入
        Scanner sc = new Scanner(System.in);
        // 增加下面一行将只把回车作为分隔符
        // sc.useDelimiter("\n");
        // 判断是否还有下一个输入项
        while(sc.hasNext()) {
            // 输出输入项
            System.out.println("键盘输入的内容是：" + sc.next());
        }
    }
}
```

```java
public class ScannerFileTest {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("ScannerFileTest.java"));
        
        // 判断是否还有下一行
        while(sc.hasNextLine()){
            // 输出文件中的下一行
            System.out.println(sc.nextLine());
        }
    }
}
```

## 7.2 系统相关

### System 类
System类提供了代表**标准输入**、**标准输出**和**错误输出**的类变量，并提供了一些静态方法用于访问环境变量、系统属性的方法，还提供了加载文件和动态链接库的方法。
```java
// 获取系统所有的环境变量
Map<String, String> env = System.getenv();
for (String name: env.keySet()) {
    System.out.println(name + " -> " + env.get(name));
}
// 获取指定环境变量的值
System.out.println(System.getenv("JAVA_HOME"));
// 获取所有的系统属性
Properties props = System.getProperties();
// 将所有的系统属性保存到props.txt文件中
props.store(new FileOutputStream("props.txt"), "System Properties");
// 输出特定的系统属性
System.out.println(System.getProperty("os.name"));
```
System类的in、out和err分别代表系统的标准输入、标准输出和错误输出流，并提供setIn()、setOut()和setErr()方法来改变系统的标准输入、标准输出和标准错误输出流。

System类还提供一个identityHashCode(Object o)方法，该方法返回指定对象的精确hashCode值，也就是根据该对象的地址计算得到的hashCode值，可以唯一地标识该对象。

### Runtime 类
Runtime类代表Java程序的运行时环境，每个java程序都有一个与之对应的Runtime实例，应用程序通过该对象与其运行时环境相连。应用程序不能创建自己的Runtime实例，但可以通过getRuntime()方法获取与之关联的Runtime对象。

与System类似的是，Runtime类也提供了gc()方法和runFinalization()方法来通知系统进行垃圾回收、清理系统资源，并提供load(String filename)和loadLibrary(String libname)方法来加载文件和动态链接库。

```java
// 获取Java程序关联的运行时对象
Runtime rt = Runtime.getRuntime();
System.out.println("处理器数量：" + rt.availableProcessors());
System.out.println("空闲内存数：" + rt.freeMemory());
System.out.println("可用最大内存数：" + rt.maxMemory());
```

Runtime类可以直接单独启动一个进程来运行操作系统的命令。
```java
Runtime rt = Runtime.getRuntime();
// 运行记事本程序
rt.exec("notepad.exe");
```
## 常用类
### Object 类
Object类是所有类、数组、枚举类的父类，Java允许把任何类型的对象赋给Object类型的变量。
Object类提供以下几个常用方法：
1. boolean equals(Object obj)：判断指定对象与该对象是否相等。标准：两个对象是同一个对象。
2. protected void finalize()：当系统中没有引用变量引用到该对象时，垃圾回收期调用此方法来清理该对象的资源。
3. Class<?> getClass()：返回该对象的运行时类。
4. int hashCode()：返回该对象的hashCode值。
5. String toString()：返回该对象的字符串表示。

Java还提供一个protected修饰的clone()方法，该方法用于帮助其他对象来实现“自我克隆”，所谓“自我克隆”就是得到一个当前对象的副本，而且两者之间完全隔离。由于Object类提供的clone()方法使用了protected修饰，因此该方法只能被子类重写或调用。

Object类提供的Clone机制只对对象里各实例变量进行“简单复制”，即只克隆对象的所有成员变量值，不会对引用类型的成员变量值所引用的对象进行克隆。如果实例变量的类型是引用类型，Object的Clone机制也只是简单的复制这个引用变量，这样**原有对象的引用类型的实例变量**与**克隆对象的引用类型的实例变量**依然**指向内存中的同一个实例**。


### Java7新增 Objects 类

### String、StringBuffer和StringBuilder
String是不可变类，即一旦一个String对象被创建以后，包含在这个对象中的字符序列是不可改变的，直至这个对象被销毁。
StringBuffer对象则代表一个字符序列可变的字符串，当一个StringBuffer被创建以后，通过StringBuffer提供的append()、insert()、reverse()、setCharAt()、setLength()等方法可以改变这个字符串对象的字符序列。一旦通过StringBuffer生成了最终想要的字符串，就可以调用它的toString()方法将其转换为一个String对象。
StringBuilder和StringBuffer基本相似，两个类的构造器和方法也基本相同。不同之处在于，StringBuffer是线程安全的，而StringBuilder则没有实现线程安全功能，所以性能略高。因此，在通常情况下，如果要创建一个内容可变的字符串对象，则应该**优先考虑使用StringBuilder类**。

String类提供了大量的构造器来创建String对象：
1. String()：创建一个包含0个字符串序列的String对象（并不返回null）。
2. String(byte[] bytes, Charset charset)：使用指定的字符集，将指定的byte[]数组解码成一个新的字符串对象。
3. String(byte[] bytes, int offset, int length)：使用平台默认字符集将指定的byte[]数组从offset开始、长度为length的子数组解码成一个新的String对象。
4. String(byte[] bytes, int offset, int length, String charsetName)：使用指定的字符集将指定的byte[]数组从offset开始、长度为length的子数组解码成一个新的String对象。
5. String(byte[] bytes, String charsetName)：使用指定字符集将指定byte[]数组解码成一个新的String对象。
6. String(char[] value, int offset, int count)：将指定的字符数组从offset开始、长度为count的字符元素连缀成字符串。
7. String(String original)：根据字符串直接量来创建一个String对象。也就是说，新创建String对象是该参数字符串的副本。
8. String(StringBuffer buffer)：根据StringBuffer对象来创建对应的String对象。
9. String(StringBuilder builder)：根据StringBuilder对象来创建对应的String对象。

操作字符串对象方法：


### Math类

### Java7的ThreadLocalRandom 与 Random
Random类专门用于生成一个伪随机数，它有两个构造器：一个构造器使用默认种子，另一个构造器需要显式传入一个long型整数的种子。

ThreadLocalRandom类是Java7新增的一个类，它是Random的增强版。在并发访问的环境下，使用ThreadLocalRandom来代替Random可以减少多线程资源竞争，最终保证系统具有更好的线程安全性。

```java
Random rand = new Random();
System.out.println("rand.nextBoolean(): " + rand.nextBoolean());

byte[] buffer = new byte[16];
rand.nextBytes(buffer);
System.out.println(Arrays.toString(buffer));
// 生成0.0 ~ 1.0之间的伪随机double数
System.out.println(rand.nextDouble());
// 生成0.0 ~ 1.0之间的伪随机float数
System.out.println(rand.nextFloat());
// 生成平均值是0.0，标准差是1.0的伪高斯数
System.out.println(rand.nextGaussian());
// 生成一个处于int整数取值范围的伪随机整数
System.out.println(rand.nextInt());
// 生成0 ~ 26之间的伪随机整数
System.out.println(rand.nextInt(26));
// 生成一个处于long整数取值范围的伪随机整数
System.out.println(rand.nextLong());
```
Random使用一个48位的种子，如果这个类的两个实例是用同一个种子创建的，对它们以同样的顺序调用方法，则它们会产生相同的数字序列。为避免两个Random对象产生相同的数字序列，通常推荐使用当前时间作为Random对象的种子。
```java
Random rand = new Random(System.currentTimeMillis());
```

### BigDecimal 类
创建BigDecimal对象时，一定要使用String对象作为构造器参数，而不是直接使用double数字。
```java
// 优先推荐此种方式
BigDecimal f1 = new BigDecimal("0.05");
// 如浮点数作为参数，可以选择此种方式：将double包装秤BigDecimal对象
BigDecimal f2 = BigDecimal.valueOf(0.01);

// 使用此构造器时，有一定的不可预知性。产生一个近似于0.05的值。
BigDecimal f3 = new BigDecimal(0.05);
```
## Java8 的日期、时间类
不推荐使用Date类，尽量使用Calendar类

### Date类
Date构造器：
1. Date()：生成一个代表当前日期时间的Date对象。该构造器在底层调用System.currentTimeMillis()获得long整数作为日期参数。
2. Date(long date)：根据指定的long型整数来生成一个Date对象。该构造器的参数表示创建的Date对象和GMT1970年1月1日00:00:00之间的时间差，已毫秒作为计时单位。

Date对象的方法：
1. boolean after(Date when)：测试该日期是否在指定日期when之后。
2. boolean before(Date when)：测试该日期是否在指定日期when之前。
3. long getTime()：返回该时间对应的long型整数，以毫秒作为计时单位。
4. void setTime(long time)：设置该Date对象的时间。

```java
Date d1 = new Date();
// 获取当前时间之后100ms的时间
Date d2 = new Date(System.currentTimeMillis() + 100);
```

### Calendar 类
Calendar类本身是一个抽象类，所以不能使用构造器来创建Calendar对象。

```java
// 创建一个默认的Calendar对象
Calendar calendar = Calendar.getInstance();
// 从Calendar对象汇总取出Date对象
Date date = calendar.getTime();
// 通过Date对象获得对应的Calendar对象
Calendar calendar2 = Calendar.getInstance();
calendar2.setTime(date);
```

Calendar类提供了大量访问、修改日期时间的方法：
1. void add(int field, int amount)：根据日历的规则，为给定日历字段添加或减去指定的时间量。
2. int get(int field)：返回指定日历字段的值。
3. int getActualMaximum(int field)：返回指定日历字段可能拥有的最大值。例如月的最大值为11.
4. int getActualMnimum(int field)：返回指定日历字段可能拥有的最小值。例如月的最小值为0.
5. void roll(int field, int amount)：与add()方法类似，区别在于加上amount后超过了该字段所能表示的最大范围时，也不会向上一字段进位。
6. void set(int field, int value)：将给定的日历字段设置为给定值。
7. void set(int year, int month, int date)：设置Calendar对象的年、月、日三个字段的值。
8. void set(int year, int month, int date, int hourOfDay, int minute, int second)：设置Calendar对象的年、月、日、时、分、秒6个字段的值。

```java
Calendar c = Calendar.getInstance();

// 年
c.get(Calendar.YEAR);
// 月
c.get(Calendar.MONTH);
// 日
c.get(Calendar.DATE);

// 社会年月日时分秒
c.set(2003, 10, 23, 12, 32, 23); // 2023-11-23 12:32:23
// 将Calendar的年向前推一年
c.add(Calendar.YEAR, -1); // output: 2022-11-23 12:32:23
// 将Calendar的月向前推8个月
c.roll(Calendar.MONTH, -8); // output: 2022-03-23 12:32:23
```

注意：
1. add与roll的区别
add(int field, int amount)的功能非常强大，add主要用于改变Calendar的特定字段的值。如果需要增加某字段的值，则让amount为正数；如果需要减少某个字段的值，则让amount为负数即可。
add(int field, int amount)有如下两条规则：
a. 当被修改的字段超出它允许的范围时，会发生进位，即上一级字段也会增大。
```java
Calendar call = Calendar.getInstance();
call.set(2003, 7, 23, 0, 0, 0); // 2003-08-23
call.add(Calendar.MONTH, 6); // 2004-02-23;
```
b. 如果下一级字段也需要改变，那么该字段会修正到变化最小的值。
```java
Calendar c = Calendar.getInstance();
c.set(2003, 7, 31, 0, 0, 0); // 2003-8-31
c.add(Calendar.MONTH, 6); // 2004-2-29
```

roll()的规则与add()的处理规则不同：当被修改的字段超出它允许的范围时，上一级字段不会增大。
```java
Calendar c = Calendar.getInstance();
c.set(2003, 7, 23, 0, 0, 0); // 2003-8-23
// Month进位，Year不会改变
c.roll(Calendar.MONTH, 6); // 2003-2-23

Calendar c2 = Calendar.getInstance();
c2.set(2003, 7, 31, 0, 0, 0); // 2003-8-31
// Month进位，Year不会改变
c2.roll(Calendar.MONTH, 6); // 2003-2-28
```

2. 设置Calendar的容错性
```java
c.setLenient(true/false); // 默认开启。关闭后将进行严格的参数检查。
```

3. set()方法延迟修改


## 7.5 正则表达式
方括号表达式：
1. 表示枚举：例如：[abc],表示a、b、c其中任意一个字符
2. 表示范围（-）：例如：[a-f],表示a~f范围内的任意一个字符
3. 表示求否(^)：例如[^abc],表示非a、b、c的任意一个字符；[^a-f],表示不是a~f范围内的任意一个字符。
4. 表示“与”运算（&&）：例如：[a-z&&[def]],a-z和[def]的交集，即d、e或f。
5. 表示“并”运算：例如：[a-d[m-p]]表示[a-dm-p],即a~d、m~p范围内的任意一个字符

正则表示还支持圆括号表达式，用于将多个表达式组成一个子表达式，圆括号中可以使用或运算符(|)。例如：((public)|(protected)|(private))用于匹配Java的三个访问控制符其中之一。


边界符：
行的开头：^
行的结尾：$
单词的边界：\b
非单词的边界：\B
输入的开头：\A
前一个匹配的结尾：\G
输入的结尾，仅用于最后的结束符：\Z
输入的结尾：\z

正则表达式支持的数量标识符有如下几个模式：
1. Greedy（贪婪模式）：数量表示服默认采用贪婪模式，除非另有表示。贪婪模式的表达式会一直匹配下去，知道无法匹配为止。
2. Reluctant（勉强模式）：用问号后缀（？）表示，它只会匹配最少的字符。也成为最少匹配模式。
3. Possessive（占有模式）：用加号后缀（+）表示，目前只有Java支持占有模式，通常比较少用。

### 使用正则表达式
Pattern对象是正则表达式编译后在内存中的表示形式。因此，正则表达式字符串必须先被便以为Pattern对象，然后再利用该Pattern对象创建对应的Matcher对象。执行匹配所涉及的状态保存在Matcher对象中，多个Matcher对象可共享同一个Pattern对象。

```java
// 将一个字符串编译成Pattern对象，可重复利用
Pattern p = Pattern.compile("a*b");
// 使用Pattern对象创建Matcher对象
Matcher m = p.matcher("aaaaab");
boolean b = m.matches(); // output: true

// 如正则表达式只用一次，可以直接使用Pattenr类的静态matches()方法
// 缺点：每次都要重新编译新的Pattern对象，效率不高
boolean b = Pattern.matches("a*b", "aaaaab");
```

Pattern是不可变类，可供多个并发线程安全使用。
Matcher类提供以下几个常用方法：
1. find()：返回目标字符串中是否包含与Pattern匹配的子串。
2. group()：返回上一次与Pattern匹配的子串。
3. start()：返回上一次与Pattern匹配的子串在目标字符串中的开始位置。
4. end()：范湖以上一次与Pattern匹配的子串在目标字符串中的结束位置加1。
5. lookingAt()：返回目标字符串前面部分与Pattern是否匹配
6. matches()：返回整个目标字符串与Pattern是否匹配
7. reset()：将现有的Matcher对象应用与一个新的字符序列

```java
String str = "我想求购一本《疯狂Java相依》，尽快联系我13500005555"
    + "交朋友，电话号码是13611112222"
    + "出售二手电脑，联系方式是15899992222";
// 创建一个Pattern对象，并用它建立一个Matcher对象
Matcher m = Pattern.compile("((13\\d)|(15\\d))\\d{8}").matcher(str);
// 将所有符合正则表达式的子串全部输出
while(m.find()) {
    System.out.println(m.group());
}
```

## 7.6 国际化与格式化
Java程序的国际化主要通过以下三个类完成：
1. java.util.ResourceBundle：用于加载国家、语言资源包。
2. java.util.Locale：用于封装特定的国家/区域、语言环境。
3. java.text.MessageFormat：用于格式化带占位符的字符串。

资源文件的命名可以有如下三种形式：
1. baseName_language_country.properties
2. baseName_language.properties
3. baseName.properties
其中baseName是资源文件的基本名，用户可以随意指定；而language和country必须是Java所支持的语言和国家。
```java
// 返回Java所支持的全部国家和语言的数组
Locale[] localeList = Locale.getAvailableLocales();
// 遍历数组的每个元素，一次获取所支持的国家和语言
for (int i = 0; i < localeList.length; i++) {
    System.out.println(localeList[i].getDisplayCountry()
        + "=" + localeList[i].getCountry() + " "
        + localeList[i].getDisplayLanguage()
        + "=" + localeList[i].getLanguage());
}
```

### 程序国际化
1. 创建翻译文件
```java
// 第一个文件：mess.properties
# 资源文件的内容是key-value对
hello=你好！

// 第二个文件：mess_en_US.properties
# 资源文件的内容是key-value对
hello=Welcome You!
```
2. 对于包含非西欧字符的资源文件，Java提供一个工具来处理：native2ascii。
```shell
native2ascii 资源文件 目的资源文件

native2ascii mess.properties mess_zh_CN.properties
```
3. 程序编码
```java
// 取得系统默认的国家/语言环境
Locale myLocale = Locale.getDefault(Locale.Category.FORMAT);
// 根据指定的国家/语言环境加载资源文件
ResourceBundle bundle =  ResourceBundle.getBundle("mess", myLocale);

// 打印
System.out.println(bundle.getString("hello"));
```

### 使用MessageFormat处理包含占位符的字符串
```java
// msg=Hello, {0}!Today is {1}.
System.out.println(MessageFormat.format(msg, "yeeku", new Date())); 
```

### 使用类文件代替资源文件
除了使用属性文件作为资源文件外，Java也允许使用类文件代替资源文件，即将所有的key-value对存入class文件，而不是属性文件。
使用类文件来代替资源文件必须满足如下条件：
1. 该类的类名必须是baseName_language_country。
2. 该类必须继承ListResourceBundle，并重写getContents()方法，该方法返回Object数组，该数组的每一项都是key-value对。
```java
public class myMess_zh_CN extends ListResourceBundle {
    // 定义资源
    private final Object myData[][] = {
        {"msg", "{0}, 你好！今天的日期是{1}"}
    };
    // 重写getContents方法
    public Object[][] getContents() {
        // 该方法返回资源的key-value对
        return myData;
    }
}
```
以上文件可以代替myMess_zh_CN.properties文件。
如果系统同时存在资源文件、类文件，系统将以类文件为主，不会调用资源文件。

## 7.7 Java8新增的日期、时间格式器

