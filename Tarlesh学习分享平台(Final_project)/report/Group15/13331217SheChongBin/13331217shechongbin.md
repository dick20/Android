# 中山大学数据科学与计算机学院本科生实验报告

## （2018年秋季学期）

| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |

| :------------: | :-------------: | :------------: | :-------------: |

| 年级 |  | 专业（方向） |  |

| 学号 |  | 姓名 | 佘崇斌 |

| 电话 |  | Email |  |

| 开始日期 |  | 完成日期 |



---



## 一、实验题目

期末project
Tarlesh

---



## 二、实现内容
完成注册，登陆以及注销界面并且实现对应功能


---



## 三、课堂实验结果

### (1)实验截图

  ![image.png-19.5kB][1]
  ![image.png-24.9kB][2]
### (2)实验步骤以及关键代码

  api：
```java
  package APIData;

import com.google.gson.annotations.SerializedName;

public class API_Response_user {
    @SerializedName("sign")
    public boolean sign;

    @SerializedName("msg")
    public String msg;

    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("uid")
        public String uid;

        public String uname;
        public String email;
        public String phone;
        public int points;
    }
}

```
  登陆回调处理:
```java
  package group15.android.tarlesh.callback;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import group15.android.tarlesh.model.LoginModel;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 登录回调处理
 */
public abstract class LoginCallBack extends Callback<LoginModel> {

    @Override
    public LoginModel parseNetworkResponse(Response response, int id) throws Exception {
        String str = response.body().string();
        JSONObject jsonObject = new JSONObject(str);
        LoginModel loginModel = new LoginModel();
        if (!jsonObject.optBoolean("sign"))
            return loginModel;
        loginModel.token = jsonObject.getString("token");
        return loginModel;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public abstract void onResponse(LoginModel response, int id);
}
```
退出回调处理
```java
package group15.android.tarlesh.callback;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import group15.android.tarlesh.model.LoginModel;
import group15.android.tarlesh.model.LogoutModel;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 退出回调处理
 */
public abstract class LogoutCallBack extends Callback<LogoutModel> {

    @Override
    public LogoutModel parseNetworkResponse(Response response, int id) throws Exception {
        String str = response.body().string();
        JSONObject jsonObject = new JSONObject(str);
        LogoutModel logoutModel = new LogoutModel();
        if (!jsonObject.optBoolean("sign"))
            return logoutModel;
        logoutModel.msg = jsonObject.getString("msg");
        return logoutModel;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public abstract void onResponse(LogoutModel response, int id);
}

```
注册回调处理
```java
package group15.android.tarlesh.callback;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import group15.android.tarlesh.model.LoginModel;
import group15.android.tarlesh.model.RegisterModel;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 注册回调处理
 */
public abstract class RegisterCallBack extends Callback<RegisterModel> {

    @Override
    public RegisterModel parseNetworkResponse(Response response, int id) throws Exception {
        String str = response.body().string();
        JSONObject jsonObject = new JSONObject(str);
        RegisterModel registerModel = new RegisterModel();
        if (!jsonObject.optBoolean("sign"))
            return registerModel;
        registerModel.token = jsonObject.getString("token");
        return registerModel;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public abstract void onResponse(RegisterModel response, int id);
}

```

### (3)实验遇到的困难以及解决思路

  
设计一个登陆，注册界面的UI。其中难点在于将用户的账号密码保存在本地，以及上传到服务器上。因为api都是队友自己写的，所以在网上查找资料，学习了nodejs以及api的基本用法。
学习Nodejs：https://www.cnblogs.com/binperson/p/5507235.html
学习api：https://blog.csdn.net/asjqkkkk/article/details/81673767,https://blog.csdn.net/asjqkkkk/article/details/81673786
---





## 五、实验思考及感想

和队友们一起完成一个大作业很有成就感，从合作以及交流里面学到很多东西，同时对前几次作业中学到的技术更加熟悉

---



#### 作业要求

* 命名要求：学号_姓名_实验编号，例如12345678_张三_lab1.md

* 实验报告提交格式为md

* 实验内容不允许抄袭，我们要进行代码相似度对比。如发现抄袭，按0分处理


  [1]: http://static.zybuluo.com/syne/1jbwec0lswt04evaqrbvwgnh/image.png
  [2]: http://static.zybuluo.com/syne/vzv8zbw51bjh24n1sbqemf3e/image.png