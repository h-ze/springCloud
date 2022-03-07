ClientConfiguration是OSSClient的配置类，您可通过此类来配置代理、连接超时、最大连接数等参数。可设置的参数如下：
参数 	描述 	方法
MaxConnections 	允许打开的最大HTTP连接数。默认为1024。 	ClientConfiguration.setMaxConnections
SocketTimeout 	Socket层传输数据的超时时间（单位：毫秒）。默认为50000毫秒。 	ClientConfiguration.setSocketTimeout
ConnectionTimeout 	建立连接的超时时间（单位：毫秒）。默认为50000毫秒。 	ClientConfiguration.setConnectionTimeout
ConnectionRequestTimeout 	从连接池中获取连接的超时时间（单位：毫秒）。默认不超时。 	ClientConfiguration.setConnectionRequestTimeout
IdleConnectionTime 	连接空闲超时时间，超时则关闭连接（单位：毫秒）。默认为60000毫秒。 	ClientConfiguration.setIdleConnectionTime
MaxErrorRetry 	请求失败后最大的重试次数。默认3次。 	ClientConfiguration.setMaxErrorRetry
SupportCname 	是否支持CNAME作为Endpoint，默认支持CNAME。 	ClientConfiguration.setSupportCname
SLDEnabled 	是否开启二级域名（Second Level Domain）的访问方式，默认不开启。 	ClientConfiguration.setSLDEnabled
Protocol 	连接OSS所采用的协议（HTTP或HTTPS），默认为HTTP。 	ClientConfiguration.setProtocol
UserAgent 	用户代理，指HTTP的User-Agent头。默认为aliyun-sdk-java。 	ClientConfiguration.setUserAgent
ProxyHost 	代理服务器主机地址。 	ClientConfiguration.setProxyHost
ProxyPort 	代理服务器端口。 	ClientConfiguration.setProxyPort
ProxyUsername 	代理服务器验证的用户名。 	ClientConfiguration.setProxyUsername
ProxyPassword 	代理服务器验证的密码。 	ClientConfiguration.setProxyPassword
RedirectEnable 	是否开启HTTP重定向。
说明 Java SDK 3.10.1及以上版本支持设置是否开启HTTP重定向，默认开启。
	ClientConfiguration.setRedirectEnable
VerifySSLEnable 	是否开启SSL证书校验。
说明 Java SDK 3.10.1及以上版本支持设置是否开启SSL证书校验，默认开启。
	ClientConfiguration.setVerifySSLEnable