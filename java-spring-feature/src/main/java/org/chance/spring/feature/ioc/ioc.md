# BeanDefinition
Spring容器里通过BeanDefinition对象来表示Bean，BeanDefinition描述了Bean的配置信息。

# BeanDefinitionRegistry
BeanDefinitionRegistry接口提供了向容器注册，删除，获取BeanDefinition对象的方法。BeanDefinitionRegistry可以用来管理BeanDefinition，所以理解AnnotationConfigApplicationContext很关键，它是spring加载bean，管理bean的最重要的类。

# Q&A
## Spring容器怎么解决循环依赖的
使用一级缓存就可以解决循环依赖了，但是这样产的问题就是不能区分缓存里面的bean到底是不是已经创建完成且是可用状态的。
使用二级缓存的问题是，如果之后后置处理器处理代理的问题，导致bean引用的还是之前的
使用三级缓存就能解决之上的问题。

# Ref
- [Spring IOC容器启动流程 AbstractApplicationContext#refresh()方法源码分析（一）](https://blog.csdn.net/f641385712/article/details/88041409)
- [Spring解析@Configuration注解的处理器：ConfigurationClassPostProcessor（ConfigurationClassParser）](https://blog.csdn.net/f641385712/article/details/88095165)
- [一文告诉你Spring是如何利用“三级缓存“巧妙解决Bean的循环依赖问题的](https://blog.csdn.net/f641385712/article/details/92801300?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522159541622719195188420150%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=159541622719195188420150&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_v1~rank_blog_v1-1-92801300.pc_v1_rank_blog_v1&utm_term=%E5%BE%AA%E7%8E%AF%E4%BE%9D%E8%B5%96&spm=1018.2118.3001.4187)
- [Spring 解决循环依赖为什么使用三级缓存，而不是二级缓存](https://www.cnblogs.com/grey-wolf/p/13034371.html#_label5)
- [Spring源码学习总结（三）：从bean实例获取对象（getObjectForBeanInstance）](https://blog.csdn.net/xxg1993/article/details/100806923)