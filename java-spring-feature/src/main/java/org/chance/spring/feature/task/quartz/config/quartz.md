### Quartz API的关键接口是：
- Scheduler - 与调度程序交互的主要API。
- Job - 由希望由调度程序执行的组件实现的接口。
- JobDetail - 用于定义作业的实例。
- Trigger（即触发器） - 定义执行给定作业的计划的组件。
- JobBuilder - 用于定义/构建JobDetail实例，用于定义作业的实例。
- TriggerBuilder - 用于定义/构建触发器实例。

### Quartz集群相关数据库表
这些SQL脚本存放于<quartz_home>/docs/dbTables目录下
#### 调度器状态表（QRTZ_SCHEDULER_STATE）
说明：集群中节点实例信息，Quartz定时读取该表的信息判断集群中每个实例的当前状态。
- instance_name：配置文件中org.quartz.scheduler.instanceId配置的名字，如果设置为AUTO,quartz会根据物理机名和当前时间产生一个名字。
- last_checkin_time：上次检入时间
- checkin_interval：检入间隔时间

#### 触发器与任务关联表（qrtz_fired_triggers）
存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息。

#### 触发器信息表（qrtz_triggers)
- trigger_name：trigger的名字,该名字用户自己可以随意定制,无强行要求
- trigger_group：trigger所属组的名字,该名字用户自己随意定制,无强行要求
- job_name：qrtz_job_details表job_name的外键
- job_group：qrtz_job_details表job_group的外键
- trigger_state：当前trigger状态设置为ACQUIRED,如果设为WAITING,则job不会触发
- trigger_cron：触发器类型,使用cron表达式

#### 任务详细信息表（qrtz_job_details）
说明：保存job详细信息,该表需要用户根据实际情况初始化
- job_name：集群中job的名字,该名字用户自己可以随意定制,无强行要求。
- job_group：集群中job的所属组的名字,该名字用户自己随意定制,无强行要求。
- job_class_name：集群中job实现类的完全包名,quartz就是根据这个路径到classpath找到该job类的。
- is_durable：是否持久化,把该属性设置为1，quartz会把job持久化到数据库中
- job_data：一个blob字段，存放持久化job对象。

#### 权限信息表（qrtz_locks）
说明：tables_oracle.sql里有相应的dml初始化，如图2.4所示。

### 集群
- fail-over 是一个节点正在执行一个或多个jobs时失败了出现的。当一个节点失败了，其他的节点就会在数据库里核查条件和鉴别jobs，这些是节点失败时记录到了数据库的。在恢复节点时，任何标记了恢复（JobDetail里的"requests recovery"属性）的jobs将会被再次执行，没有标记的将会简单地释放掉。
- load balancing 是自动出现的，集群的每个节点尽可能快地触发job。当一个触发器触发时刻到了，第一个将获取触发器（并加锁）的节点就是将要触发它的节点。

### 参考
- [Quartz集群原理及配置应用](https://www.cnblogs.com/zhenyuyaodidiao/p/4755649.html)
- [quartz详解1：初步了解quartz](http://blog.itpub.net/11627468/viewspace-1763389/)