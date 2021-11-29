# springCloud

nacos 

ribbon 本地负载均衡 在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务调用技术。

jmeter 压力测试

Feign
feign.hystrix.enabled=true  是否开启熔断器

hystrix.command.default.execution.timeout.enabled=true  是否开启超时熔断, 如果为false, 则熔断机制只在服务不可用时开启

hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=300   设置超时熔断时间

服务的降级 熔断 限流

nginx +网关 +微服务

zuul1 是阻塞框架
gateway是异步非阻塞
Spring WebFlux是spring5.0之后引入的新的响应式框架，区别于springmvc，它不需要依赖于servlet api，是完全的异步非阻塞的，并且是基于Reactor来实现响应式流规范


curl命令

application.yml是用户级的资源配置项
bootstrap.yml是系统级的,优先级更高


Hystrix
服务降级
1.服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback
2.
    2.1 哪些情况会出发降级
        2.1.1 程序运行异常
        2.1.2 超时
        2.1.3 服务熔断触发服务降级
        2.1.4 线程池/信号量打满也会导致服务降级
服务熔断
1.类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示
2.就是保险丝 服务的降级->进而熔断->恢复调用链路
服务限流
1.秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行

Hystrix中需要进行高并发测试

SpringCloud Bus
消息总线
SpringCloud Bus动态刷新全局广播
SpringCloud Bus动态刷新定点通知
Bus中使用到了rabbitmq


SpringCloud Config
分布式配置中心



SpringCloud Gateway
新一代网关
SpringCloud Gateway 使用的Webflux中的reactor-netty响应式编程组件，底层使用了Netty通讯框架。
作用：
1.反向代理
2.鉴权
3.流量控制
4.熔断
5.日志监控
核心概念：
1.Route(路由)
  路由是构建网关的基本模块，它由ID，目标URI，一系列的断言和过滤器组成，如果断言为true则匹配该路由
2.Predicate(断言)
  参考的是Java8的java.util.function.Predicate
  开发人员可以匹配HTTP请求中的所有内容(例如请求头或请求参数)，如果请求与断言相匹配则进行路由
3.Filter(过滤)
  指的是Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改。
Gateway工作流程
核心逻辑为
路由转发+执行过滤器链


SpringCloud Stream
消息驱动