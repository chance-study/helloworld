### apache dubbo 2.7新特性
- [github >> dubbo](https://github.com/apache/dubbo)
- [dubbo-spring-boot-project](https://github.com/apache/dubbo-spring-boot-project)
https://dubbo.apache.org/zh-cn/blog/dubbo-27-features.html


### dubbo异常的处理
- 建议使用异常汇报错误，而不是返回错误码，异常信息能携带更多信息，并且语义更友好。
- 如果担心性能问题，在必要时，可以通过 override 掉异常类的 fillInStackTrace() 方法为空方法，使其不拷贝栈信息。
- 查询方法不建议抛出 checked 异常，否则调用方在查询时将过多的 try...catch，并且不能进行有效处理。
- 服务提供方不应将 DAO 或 SQL 等异常抛给消费方，应在服务实现中对消费方不关心的异常进行包装，否则可能出现消费方无法反序列化相应异常。

### Dubbo QoS命令
[Dubbo QoS命令](http://dubbo.apache.org/zh-cn/docs/user/references/qos.html)
- help: 帮助命令，列出
- ls: 列出当前所有的正在提供的服务，以及消费的服务
- online: 动态将某个或全部服务向注册中心进行注册
- offline: 动态将某个或全部服务从注册中心摘除（反注册）
- quit: 退出当前telnet会话

### 参考文献
- [apache官方文档](http://dubbo.apache.org/zh-cn/)
- [服务化的最佳](http://dubbo.apache.org/zh-cn/docs/user/best-practice.html)