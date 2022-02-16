# 授权服务
服务授权过程：
Oauth2ServerConfig::configure(ClientDetailsServiceConfigurer clients)  
这个设置了客户端的一些信息，但是demo中是先把数据加载到内存中，做校验时，从内存拿。但是实际中我们应该是从数据库或者redis缓存中拿，
它还提供了一些其他的方法可以参考使用

UserServiceImpl::UserDetails loadUserByUsername(String s)
这里是对用户信息来做校验的，同样，只是一个简单的demo，实际上我们还需要再重写这里