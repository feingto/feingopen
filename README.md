# feingopen

#### 项目介绍
基于 Spring Boot 1.5 - 2.0、Spring Cloud Edgware - Finchley 构建的微服务综合管理应用平台。 目前包含注册中心、网关、管理平台

目前处于提交整改阶段...

#### 软件架构
软件架构说明
* feing-account - 用户中心
* feing-admin - 管理平台
* feing-config - 分布式配置
* feing-gateway - API网关 - 分布式金丝雀动态路由
* feing-remote - Feign客户端服务
* feing-uaa - OAuth2授权服务中心


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

#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [http://git.mydoc.io/](http://git.mydoc.io/)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)