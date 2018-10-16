# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 16级  | 专业（方向） | 软件工程 |
| 学号 | 16340132 | 姓名 | 梁颖霖 |
| 电话 | 13680473185 | Email | 806179953@qq.com |
| 开始日期 | 9/25 | 完成日期 | 10/16

---

## 一、实验题目

**实验一: 中山大学智慧健康服务平台应用开发**

---

## 二、实现内容
### 1.基本的UI界面设计
实现一个Android应用，界面呈现如图中的效果。  
 ![preview](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/preview.jpg) 
#### 要求  
* 该界面为应用启动后看到的第一个界面。  
* 各控件的要求
   1. 标题字体大小**20sp**，与顶部距离**20dp**，居中；
   2. 图片与上下控件的间距均为**20dp**，居中；  
   3. 输入框整体距左右屏幕各间距**20dp**，内容（包括提示内容）如图所示，内容字体大小**18sp**；  
   4. 按钮与输入框间距**10dp**，文字大小**18sp**。按钮背景框左右边框与文字间距**10dp**，上下边框与文字间距**5dp**，圆角半径**180dp**，背景色为**#3F51B5**；  
   5. 四个单选按钮整体居中，与输入框间距10dp，字体大小**18sp**，各个单选按钮之间间距**10dp**，默认选中的按钮为第一个。

#### 使用的组件
TextView、EditText、ConstraintLayout、Button、ImageView、RadioGroup、RadioButton。 
实现一个Android应用，界面呈现如图中的效果。  

### 验收内容
* 各控件的位置，间距，字体大小等属性与要求无误
* 图片大小不作为验收内容给之一

---
### 2.基础的事件处理
![preview](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/preview.jpg)  
#### 要求  
* 该界面为应用启动后看到的第一个界面。  
* 各控件处理的要求
   1. 点击搜索按钮：
      * 如果搜索内容为空，弹出Toast信息“**搜索内容不能为空**”。
      * 如果搜索内容为“Health”，根据选中的RadioButton项弹出如下对话框。  
![success](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/success.jpg)  
点击“确定”，弹出Toast信息——**对话框“确定”按钮被点击**。  
点击“取消”，弹出Toast 信息——**对话框“取消”按钮被点击**。  
否则弹出如下对话框，对话框点击效果同上。  
![fail](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/fail.jpg)  
   2. RadioButton选择项切换：选择项切换之后，弹出Toast信息“**XX被选中**”，例如从图片切换到视频，弹出Toast信息“**视频被选中**”  

---

### 验收内容
* 布局是否正常
* 搜索内容为空时，提示是否正常
* 输入搜索内容后，点击搜索按钮是否能根据不同的搜索内容显示相应的弹出框，以及弹出框内容是否符合要求
* 点击弹出框的相应按钮是否能提示正确的内容
* RadioButton切换时，提示是否正常

---
### 3.Intent、Bundle的使用以及RecyclerView、ListView的应用
本次实验模拟实现一个健康食品列表，有两个界面，第一个界面用于呈现食品列表 如下所示  
![img1](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img1.jpg)  
数据在"manual/素材"目录下给出。  
点击右下方的悬浮按钮可以切换到收藏夹  
![img2](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img2.jpg)   
上面两个列表点击任意一项后，可以看到详细的信息：  
![img3](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img3.jpg) 

#### UI要求  
* 食品列表  
      每一项为一个圆圈和一个名字，圆圈和名字都是垂直居中。圆圈内的内容是该食品的种类，内容要处于圆圈的中心，颜色为白色。食品名字为黑色，圆圈颜色自定义，只需能看见圆圈内的内容即可。
* 收藏夹  
      与食品列表相似
* 食品详情界面  
   1. 界面顶部  
   ![img4](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img4.jpg)  
   顶部占整个界面的1/3。每个食品详情的顶部颜色在数据中已给出。返回图标处于这块区域的左上角，食品名字处于左下角，星标处于右下角，边距可以自己设置。 **返回图标与名字左对齐，名字与星标底边对齐。** 建议用RelativeLayout实现，以熟悉RelativeLayout的使用。  
   2. 界面中部  
   ![img5](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img5.jpg)  
   使用的黑色argb编码值为#D5000000，稍微偏灰色的“富含”“蛋白质”的argb编码值为#8A000000。"更多资料"一栏上方有一条分割线，argb编码值为#1E000000。右边收藏符号的左边也有一条分割线，要求与收藏符号高度一致，垂直居中。字体大小自定。"更多资料"下方分割线高度自定。这部分所有的分割线argb编码值都是#1E000000。  
   3. 界面底部  
   ![img6](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img6.jpg)  
   使用的黑色argb编码值为#D5000000。  
* 标题栏  
      两个界面的标题栏都需要去掉  

