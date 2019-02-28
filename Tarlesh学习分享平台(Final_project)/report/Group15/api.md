# 一个文档

 by lt

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