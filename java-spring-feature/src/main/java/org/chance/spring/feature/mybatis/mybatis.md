# 重点注解

# 重点组件和类介绍
## Configuration
## SqlSessionFactory 
## SqlSession 
接口层，实现所有数据库操作的API
## Executor

## MapperProxy
mapper接口的动态代理实现

# mybatis配置文件详解
## property
## environment
## typeAlias 
typeAliases节点主要用来设置别名，其实这是挺好用的一个功能， 通过配置别名，我们不用再指定完整的包名，并且还能取别名。
## typeHandler
无论是 MyBatis 在预处理语句（PreparedStatement）中设置一个参数时，还是从结果集中取出一个值时，都会用类型处理器将获取的值以合适的方式转换成 Java 类型。Mybatis默认为我们实现了许多TypeHandler, 当我们没有配置指定TypeHandler时，Mybatis会根据参数或者返回结果的不同，默认为我们选择合适的TypeHandler处理。
## objectFactory
MyBatis 每次创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成。默认的对象工厂需要做的仅仅是实例化目标类，要么通过默认构造方法，要么在参数映射存在的时候通过参数构造方法来实例化。默认情况下，我们不需要配置，mybatis会调用默认实现的objectFactory。 除非我们要自定义ObjectFactory的实现， 那么我们才需要去手动配置。
## plugin
plugins 是一个可选配置。mybatis中的plugin其实就是个interceptor， 它可以拦截Executor 、ParameterHandler 、ResultSetHandler 、StatementHandler 的部分方法，处理我们自己的逻辑。Executor就是真正执行sql语句的东西， ParameterHandler 是处理我们传入参数的，还记得前面讲TypeHandler的时候提到过，mybatis默认帮我们实现了不少的typeHandler, 当我们不显示配置typeHandler的时候，mybatis会根据参数类型自动选择合适的typeHandler执行，其实就是ParameterHandler 在选择。ResultSetHandler 就是处理返回结果的。
## mapper
 所谓的mapper映射文件，就是让mybatis 用来建立数据表和javabean映射的一个桥梁。在我们实际开发中，通常一个mapper文件对应一个dao接口， 这个mapper可以看做是dao的实现。所以,mappers必须配置。
### 子节点
select, insert, update, delete, cache, cache-ref, resultMap, sql 
###
###

##
##
##

# Reference
- [SpringBoot启动流程及其原理
](https://blog.csdn.net/weixin_30364147/article/details/97326919?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522159722326819725250102112%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=159722326819725250102112&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_click~default-1-97326919.pc_ecpm_v3_pc_rank_v3&utm_term=springboot%E7%9A%84%E5%90%AF%E5%8A%A8%E6%B5%81%E7%A8%8B%E5%8F%8A%E5%8E%9F%E7%90%86&spm=1018.2118.3001.4187)
