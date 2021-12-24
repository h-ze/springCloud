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
//cloud-hystrix8103
//cloud-hystrix-dashboard9001


SpringCloud Bus
消息总线
SpringCloud Bus动态刷新全局广播
SpringCloud Bus动态刷新定点通知
Bus中使用到了rabbitmq
//cloud-config-center3344
//cloud-config-center3355
//cloud-config-center3366

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
//cloud-gateway9100

SpringCloud Stream
消息驱动

注意在stream中处于同一个group中的多个消费者是竞争关系，就能保证消息只被其中一个应用消费一次
不同组是可以全面消费的（重复消费）
同一组内会发生竞争关系，只有其中一个可以消费
解决重复消费的方法是分组，分到同一组就不会重复消费
//cloud-stream-rabbitmq-consumer8802
//cloud-stream-rabbitmq-consumer8803
//cloud-stream-rabbitmq-provider8801


SpringCloud Sleuth 分布式请求链路跟踪




SpringCloudAlibaba 重点

Nacos
支持ap和cp
cloudAlibaba-order80
cloudAlibaba-provider-payment9001
cloudAlibaba-provider-payment9003

查看nacos集群的启动
ps -ef|grep nacos|grep -v grep|wc -l

启动nginx
./nginx -c /usr/local/nginx/conf/nginx.conf

阿里云上启动nacos方式
1.如果服务器多或配置够好可以配置多个
2.如果服务器配置过低可以使用不同端口号进行调试
  方法为进入nacos3333中 进入bin目录中 使用命令行为:./start.sh -p 端口号
  目前为3333 4444 5555
3.使用docker


sentinel
Sentinel是阿里开源的项目,提供了流量控制、熔断降级、系统负载保护等多个维度来保障服务之间的稳定性
git官方地址:
https://github.com/alibaba/Sentinel/wiki/%E6%B5%81%E9%87%8F%E6%8E%A7%E5%88%B6#%E5%9F%BA%E4%BA%8E%E8%B0%83%E7%94%A8%E5%85%B3%E7%B3%BB%E7%9A%84%E9%99%90%E6%B5%81

需要了解：如何将sentinel导入到nacos


seata
分布式事务解决方案是业务层面的解决方案，只依赖于单台数据库的事务能力

使用前需要开启本地的seata服务 云端的如果已经开启了不用管




springcloud认证授权
我们理想的解决方案应该是这样的，认证服务负责认证，网关负责校验认证和鉴权，其他API服务负责处理自己的业务逻辑。安全相关的逻辑只存在于认证服务和网关服务中，其他服务只是单纯地提供服务而没有任何安全相关逻辑。
相关服务划分：
    cloud-gateway-oauth2-nacos9101：网关服务，负责请求转发和鉴权功能，整合Spring Security+Oauth2；
    cloud-auth8888：Oauth2认证服务，负责对登录用户进行认证，整合Spring Security+Oauth2；
    其余服务只需要进行系统间的调用 用户鉴权通过后可以访问其他服务，不整合Spring Security+Oauth2。