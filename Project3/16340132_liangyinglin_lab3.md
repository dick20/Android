# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 16级  | 专业（方向） | 软件工程 |
| 学号 | 16340132 | 姓名 | 梁颖霖 |
| 电话 | 13680473185 | Email | 806179953@qq.com |
| 开始日期 | 11.5 | 完成日期 | 11.10

---

## 一、实验题目

## 个人项目3 
## 数据存储（一）应用开发
---

## 二、实现内容


## 第九周任务  
---

### 实验目的
1. 学习SharedPreference的基本使用。  
2. 学习Android中常见的文件操作方法。
3. 复习Android界面编程。
   
---

### 实验内容

#### 要求  
* Figure 1：首次进入，呈现创建密码界面。  
 ![preview](https://gitee.com/code_sysu/PersonalProject3/raw/master/manual/images/fig1.jpg)
* Figure 2：若密码不匹配，弹出Toast提示。  
 ![preview](https://gitee.com/code_sysu/PersonalProject3/raw/master/manual/images/fig2.jpg) 
* Figure 3：若密码为空，弹出Toast提示。  
 ![preview](https://gitee.com/code_sysu/PersonalProject3/raw/master/manual/images/fig3.jpg) 
* Figure 4：退出后第二次进入呈现输入密码界面。  
 ![preview](https://gitee.com/code_sysu/PersonalProject3/raw/master/manual/images/fig4.jpg) 
* Figure 5：若密码不正确，弹出Toast提示。  
 ![preview](https://gitee.com/code_sysu/PersonalProject3/raw/master/manual/images/fig5.jpg)
* Figure 6：文件加载失败，弹出Toast提示。  
 ![preview](https://gitee.com/code_sysu/PersonalProject3/raw/master/manual/images/fig6.jpg) 
* Figure 7：成功导入文件，弹出Toast提示。  
 ![preview](https://gitee.com/code_sysu/PersonalProject3/raw/master/manual/images/fig7.jpg) 
* Figure 8：成功保存文件，弹出Toast提示。  
 ![preview](https://gitee.com/code_sysu/PersonalProject3/raw/master/manual/images/fig8.jpg) 
###
1.  如Figure 1至Figure 8所示，本次实验演示应用包含两个Activity。 
2.  首先是密码输入Activity：
    * 若应用首次启动，则界面呈现出两个输入框，分别为新密码输入框和确认密码输入框。  
    * 输入框下方有两个按钮：  
        - OK按钮点击后：  
            + 若New Password为空，则发出Toast提示。见Figure 3。
            + 若New Password与Confirm Password不匹配，则发出Toast提示，见Figure 2。
            + 若两密码匹配，则保存此密码，并进入文件编辑Activity。
        - CLEAR按钮点击后：清楚两输入框的内容。  
    * 完成创建密码后，退出应用再进入应用，则只呈现一个密码输入框，见Figure 4。
        - 点击OK按钮后，若输入的密码与之前的密码不匹配，则弹出Toast提示，见Figure 5。
        - 点击CLEAR按钮后，清除密码输入框的内容。
    * **出于演示和学习的目的，本次实验我们使用SharedPreferences来保存密码。但实际应用中不会使用这种方式来存储敏感信息，而是采用更安全的机制。见[这里](http://stackoverflow.com/questions/1925486/android-storing-username-and-password)和[这里](http://stackoverflow.com/questions/785973/what-is-the-most-appropriate-way-to-store-user-settings-in-android-application/786588)。**
3.  文件编辑Activity：
    * 界面底部有三个按钮，高度一致，顶对齐，按钮水平均匀分布，三个按钮上方除ActionBar和StatusBar之外的全部空间由一个EditText占据（保留margin）。EditText内的文字竖直方向置顶，左对齐。
    * 在编辑区域输入任意内容，点击SAVE按钮后能保存到指定文件（文件名随意）。成功保存后，弹出Toast提示，见Figure 8。
    * 点击CLEAR按钮，能清空编辑区域的内容。
    * 点击LOAD按钮，能够从同一文件导入内容，并显示到编辑框中。若成功导入，则弹出Toast提示。见Figure 7.若读取文件过程中出现异常（如文件不存在），则弹出Toast提示。见Figure 6.
4.  特殊要求：进入文件编辑Activity后，若点击返回按钮，则直接返回Home界面，不再返回密码输入Activity。

---
## 三、实验结果
### (1)实验截图
首次进入，显示新建密码以及确认密码界面

![1](https://gitee.com/dic0k/PersonalProject3/raw/master/report/Wednesday/16340132LiangYingLin/image/ex1-img1.png)
  
 密码不匹配，弹出Toast提示。

 ![2](https://gitee.com/dic0k/PersonalProject3/raw/master/report/Wednesday/16340132LiangYingLin/image/ex1-img3.png)
 
若密码为空，弹出Toast提示。

![3](https://gitee.com/dic0k/PersonalProject3/raw/master/report/Wednesday/16340132LiangYingLin/image/ex1-img2.png)

退出后第二次进入呈现输入密码界面。

![4](https://gitee.com/dic0k/PersonalProject3/raw/master/report/Wednesday/16340132LiangYingLin/image/ex-img7.png)

若密码不正确，弹出Toast提示。

![5](https://gitee.com/dic0k/PersonalProject3/raw/master/report/Wednesday/16340132LiangYingLin/image/ex1-img9.png)

文件加载失败，弹出Toast提示。

![6](https://gitee.com/dic0k/PersonalProject3/raw/master/report/Wednesday/16340132LiangYingLin/image/ex1-img8.png)

成功保存文件，弹出Toast提示。

![8](https://gitee.com/dic0k/PersonalProject3/raw/master/report/Wednesday/16340132LiangYingLin/image/ex1-img5.png)

成功导入文件，弹出Toast提示。

![7](https://gitee.com/dic0k/PersonalProject3/raw/master/report/Wednesday/16340132LiangYingLin/image/ex1-img6.png)


### (2)实验步骤以及关键代码
#### 1.实现登陆的UI界面
这里还是使用ConstrainLayout来进行布局，两个输入框EditText，由于是输入密码，不能以明文的形式显示。故需要在**inputType设置参数为textPassword**.而提示信息就写在hint属性中，在未输入时，显示提示New password等。

至于button方面，简单的调整间距，设置id即可。

```xml
<EditText
        android:id="@+id/new_password"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:inputType="textPassword"
        android:hint="New Password"
        app:layout_constraintBottom_toTopOf="@id/confirm_password"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        android:layout_marginRight="10dp"
        android:layout_marginLeft="10dp"
        android:layout_marginBottom="10dp"
        />

    <EditText
        android:id="@+id/confirm_password"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:inputType="textPassword"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:hint="Confirm Password"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        />
```

#### 2.实现SharedPreference的保存与获取
boolean变量is_new是用于确认是否第一次进入app。

而我通过getSharedPreferences的getString函数拿到对应的password，如果是第一次进入app，那么里面应该没有这个属性，故会赋值成defValue。否则是密码的字符串。

所以根据这一点，可以调整界面中EditText的可见情况，决定哪些可以显示，哪些需要隐藏。
```java
 	   sharedPreferences = getSharedPreferences ( PREFERENCE_NAME, MODE );
        String password = sharedPreferences.getString("password","defValue");
        if(password.equals("defValue")){
            // do nothing
        }
        else{
            new_password = findViewById(R.id.new_password);
            new_password.setVisibility(View.GONE);
            confirm_password = findViewById(R.id.confirm_password);
            confirm_password.setHint("Password");
            is_new = false;
        }
```

#### 3.实现按钮监听事件的绑定
对于清除按钮的事件，十分简单，只需要将两个EditText利用setText为空字符串即可
```java
but_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_password.setText("");
                new_password.setText("");
            }
        });
```
而对于ok按钮，需要判断是第一次登入app还是其他，这里就利用上面所设置的is_new变量。

当是第一次进入该app，需要输入新密码以及确认密码。这里首先判断这两个密码是否为空，然后再判断密码是否匹配，为空或者不匹配需要弹出Toast。

当密码匹配时，将新密码放入SharedPreference中。这里用到的是Editor，通过edit()，putString()以及commit()这三个函数来进入写入。然后startActivity来跳转到文件编辑的页面

**这里要注意，在AndroidManifest中注册新的活动，否则跳转会失败**
```java
but_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_confirm_password = confirm_password.getText().toString();
                String str_new_password = new_password.getText().toString();
                if(is_new){
                    //密码为空
                    if(str_confirm_password.isEmpty() || str_new_password.isEmpty()){
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //两次密码匹配
                        if(str_confirm_password.equals(str_new_password)){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("password", confirm_password.getText().toString());
                            editor.commit();
                            //跳转
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,FileEditorActivity.class);
                            startActivity(intent);
                        }
                        //两次密码不匹配
                        else{
                            //弹出 Toast
                            Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    if(sharedPreferences.getString("password","defValue").equals(str_confirm_password)){
                        //跳转
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,FileEditorActivity.class);
                        startActivity(intent);
                    }
                    else{
                        //弹出 Toast
                        Toast.makeText(MainActivity.this, "Password Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
```

#### 4.实现文件编辑的UI界面
对于文件编辑UI界面的设计，包括三个button，以及一个EditText。这里使用的是线性布局 orientation="vertical"，然后利用layout_weight参数来进行调整页面的占用页面比例。
```xml
	<EditText
        android:id="@+id/edit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:layout_weight="1"
        android:gravity="top"/>
```
而对于三个按钮也放置在一个线性布局中，不过该布局是水平的，调整边距即可。

#### 5.实现按钮保存文件功能
这里需要绑定三个按钮的功能，清除按钮与上面登陆的清除按钮类似，不再复述。

对于文件的保存按钮以及加载按钮都必须采用try-catch结构，因为文件读取不到的时候会出现错误，这样做可以避免应用程序的崩溃。

对于保存事件，采用的是FileOutputStream来进行输入，然后利用write()函数写入，不过要注意将字符串转化为byte数组，才能write。

```java
	but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                    String str = editText.getText().toString();
                    fileOutputStream.write(str.getBytes());
                    //弹出 Toast
                    Toast.makeText(FileEditorActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "Successfully saved file.");
                } catch (IOException ex) {
                    Log.e("TAG", "Fail to save file.");
                }
            }
        });
```

对于加载事件，要使用fileInputStream，以及利用read（）函数来读入到byte数组中。当读取成功或者失败的时候都要弹出相应的toast。
```java
 	but_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileInputStream fileInputStream = openFileInput(FILE_NAME)) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    String str = new String(contents,"UTF-8");
                    editText.setText(str);
                    //弹出 Toast
                    Toast.makeText(FileEditorActivity.this, "Load successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    //弹出 Toast
                    Toast.makeText(FileEditorActivity.this, "Fail to read file.", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to read file.");
                }
            }
        });
```

### (3)实验遇到的困难以及解决思路
#### 1.关于文件保存与读出，byte数组与string类的转化
一开始在读取文件的时候，没有注意读取出来的是byte数组，直接将其赋值给EditText导致出现乱码的情况。查看函数的返回值才得知需要将byte数组转化成string再setText

这里我使用的是string的构造函数，将byte数组作为参数构造字符串，第二个参数为charsetName.
```java
//contents为byte数组
String str = new String(contents,"UTF-8");
```

而将string转化为byte数组只需要利用getBytes()函数即可
```java
String str = editText.getText().toString();
fileOutputStream.write(str.getBytes());
```

#### 2.如何按返回键退出整个应用
本次实验要求在文件编辑页面按返回键要退出整个应用，不能返回第一个页面。

我先测试不添加任何的东西的情况下，发现按下返回键是finish掉了当前的活动，回到了前面的第一个页面。

所以，我必须重构onKeyDown函数来，改变返回键的功能，这里我利用了launchMode="singleTask"的性质，将第一个主页面设置成单个task，那么**返回到第一个页面的时候会调用onNewIntent函数**，我只需要在这个函数调用finish即可，结束掉第一个页面。

点击返回键，第二个页面会跳转到第一个页面，而跳转的时候第一个页面马上执行onNewIntent函数结束页面，即结束了整个应用。
```xml
 <activity android:name=".MainActivity" android:launchMode="singleTask">
```

```java
@Override
    protected void onNewIntent(Intent intent) {
        finish();
    }
```

```java
@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(FileEditorActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
```

#### 3.SharedPreference的有效生命周期
当我在手机模拟器实验SharedPreference的时候，在第一次进入应用设置密码后，不断重装应用也不会再出现新密码的页面情况，一开始我还以为是我的页面逻辑出错，经过log调试后才发现没有问题。那么就是SharePreference没有被重置过。

于是我查询了SharePreference的相关信息，根据网上资料，**只有当卸载应用的时候SharePreference才会被顺带删除掉**。而像我这样在虚拟机重装是不会改变SharePreference的。

所以在卸载后再重装发现，这个问题顺利解决。

  
---
## 四、实验思考及感想
### 实验思考
#### 关于Internal Storage和External Storage的区别，以及它们的适用场景。
##### Internal Storage
 + 默认情况下，保存在 Internal Storage 的⽂件只有应⽤程序可⻅，其他应⽤，以及⽤⼾本⾝是⽆法访问这些⽂件的。
 + Internal Storage 把数据存储在设备内部存储器上，存储在/data/data/\<package name\>/files目录下。
 + 卸载应用程序后，内部存储器的/data/data/\<package name\>目录及其下子目录和文件一同被删除。

##### External Storage
 + Android ⽀持使⽤ Java 的⽂件 API 来读写⽂件，但是关键的点在于要有⼀个合适的路径。如果你要存储⼀些公开的，体积较⼤的⽂件（如媒体⽂件），External Storage 就是⼀个⽐较合适的地⽅。
 + 保存在这里的文件可能被其他程序访问。
 + 卸载app时，系统仅仅会删除external根目录（getExternalFilesDir()）下的相关文件。

所以当不想被外部程序访问，且需要保存的数据文件比较小的情况，例如密码，用户信息等等可以保存在Internal Storage。而需要被外部访问的数据，而且这些数据文件较大，占用较多的内存空间，例如视频相片等等可以保存在External Storage

### 感想
这次实验是关于数据的储存，一个好的应用离不开对于数据的处理。如何放置这些数据是十分讲究，这里第一部分学习了SharedPreference与内部外部Storage两方面的内容。我们应该将某些数据放入其中，而不是简单地堆放在java文件中。而这次实验，我还了解到了应用程序退出的几种方法，更加深入地认识了活动的生命周期。关于线性布局占页面比也有了更好的认识，操作UI设计起来也更加熟练。

---







