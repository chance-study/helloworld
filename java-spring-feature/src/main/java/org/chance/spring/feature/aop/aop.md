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