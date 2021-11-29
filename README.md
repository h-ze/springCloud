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
Spring WebFlux是spring5.0之后引入的新的响应式框架，区别于springmvc，它不需要依赖于servlet api，是完全的异步非阻塞的，并且是基于Reactor来实现响应式流规范# springcloudmac
