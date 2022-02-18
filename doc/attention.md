Service层处理逻辑
  主要业务放在service中，因为service中有事务处理
  
rabbitmq 处理

redis

mongodb处理

单元测试

日志集成

自动化部署 jenkins

so文件位置

mybatis配置文件

mysql (处理完成)
sql编写 (sql语句基本完成)
select * from t_user where userid=#{userId}
update t_user set username =#{username} where userid=#{userId}
delete from t_user where userid=#{userId}
insert into t_user values(#{userId},#{username},#{age})
查询语句参考 : https://zhuanlan.zhihu.com/p/153271429 

连接超时 自动断开连接等问题

mysql索引

gRpc
