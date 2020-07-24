# design-pattern简介
设计模式学习

# 设计模式的六大原则
## [开闭原则（Open Close Principle）](https://blog.csdn.net/lovelion/article/details/7537584)
开闭原则就是说对扩展开放，对修改关闭。在程序需要进行拓展的时候，不能去修改原有的代码，实现一个热插拔的效果。所以一句话概括就是：为了使程序的扩展性好，易于维护和升级。想要达到这样的效果，我们需要使用接口和抽象类。

## [里氏代换原则（Liskov Substitution Principle）](https://blog.csdn.net/lovelion/article/details/7540445)
里氏代换原则(Liskov Substitution Principle LSP)面向对象设计的基本原则之一。 里氏代换原则中说，任何基类可以出现的地方，子类一定可以出现。 LSP是继承复用的基石，只有当衍生类可以替换掉基类，软件单位的功能不受到影响时，基类才能真正被复用，而衍生类也能够在基类的基础上增加新的行为。里氏代换原则是对“开-闭”原则的补充。实现“开-闭”原则的关键步骤就是抽象化。而基类与子类的继承关系就是抽象化的具体实现，所以里氏代换原则是对实现抽象化的具体步骤的规范。—— From Baidu 百科

## [依赖倒置原则（Dependence Inversion Principle）](https://blog.csdn.net/lovelion/article/details/7562783)
这个是开闭原则的基础，具体内容：真对接口编程，依赖于抽象而不依赖于具体。

## [接口隔离原则（Interface Segregation Principle）](https://blog.csdn.net/lovelion/article/details/7562842)
这个原则的意思是：使用多个隔离的接口，比使用单个接口要好。还是一个降低类之间的耦合度的意思，从这儿我们看出，其实设计模式就是一个软件的设计思想，从大型软件架构出发，为了升级和维护方便。所以上文中多次出现：降低依赖，降低耦合。

## [迪米特法则（最少知道原则）（Demeter Principle）](https://blog.csdn.net/lovelion/article/details/7563445)
为什么叫最少知道原则，就是说：一个实体应当尽量少的与其他实体之间发生相互作用，使得系统功能模块相对独立。

## [单一职责原则（Single-Responsibility-Principle）](https://blog.csdn.net/lovelion/article/details/7536542)
核心：一个类只负责一个功能领域中相应的职责，或者可以定义为：就一个类而言，应该只有一个引起它变化的原因。 
思想：如果一个类承担的职责过多，就等于把这些职责耦合在一起，一个职责的变化可能会削弱或者抑制这个类完成其他职责的能力。这种耦合会导致脆弱的设计，当变化发生时，设计会遭受到意想不到的破坏。

# GOF的23种设计模式
根据其目的（模式是用来做什么的）可分为创建型(Creational)，结构型(Structural)和行为型(Behavioral)三种:
- 创建型模式主要用于创建对象。
- 结构型模式主要用于处理类或对象的组合。
- 行为型模式主要用于描述对类或对象怎样交互和怎样分配职责。
根据范围（模式主要是用于处理类之间关系还是处理对象之间的关系）可分为类模式和对象模式两种：
- 类模式处理类和子类之间的关系，这些关系通过继承建立，在编译时刻就被确定下来，是属于静态的。
- 对象模式处理对象间的关系，这些关系在运行时刻变化，更具动态性。

## 创建型(Creational)
### 抽象工厂模式(Abstract Factory)
### 建造者模式(Builder)
### 工厂方法模式(Factory Method)
### 原型模式(Prototype)
### 单例模式(Singleton)

## 结构型(Structural)
### 适配器模式(Adapter)
### 桥接模式(Bridge)
### 组合模式(Composite)
### 装饰模式(Decorator)
### 外观模式(Facade)
### 享元模式(Flyweight)
### 代理模式(Proxy)

## 行为型(Behavioral)
### 职责链模式(Chain of Responsibility)
### 应用场景示例
- [消灭成堆的分支语句之类责任链模式](https://my.oschina.net/redraiment/blog/105209)
### 命令模式(Command)
### 解释器模式(Interpreter)
### 迭代器模式(Iterator)
### 中介者模式(Mediator)
### 备忘录模式(Memento)
### 观察者模式(Observer)
### 状态模式(State)
### 策略模式(Strategy)
### 模板方法模式(Template Method)
### 访问者模式(Visitor)

# Reference
- [责任链模式](https://www.runoob.com/design-pattern/chain-of-responsibility-pattern.html)
- [设计模式的三大分类及六大原则](https://blog.csdn.net/ttxs99989/article/details/81844135)
- [详解Spring AOP的底层代理JdkDynamicAopProxy和ObjenesisCglibAopProxy的源码分析](https://blog.csdn.net/f641385712/article/details/88952482)
- [GoF 的 23 种设计模式](http://c.biancheng.net/view/1348.html)

# License
Validator is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).