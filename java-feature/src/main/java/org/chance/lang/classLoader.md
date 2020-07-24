# 类加载过程
JVM将类加载过程分为三个步骤：装载（Load），链接（Link）和初始化(Initialize)
## 装载（Load）
查找并加载类的二进制数据；
## 链接（Link）
### 验证
确保被加载类信息符合JVM规范、没有安全方面的问题。
### 准备 
为类的静态变量分配内存，并将其初始化为默认值。
### 解析 
把虚拟机常量池中的符号引用转换为直接引用。
ps:Java 中，虚拟机会为每个加载的类维护一个常量池【不同于字符串常量池，这个常量池只是该类的字面值（例如类名、方法名）和符号引用的有序集合。 而字符串常量池，是整个JVM共享的】这些符号（如int a = 5;中的a）就是符号引用，而解析过程就是把它转换成指向堆中的对象地址的相对地址。
## 初始化(Initialize)
为类的静态变量赋予正确的初始值。

# 双亲委派模型
双亲委派模型的原理很简单，实现也简单。每次通过先委托父类加载器加载，当父类加载器无法加载时，再自己加载。其实ClassLoader类默认的loadClass方法已经帮我们写好了，一般情况下我们无需去写。
双亲委派模式优势：
1、采用双亲委派模式的是好处是Java类随着它的类加载器一起具备了一种带有优先级的层次关系，通过这种层级关可以避免类的重复加载，当父亲已经加载了该类时，就没有必要子ClassLoader再加载一次。
2、其次是考虑到安全因素，java核心api中定义类型不会被随意替换，假设通过网络传递一个名为java.lang.Integer的类，通过双亲委托模式传递到启动类加载器，而启动类加载器在核心Java API发现这个名字的类，发现该类已被加载，并不会重新加载网络传递的过来的java.lang.Integer，而直接返回已加载过的Integer.class，这样便可以防止核心API库被随意篡改。
思考：假如我们自己写了一个java.lang.String的类，我们是否可以替换调JDK本身的类？
答案是否定的。我们不能实现。为什么呢？我看很多网上解释是说双亲委托机制解决这个问题，其实不是非常的准确。因为双亲委托机制是可以打破的，你完全可以自己写一个classLoader来加载自己写的java.lang.String类，但是你会发现也不会加载成功，具体就是因为针对java.*开头的类，jvm的实现中已经保证了必须由bootstrp来加载。（全盘负责）

# 自定义类加载器
- 继承java.lang.ClassLoader
- 重写父类的findClass方法（应为父类此方法没有默认实现，子类必须实现）。其余方法都不用我们实现，因为JDK已经在loadClass方法中帮我们实现了ClassLoader搜索类的算法，当在loadClass方法中搜索不到类时，loadClass方法就会调用findClass方法来搜索类，所以我们只需重写该方法即可。如没有特殊的要求，一般不建议重写loadClass搜索类的算法。

自定义类加载器的方法:
1、如果不想打破双亲委派模型，那么只需要重写findClass方法即可
2、如果想打破双亲委派模型，那么就重写整个loadClass方法

![加载一个类的过程](https://img-blog.csdnimg.cn/20190131161345614.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Y2NDEzODU3MTI=,size_16,color_FFFFFF,t_70)

# Ref
- [从原理层面理解Java中的类加载器：ClassLoader、双亲委派模型、线程上下文类加载器](https://blog.csdn.net/f641385712/article/details/86715906)
- [真正理解线程上下文类加载器](https://blog.csdn.net/yangcheng33/article/details/52631940)
- [详解Java—ServiceLoader之源码分析](https://www.jianshu.com/p/e60220817726)
- [探讨注解驱动Spring应用的机制，详解ServiceLoader、SpringFactoriesLoader的使用（以JDBC、spring.factories为例介绍SPI）](https://blog.csdn.net/f641385712/article/details/89231174)