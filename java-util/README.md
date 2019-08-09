### gradle的配置和使用
[Gradle User Guide 中文版](https://dongchuan.gitbooks.io/gradle-user-guide-/content/troubleshooting/)
#### 设置环境变量
```bash
$ export GRADLE_HOME = /Users/UFreedom/gradle
$ export export PATH=$PATH:$GRADLE_HOME/bin
$ gradle -v
```

#### JVM 选项
- JAVA_OPTS 是一个用于 JAVA 应用的环境变量. 一个典型的用例是在 JAVA_OPTS 里设置HTTP代理服务器(proxy)
- GRADLE_OPTS 是内存选项. 这些变量可以在 gradle 的一开始就设置或者通过 gradlew 脚本来设置

#### 使用 Gradle 命令行
- 多任务调用 gradle compile test
- 排除任务 gradle dist -x test
- 失败后继续执行构建 --continue 选项

### 打印日志
```bash 
logger.quiet('An info log message which is always logged.')
logger.error('An error log message.')
logger.warn('A warning log message.')
logger.lifecycle('A lifecycle info log message.')
logger.info('An info log message.')
logger.debug('A debug log message.')
logger.trace('A trace log message.')
```
