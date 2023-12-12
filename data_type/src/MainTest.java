import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class MainTest {
    // 类常量:定义在main方法外
    public static final double PI = 3.14;

    public static void main(String[] args) {
        // Java共有8种基本类型：
        // 1. 4种整型：int(4字节), short(2字节), long(8字节, 后缀L或l), byte(1字节)
        int i = 1;
        int b = 0b0010; // 二进制0010 = 2
        System.out.printf("i = %d, b = %d\n", i, b);
        // 2. 2种浮点型: float(4字节, 后缀F或f), double(8字节, 后缀D或d,默认)
        float d = 0.02f;
        double d2 = 0.01;
        System.out.printf("d = %.2f, d2 = %f\n", d, d2);
        // 3. 1种字符型:
        char a = 'A';
        System.out.printf("a = %c\n", a);
        // 4. 1种boolean型:
        boolean bool = true;
        System.out.printf("bool = %b\n", bool);

        System.out.printf("PI = %.2f\n", MainTest.PI);

        // 字符串
        String greeting = "Hello";
        String s = greeting.substring(0, 3);
        System.out.printf("s = %s\n", s);
    }
}
