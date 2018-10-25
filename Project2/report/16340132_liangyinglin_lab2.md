# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 16级  | 专业（方向） | 软件工程 |
| 学号 | 16340132 | 姓名 | 梁颖霖 |
| 电话 | 13680473185 | Email | 806179953@qq.com |
| 开始日期 | 9/17 | 完成日期 | 10/19

---

## 一、实验题目
个人项目二: 中山大学智慧健康服务平台应用开发
实验代码：传送门：https://github.com/dick20/Android

---

## 二、实现内容
# 个人项目2
# 中山大学智慧健康服务平台应用开发

---  

---  

## 第七周任务  
## Broadcast 使用

---
  
### 实验目的
   1. 掌握 Broadcast 编程基础。  
   2. 掌握动态注册 Broadcast 和静态注册 Broadcast。
   3. 掌握Notification 编程基础。
   4. 掌握 EventBus 编程基础。
   
---

### 实验内容
在第六周任务的基础上，实现静态广播、动态广播两种改变Notification 内容的方法。  

#### 要求  
* 在启动应用时，会有通知产生，随机推荐一个食品。  
 ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week7_static_notification.jpg)
* 点击通知跳转到所推荐食品的详情界面。  
 ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week7_static_jump.jpg) 
* 点击收藏图标，会有对应通知产生，并通过Eventbus在收藏列表更新数据。  
 ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week7_requirement3.jpg) 
