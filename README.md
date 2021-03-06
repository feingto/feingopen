# feingopen

#### 项目介绍
基于 Spring Boot 1.5 - 2.0、Spring Cloud Edgware - Finchley 构建的微服务综合管理应用平台。 目前包含注册中心、网关、管理平台

#### 目前提交整改阶段...
#### UI单独另外一个仓库

#### 软件架构
软件架构说明
* feingto-account - 用户中心
* feingto-admin - 管理平台
* feingto-config - 分布式配置
* feingto-gateway - API网关 - 分布式金丝雀动态路由
* feingto-remote - Feign客户端服务
* feingto-uaa - OAuth2授权服务中心

![login](http://www.feingto.com/static/gateway_demo/login.png)
![api_group](http://www.feingto.com/static/gateway_demo/api_group.png)
![api_group_stage](http://www.feingto.com/static/gateway_demo/api_group_stage.png)
![api list](http://www.feingto.com/static/gateway_demo/api_list.png)
![api_detail](http://www.feingto.com/static/gateway_demo/api_detail.png)
![api_history](http://www.feingto.com/static/gateway_demo/api_history.png)


#### 安装教程

1. cmd.bat (windows)
2. docker-compose up -d

#### 使用说明

##### UUA授权服务中心
* http://localhost:8800/uaa
* 默认账号 admin
* 密码 123456

##### 用户中心
* 端口 8900

##### 管理平台
* http://localhost:9000
* 默认账号 admin
* 密码 123456

##### API 网关 - 分布式金丝雀动态路由
* http://localhost:8215
###### 主要功能：
* 多服务聚合
* 多环境变量配置
* 灰度发布，支持TEST、PRE、RELEASE三个Stage，调用参数 X-Ca-Stage: RELEASE
* 请求参数（Header、Query、Path）和服务参数映射配置
* 结果缓存
* 统一授权认证
* 流控策略（API多种环境策略限流）
* Mock数据，根据MockJs规则模拟
* 在线调试
* 自动刷新配置管理
* 监控规则配置、监控预警、拦截提醒
* 服务跟踪、监控信息

##### 注解说明
* 启用Druid数据源 @EnableDruidAutoConfiguration
* 启用动态数据源 @EnableDynamicDataSource, 默认使用Druid数据库连接池
* 启用JPA事务和数据源事务管理 @EnableMuliTransactionManager
* 启用JPA事务管理 @EnableJpaTransactionManager
* 启用Mybatis @EnableMybatisAutoConfiguration
* 启用事件 @EnableEvent, 实现IEvent接口
* 启用插件 @Plugin, 前后置通知 @PluginBeforeAdvice, @PluginAfterAdvice
* 启用Redis @EnableRedisAutoConfiguration, 方法注解 @RedisCacheable, @RedisCachePut, @RedisCacheEvict

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

##### OAuth2说明
###### 授权模式以及API权限验证
* 授权码模式 authorization code
* 客户端模式 client credentials
* 密码模式 resource owner password credentials
* 简化模式 implicit

#### Docker说明
* 在`${maven_home}/conf/settings.xml`中增加`server`节点，示例：
```
<server>
    <id>docker-aliyun</id>
    <username>登录名</username>
    <password>登录密码</password>
    <configuration>
        <email>邮箱</email>
    </configuration>
</server>
```
* 使用`docker:build`命令编译docker镜像
* 使用`docker:push`命令推送docker镜像到上面配置的服务器
