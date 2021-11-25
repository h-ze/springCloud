# springCloud

nacos 

ribbon 本地负载均衡 在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务调用技术。

jmeter 压力测试

Feign
feign.hystrix.enabled=true  是否开启熔断器

hystrix.command.default.execution.timeout.enabled=true  是否开启超时熔断, 如果为false, 则熔断机制只在服务不可用时开启

hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=300   设置超时熔断时间

服务的降级 熔断 限流