#### 功能要求
* 使用RecyclerView实现食品列表。点击某个食品会跳转到该食品的详情界面，呈现该食品的详细信息。长按列表中某个食品会删除该食品，并弹出Toast，提示 **"删除XX"** 。
* 点击右下方的FloatingActionButton，从食品列表切换到收藏夹或从收藏夹切换到食品列表，并且该按钮的图片作出相应改变。
* 使用ListView实现收藏夹。点击收藏夹的某个食品会跳转到食品详情界面，呈现该食品的详细信息。长按收藏夹中的某个食品会弹出对话框询问是否移出该食品，点击确定则移除该食品，点击取消则对话框消失。如长按“鸡蛋”，对话框内容如下图所示。  
![img7](https://gitee.com/code_sysu/PersonalProject1/raw/master/manual/images/img7.jpg)
* 商品详情界面中点击返回图标会返回上一层。点击星标会切换状态，如果原本是空心星星，则会变成实心星星；原本是实心星星，则会变成空心星星。点击收藏图表则将该食品添加到收藏夹并弹出Toast提示 **"已收藏"** 。

---

## 三、实验结果
### A.基本的UI界面设计与基础事件处理

### (1)实验截图
切换按钮时候，显示当前切换到的按钮名字，如下图，视频被选中

![pic1](https://img-blog.csdn.net/20180929154002286?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

搜索Health关键词时，显示对话框搜索成功

![pic2](https://img-blog.csdn.net/20180929154026505?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

搜索其他关键词，无法正确搜索，显示搜索错误对话框

![pic3](https://img-blog.csdn.net/20180929154043378?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

点击取消按钮时，显示toast取消被单击

![pic4](https://img-blog.csdn.net/20180929154058786?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
  
### (2)实验步骤以及关键代码
这个实验前两部分包括简单的UI设计以及UI的交互。
首先，我们当然要从UI的构建开始。
#### 1.插入标题以及图片
这里应用到了TextView以及ImageView两个控件。由于本次的ui是使用ConstraintLayout布局，所以必须对每一个控件设置左右上下分别对齐什么。故要利用app:layout_constraintLeft_toLeftOf等属性，表示该组件的左边对齐于xx的左边，这里的textview就要与parent即整个页面的左边对齐，然后设置居中。宽度，大小就根据实验要求来设置，而id是用于后面的交互部分识别该控件用的。
```xml
<TextView android:id="@+id/title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/title"
        android:textSize="20sp"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_marginTop="20dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        />
    <ImageView
        android:id="@+id/image"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:src="@mipmap/sysu"
        app:layout_constraintBottom_toTopOf="@+id/search_content"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/title" />
```
#### 2.插入搜索输入框以及搜索按钮
对于输入框要使用EditText控件，对于按钮要使用Button控件。对于输入框的显示内容，预先在string文件中写入，然后直接在控件中调用即可。对于button还用到了style属性，表示直接引用style写好的按钮样式。而style里面又调用了其他文件中已经预设好的属性，例如color中颜色。
```xml
<style name="search_button">
        <item name="android:textColor">@color/white</item>
        <item name="android:background">@drawable/button</item>
    </style>
```
```xml
<EditText
        android:id="@+id/search_content"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="10dp"
        android:layout_marginTop="20dp"
        android:gravity="center"
        android:hint="@string/search_content"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@+id/radioGroup"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@+id/but1"
        app:layout_constraintTop_toBottomOf="@id/image" />
    <Button
        android:id="@+id/but1"
        style="@style/search_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginRight="20dp"
        android:layout_marginTop="20dp"
        android:text="@string/search_button"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@+id/radioGroup"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/image" />
```
#### 3. 插入选择按钮
选择按钮组要使用RadioGroup与RadioButton相配合，在group中设置边距以及大小，对于每一个radiobutton使用其他设置好的样式属性，在第一个选择按钮中设置checked属性设置为true就会默认第一个按钮被选定。
```xml
<RadioGroup
        android:id="@+id/radioGroup"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:orientation="horizontal"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/search_content">
        <RadioButton
            android:id="@+id/selection1"
            style="@style/MyRadioButton"
            android:layout_height="match_parent"
            android:checked="true"
            android:text="@string/selection1" />
        <RadioButton
            android:id="@+id/selection2"
            style="@style/MyRadioButton"
            android:text="@string/selection2" />
        <RadioButton
            android:id="@+id/selection3"
            style="@style/MyRadioButton"
            android:text="@string/selection3" />
        <RadioButton
            android:id="@+id/selection4"
            style="@style/MyRadioButton"
            android:text="@string/selection4" />
    </RadioGroup>
```
**这就基本完成了UI的界面设置，接下来要根据他们的id来设置一些函数实现实验要求，例如弹出对话框或者toast等等。**
#### 4.获取搜索输入框的内容，以及点击搜索按钮显示提示
这一步主要要调用findViewById这个函数来分别得到输入框以及按钮，给按钮设置监听函数setOnClickListener, 然后在里面对于输入框的内容searchContent.getText().toString()来进行判断，分别有三种情况，搜索内容为空，搜索内容为Health，搜索内容为其他。

然后，关于对话框的显示要使用dialog，分别给它设置标题，中间内容以及按钮。而toast则要对于对话框的按钮来设置监听函数，当点击时候来Toast.makeText（）显示一个具体的toast内容。
```java
		Button button =(Button) findViewById(R.id.but1);
        final EditText searchContent = (EditText) findViewById(R.id.search_content);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //搜索为空情况
                if(TextUtils.isEmpty(searchContent.getText().toString())){
                    //弹出 Toast
                    Toast.makeText(MainActivity.this, "搜索内容不能为空",Toast.LENGTH_SHORT).show();
                }
                //搜索成功情况
                else if(searchContent.getText().toString().equals("Health")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("提示");
                    RadioButton temp = findViewById(radioGroup.getCheckedRadioButtonId());
                    dialog.setMessage(temp.getText().toString()+"搜索成功");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "对话框“确定”按钮被点击",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "对话框“取消”按钮被点击",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
                //搜索失败情况
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("搜索失败");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "对话框“确定”按钮被点击",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "对话框“取消”按钮被点击",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
            }
        });
```
#### 4.对于选择按钮组的切换
与上面相同，先要通过id来找到相应的控件，然后对于radioGroup来设置选择改变的监听函数，当切换的时候会根据选择的不同按钮上的信息来生成一个toast。
```java
 		final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            //选择变化时，弹出toast提示信息
            public void onCheckedChanged(RadioGroup group, int checkedID){
                String str = "";
                RadioButton select1 = findViewById(R.id.selection1);
                RadioButton select2 = findViewById(R.id.selection2);
                RadioButton select3 = findViewById(R.id.selection3);
                RadioButton select4 = findViewById(R.id.selection4);
                if(select1.getId() == checkedID){
                    str = select1.getText().toString();
                }
                else if(select2.getId() == checkedID){
                    str = select2.getText().toString();
                }
                else if(select3.getId() == checkedID){
                    str = select3.getText().toString();
                }
                else if(select4.getId() == checkedID){
                    str = select4.getText().toString();
                }
                Toast.makeText(MainActivity.this, str + "被选中",Toast.LENGTH_SHORT).show();
            }
        });
```
### (3)实验遇到的困难以及解决思路
#### a.关于UI部分的边距问题
起初对于ConstraintLayout布局不熟悉，不理解为什么需要对于一个控件的左右边限制跟随另一个的左右边，单纯认为只需要改变margin即可完成布局。而实际情况时，根据布局出来的结果可以看到仅改变margin之后相对于父亲来改变距离，而不能完全地设置两个组件的相应距离。于是完成一个组件时候，对于下一个组件的上下左右边缘要根据相对应的组件来限制一下。

而在修改UI的时候，多使用preview功能以及在xml下切换至design模式，可以清晰看出组件之间的边距关系，查看布局是否正确。

![ui design](https://img-blog.csdn.net/20180929151520686?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

#### b.如何让中间的搜索框以及搜索按钮以合适的大小安放在同一行
这个问题就是在ui部分一直困扰我的，由于搜索框与左边要有限制，在右边又要与搜索按钮有限制，而搜索框也要与右边有限制。这样设置app:layout_constraintRight_toRightOf等等需要十分注意。

而且输入框的长度也要合适，当android:layout_width="wrap_parent"时候仅显示了提示内容的长度。而android:layout_width="fill_parent"时候又占满了整个显示屏，显然是不行。而选择固定长度则不符合我们安卓手机界面设计的原则，无法在各种机型中显示合理。

经过查询多种资料，可以通过**设置android:layout_width="0dp"来使这个输入框自适应边距**，因此问题迎刃而解。

#### c.实现交互部分的api
比较通用的找到控件的函数为findViewById，通过id来找到控件，这与我们设置的id就很关键了，必须要注意大小写以及名字的正确性。

关于组件的监听函数，包括点击按钮，切换radiobutton等等，都要了解其中的参数，查看手册。

---
### B.Intent、Bundle的使用以及RecyclerView、ListView的应用
### (1)实验截图
下图为食物列表的展示，浮标图案为前往收藏夹

![主页面](https://img-blog.csdn.net/20181016133040749?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

下图为收藏夹初始页面的展示，浮标图案为返回主页样式

![收藏夹](https://img-blog.csdn.net/20181016133246120?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
  
下图为大豆食物的详情信息

![详情](https://img-blog.csdn.net/20181016133711173?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

下图为点击星星以及收藏按钮产生的事件截图

![收藏事件](https://img-blog.csdn.net/20181016133741170?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

下图为收藏大豆事件后，收藏夹的信息截图

![收藏列表](https://img-blog.csdn.net/20181016134020919?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

下图为长按大豆列表删除时的操作截图

![删除收藏夹](https://img-blog.csdn.net/20181016134112563?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

下图为在食物列表长按食物删除的操作截图

![移除食物](https://img-blog.csdn.net/20181016134323958?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### (2)实验步骤以及关键代码
本次实验的内容有点多，要完成三个页面的设计以及不同活动之间的信息交互。
#### 1.完成从搜索页面跳转到FoodList页面
由于上次的实验中完成了一个搜索的界面，我为了将两次实验连接到一起，因此在搜索页面搜索**switch**时候会跳转到食物列表页面（即本次实验内容）

**要记得在mainfest中注册该活动，否则会出现应用闪退的现象，下面的两个页面也是如此，不再详述。**
![注册活动](https://img-blog.csdn.net/20181016135107456?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RpY2tkaWNrMTEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

这里使用startActivity以及intent来实现页面的跳转。
```java
				...
				//切换至食物列表,第二周任务的衔接第一周任务
                else if(searchContent.getText().toString().equals("switch")){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, FoodList.class);
                    startActivity(intent);
                }
                ...
```

#### 2.存储食物数据
为了保存这些食物数据，我新建了一个MyCollection类来存储，类函数包括构造函数以及各个参数的get，set函数，不必详述。
```java
public class MyCollection implements Serializable {
    private String name;			//食物名字
    private String content;			//食物图标
    private String type;			//食物种类
    private String material;		//食物成分
    private boolean is_collected;	//是否被收藏
    private boolean is_star;		//是否被加星
    
    public MyCollection(){
        is_collected =false;
    }

    public MyCollection(String _name, String _content, String _type, String _material, boolean _is_star){
        name = _name;
        content = _content;
        type =_type;
        material = _material;
        is_star = _is_star;
        is_collected = false;
    }
	...
	//各种get，set函数
```
  
#### 3.利用RecycleView实现FoodList
这一部分可以说是这次实验的难点，我用了一天的时间才能理解RecycleView的实现过程。
一个RecycleView需要一个Adater以及一个Holder来实现，存储的数据利用Holder，而用户点击的事件则利用Adater.

首先实现MyViewHolder类，它必须继承RecyclerView.ViewHolder。其中通过findViewById函数来查找列表的填充项，如果已经查找过了就从数组中直接拿出即可，这样可以加快应用的速度，优化性能。
```java
public class MyViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View view;

    public MyViewHolder(View _view) {
        super(_view);
        view = _view;
        views = new SparseArray<View>();
    }

    public <T extends View> T getView(int _viewId) {
        View _view = views.get(_viewId);
        if (_view == null) {
            //创建view
            _view = view.findViewById(_viewId);
            //将view存入views
            views.put(_viewId, _view);
        }
        return (T) _view;
    }
}
```

接着是MyRecyclerViewAdapter类，它必须继承RecyclerView.Adapter类，其中利用MyViewHolder来存储列表的数据。该类实现点击的功能，这里新建了item的点击监听器，包括单击以及长按两种操作。

除此之外，它必须重构onCreateViewHolder，onBindViewHolder，getItemCount这三个函数。

在onBindViewHolder中为item来重构点击事件，其中**长按事件函数要返回false，不然会与单击事件同时触发**
```java
public class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyViewHolder>{
    private List<MyCollection> data;
    private Context context;
    private int layoutId;
    private OnItemClickListener onItemClickListener;
    public MyRecyclerViewAdapter(Context _context, int _layoutId, List<MyCollection> _data){
        context = _context;
        layoutId = _layoutId;
        data = _data;
    }
    //点击事件的接口
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener _onItemClickListener) {
        this.onItemClickListener = _onItemClickListener;
    }
    //删除数据
    public void deleteData(int position){
        data.remove(position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //convert
        ((TextView)holder.getView(R.id.recipeName)).setText(data.get(position).getName());
        ((TextView)holder.getView(R.id.img)).setText(data.get(position).getContent());
        
        if (onItemClickListener != null) {
        	//单击
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            //长按
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        if(!data.isEmpty())
            return data.size();
        return 0;
    }
}
```
我们先在FoodList.xml布局文件中预先设置了recycleview，以及新建一个item.xml来初始化列表项，包括两个textView组件来存放食物的标志以及文字。

在FoodList.java中来通过recycleview的id来找到该组件，然后来通过adapter来设置。首先要利用setLatoutManager函数类似ListView来设置layout。

然后设置监听器，单击跳转到详情页面根据点击的位置gotoDetail_for_Foodlist(position);该函数在后面部分叙述，此处只需知道它跳转到了详情页面。

而长按时，要将数据删除，这里使用notifyItemRemoved(position);以及之前在Adapter实现了的删除函数来实现这一功能，最后弹出一个toast。

```java
		RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(FoodList.this)); // 类似ListView
        final MyRecyclerViewAdapter myAdapter = new MyRecyclerViewAdapter<MyCollection>(FoodList.this, 
        R.layout.item, data2);
        
        myAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                gotoDetail_for_Foodlist(position);
            }

            @Override
            public void onLongClick(int position) {
                myAdapter.notifyItemRemoved(position);
                myAdapter.deleteData(position);
                Toast.makeText(FoodList.this,"移除第"+(position+1)+"个商品",
                        Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(myAdapter); //不使用动画情况，后面为其加自定义动画，见实验思考内容
```

到这里，我就完成了第一个页面FoodList中列表的设计，但是还需要一个浮动按键。根据实验的教程来引入依赖后，在FoodList.xml为其新建组件，设定id。

然后在食物列表页面通过id找到按键来处理，这里要求改变图片以及展示的内容，需要用到setVisibility，setImageResource这两个函数，通过一个tag来确定显示哪个页面，然后通过设置其是否展示或者展示哪张图片即可。
```java
//点击浮标事件
        final FloatingActionButton f_but = findViewById(R.id.btn);
        f_but.setOnClickListener(new View.OnClickListener(){
            boolean tag_for_foodlist = true;
            @Override
            public void onClick(View v){
                if(tag_for_foodlist){
                    findViewById(R.id.recyclerView).setVisibility(View.GONE);//设置Foodlist不可见
                    findViewById(R.id.listView).setVisibility(View.VISIBLE);
                    tag_for_foodlist = false;
                    f_but.setImageResource(R.mipmap.mainpage);
                }
                else{
                    findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
                    findViewById(R.id.listView).setVisibility(View.GONE);//设置Favourite不可见
                    tag_for_foodlist = true;
                    f_but.setImageResource(R.mipmap.collect);
                }
            }
        });
```
#### 4.利用ListView实现Collection收藏夹页面
首先在FoodList建立listview组件，然后才能通过id来找到。
ListView就比前面简单很多，可以直接使用simpleAdapter来直接设置，只需调整传入的内容参数即可，点击的监听器要分别设单击事件，前往详情页面；长按事件，弹出询问框是否删除，这一部分是上一实验的内容不再详述。
```java
//ListView部分
        ListView listview = (ListView) findViewById(R.id.listView);
        simpleAdapter = new SimpleAdapter(this, favourite, R.layout.item, 
        	new String[] {"img", "recipeName"}, new int[] {R.id.img, R.id.recipeName});
        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0)
                    gotoDetail_for_Collect(i);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 处理长按事件
                if(i != 0){
                    //弹出询问框
                    final int delete_num = i;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FoodList.this);
                    dialog.setTitle("删除");
                    dialog.setMessage("确定删除"+favourite.get(i).get("recipeName")+"?");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            favourite.remove(delete_num);
                            simpleAdapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
                return true; //这样长按与短按不会同时触发
            }
        });
```
#### 5.利用Relative布局，Linear布局，Constraint布局实现Detail详情页面的UI部分
这一部分需要我了解各种布局的一些具体情况，比如如何设置水平垂直居中，如何设置三分之一，如何与别的组件保持在一水平线上等等。

因为上次实验已经使用了Constraint布局来设计UI，所以这里只分析一下对于用Relative布局的详情页面的顶部，要将顶部设置为三分之一，需要利用**android:layout_weight="1"**这一属性，需要注意的是使用这一属性时，必须将高度设置为0，让其自动来匹配页面，以达成三分之一的效果。

对于RelativeLayout布局，layout_alignParentLeft表示返回图标位于页面的左侧，其次食物名字要与返回图标的左侧对齐就要使用android:layout_alignLeft="@id/back"，里面的参数为想要对齐的id。

对于星星图标的处理可以预先设置为空星星，而且增加tag来为后面的变化做准备。

**而我为了保存星星的状态，不使用这一方法，所以在xml上不写图片，而在Detail.xml根据食物来动态生成。见实验思考**
```xml
	<RelativeLayout
        android:id="@+id/top"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:background="#3F51BB"
        android:layout_weight="1"
        >
        <ImageView
            android:id="@+id/back"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:src="@mipmap/back"
            android:layout_alignParentLeft="true"
            android:layout_marginTop="10dp"
            android:layout_marginStart="10dp"
            android:layout_marginLeft="10dp" />
        <TextView
            android:id="@+id/name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_marginBottom="20dp"
            android:layout_marginRight="20dp"
            android:text="牛奶"
            android:textSize="30dp"
            android:textColor="@color/white"
            android:layout_alignLeft="@id/back"
            android:layout_marginEnd="10dp" />
        <ImageView
            android:id="@+id/star"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:layout_alignParentEnd="true"
            android:layout_alignTop="@+id/name"
            android:layout_marginEnd="20dp"
            android:layout_marginRight="20dp"
            android:layout_alignParentRight="true" />
        </RelativeLayout>
```
#### 6.利用Intent,startActivity等实现不同活动之间的传递信息
从前面点击监听器所绑定的跳转函数开始说明，这里是跳转到详情食物页面的函数，它必须根据坐标来将MyCollection中的内容读取出来，并将其放到bundle中，利用startActivityForResult来跳转到详情页面并等待返回参数来进行处理。这里需要的处理包括星星事件的点击，已经加入收藏夹的事件。
```java
private  void gotoDetail_for_Foodlist(int position){
        Intent intent = new Intent();
        intent.setClass(FoodList.this,Details.class);
        Bundle bundle = new Bundle();
        String s[] = new String [5];
        s[0] = data2.get(position).getName();
        s[1] = data2.get(position).getMaterial();
        s[2] = data2.get(position).getType();
        s[3] = data2.get(position).getContent();
        s[4] = data2.get(position).getIs_star()?"yes":"no";
        bundle.putStringArray("msg",s);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE);//REQUEST_CODE --> 1
    }
```

然后，在Detail.java中返回参数到主页面的函数。当点击星星以及收藏时候，我们只改变MyCollection的属性，而不是真正返回活动，而到点击返回按钮时候才根据这些改变的属性来传递不同的参数。

当返回2时，表示详情页面出现了收藏事件，必须将MyCollection的信息传递回去bundle.putSerializable("collect", temp)，并且使用setResult来返回参数。
当返回3时，表示详情页面出现了改变星星状态事件。
当返回4时，表示两种事件同时发生。
不然，则直接调用finish事件来结束活动。
```java
		//处理返回按钮
        final ImageView back_but = findViewById(R.id.back);
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp.getIs_collected() == true && temp.getIs_star() != (str[4].equals("yes"))){
                    Intent intent = new Intent(Details.this, FoodList.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("collect", temp);
                    intent.putExtras(bundle);
                    setResult(4,intent); //RESULT_CODE --> 4
                }
                //收藏夹
                else if (temp.getIs_collected() == true) {
                    Intent intent = new Intent(Details.this, FoodList.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("collect", temp);
                    intent.putExtras(bundle);
                    setResult(2,intent); //RESULT_CODE --> 2
                }
                //保存星星状态
                else if(temp.getIs_star() != (str[4].equals("yes"))){
                    Intent intent = new Intent(Details.this, FoodList.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("collect", temp);
                    intent.putExtras(bundle);
                    setResult(3,intent); //RESULT_CODE --> 3
                }
                Details.this.finish();
            }
        });
```
这样在主页面中只需重构OnActivityResult函数即可以处理这些事件。处理结果为2时，从intent中拿回食物的信息，通知收藏夹列表来改变列表，这里使用我的私有函数refreshList，太过简单也不再细述，详情参见代码。

而处理结果为3时，则要**在两个列表中查找所有该食物的状态，更改星星的情况，以此实现星星状态的长期保存**。
```java
	// 为了获取结果
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == 2) {
             if (requestCode == REQUEST_CODE) {
                 MyCollection mc = (MyCollection)data.getSerializableExtra("collect");
                 refreshList(mc,simpleAdapter);
             }
         }
         else if(resultCode == 3){
             if (requestCode == REQUEST_CODE) {
                 MyCollection mc = (MyCollection)data.getSerializableExtra("collect");
                for(int i = 0; i < data2.size(); i++){
                    if(data2.get(i).getName().equals(mc.getName())){
                        data2.set(i,mc);
                    }
                }
                 for(int i = 0; i < favourite.size(); i++){
                    if(favourite.get(i).get("recipeName").toString().equals(mc.getName())){
                        favourite.get(i).remove("star");
                        favourite.get(i).put("star",mc.getIs_star());
                    }
                 }
             }
         }
         else if(resultCode == 4){
             if (requestCode == REQUEST_CODE) {
                 MyCollection mc = (MyCollection)data.getSerializableExtra("collect");
                 for(int i = 0; i < data2.size(); i++){
                     if(data2.get(i).getName().equals(mc.getName())){
                         data2.set(i,mc);
                     }
                 }
                 refreshList(mc,simpleAdapter);
             }
         }
    }
```
#### 7.在Detail页面来根据不同的食物内容来动态生成UI界面
这里的动态包括，详情页面上部的颜色，以及名字，营养成分等等。**星星的状态也要动态生成**

通过intent拿出的信息来改变xml中相对应id的组件，只需注意id的正确，以及颜色的选取即可。
```java
	Bundle bundle=this.getIntent().getExtras();
        str = bundle.getStringArray("msg");
        TextView name = findViewById(R.id.name);
        name.setText(str[0]);
        TextView material = findViewById(R.id.material);
        material.setText("富含 "+ str[1]);
        TextView type = findViewById(R.id.type);
        type.setText(str[2]);
        temp = new MyCollection(str[0],str[3],str[2],str[1],false);

        //根据上次情况保存星星状态
        final ImageView star_but = findViewById(R.id.star);
        if(str[4].equals("yes")){
            star_but.setImageResource(R.mipmap.full_star);
            temp.setIs_star(true);
        }
        else{
            star_but.setImageResource(R.mipmap.empty_star);
            temp.setIs_star(false);
        }
```

### (3)实验遇到的困难以及解决思路
#### 1.RecycleView无法正确生成列表
按照老师给的教程一步步写好Adapter与Holder后，运行应用时出现闪退情况。报错信息为，**无法得到资源**。一有报错，第一步当然是将报错信息扔上搜索引擎，但是网页上的信息都说是因为setText()里面的参数为String而不是其他。但细看自己的程序并没有出现setText的错误参数情况。

然后，我对于类的传参开始找问题，结果发现是convert函数在传参的时候，没有找到资源，而是一个空的对象。于是再修改convert函数后，完成了这一部分的工作。
 
 #### 2.收藏列表的错误点击
 收藏列表的第一项为“*”与“收藏夹”，这两个不应该被触发点击事件，否则会传递一个空的MyCollection到详情页面会出现报错。所以必须在点击收藏列表的监听函数时加一个判断，当点击的是第一个item时，不要触发跳转事件。

#### 3.食物详情页面的UI设计不符合位置
这次详情页面的UI有点难度，对于三分之一的上部设置就弄了相当长的时间，当知道使用layout_weight时候，然而在实际使用的时候，却并没有达到三分之一的效果。后来，才知道没有将height设置为0dp，而是为wrap_content.。这样导致权重设置失败。

其次，对于设置分割线以及收藏图标如何垂直居中，间距合适遇到了困难。由于我在下部使用的是ConstrainLayout布局，所以必须要以别的组件来作相对设置位置。这里我对于这两个组件，分别相对于parent的上方，以及下面分割线的下方作为限制。这样就好像上下两个作用力，使其位于垂直居中的位置。

最后只需调整线条的长度以及图片的大小即可。
```xml
		<TextView
            android:id="@+id/ver_line"
            android:layout_width="2dp"
            android:layout_height="45dp"
            android:background="#1E000000"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintBottom_toBottomOf="@id/line1"
            android:gravity="center"
            android:layout_marginRight="10dp"
            app:layout_constraintRight_toLeftOf="@+id/collect" />

        <ImageView
            android:id="@+id/collect"
            android:layout_width="45dp"
            android:layout_height="45dp"
            android:scaleType="fitXY"
            android:src="@mipmap/collect"
            app:layout_constraintBottom_toBottomOf="@id/line1"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            android:layout_marginRight="20dp"
            />
```
#### 4.Detail页面与Foodlist页面进行交换信息时候，对于数据包的处理
由于要使用intent来实现不同活动中的交互，必须将食物的信息传递到详情页面，以及在详情页面中改变后的食物信息传递回食物列表存储。于是，就要求他们交换信息时候必须要满足两个条件，第一是要一次传递一个食物对象，第二是要满足intent的信息交互函数。这里使用的是bundle的putSerializable函数，**这也要求我们的食物类必须要实现Serializable类。**

```java
		Intent intent = new Intent(Details.this, FoodList.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("collect", temp);
        intent.putExtras(bundle);
        setResult(2,intent); //RESULT_CODE --> 2
```


---

## 四、实验思考及感想
### A.UI界面设计与基础事件交互
本次是第一次安卓开发的实验，主要关于UI界面的设计与一些简单的交互，这与我之前学过的web网页设计十分相似，定义组件以及通过id来对于每一个组件来设置一些监听函数，完成所需要的功能。

但是，安卓开发上也有许多不同之处，对于java文件中必须要了解调用组件的监听函数，名字比较长，而且参数多，必须在平时熟练使用并要经常查阅手册。

对于ui界面，我这次主要是通过xml的书写来生成界面，用里面的一些属性来定义组件的大小，边距等等，除此之外，安卓开发中还很讲究文件的分类，将string，color，style设置成另外的文件，在主的xml可以调用这些文件中的内容来实现，这样的好处便于修改界面的内容，例如可以根据这个来开发中文英文不同的ui界面版本。

### B.Intent、Bundle的使用以及RecyclerView、ListView的应用
这次实验花了不少的时间来理解不同列表的实现方式，学习了不同ui布局的位置设置，活动之间的交互信息方法，按钮监听函数。

但是，对于实验基本要求所做出了的应用程序还是有一些不太完美的地方，于是，我做了一些**改进的地方（加分项）**，使其更加符合日常使用，包括对于详情列表星星的状态保存，在详情页面不按返回图标而是点击手机的返回键时无法收藏该食物状态，还为RecycleList加了一个自定义的动画效果，使其更加美观。

### 1.对于星星状态的持久化改进
星星的状态持久化，我实现出来的效果是当该食物被加星后，无论是在食物列表还是在收藏列表都会出现加星的同步状态，不会出现个别加星个别不加星。

这里实现的持久化，实际就是给食物添加多一个is_star属性来判断该食物的状态，并将该状态传递到详情页面来动态处理。

```java
//根据上次情况保存星星状态
        final ImageView star_but = findViewById(R.id.star);
        if(str[4].equals("yes")){
            star_but.setImageResource(R.mipmap.full_star);
            temp.setIs_star(true);
        }
        else{
            star_but.setImageResource(R.mipmap.empty_star);
            temp.setIs_star(false);
        }
```

而在改变后返回到其他界面时，也要将改变了的星星状态返回，以此改变该食物在数据结构中的信息

```java
else if(resultCode == 3){
             if (requestCode == REQUEST_CODE) {
                 MyCollection mc = (MyCollection)data.getSerializableExtra("collect");
                for(int i = 0; i < data2.size(); i++){		//更新FoodList
                    if(data2.get(i).getName().equals(mc.getName())){
                        data2.set(i,mc);
                    }
                }
                 for(int i = 0; i < favourite.size(); i++){		//更新CollectList
                    if(favourite.get(i).get("recipeName").toString().equals(mc.getName())){
                        favourite.get(i).remove("star");
                        favourite.get(i).put("star",mc.getIs_star());
                    }
                 }
             }
         }
```

### 2.对于手机系统返回键的处理
这里出现的bug是在详情页面点击收藏后，不按返回小图标，而是点击手机返回键时，无法收藏该食物。这是因为点击手机收藏键是没有将信息传递回主页面的，所以我们必须根据这个按键重构返回键的功能，来让该功能与点击返回小图标是一样的。

当在详情页面，得到返回键被单击时，实现的功能与点击返回图标相同。而其他则继续执行系统的默认按键功能在最后添加return super.onKeyDown(keyCode, event);
```java
 	//点击返回时候，加入收藏也要生效
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //两种情况同时实现
            if(temp.getIs_collected() == true && temp.getIs_star() != (str[4].equals("yes"))){
                Intent intent = new Intent(Details.this, FoodList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("collect", temp);
                intent.putExtras(bundle);
                setResult(4,intent); //RESULT_CODE --> 4
            }
            //收藏夹
            else if (temp.getIs_collected() == true) {
                Intent intent = new Intent(Details.this, FoodList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("collect", temp);
                intent.putExtras(bundle);
                setResult(2,intent); //RESULT_CODE --> 2
            }
            //保存星星状态
            else if(temp.getIs_star() != (str[4].equals("yes"))){
                Intent intent = new Intent(Details.this, FoodList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("collect", temp);
                intent.putExtras(bundle);
                setResult(3,intent); //RESULT_CODE --> 3
            }

            else{
                Details.this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
```

### 3.实现RecycleList的动画
在res文件夹建立anim文件夹来放置动画的xml文件，首先要建立layout_animation_fall_down.xml文件

其中animation为列表每一项item的动画，其文件在后面再实现，delay表示动画的延迟时间，animationOrder表示动画item的顺序是正常，即从大到小，在这里实现的效果就是从搞到低
```xml
<?xml version="1.0" encoding="utf-8"?>
<layoutAnimation
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:animation="@anim/item_animation_fall_down"
    android:delay="15%"
    android:animationOrder="normal"
    />
```
接着对item实现layout_animation_fall_down.xml文件，来控制列表每一项的动画效果。

translate组件中fromYDelta表示item首先位于y轴的上方20%出发，然后toYDelta表示item所要到达的位置，这里的0表示为回到本应该的位置。interpolator里面的属性表示减速实现动画过程。

alpha组件表示透明度的变化，由0到1，加速实现动画过程

scale组件表示item的大小，由105%变化为100%，略微缩放动画。

```xml
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="500" >
    <translate
        android:fromYDelta="-20%"
        android:toYDelta="0"
        android:interpolator="@android:anim/decelerate_interpolator"
        />
    <alpha
        android:fromAlpha="0"
        android:toAlpha="1"
        android:interpolator="@android:anim/decelerate_interpolator"
        />
    <scale
        android:fromXScale="105%"
        android:fromYScale="105%"
        android:toXScale="100%"
        android:toYScale="100%"
        android:pivotX="50%"
        android:pivotY="50%"
        android:interpolator="@android:anim/decelerate_interpolator"
        />

</set>
```

这样就实现了一个列表的从上到下，逐渐出现的动画。

#### 感想
通过不断的学习，总算理解了android的一些机制，也能简单的写出了一个程序了。但是对于java语言的虚函数，静态函数，接口，数据类型等等都需要加强，这会使我更方便地理解类与类之间的关系。对于ui的设计要熟练掌握三种布局的运用，可以适当给某些组件先赋值通过preview来查看位置，再在java文件中实现动态赋值，这样做既能保证ui也能动态生成页面。这次实验使用的是绑定数据是运用数组，猜想未来应该可以引入数据库的绑定，这样会使代码更加简洁。

---

