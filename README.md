# 警告！！

**本项目中包含：**

​	**HanYuPinYin**

​	**命名不规范**

​	**代码重复**

基本操作，无需吐槽

# TIPS：

本项目主要逻辑均基于SpringBoot+MyBatis+MySQL实现，采用机器人框架->调用API->返回数据->发送消息的方式。

项目主要实现机器人的自定义事件触发、自动回复。

感谢[机器人框架](https://github.com/OPQBOT/OPQ/wiki)提供的技术支持，有关框架的部署请参考wiki文档。

感谢[兔兔阿米娅](https://github.com/vivien8261/Amiya-Bot)提供的数据支持，目前数据仍采用手动更新，自动爬取数据功能在开发中(在做了，在做了.jpg)。

感谢[百度智能云](https://console.bce.baidu.com/)提供的图像识别技术，公开招募截图可以直接截图查询。

感谢[pixiv接口](https://github.com/ScienJus/pixiv-parser)提供pixiv网站接口，涩图功能仍在开发中。

# 项目结构

main：

​	java：

​		config:Spring配置文件

​		controller：控制层

​		dao：Mapper文件，mapping的interface

​		job：定时任务

​		model：数据封装bean类

​		service：服务类，主要计算逻辑

​		util：工具类，存放静态方法

​		vo：返回类型封装

​	resource：

​		lua：lua插件留底

​		mapping：mapping文件，SQL语句

​		sql：数据库结构及数据留底

# 项目部署：

[机器人框架部署文档](https://github.com/OPQBOT/OPQ/wiki/%E5%AE%89%E8%A3%85%E6%8C%87%E5%8D%97)

[本项目部署文档](https://www.cnblogs.com/strelizia/p/14120201.html)

# 责任声明：

**我国有一套完善的法律制度，请不要使用本项目传播不能过审的信息，请不要使用本项目进行盈利行为，应当遵守当地法律法规。**

部署后将机器人拉入群聊，发送[##菜单]即可获取功能详情，当前触发关键字是##，后期会酌情更改。

抽卡的垫刀数是根据抽卡人QQ分别记录的，因此会记录QQ，群昵称以及群号涉及部分隐私信息，虽已将信息加密存储，但因为不同的服务器安全等级不同，仍有泄露风险，请酌情使用或修改源码。

自8.2事变起，企鹅机器人风控异常严格，容易造成机器人账号冻结，为了您的账号安全，请使用小号进行测试，小号提前预热活跃度可以有效减少冻结几率。

# 售后服务：

当然这种小辣鸡项目可能bug都不会有。

bug报修请私信博客园，内容包括但不限于：bug触发条件、截图、报错信息。

[博客地址](https://www.cnblogs.com/strelizia/)，有其他问题也可直接联系。不定期收私信，可能回复有延迟。