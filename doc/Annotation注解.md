# Annotation 注解

## 14.1 基本Annotation
Java提供的5个基本Annotation的用法，使用Annotation时要在其前面增加@符号，并把该Annotation当成一个修饰符使用，用于修饰它支持的程序元素。

### 14.1.1. 限定重写父类方法：@Override
@Override就是用来指定方法覆载的，它可以强制一个子类必须覆盖父类的方法。
```java
public class Fruit {
    public void info() {
        System.out.println("水果的info方法。。。");
    }
}
class Apple extends Fruit {
    // 使用@Override指定下面方法必须重写父类方法
    @Override
    public void info() {
        System.out.println("Apple重写服了Fruit的info方法。。。");
    }
}
```
@Override主要是帮助程序员避免一些低级错误。
### 14.1.2. 标识已过时：@Deprecated
@Deprecated用于表示某个程序元素（类、方法等）已过时，当其他应用程序使用已过时的类、方法时，编译器将会给出警告。

### 14.1.3. 抑制编译器警告：@SuppressWarnings

### 14.1.4. @SafeVarargs
### 14.1.5. @FunctionalInterface





