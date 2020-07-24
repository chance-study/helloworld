# aop的相关概念
切面（Aspect）：一个关注点的模块化，这个关注点实现可能另外横切多个对象。事务管理是J2EE应用中一个很好的横切关注点例子。切面用spring的 Advisor或拦截器实现。
连接点（Joinpoint）: 程序执行过程中明确的点，如方法的调用或特定的异常被抛出。
通知（Advice）: 在特定的连接点，AOP框架执行的动作。各种类型的通知包括“around”、“before”和“throws”通知。通知类型将在下面讨论。许多AOP框架包括Spring都是以拦截器做通知模型，维护一个“围绕”连接点的拦截器链。Spring中定义了四个advice: BeforeAdvice, AfterAdvice, ThrowAdvice和DynamicIntroductionAdvice
切入点（Pointcut）: 指定一个通知将被引发的一系列连接点的集合。AOP框架必须允许开发者指定切入点：例如，使用正则表达式。 Spring定义了Pointcut接口，用来组合MethodMatcher和ClassFilter，可以通过名字很清楚的理解， MethodMatcher是用来检查目标类的方法是否可以被应用此通知，而ClassFilter是用来检查Pointcut是否应该应用到目标类上
引入（Introduction）: 添加方法或字段到被通知的类。 Spring允许引入新的接口到任何被通知的对象。例如，你可以使用一个引入使任何对象实现 IsModified接口，来简化缓存。Spring中要使用Introduction, 可有通过DelegatingIntroductionInterceptor来实现通知，通过DefaultIntroductionAdvisor来配置Advice和代理类要实现的接口（使用较少）
目标对象（Target Object）: 包含连接点的对象。也被称作被通知或被代理对象。POJO
AOP代理（AOP Proxy）: AOP框架创建的对象，包含通知。 在Spring中，AOP代理可以是JDK动态代理或者CGLIB代理。
织入（Weaving）: 组装方面来创建一个被通知对象(通俗点说就是把切面应用到目标对象来创建新的代理对象的过程)，织入一般发生在如下几个时机:
(1)编译时：当一个类文件被编译时进行织入，这需要特殊的编译器才可以做的到，例如AspectJ的织入编译器
(2)类加载时：使用特殊的ClassLoader在目标类被加载到程序之前增强类的字节代码
(3)运行时：切面在运行的某个时刻被织入,SpringAOP就是以这种方式织入切面的，原理应该是使用了JDK的动态代理技术

# Pointcut表达式类型
标准的AspectJ Aop的pointcut的表达式类型是很丰富的，但是Spring Aop只支持其中的9种，外加Spring Aop自己扩充的一种一共是11(10+1)种类型的表达式，分别如下：
- execution：一般用于指定方法的执行，用的最多。
- within：指定某些类型的全部方法执行，也可用来指定一个包。
- this：Spring Aop是基于动态代理的，生成的bean也是一个代理对象，this就是这个代理对象，当这个对象可以转换为指定的类型时，对应的切入点就是它了，Spring Aop将生效。
- target：当被代理的对象可以转换为指定的类型时，对应的切入点就是它了，Spring Aop将生效。
- args：当执行的方法的参数是指定类型时生效。
- @target：当代理的目标对象上拥有指定的注解时生效。
- @args：当执行的方法参数类型上拥有指定的注解时生效。
- @within：与@target类似，看官方文档和网上的说法都是@within只需要目标对象的类或者父类上有指定的注解，则@within会生效，而@target则是必须是目标对象的类上有指定的注解。而根据笔者的测试这两者都是只要目标类或父类上有指定的注解即可。
- @annotation：当执行的方法上拥有指定的注解时生效。
- reference pointcut：(经常使用)表示引用其他命名切入点，只有@ApectJ风格支持，Schema风格不支持
- bean：当调用的方法是指定的bean的方法时生效。(Spring AOP自己扩展支持的)

# ProxyConfig：AOP配置类
```java
public class ProxyConfig implements Serializable {
	// 标记是否直接对目标类进行代理，而不是通过接口产生代理 jdk动态代理、cglib
	private boolean proxyTargetClass = false;
	// 标记是否对代理进行优化。true：那么在生成代理对象之后，如果对代理配置进行了修改，已经创建的代理对象也不会获取修改之后的代理配置。
	// 如果exposeProxy设置为true，即使optimize为true也会被忽略。
	private boolean optimize = false;
	// 标记是否需要阻止通过该配置创建的代理对象转换为Advised类型，默认值为false，表示代理对象可以被转换为Advised类型
	//Advised接口其实就代表了被代理的对象（此接口是Spring AOP提供，它提供了方法可以对代理进行操作，比如移除一个切面之类的），它持有了代理对象的一些属性，通过它可以对生成的代理对象的一些属性进行人为干预
	// 默认情况，我们可以这么完 Advised target = (Advised) context.getBean("opaqueTest"); 从而就可以对该代理持有的一些属性进行干预勒   若此值为true，就不能这么玩了
	boolean opaque = false;
	//标记代理对象是否应该被aop框架通过AopContext以ThreadLocal的形式暴露出去。
	//当一个代理对象需要调用它【自己】的另外一个代理方法时，这个属性将非常有用。默认是是false，以避免不必要的拦截。
	boolean exposeProxy = false;
	//标记是否需要冻结代理对象，即在代理对象生成之后，是否允许对其进行修改，默认为false.
	// 当我们不希望调用方修改转换成Advised对象之后的代理对象时，就可以设置为true 给冻结上即可
	private boolean frozen = false;
}
```

Advice: 通知拦截器
Advisor: 通知 + 切入点的适配器
Advised: 包含所有的Advised 和 Advice

# cglib、jdkDynamic
- JdkDynamicAopProxy 入口方法是动态代理的 invoke() 方法，CGLIB 使用的是 DynamicAdvisedInterceptor.intercept()方法
- JdkDynamicAopProxy使用的MethodInvocation 是： ReflectiveMethodInvocation 子类，
CGLIB 使用的是CglibMethodInvocation
它俩都是ProxyMethodInvocation接口的实现类。并且CglibMethodInvocation是继承自ReflectiveMethodInvocation的
- CGLib更适合代理不需要频繁实例化的类，而Spring绝大多数Bean都是单例的，因此在Spring AOP中我极力推荐使用CGLib，它的功能更强大些

# 注意点
- proxy(代理对象)代理的不是target,而是TargetSource,这点非常重要!!!
- 在使用Spring AOP的时候，我们从IOC容器中获取的Bean对象其实都是代理对象，而不是那些Bean对象本身，由于this关键字引用的并不是该Service Bean对象的代理对象，而是其本身，因此Spring AOP是不能拦截到这些被嵌套调用的方法的。

# Reference
- [Spring AOP中@Pointcut切入点表达式最全面使用介绍](https://blog.csdn.net/f641385712/article/details/83543270)
- [Spring AOP各个组件概述与总结](https://blog.csdn.net/f641385712/article/details/89312652)
- []()
- []()
- []()