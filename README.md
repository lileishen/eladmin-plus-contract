### 计划安排

- [ ] 实现分库分表的界面可配置运维管理，为后续业务扩展进行强有力支撑。
- [ ] **欢迎大家提想法和意见，看到会第一时间交流和开发**

### 已完成新特性

- [x] 集成[screw-pro](https://gitee.com/rayson517/screw-pro) 强大的数据库表结构文档生成, 可以导出HTML/WORD/MD/EXCEL。 
- [x] 集成腾讯云/阿里云/百度云/SFTP/FTP/Minio等文件存储服务。

# eladmin-plus
eladmin的mybatis-plus移植版本。目前更新到2021年8月。<br>

对应前端vue: [eladmin-plus/eladmin-ui](https://gitee.com/rayson517/eladmin-plus/tree/master/eladmin-ui)

本项目只是将eladmin的dao层从JPA改为Mybatis-Plus，log日志在生产环境取消彩色。
其它地方没有什么修改，包括数据库结构和web前端。
想了解功能模块的说明，demo地址，文档，UI截图，可以参考原项目。

#### 主要特性
- 使用最新技术栈，社区资源丰富。
- 高效率开发，代码生成器可一键生成前后端代码
- 支持数据字典，可方便地对一些状态进行管理
- 支持接口限流，避免恶意请求导致服务层压力过大
- 支持接口级别的功能权限与数据权限，可自定义操作
- 自定义权限注解与匿名接口注解，可快速对接口拦截与放行
- 对一些常用地前端组件封装：表格数据请求、数据字典等
- 前后端统一异常拦截处理，统一输出异常，避免繁琐的判断
- 支持在线用户管理与服务器性能监控，支持限制单用户登录
- 支持运维管理，可方便地对远程服务器的应用进行部署与管理

### 项目捐赠

项目的发展离不开你的支持，请作者喝杯咖啡吧☕
<div style="float:left;border:solid 1px 000;margin:2px;"><img src="https://images.gitee.com/uploads/images/2021/0730/075815_7679f643_1436870.jpeg"  width="200" height="260" ><img src="https://images.gitee.com/uploads/images/2021/0730/075751_9b32be37_1436870.jpeg" width="200" height="260" ></div>
 

####  系统功能
- 用户管理：提供用户的相关配置，新增用户后，默认密码为123456
- 角色管理：对权限与菜单进行分配，可根据部门设置角色的数据权限
- 菜单管理：已实现菜单动态路由，后端可配置化，支持多级菜单
- 部门管理：可配置系统组织架构，树形表格展示
- 岗位管理：配置各个部门的职位
- 字典管理：可维护常用一些固定的数据，如：状态，性别等
- 系统日志：记录用户操作日志与异常日志，方便开发人员定位排错
- SQL监控：采用druid 监控数据库访问性能，默认用户名admin，密码123456
- 定时任务：整合Quartz做定时任务，加入任务日志，任务运行情况一目了然
- 代码生成：高灵活度生成前后端代码，减少大量重复的工作任务
- 邮件工具：配合富文本，发送html格式的邮件
- 七牛云存储：可同步七牛云存储的数据到系统，无需登录七牛云直接操作云数据
- 支付宝支付：整合了支付宝支付并且提供了测试账号，可自行测试
- 服务监控：监控服务器的负载情况
- 运维管理：一键部署你的应用

#### 项目结构
项目采用按功能分模块的开发方式，结构如下

- `eladmin-common` 为系统的公共模块，各种工具类，公共配置存在该模块

- `eladmin-system` 为系统核心模块也是项目入口模块，也是最终需要打包部署的模块

- `eladmin-logging` 为系统的日志模块，其他模块如果需要记录日志需要引入该模块

- `eladmin-tools` 为第三方工具模块，包含：图床、邮件、云存储、本地存储、支付宝

- `eladmin-generator` 为系统的代码生成模块，代码生成的模板在 system 模块中

- `eladmin-ui` 为后台管理 前端vue 代码。

#### 详细结构

```
- eladmin-common 公共模块
    - annotation 为系统自定义注解
    - aspect 自定义注解的切面
    - base 提供了Entity、DTO基类和mapstruct的通用mapper
    - config 自定义权限实现、redis配置、swagger配置、Rsa配置等
    - exception 项目统一异常的处理
    - utils 系统通用工具类
- eladmin-system 系统核心模块（系统启动入口）
	- config 配置跨域与静态资源，与数据权限
	    - thread 线程池相关
	- modules 系统相关模块(登录授权、系统监控、定时任务、运维管理等)
- eladmin-logging 系统日志模块
- eladmin-tools 系统第三方工具模块
- eladmin-generator 系统代码生成模块
- eladmin-ui 系统前端Vue代码
```


**原项目地址：**  [https://github.com/elunez/eladmin](https://github.com/elunez/eladmin)
