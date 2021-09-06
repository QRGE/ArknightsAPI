# 警告！！

**本项目中包含：**

​	**HanYuPinYin**

​	**命名不规范**

​	**代码重复**

基本操作，无需吐槽

# TIPS：

本项目主要逻辑均基于SpringBoot+MyBatis+MySQL实现，采用机器人框架->调用API->返回数据->发送消息的方式。

项目主要实现机器人的自定义事件触发、自动回复。

感谢[OPQ机器人框架](https://github.com/OPQBOT/OPQ/wiki)提供的技术支持，有关框架的部署请参考wiki文档。

感谢[兔兔阿米娅](https://github.com/vivien8261/Amiya-Bot)，[企鹅物流](https://penguin-stats.cn/)，[kokodayo资料站](https://kokodayo.fun/)，[ArknightsGameData](https://github.com/Kengxxiao/ArknightsGameData)提供的数据支持。

感谢[百度智能云](https://cloud.baidu.com/)提供的图像识别技术，公开招募可以直接截图查询。

前端主页：[Angelina's Home](http://www.angelina-bot.top/)

# 责任声明：

**我国有一套完善的法律制度，请不要使用本项目传播不能过审的信息，应当遵守中华人民共和国法律法规。**

部署后将机器人拉入群聊，发送[##菜单]即可获取功能详情，当前触发关键字是##，后期会酌情更改。

抽卡的垫刀数是根据抽卡人QQ分别记录的，因此会记录QQ，群昵称以及群号，涉及部分隐私信息，虽已将信息加密存储，由于不同的服务器安全等级不同，仍有泄露风险，请酌情使用或修改源码。

自8.2事变起，企鹅机器人风控异常严格，容易造成机器人账号冻结，为了您的账号安全，请使用小号进行测试，小号提前预热活跃度可以有效减少冻结几率。

# 功能实现：

[功能文档](https://github.com/Strelizia02/ArknightsAPI/wiki)

# 项目结构：

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

[视频教程](https://www.bilibili.com/video/BV1hw411f7a4)

# TODO：

- [x] 增加敌人信息
- [x] 材料信息自动更新
- [ ] 对接微博api监听微博更新
- [x] 对接B站api提供作业查询
- [x] 给孩子起个名吧，球球啦
- [ ] 自然语言处理，中文分词，关键词触发
- [ ] 语音识别系统
- [ ] 增加前端管理页面
- [x] 增加材料图标、干员立绘
- [x] 增加干员档案信息
- [x] 干员生日提醒推送

# 售后服务：

当然这种小辣鸡项目可能bug都不会有。

bug报修请私信博客园，内容包括但不限于：bug触发条件、截图、报错信息。

[博客地址](https://www.cnblogs.com/strelizia/)，有其他问题也可直接联系。不定期收私信，可能回复有延迟。

# 打赏通道：

![1629880378050](https://user-images.githubusercontent.com/48582013/132192555-e08f910d-5246-456f-9cfb-e0c2fa10ee3d.jpg)
![mm_facetoface_collect_qrcode_1630919564923](https://user-images.githubusercontent.com/48582013/132192590-1cf72f8b-051d-42fd-a977-32094073da54.png)
