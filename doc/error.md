错误信息：
NACOS SocketTimeoutException httpGet] currentServerAddr:http://localhost:8848， err : connect timed out
原因：
bootstrap.yml（bootstrap.properties）用来在程序引导时执行，应用于更加早期配置信息读取，如可以使用来配置application.yml中使用到参数等；
application.yml（application.properties) 应用程序特有配置信息，可以用来配置后续各个模块中需使用的公共参数等。
注意:
后来经过对比依赖文件发现是因为依赖了nacos-config包造成的，如果项目中用不到这个功能依赖，可以将这部分依赖删除，删除之后application.yml中的nacos配置有效。