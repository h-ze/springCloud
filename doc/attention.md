Service层处理逻辑
  主要业务放在service中，因为service中有事务处理
  
1.rabbitmq 处理 基本完成 还需再多测试

2.redis 已使用部分内容 shiro中的缓存使用的是redis

3.mongodb处理 未安装mongodb

4.单元测试 处理完成
Service层进行单元测试
Spring Boot中单元测试类写在在src/test/java目录下，
你可以手动创建具体测试类，如果是IDEA，则可以通过IDEA自动创建测试类，
也可以通过快捷键⇧⌘T(MAC)或者Ctrl+Shift+T(Window)来创建

当需要对Controller层（API）做测试，这时候就得用到MockMvc了，你可以不必启动工程就能测试这些接口。
MockMvc实现了对Http请求的模拟，能够直接使用网络的形式，转换到Controller的调用，
这样可以使得测试速度快、不依赖网络环境，而且提供了一套验证的工具，这样可以使得请求的验证统一而且很方便

单元个测试的时候如果不想造成垃圾数据，可以开启事物功能，记在方法或者类头部添加@Transactional注解即可

这样测试完数据就会回滚了，不会造成垃圾数据。如果你想关闭回滚，只要加上@Rollback(false)注解即可。@Rollback表示事务执行完回滚，支持传入一个参数value，默认true即回滚，false不回滚。

如果你使用的数据库是Mysql，有时候会发现加了注解@Transactional 也不会回滚，
那么你就要查看一下你的默认引擎是不是InnoDB，如果不是就要改成InnoDB。

两个版本中的大多数注释都是相同的，但很少有区别。这是一个快速比较。
特征	                                JUNIT 4	        JUNIT 5
声明一种测试方法	                        @Test	        @Test
在当前类中的所有测试方法之前执行	        @BeforeClass	@BeforeAll
在当前类中的所有测试方法之后执行	        @AfterClass	    @AfterAll
在每个测试方法之前执行	                @Before	        @BeforeEach
每种测试方法后执行                   	@After	        @AfterEach
禁用测试方法/类	                        @Ignore	        @Disabled
测试工厂进行动态测试	                    NA	            @TestFactory
嵌套测试	                                NA	            @Nested
标记和过滤	                            @Category	    @Tag
注册自定义扩展	                        NA	            @ExtendWit


5.日志集成 处理完成 还需研究打印内容
分别使用logback和log4j2两种方式 需要放开不同的依赖
不使用log4j日志方式

6.jenkins自动化部署 

7.so文件位置

8.mybatis配置文件

9.mysql (处理完成)
sql编写 (sql语句基本完成)
select * from t_user where userid=#{userId}
update t_user set username =#{username} where userid=#{userId}
delete from t_user where userid=#{userId}
insert into t_user values(#{userId},#{username},#{age})
查询语句参考 : https://zhuanlan.zhihu.com/p/153271429 

-----连接超时 自动断开连接等问题

-----mysql索引

10.gRpc 尝试过

11 oss阿里云 已购买 已经实现上传文件 接下来需要整合springboot

12 null判断该如何处理