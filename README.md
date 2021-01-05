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

感谢[兔兔阿米娅](https://github.com/vivien8261/Amiya-Bot)提供的数据支持，因服务器性能原因，关闭了每日自动更新，采用swagger触发的模式。

感谢[百度智能云](https://console.bce.baidu.com/)提供的图像识别技术，公开招募截图可以直接截图查询。

感谢[pixiv接口](https://github.com/ScienJus/pixiv-parser)提供pixiv网站接口。

# 责任声明：

**我国有一套完善的法律制度，请不要使用本项目传播不能过审的信息，请不要使用本项目进行盈利行为，应当遵守中华人民共和国法律法规。**

部署后将机器人拉入群聊，发送[##菜单]即可获取功能详情，当前触发关键字是##，后期会酌情更改。

抽卡的垫刀数是根据抽卡人QQ分别记录的，因此会记录QQ，群昵称以及群号，涉及部分隐私信息，虽已将信息加密存储，由于不同的服务器安全等级不同，仍有泄露风险，请酌情使用或修改源码。

自8.2事变起，企鹅机器人风控异常严格，容易造成机器人账号冻结，为了您的账号安全，请使用小号进行测试，小号提前预热活跃度可以有效减少冻结几率。

# 功能实现：

## root权限：

目前仅包括无限抽卡权限，无限涩图权限，爆率拉满权限。

管理员维护需从数据库中修改数据表a_admin_user，不提供外部接口。

qq和name两个字段推荐从a_user_found中复制，默认权限都为0(无权限)，修改为1以后即可获取相应权限。



## 0.菜单：

	使用方法：输入{##菜单}或{##详细菜单}
## 1.模拟寻访：

	使用方法：输入{##十连 卡池名}或{##单抽 卡池名}
	例：##卡池 无拘熔火
## 2.卡池列表：

```
使用方法：输入{##卡池}
```

## 3.卡池清单：

```
使用方法：输入{##卡池清单 卡池名}
```

## 4.查询个人垫刀数、今日抽卡数、当前六星概率：

```
使用方法：输入{##垫刀查询}
```

## 5.查询技能专精材料：

	使用方法：输入{##专精材料 干员名 第几技能 专精等级}或{##专精材料 技能名 专精等级}
	例1：##专精材料 艾雅法拉 3 3
	例2：##专精材料 艾雅法拉 三技能 专三
	例3：##专精材料 火山 3
## 6.精英化材料查询：

	使用方法：输入{##精一材料 干员名}或{精二材料 干员名}
	例1：##精一材料 克洛丝
	例2：##精二材料 陈
## 7.查询材料合成路线：

	使用方法：输入{##合成路线 材料名}
	例：##合成路线 D32钢
## 8.查询材料掉落关卡：

	使用方法：输入{##材料获取 材料名}
	例：##材料获取 研磨石
## 9.公招结果查询：

​	使用方法：输入{##公招截图 [图片]}，自动识图并返回结果

## 10.公开招募tag组合查询：

	使用方法：输入{##公开招募 [tag1],[tag2]}
	例：##公开招募 爆发,近战位,高级资深干员
## 11.涩图功能：

```
使用方法：输入{##涩图}
由于涩图大小原因，回复会产生一定的延迟，极低概率会出现丢包的情况（线程池满导致），若涩图无响应请等待至少10秒钟再次请求
```

## 12.上传涩图：

	使用方法：输入{##给你涩图[图片]}或纯图片私聊{[图片]}
	请不要上传过大的涩图，更不要上传不能过审的涩图

注：本项目需严格按照格式输入，自然语言处理功能将在后期优化

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

# TODO：

- [ ] 增加敌人信息
- [ ] 材料信息自动更新
- [ ] 对接微博api监听微博更新
- [ ] 对接B站api提供作业查询
- [ ] 给孩子起个名吧，球球啦
- [ ] 自然语言处理，中文分词，关键词触发

# 售后服务：

当然这种小辣鸡项目可能bug都不会有。

bug报修请私信博客园，内容包括但不限于：bug触发条件、截图、报错信息。

[博客地址](https://www.cnblogs.com/strelizia/)，有其他问题也可直接联系。不定期收私信，可能回复有延迟。