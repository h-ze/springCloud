# Log Async Spring Boot Starter

LockAsyncSpringBootStarter集成SpringBoot,@ActionLog灵活开启系统日志切面记录文件

支持2.3.x


## 使用方法

### 1. 添加`log-async-db-spring-boot-starter`依赖到您项目:
Maven

```xml
    <dependency>
        <groupId>com.mcy</groupId>
        <artifactId>log-async-db-spring-boot-starter</artifactId>
        <version>1.0.0.RELEASE</version>
    </dependency>
```
### 2. 添加配置项 `application.yaml`
```yaml
# 1.配置日志
#切面日志
action:
  async:
    log:
      enable: true  #是否开启插件
      project: ${spring.application.name} #项目名称
      version: ${info.version} #项目版本
      profile: ${spring.profiles.active} #运行环境
      trace: traceId #全局链路trace的key
      token: x-amz-security-token #用户令牌的key
```
### 3.Java代码使用

```java
        /**
          * 记录异常日志
          */
         @GetMapping(value = "/testRateLimiter")
         @RateLimiter(limit=1, timeout=1,unit = TimeUnit.MINUTES)
         @ResponseBody
         @ActionLog(moudle="用户模块",actionType = "获取流量")
         public Map<String,Object> testRateLimiter(){
             System.err.println("进入testRateLimiter方法");
             int a=1/0;
             /*Map<String,Object> result=new ConcurrentHashMap<String,Object>();*/
             testRate();
             Map<String,Object> result=new HashMap<String,Object>();
             result.put("a", null);
             result.put("b", "");
             result.put("c", "马大哈");
             return result;
         }
         /**
         * 记录正常日志
         */
        @PostMapping("/testCache")
        @ResponseBody
        @Cacheable(value="view",keyGenerator="keyGenerator")
        @ActionLog(moudle="字典数据",actionType = "系统数据")
        public String testCache(@RequestBody TUser user){
            System.err.println("进入testCache方法，本次传入参数:name="+user.getUsername()+",age="+user.getAge()+"\t 当前线程:"+Thread.currentThread().getName());
            testCache1(user);
            return user.getUsername();
        }
        


        /**
        * 线程池推荐用法
        */
       @Autowired
       ExecutorService mdcExecutor;
       CompletableFuture.supplyAsync(SupplierWrapper.of(()->{
          //TODO 自定义任务处理代码块              
       }),mdcExecutor);
    }
```
### 4.注意事项：
- #### 1.@ActionLog注解使用AOP处理，相同类A的两个带有@ActionLog方法调用时，被调用方缓存会失效。

- #### 2.插件支持灵活配置，是否开启异步日志，是否开启存储数据库。

- #### 3.遇到Aop导致@ActionLog注解失效时,A.使用@Autowired注入对象，替换this调用。B.将出问题的方法，拆分不同类调用。

- #### 4.切面日志针对，ServletRequest,MultipartFile类型，不执行参数打印。

- #### 5.微服务日志本处采用的类似SEATA的模式，在每个微服务数据库内部自带当前微服务日志表，记录微服务请求的日志信息。

- #### 6.每个微服务通过切面获取日志，记录日志到activeMQ指定队列，单独有个微服务读取activeMq日志队列数据，集中写入一个固定数据库。

- #### 7.插件启动时，会默认清除超过7天数的日志，防止数据库表数据量过大，支持用户配置插件线程池数量，日志存储时间等插件配置。

- #### 8.插件支持配置定时任务，清理指定时间段的日志，支持灵活配置，默认开启每1号执行一次日志清理，清理超过30天的日志。


