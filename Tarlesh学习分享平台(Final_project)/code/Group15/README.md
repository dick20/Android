# 一个文档

 by lt



## 使用方法  

安装运行服务器：  
先执行init.sql文件。  （如果你有filesystem这个名字的数据库，请先删掉）
server目录下，yarn（没有的话请自行安装），然后 node index。 （因为有的包我全局装了可能没注意，缺啥装啥就对了）  
然后就运行起来了。  

------

## 功能

1. 用户管理
   - 登录登出，注册，用户详情  
2. 文件管理  
   - 上传下载
3. 搜索  
   - 标签分组之类的 （这个没想好怎么搞）
   - 名称之类的
4. 激励  
   - 用户排名
   - 文件排名
   - （其实我觉得换成积分会稍微好点，而且应该是一个表的事，当然下载验证的时候也要加一点东西（＞人＜；）

页面功能：

- 打开APP页面，广告三秒跳转。（梁颖霖
- 登陆注册注销页面。
- 文档排行榜。（梁颖霖
- 主页推荐，搜索页面。上传按钮。（梁颖霖
- 文档详情，评分，访问次数，下载量。下载按钮。（梁育诚
- 问答quiz。（梁育诚
- 用户详情页面 一些积分，名字，退出，曾经下载，曾经上传。（梁庭



api：

- 用户注册
- 用户登陆
- 文档的上传
- 文档的下载
- quiz的新建
- 新建一个quiz的回答



基类：

用户

- 积分
- 名字
- 曾经下载
- 曾经上传



文档

- 名字
- 评分
- 下载量
- 内容详情介绍
- 评价数组



quiz

- answer的数组

------

## 数据库设计  

### 1. user 

用户表

------

| uid      | username       | pwd          | phone  | email |
| -------- | -------------- | ------------ | ------ | ----- |
| 标识用户 | 字符串，用户名 | 字符串，密码 | 手机号 | 邮箱  |

------

### 2. file  

文件表  

------

| fid                      | filename     | description | createTime | owner  |
| ------------------------ | ------------ | ----------- | ---------- | ------ |
| 标识文件以及存储用的名字 | 原来的文件名 | 描述        | 上传时间   | 所有者 |

| cid      | uid    | fid    | msg      | createTime |
| -------- | ------ | ------ | -------- | ---------- |
| 评论识别 | 用户id | 文件id | 评论内容 | 评论时间   |



------

### 3. operateRecord  

下载（上传，点赞等，然而现在并没有）操作的记录   

------

|   oid    |  fid   |  uid   |  opTime  |
| :------: | :----: | :----: | :------: |
| 操作识别 | 文件id | 用户id | 操作时间 |

------

### 积分  

根据operateRecord现场算  

### 论坛  

| qid    | uid      | title | content | createTime |
| ------ | -------- | ----- | ------- | ---------- |
| 问题id | 发起人id | 标题  | 内容    | 创建时间   |



## api  

### post /api/login/trylogin  

作用： 登陆  

参数   

| name     | type   | description |
| -------- | ------ | ----------- |
| username | string | none        |
| password | string | none        |

返回值    

| name  | type   | description   |
| ----- | ------ | ------------- |
| sign  | bool   | 请求状态      |
| msg   | string | 详细信息      |
| token | string | opt，状态令牌 |



### post /api/login/logout  

作用： 注销  

参数  

| name | type | description |
| ---- | ---- | ----------- |
| none | -    | -           |



返回值   

| name | type   | description |
| ---- | ------ | ----------- |
| sign | bool   | 请求状态    |
| msg  | string | 详细信息    |

### post /api/register/tryregister 

作用：注册    

参数  

| name     | type   | description                     |
| -------- | ------ | ------------------------------- |
| username | string | 5-17位                          |
| password | string | 6-12位                          |
| email    | string | 邮箱，须符合邮箱格式，xxx@xx.xx |
| phone    | string | 数字，11位                      |

返回值   

| name  | type   | description   |
| ----- | ------ | ------------- |
| sign  | bool   | 请求状态      |
| msg   | string | 详细信息      |
| token | string | opt，状态令牌 |

## 后面的api需要附带token才能使用。

在header附加字段x-access-token，里面放token即可。登陆或者注册之后会返回有。一段时间（24小时）之后会过期。

### post  /api/detail/getuser

作用： 获取用户信息    

参数
无

返回值   

| name | type   | description                        |
| ---- | ------ | ---------------------------------- |
| sign | bool   | 请求状态                           |
| msg  | string | 详细信息                           |
| data | json   | opt， uid,uname,email,phone,points |

### post /api/file/fileList

参数：

| name     | type     | description                                                  |
| -------- | -------- | ------------------------------------------------------------ |
| filename | string   | opt,加上就搜索该名字类似的文件                               |
| rankType | interger | default 0. 0 表示上传时间降序（新的在前），1表示下载量降序，2表示评分降序 |