* 点击通知返回收藏列表。  
 ![preview](https://gitee.com/code_sysu/PersonalProject2/raw/master/manual/images/week7_requirement4.jpg) 
* 实现方式要求:启动页面的通知由静态广播产生，点击收藏图标的通知由动态广播产生。   

---

### 验收内容
* 静态广播：启动应用是否有随机推荐食品的通知产生。点击通知是否正确跳转到所推荐食品的详情界面。
* 动态广播：点击收藏后是否有提示食品已加入收藏列表的通知产生。同时注意设置launchMode。点击通知是否跳转到收藏列表。
* Eventbus:点击收藏列表图标是否正确添加食品到收藏列表。每点击一次,添加对应的一个食品到收藏列表并产生一条通知。

---


## 三、实验结果
### (1)实验截图
下图为打开app后，产生一个推荐食品的通知

![1](https://img-blog.csdn.net/20181018164825325?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
  
  下图为点击该通知，会跳转至食物详情页面。点击收藏按钮时，产生收藏的通知

![2](https://img-blog.csdn.net/20181018164958456?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

下图为点击收藏通知，跳转至收藏列表页面

![3](https://img-blog.csdn.net/20181018165050156?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### (2)实验步骤以及关键代码
#### 1.利用静态广播实现今日推荐功能
##### a.在AndroidManifest.xml注册静态广播接受方
其中StaticReceiver为类名
```xml
	<receiver android:name=".StaticReceiver">
            <intent-filter>
                <action android:name="com.example.asus.health.MyStaticFilter" />
            </intent-filter>
        </receiver>
```
#### b.实现StaticReceiver类，重构onReceive函数
其中要根据intent的action来确定是否接受该广播的内容，来实现功能，而需要实现的包括一个notification的弹出以及点击它跳转到详情页面。

notification部分由builder的设置函数来设置名字，内容，等等，由NotificationManager来发出该notification。

点击后跳转的功能则需要给builder设置一个ContentIntent，这个intent为PeddingIntent,即不会马上跳转，而是需要等待用户的操作。它的构造函数传递了一个普通的intent，而这个intent是携带了所需的数据来生成详情页面。
```java
public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATICACTION)){
            Bundle bundle = intent.getExtras();
            //TODO:添加Notification部分
            Notification.Builder builder = new Notification.Builder(context);
            //跳回主页面
            Intent intent2 = new Intent(context,Details.class);
            Bundle bundle2 = new Bundle();
            String s[] = new String [5];
            s[0] = ((MyCollection)bundle.getSerializable("collect")).getName();
            s[1] = ((MyCollection)bundle.getSerializable("collect")).getMaterial();
            s[2] = ((MyCollection)bundle.getSerializable("collect")).getType();
            s[3] = ((MyCollection)bundle.getSerializable("collect")).getContent();
            s[4] = ((MyCollection)bundle.getSerializable("collect")).getIs_star()?"yes":"no";
            bundle2.putStringArray("msg",s);
            intent2.putExtras(bundle2);
            PendingIntent contentIntent = PendingIntent.getActivity(
                    context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            //对Builder进行配置
            builder.setContentTitle("今日推荐")   //设置通知栏标题：发件人
                    .setContentText(((MyCollection)bundle.getSerializable("collect")).getName())   //设置通知栏显示内容：短信内容
                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
                    .setSmallIcon(R.mipmap.empty_star)   //设置通知小ICON 空星
                    .setContentIntent(contentIntent)  //传递内容
                    .setAutoCancel(true);   //设置这个标志当用户单击面板就可以让通知将自动取消
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);
        }
    }
```

##### c.在FoodList主页面onCreat时生成广播
注意action的字符串要与上面的Reciver的相同，不然无法正确接受广播，随机数则是返回一个0到n-1的整数表示随机生成一个推荐食物，然后将所需数据放入intent，通过sendBroadcast函数发送该广播。
```java
//打开应用时，发送一个静态广播
    private void boardcastforOpen(int n){
        final String STATICACTION = "com.example.asus.health.MyStaticFilter";
        Random random = new Random();
        int num = random.nextInt(n); //返回一个0到n-1的整数
        Intent intentBroadcast = new Intent(STATICACTION); //定义Intent
        Log.i("se",getPackageName());
        Bundle bundles = new Bundle();
        bundles.putSerializable("collect", data2.get(num));
        intentBroadcast.putExtras(bundles);
        sendBroadcast(intentBroadcast);
    }
```

#### 2.利用动态广播实现收藏信息提示
##### a.实现广播接受器DynamicReceiver类
与静态Receiver的实现过程差不多，一样是实现builder，然后放置peddingIntent，这里就不再重复放代码。唯一的不同点在于，它所要跳回的是收藏夹页面，即FoodList主页面，这里要对intent设置flag，否则无法在foodlist中get到新的intent。

```java
		//跳回收藏夹
            Intent intent2 = new Intent(context,FoodList.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("tag","collect");
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent2.putExtras(bundle2);
```

##### b.对详情页面的收藏事件进行处理
在监听器中设置发送广播的intent，当按下收藏后会发出广播，并传递参数。其中还使用了eventbus来传递收藏的数据
```java
//处理收藏按钮
        final ImageView collect_but = findViewById(R.id.collect);
        collect_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                temp.setIs_collected(true);
                Toast.makeText(Details.this, "已收藏",Toast.LENGTH_SHORT).show();

                EventBus.getDefault().post(new MessageEvent(temp));

                //发送广播
                Intent intentBroadcast = new Intent();
                intentBroadcast.setAction(DYNAMICACTION);
                
                sendBroadcast(intentBroadcast);
            }
        });
```

##### c. 在FoodList注册动态接收器以及注销动态接收器
注意分别要在onCreate函数以及onDestroy函数中实现注册与注销 。

#### 3.使用EventBus来实现数据的传输
在这一点上，要改进上一周实验的代码，不再需要点击返回按钮利用setResult以及onActivityResult两个函数来返回信息。而是通过eventbus的订阅发布模式。

在FoodList来注册订阅者，订阅消息。而在Detail来发布信息。其中onMessageEvent函数用于收到发布消息后，来调用之前的接口函数刷新列表。

发布消息就是上面点击收藏按钮后 EventBus.getDefault().post(new MessageEvent(temp));
```java
 //注册订阅者(注册收藏列表所在Activity为订阅者)
        EventBus.getDefault().register(this);

	@Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Log.i("hello","this is eventbus.");
        MyCollection mc = event.getCollection();
        refreshList(mc,simpleAdapter);
    }
```

#### 4.从详情跳转回收藏夹
这里由于收藏夹FoodList为经常返回的页面，故这里使用了android:launchMode="singleInstance"，即不让它重复创建新的活动。

所以再get我的返回intent时是拿不到新的intent的，这里需要重写onNewIntent函数，而且接收新的intent要在onResume中。

这里要求要显示收藏夹页面，所以要将食物列表隐藏起来。
```java
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
    }
    
    @Override
    protected void onResume(){
        super.onResume();
        //处理跳转
        Bundle bundle=this.getIntent().getExtras();
        if(bundle != null) {
            String str = bundle.getString("tag");
            Log.i("resume2",str);
            if (str.equals("collect")) {
                findViewById(R.id.recyclerView).setVisibility(View.GONE);
                findViewById(R.id.listView).setVisibility(View.VISIBLE);//设置Favourite可见
                tag_for_foodlist = false;
                f_but.setImageResource(R.mipmap.mainpage);
            }
        }
    }
```

### (3)实验遇到的困难以及解决思路
  #### 1.在安卓8.0版本中无法使用静态接收器，发送广播后，无法成功接收。
  方法一：解决这个问题，需要给receiver设置component，给予它的包名以及类名。
  ```java
intent.setComponent(new ComponentName(getPackageName(),getPackageName()+".xxxxReceiver"));
```

方法二：下载新的虚拟机，使用安卓7.0版本，则可以顺利接收静态广播。

#### 2.使用EventBus时候，FoodList主页面无法得到post的信息。
我按部就班地在Detail页面收藏按钮post，在FoodList订阅消息却毫无反应。首先，我认为是我的接收函数写错了，没有订阅到信息。通过Log.i发现确实没有进入到onMessageEvent函数中，于是对这个问题进行了查阅。网上有推荐使用stickyPost的，**怀疑原因出在信息接收发生在创建接收者之前**，但显然与函数执行顺序不符，它是先来到了主页面，所以必然创建了receiver。

经过大半个小时的查找发现是，post传的参数错误，并**没有生成MessageEvent,而是错误地直接传递了数据包。**
```java
//错误
EventBus.getDefault().post(temp);
//正确
EventBus.getDefault().post(new MessageEvent(temp));
```
#### 3.从收藏通知返回主页面时候，出现无法拿到intent的情况
由于我是在动态接收方的builder绑定了Peddingintent，当点击通知，应该要返回这个intent到主页面，然而主页面所获取的intent是空值。这一点让我怀疑了很久，问了同学才得知，这是声明了singleInstance的问题。

比如说在一个应用中A activity 跳转至 B activity 在跳转至 C activity 然后C做了一定的操作之后再返回A 界面。这样在A activity的启动模式设置为singleTask后。C界面跳转至A界面时，就会去判断栈内是否有改Activity实例，如果有就直接执行A界面的onNewIntent()方法，我们就可以把逻辑处理放在改生命周期方法中，如果没有就会走Activity的oncrate方法去创建实例。

所以这里需要重写onNewIntent来获取新的intent，而不是直接传递旧intent导致错误。
```java
 @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
    }
```

---

## 四、实验思考及感想
这次实验需要在安卓8.0与安卓7.0之间权衡，有些属性方法已经在8.0版本出现了变化，所以当使用错误，出现奇怪的现象时，第一步先检查自己的代码逻辑有否问题，第二步就是要查阅是否存在版本的兼容性问题产生了这些错误。这次作业就是如此，关于广播的实现，个人还是喜欢动态广播，不需要再静态注册在manifest中，代码也更加简便。

对于不同活动之间的传输，使用EventBus比之前的intent更加方便，减轻了耦合性，不用经常记住，哪个intent返回哪里，所以这次我也修改了不少前面实验使用intent的代码。除此之外，充分理解信息传输还需要理解一下活动的存活过程，什么时候调用onCreat，什么时候使用onResume。


---