返回值   

| name | type     | description                                                  |
| ---- | -------- | ------------------------------------------------------------ |
| sign | bool     | 请求状态                                                     |
| msg  | string   | 详细信息                                                     |
| data | json数组 | opt， fid uname fname points createTime description download_count |

### post /api/file/upload

作用： 上传  

参数： 

| name        | type   | description |
| ----------- | ------ | ----------- |
| description | string | 文件描述    |
| file        | file   | 文件主体    |

返回值   

| name | type   | description |
| ---- | ------ | ----------- |
| sign | bool   | 请求状态    |
| msg  | string | 详细信息    |

### post /api/file/download

作用： 下载  
参数：

| name | type   | description |
| ---- | ------ | ----------- |
| fid  | string | 文件id      |

返回值   

| name  | type   | description |
| ----- | ------ | ----------- |
| sign  | bool   | 请求状态    |
| msg   | string | 详细信息    |
| file  | buffer | 文件buffer  |
| fname | string | 文件名      |

### post /api/file/upload2

作用：上传  
参数： 

| name        | type   | description |
| ----------- | ------ | ----------- |
| description | string | 文件描述    |
| file        | string | 文件主体    |
| filename    | string | 文件名      |

返回值   

| name | type   | description |
| ---- | ------ | ----------- |
| sign | bool   | 请求状态    |
| msg  | string | 详细信息    |

### post /api/file/download2

作用： 下载  
参数：

| name | type   | description |
| ---- | ------ | ----------- |
| fid  | string | 文件id      |

返回值   

| name  | type   | description |
| ----- | ------ | ----------- |
| sign  | bool   | 请求状态    |
| msg   | string | 详细信息    |
| file  | string | 文件主体    |
| fname | string | 文件名      |

### post /api/file/detail 

作用：  获取文件状态 （获取文件具体信息，所有者，描述，下载次数之类的）     

参数：

| name | type   | description |
| ---- | ------ | ----------- |
| fid  | string | 文件id      |

返回值   

| name     | type     | description                                                  |
| -------- | -------- | ------------------------------------------------------------ |
| sign     | bool     | 请求状态                                                     |
| msg      | string   | 详细信息                                                     |
| file     | json     | 文件详情，fid，points，fname，uid，uname，description，createTime |
| comments | json数组 | 评论数组，cid，uname，content，points，createTime            |

### post /api/file/comment

作用 评论文件  

参数：

| name   | type   | description |
| ------ | ------ | ----------- |
| fid    | string | 文件id      |
| msg    | string | 评论内容    |
| points | number | 评分        |

返回值   

| name     | type     | description                                       |
| -------- | -------- | ------------------------------------------------- |
| sign     | bool     | 请求状态                                          |
| msg      | string   | 详细信息                                          |
| comments | json数组 | 评论数组，cid，uname，content，points，createTime |

### post  /api/qa/getQuestionList

作用 获得问题列表  

参数：  
无

返回值   

| name | type     | description                                                  |
| ---- | -------- | ------------------------------------------------------------ |
| sign | bool     | 请求状态                                                     |
| msg  | string   | 详细信息                                                     |
| data | json数组 | 问题数组， qid，uname，title，content，createTime，answers_counts |

### post /api/qa/createQuestion

作用： 创建问题    

参数：  

| name    | type   | description |
| ------- | ------ | ----------- |
| title   | string | 问题标题    |
| content | string | 问题详情    |

返回值   

| name | type   | description                                   |
| ---- | ------ | --------------------------------------------- |
| sign | bool   | 请求状态                                      |
| msg  | string | 详细信息                                      |
| data | json   | 问题，cid，uname，content，points，createTime |

### post /api/qa/answerQuestion

作用： 回答问题  

参数：

| name    | type   | description |
| ------- | ------ | ----------- |
| qid     | string | 问题id      |
| content | string | 回答详情    |

返回值   

| name    | type     | description                        |
| ------- | -------- | ---------------------------------- |
| sign    | bool     | 请求状态                           |
| msg     | string   | 详细信息                           |
| answers | json数组 | 回答，aid,content,uname,createTime |

### post  /api/qa/getQuestion

作用： 获取问题详情

参数：

| name | type   | description |
| ---- | ------ | ----------- |
| qid  | string | 问题id      |

返回值   

| name     | type     | description                        |
| -------- | -------- | ---------------------------------- |
| sign     | bool     | 请求状态                           |
| msg      | string   | 详细信息                           |
| question | json     | qid,uname,title,content,createTime |
| answers  | json数组 | 回答，aid,content,uname,createTime |

### post  /api/file/fileRank  

作用 获取用户排行榜  

参数：无

返回值

| name | type     | description             |
| ---- | -------- | ----------------------- |
| sign | bool     | 请求状态                |
| msg  | string   | 详细信息                |
| data | json数组 | 用户信息，uname, counts |