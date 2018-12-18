# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 |   任课老师   |         郑贵锋         |
| :------: | :--------------: | :----------: | :--------------------: |
|   年级   |        16        | 专业（方向） | 软件工程（计算机应用） |
|   学号   |     16340132     |     姓名     |         梁颖霖         |
|   电话   |   13680473185    |    Email     |      dic0k@qq.com      |
| 开始日期 |    2018/12/6     |   完成日期   |       2018/12/18       |

---

## 一、实验题目

**WEB API**

**第十四周实验目的**

1. 学会使用HttpURLConnection请求访问Web服务
2. 学习Android线程机制，学会线程更新UI
3. 学会解析JSON数据
4. 学习CardView布局技术

---

## 二、实现内容

#### 实现一个bilibili的用户视频信息获取软件
<table>
    <tr>
        <td ><img src="../../../manual/images/img1.png" >打开程序主页面</td>
        <td ><img src="../../../manual/images/img2.png" >输入用户id，要求正整数int类型，不满足的弹Toast提示即可</td>
    </tr>
    <tr>
        <td ><img src="../../../manual/images/img3.png" >输入用户id，点击搜索，网络没打开则弹Toast提示网络连接失败</td>
        <td ><img src="../../../manual/images/img4.png" >网络打开情况下，输入用户id，不存在相应数据的弹Toast提示</td>
    </tr>
    <tr>
        <td ><img src="../../../manual/images/img5.png" >输入用户id = 2，点击搜索，展示图片/播放数/评论/时长/创建时间/标题/简介内容</td>
        <td ><img src="../../../manual/images/img6.png" >再次输入用户id = 7，接着上次结果继续展示以上内容</td>
    </tr>
</table>


* 搜索框只允许正整数int类型，不符合的需要弹Toast提示
* 当手机处于飞行模式或关闭wifi和移动数据的网络连接时，需要弹Toast提示
* 由于bilibili的API返回状态有很多，这次我们特别的限制在以下几点
    * 基础信息API接口为： `https://space.bilibili.com/ajax/top/showTop?mid=<user_id>`
    * 图片信息API接口为基础信息API返回的URL，cover字段
    * 只针对前40的用户id进行处理，即`user_id <= 40`
    * [2,7,10,19,20,24,32]都存在数据，需要正确显示
* **在图片加载出来前需要有一个加载条，不要求与加载进度同步**
* 布局和样式没有强制要求，只需要展示图片/播放数/评论/时长/创建时间/标题/简介的内容即可，可以自由发挥
* **布局需要使用到CardView和RecyclerView**
* 每个item最少使用2个CardView，布局怎样好看可以自由发挥，不发挥也行
* 不完成加分项的同学可以不显示SeekBar
* 输入框以及按钮需要一直处于顶部

---
### 验收内容
1. 图片/播放数/评论/时长/创建时间/标题/简介 显示是否齐全正确，
2. 是否存在加载条
3. Toast信息是否准确，特别地，针对用户网络连接状态和数据不存在情况的Toast要有区别
4. 多次搜索时是否正常
5. 代码+实验报告
6. 好看的界面会酌情加分，不要弄得像demo那么丑= =

---
### 加分项
<table>
    <tr>
        <td ><img src="../../../manual/images/img7.png" >拖动SeekBar，显示相应位置的预览图</td>
        <td ><img src="../../../manual/images/img8.png" >拖动SeekBar，显示相应位置的预览图</td>
    </tr>
</table>


* 拖动前后均显示原图片
* 模拟bilibili网页PC端，完成可拖动的预览功能
* 拖动seekBar，预览图会相应改变
* 前40的用户id中，32不存在预览图，可以忽略也可以跟demo一样将seekbar的enable设置为false
* 需要额外使用两个API接口，分别为
    * 利用之前API获得的信息，得到aid传入`https://api.bilibili.com/pvideo?aid=<aid>`
    * 利用`api.bilibili.com`得到的信息，解析image字段得到`"http://i3.hdslb.com/bfs/videoshot/3668745.jpg` 的图片
    * 分割该图片即可完成预览功能
* 加分项存在一定难度，需要不少额外编码，**可不做**。
* 32不存在预览图，可忽略或处理该异常情况

---

## 三、实验结果
### (1)实验截图

截图一：打开程序主页面



![1](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/1.png)



截图二：搜索id=2的用户信息，用户存在



![2](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/2.png)



截图三：搜索id=3的用户信息，用户不存在



![3](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/3.png)



截图四：网络关闭的情况下搜索id=7的用户信息（用户存在但网络关闭）



![4](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/4.png)



截图五：搜索多个用户的信息

![5](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/5.png)



截图六：**加分项**，拖动SeekBar显示视频的缩略图

<table>
    <tr>
        <td ><img src="https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/6.png" ><br/>拖动SeekBar，显示相应位置的预览图</td>
        <td ><img src="https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/7.png" ><br/>拖动SeekBar，显示相应位置的预览图</td>
    </tr>
</table>



### (2)实验步骤以及关键代码

#### a.设计recyclerView所使用的item.xml

其中主要包括了cardView的使用，设置边距。

主要效果如下图所示：

![8](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/8.png)



两个CardView使用线性布局，布局方向为垂直。而在CardView里面使用限制性布局，将播放、评论、时长等元素依次放置。

关于CardView整体的布局边距以及颜色的设置如下

```xml
<android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:foreground = "?attr/selectableItemBackground"
        app:cardBackgroundColor = "#f0e3c4"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        app:cardCornerRadius="8dp"
        app:contentPadding="5dp">
    ······
```

最后我还在每一组用户数据后面添加了一条分界线，让界面更加清晰友好。



#### b.建立RecyclerObj类

RecyclerObj类是用于保存用户的信息以及显示在RecyclerView中。

这个类是根据b站所提供的api所得到的json数组所对应设计的，而其中的data就是保存我们用户视频的信息，包括封面图，名字，时间，评价数，评论等等。

而ArrayList<ImagePiece> pieces是使用在**加分项中存储一系列预览图的**，ImagePiece就是它的基类，包括index与图片两个属性。

```java
public class RecyclerObj {
    private Boolean status;
    private Data data;
    private ArrayList<ImagePiece> pieces;

    public static class ImagePiece{
        private Bitmap bitmap;
        private int index;
		·····
    }

    public static class Data{
        private String aid;
        private String state;
        private String cover;
        private String title;
        private String content;
        private String play;
        private String duration;
        private String video_review;
        private String create;
        private String rec;
        private String count;
        private Bitmap cover_image;
		······
    }
    ······
}
```



#### c.RecyclerView的显示

这一部分与之前第一个项目的实现类似，包括一个Holder以及一个Adapter。具体实现代码不再重复放置，主要逻辑是将List<RecyclerObj> data传入Adapter中，Adapter根据位置的不同来绑定不同的数据。

Holder是使用是为了在onBindViewHolder 中获取页面的元素，为其绑定数据。下面给出两个简单的代码展示，其他TextView的内容显示也是如此。

```java
public void onBindViewHolder(final MyViewHolder holder, final int position) {
 		······ 
            // 给封面图设置图片，该图片是从data中获得的
        ((ImageView)holder.getView(R.id.web_image)).setImageBitmap(
            data.get(position).getData().getCover_image());
    		// 同理。设置播放数量
        ((TextView)holder.getView(R.id.play_amount)).setText(
            data.get(position).getData().getPlay());
        ······
}
```



#### d.为输入按钮绑定事件，判断输入数据的准确性

这里给button设置监听器，当点击时获取EditText中的数据，然后利用正则匹配来解决非数字或者非整数的错误判断。

除此以外，还限定了输入的数字不能大于40或者小于0.

若无错误，则开始获取用户信息的线程。

```java
		// 判断输入框,处理user的id
        final EditText editText = findViewById(R.id.input);
        Button button = findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                // 正则匹配，判断是否是数字
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher matcher = pattern.matcher(s);
                if (!matcher.matches()){
                    Toast.makeText(MainActivity.this,"搜索框只允许正整数int类型，请重新输入！",Toast.LENGTH_SHORT).show();
                }
                else {
                    user_id = Integer.parseInt(s);
                    if (user_id > 40 || user_id < 0){
                        Toast.makeText(MainActivity.this,"user的id查询只允许小于等于40且大于0",Toast.LENGTH_SHORT).show();
                    }
                }
                // 获取该id的信息
                thread.start();
            }
        });
```



#### e.通过HTTPConnection获取数据，并解析json

由于通过HTTPConnection获取数据是耗时操作，所以必须另开线程。

首先设置url，根据提供的api，以及用户所提供的user_id来新建地址。

```java
		URL url = null;
            try {
                url = new URL("https://space.bilibili.com/ajax/top/showTop?mid="+user_id);
            } catch (MalformedURLException e) {
                //网络连接错误
                handler.sendEmptyMessage(NETWORK_ERROR);
                e.printStackTrace();
            }
```

第二步就是通过这个url来打开链接，使用GET方法访问网络，然后设置它不能超过时间10s，否则回捕捉到这个异常，然后发送消息给handler来处理，发出toasat网络异常。

接着，利用inputStream获取数据，利用InputStreamReader将数据解析出来，将json类型转化成之前设计好的RecyclerObj类。

最后只需要将消息传递回handler处理，表示已经获取完数据了，并将这个recyclerObj对象加入到data列表中，回到handler利用Adapter的notifyDataSetChanged即可实现UI界面的变化。

```java
 				// 获取连接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 使用GET方法访问网络
                connection.setRequestMethod("GET");
                // 超时时间为10秒
                connection.setConnectTimeout(10000);
                // 获取返回码
                int code = connection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = connection.getInputStream();
                    String result = new BufferedReader(new InputStreamReader(inputStream))
                            .lines().collect(Collectors.joining(System.lineSeparator()));
                    Message msg = Message.obtain();
                    // 处理字符串放入列表中，用于显示UI
                    RecyclerObj recyclerObj;
                    try {
                        // 处理json
                        recyclerObj = new Gson().fromJson(result, RecyclerObj.class);
                        // 获取预览图
                        data.add(recyclerObj);
                        msg.obj = recyclerObj;
                        msg.what = GET_DATA_SUCCESS;
                    }
                    catch (Exception e){
                        msg.obj = null;
                        msg.what = GET_DATA_EMPTY;
                    }
                    handler.sendMessage(msg);
                    inputStream.close();
                }else {
                    //服务启发生错误
                    handler.sendEmptyMessage(SERVER_ERROR);
                }
```



#### f.Handler的设计

Handler的作用是处理其他线程返还回来的数据或者信息。

这里用用户信息获取成功作为例子讲述，当我获取完用户的数据后，且已经将数据传递给recyclerObj，这时候我要做的操作是根据这个用户提供的封面图url来再次获取图片，**以及完成加分项获取多个预览图**。这些都是耗时操作，所以我写了别的线程来处理，这里只需要去调用即可。

myAdapter.notifyDataSetChanged();就是在主线程来更新RecyclerView的显示，因为之前已经将数据加入到了list中。

```java
public void handleMessage(Message msg) {
            switch (msg.what){
                // 获取用户信息成功
                case GET_DATA_SUCCESS:
                    myAdapter.notifyDataSetChanged();
                    RecyclerObj recyclerObj = (RecyclerObj)msg.obj;
                    setImageURL(recyclerObj);
                    getImagePeacesByAid(recyclerObj);
                    break;
                // 获取不到信息
                case GET_DATA_EMPTY:
                    Toast.makeText(MainActivity.this,"数据库不存在记录",Toast.LENGTH_SHORT).show();
                    break;
                // 网络连接失败
                case NETWORK_ERROR:
                    Toast.makeText(MainActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    break;
                // 服务器错误
                case SERVER_ERROR:
                    Toast.makeText(MainActivity.this,"服务器发生错误",Toast.LENGTH_SHORT).show();
                    break;
                // 获取封面图成功
                case GET_IMAGE_SUCCESS:
                    // 去除缓冲的圆圈
                    myAdapter.notifyDataSetChanged();
                    Log.i("handler","设置照片成功");
                    break;
                // 获取预览图成功
                case GET_IMAGEPEACES_SUCCESS:
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
```



#### g. 获取用户视频的封面图

这一个操作与获取用户资料类似，只不过这次是一张图片而已。HTTPConnection部分类似，这里只叙述如何将获取的图片inputStream转化到用户recyclerObj类中。

这里使用工厂把网络的输入流生产Bitmap，然后将这张bitmap赋值到recyclerObj中，这样recyclerObj就已经有了封面图的bitmap了，返回消息给handler，让它来更新ui，包括加载出封面图以及取消ProcessBar的显示。

```java
InputStream inputStream = connection.getInputStream();
//使用工厂把网络的输入流生产Bitmap
Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//利用Message把图片发给Handler
Message msg = Message.obtain();
data.remove(recyclerObj);
recyclerObj.getData().setCover_image(bitmap);
 msg.what = GET_IMAGE_SUCCESS;
data.add(recyclerObj);
handler.sendMessage(msg);
inputStream.close();
```



这样，我们就可以获得基础的应用结果了，搜索用户id获得一些信息。

**至于拖动seekBar显示缩略图部分，留到实验思考与感想部分叙述**



### (3)实验遇到的困难以及解决思路

#### a.处理ProcessBar的显示

由于网速加载速度太快，导致看不出ProcessBar的出现，而是直接出现封面图。

我在获取封面图的线程中先让线程sleep了一秒再进行获取，这样就可以利用这个时间差来显示出加载条的转动。

```java
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
```

#### b.处理inputStream中的信息

通过HttpURLConnection获取的信息存放在inputStream，我所要做的任务是如何将里面的信息获取出来。这里针对两个方面，第一个是图片数据，第二个是纯文字json数据。

关于文字json数据，按行来获取数据，并直接转化成String。

```java
String result = new BufferedReader(new InputStreamReader(inputStream))                         .lines().collect(Collectors.joining(System.lineSeparator()));
```

关于图片数据，使用Bitmap工厂

```java
Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
```



除此之外，网上还有多种处理inputStream的方法

参考链接：[将InputStream读取为String](https://www.cnblogs.com/milton/p/6366916.html)

#### c. 处理seekBar显示预览图

这是在做加分项时候遇到的困难，一开始以为缩略大图只有一张，index大小会小于100个。结果发现user_id = 24的时候，缩略图有两张，这样会使我的程序崩溃。

由于一张缩略图可以装载100张预览图，即index数量可以到达100个。所以我利用这个信息来区分是需要读取一个缩略图还是多个，然后将这些index与缩略图对应起来写进recyclerObj.

```java
						// 两张缩略图
                        if (indexArray.length > 100){
                            imageUrlArray = image_url.split(",");
                            imageUrlArray[0] = imageUrlArray[0].substring
                                (2,imageUrlArray[0].length()-1);
                            imageUrlArray[1] = imageUrlArray[1].substring
                                (1,imageUrlArray[1].length()-2);
                        }
                        // 一张缩略图
                        else{
                            image_url = image_url.substring
                                (2,image_url.length()-2);
                            imageUrlArray[0] = image_url;
                        }
```



---

## 四、实验思考及感想

### a.加分项：完成拖动seekBar显示缩略图

**主要步骤：**

1. 通过api获取缩略图的url地址与index数组。

2. 根据这个url获取到图片到应用。
3. 将图片切分并与index对应起来，放入recyclerObj的ImagePieces链表中
4. 设置seekBar的变化监听器，处理拖动事件与初始化

#### 1.通过api获取缩略图的url地址与index数组

这一步与之前的HTTPUrlConnection一样，没有什么不同。

唯一需要做的是，我这次不再需要整个json都拿去下来，而只是要拿两个元素，所以我没再使用json而是利用JSONObject以及它对应的属性名就可以处理。

```java
// 测试获取图片的url字符串
JSONObject obj = new JSONObject(result);
String image_url = obj.getJSONObject("data").getString("image");
String index = obj.getJSONObject("data").getString("index");
```

获取完成后，还要对字符串进行处理，例如对于index需要切分，放到数组当中。判断index的个数决定有多少张缩略图。

```java
index = index.substring(1,index.length()-1);
String[] indexArray = index.split(",");
```

同样，根据缩略图的数量来处理url

```java
// 两张缩略图
if (indexArray.length > 100){
	imageUrlArray = image_url.split(",");
    imageUrlArray[0] = imageUrlArray[0].substring(2,imageUrlArray[0].length()-1);
     imageUrlArray[1] = imageUrlArray[1].substring(1,imageUrlArray[1].length()-2);
}
// 一张缩略图
else{
	image_url = image_url.substring(2,image_url.length()-2);
    imageUrlArray[0] = image_url;
}
```



#### 2.根据这个url获取到图片到应用。

这一步与之前获取图片一致，不重复



#### 3.将图片切分并与index对应起来，放入recyclerObj的ImagePieces链表中

切分的关键是知道原始图的大小，以及一块切分后的图片的大小，我们通过api拿回来的数据可以看到原始图是1600*900大小，且一行有十张预览图，一共有十行。因此，我们利用这个信息来进行循环切割，每切割一份，将它与index联系在一起放入到ImagePieces中。

这里主要是利用了Bitmap.createBitmap (bitmap,xValue,yValue,width,height);

+ bitmap是原始图片
+ xValue是原始图片的横坐标
+ yValue是原始图片的纵坐标
+ width是需要切割的宽度
+ height是需要切割的高度

```java
		int width = 160;
        int height = 90;
        int xValue = 0;
        int yValue = 0;
		for (int i = 1; i <= size; i++){
        	Bitmap piece_bitmap = Bitmap.createBitmap
                (bitmap,xValue,yValue,width,height);
            xValue += width;
            // 换行
            if(i%10==0){
                yValue += height;
                xValue = 0;
            }
         RecyclerObj.ImagePiece piece = new RecyclerObj.ImagePiece
             (piece_bitmap,Integer.valueOf(indexArray[i-1]));
          imagePieces.add(piece);
         }
```



#### 4.设置seekBar的变化监听器，处理拖动事件与初始化

这在Adapter的onBindViewHolder函数中来处理，初始化seekBar的最大progress为视频的时间秒数，初始位置为0.

```java
			((SeekBar)holder.getView(R.id.seekBar)).setEnabled(true);
            String timeStr = data.get(position).getData().getDuration();
            String[] timeArray = new String[2];
            timeArray = timeStr.split(":");
            int minute = Integer.valueOf(timeArray[0]);
            int second = Integer.valueOf(timeArray[1]);
            int time = minute * 60 + second;
            Log.i("时间长度",time+"");
            ((SeekBar)holder.getView(R.id.seekBar)).setMax(time);
            ((SeekBar)holder.getView(R.id.seekBar)).setProgress(0);
```

然后为其设置监听器，当它改变的时候，查看是否在该index中有预览图，若有就显示，若无不改变

拖动结束后，要将封面图变回原来的，且process值归零.

```java
((SeekBar)holder.getView(R.id.seekBar)).setOnSeekBarChangeListener
(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, 
                boolean fromUser) {
                    ArrayList<RecyclerObj.ImagePiece> pieces =  data.get(position).getPieces();
                    Log.i("时间长度",progress+"");
                    for (int i = 0; i < pieces.size(); i++){
                        if (pieces.get(i).getIndex() == progress){
                            Bitmap bitmap = pieces.get(i).getBitmap();
                            ((ImageView)holder.getView(R.id.web_image)).
                            setImageBitmap(bitmap);
                            break;
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // 设置回初始状态
                    seekBar.setProgress(0);
                    ((ImageView)holder.getView(R.id.web_image)).
                    setImageBitmap(data.get(position).getData().getCover_image());
                }
            });
```



### b. 感想

这次实验利用的是Http访问api来获取数据，其中涉及了对json数据的转化，对字符串的规范化处理，以及对于图片的获取。与此同时，复习了最初的recyclerView的显示与设置，线程与handler的知识。这次还使用了新的UI组件CardView与ProcessBar，丰富了UI的元素，学习了更多的界面设计。

关于加分项的缩略图处理还是挺有难度的，花费了一个下午的时间才能完成。通过这个加分项学习到了如何切割图片，如何获取个别json数据，将图片与SeekBar联系在一起显示，感觉这个功能还是很实用的。加分项虽然比较难，但是做出来的成就感就更大了。



------



## 一、实验题目

###  第十五周实验目的

1. 理解Restful接口
2. 学会使用Retrofit2
3. 复习使用RxJava
4. 学会使用OkHttp

------

## 二、实现内容

#### 实现一个github用户repos以及issues应用

| ![img](https://gitee.com/dic0k/PersonalProject5/raw/master/manual/images/img9.png)主界面有两个跳转按钮分别对应两次作业 | ![img](https://gitee.com/dic0k/PersonalProject5/raw/master/manual/images/img10.png)github界面，输入用户名搜索该用户所有可提交issue的repo，每个item可点击 |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![img](https://gitee.com/dic0k/PersonalProject5/raw/master/manual/images/img11.png)repo详情界面，显示该repo所有的issues | ![img](https://gitee.com/dic0k/PersonalProject5/raw/master/manual/images/img12.png)加分项：在该用户的该repo下增加一条issue，输入title和body即可 |

+ 教程位于`./manual/tutorial_retrofit.md`
+ 每次点击搜索按钮都会清空上次搜索结果再进行新一轮的搜索
+ 获取repos时需要处理以下异常：HTTP 404 以及 用户没有任何repo
+ 只显示 has_issues = true 的repo（即fork的他人的项目不会显示）
+ repo显示的样式自由发挥，显示的内容可以自由增加（不能减少）
+ repo的item可以点击跳转至下一界面
+ 该repo不存在任何issue时需要弹Toast提示
+ 不完成加分项的同学只需要显示所有issues即可，样式自由发挥，内容可以增加

------

## 三、实验结果

### (1)实验截图

1.新增初始页面

![1](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/proj%202/1.png)



2.进入GITHUB API初始页面

![2](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/proj%202/2.png)



3. 搜索dick20的github项目



![3](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/proj%202/3.png)



4.点击进入其中一个项目，查看Issue



![4](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/proj%202/4.png)



5.新建一个Issue



![5](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/proj%202/5.png)



6.点击进入一个没有Issue的repo



![6](https://gitee.com/dic0k/PersonalProject5/raw/master/report/Wednesday/16340132LiangYingLin/images/proj%202/6.png)



### (2)实验步骤以及关键代码

#### a.页面的设计

新增的内容也可以复用之前的页面架构，都是使用RecyclerView来显示列表的内容。具体的边距也没有很大的调整，只是单纯改变其中的Text。这里不再叙述。



#### b.通过用户名获取Github的Repo

首先，要设计获取过来Repo的内容要显示些什么。下面包括5个属性，name名字，description描述，id仓库的号码，has_issues表示该仓库是否包含Issue，open_issues表示开放issue的数量

```java
public class Repo {
    String name;
    String description;
    String id;
    Boolean has_issues;
    int open_issues;
    ···
}
```



创建RecyclerView就不再重复放置，这里重点说一下使用Retrofit2+RxJava如何实现get到用户的仓库。首先，我们先定义一个接口，使用GET参数，并且传入一个用户名。

```java
 public interface GitHubService {
        @GET("users/{user_name}/repos")
        Observable<List<Repo>> getRepo(@Path("user_name") String user_name);
    }
```

第二步，分别定义OkHttpClient，设置其的超时时间限制。retrofit设置**网络请求的Url基地址**，添加支持RxJava的转换工厂。

```java
private void request_repo(String userName){
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/") // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .client(build)
                .build();
```

第三步，根据之前的接口定义来创建repoObservable，然后就像对RxJava对象一样操作，在主线程观察其改变，在io线程订阅。**其意义在于在UI线程来改变UI，而在io线程来进行网络访问**

接着，填写onError函数来处理无法找到用户的情况。填写onNext函数来处理拿回来的List<Repo>,将它一个个加入显示的列表中，最后利用adapter的notifyDataSetChanged，这样实现UI的变化。

```java
			
        GitHubService service = retrofit.create(GitHubService.class);
        Observable<List<Repo>> repoObservable = service.getRepo(userName);
		repoObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(GithubActivity.this,
                           "无法找到该用户",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        for (int i = 0; i < repos.size(); i++){
                            Log.i("list",repos.get(i).getName());
                            list.add(repos.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
```



#### c.为每个仓库条目设置跳转事件

在获取完仓库列表后，要跳转到某特定仓库的里面，查看Issue的情况。这里利用的是我在Adapter定义的接口来重构点击事件，为其创建特定监听事件。

这里仅仅需要处理单击事件即可，长按事件可以不填忽略。

传递参数包括仓库的名字，用户名字，该仓库是否有Issue，这三个参数都是在Issue页面所要用到，通过API获取Issue必须的参数。

```java
adapter.setOnItemClickListener(new 
            GithubRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                // 传递的三个参数
                bundle.putString("repoName",
                                 list.get(position).getName());
                bundle.putString("userName",userName);
                bundle.putBoolean("hasIssue",
                                  list.get(position).getHas_issues());

                intent.setClass(GithubActivity.this,IssueActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
```



#### d.通过用户名，仓库名获取Issue

Issue类的设计，需要显示的内容包含title名称，body内容，created_at创建的时间，state状态表示该Issue是open还是close。

```java
public class Issue {
    String title;
    String body;
    String created_at;
    String state;
    ···
}
```

与获取仓库类似，获取Issue这里还是需要GET参数，以及这次需要传入用户名以及仓库名

```java
public interface IssueService {
        @GET("repos/{user_name}/{repo_name}/issues")
        Observable<List<Issue>> getIssue(@Path("user_name") 
                String user_name, @Path("repo_name") String repo_name);
    }
```

RxJava的操作也类似，这里就不再重复说明，获取过来的issue也是添加到列表，然后adpter来刷新显示。



#### c.处理没有Issue的提示

跳转过来后，要先判断has_issue是否为真，如果不是则证明该仓库是fork来的，故没有issue这一功能，需要把显示与添加功能屏蔽掉，并显示toast提醒。

```java
		Boolean hasIssue = bundle.getBoolean("hasIssue");
        if (hasIssue){
            request_issue(userName,repoName);
            // 加分项，绑定按键监听器，post一个issue
            bind_post_issue();
        }
		// 显示提醒Toast
        else{
            Toast.makeText(IssueActivity.this,
                           "该repo不存在issue",Toast.LENGTH_SHORT).show();
        }
```



### (3)实验遇到的困难以及解决思路

#### a.POST操作时候，返回数据不正确，不能正常post

这个问题困扰了我很久，我post的结果是返回了一个json的数组，而在postman软件测试api的时候明明只返回了一个json，而且我得到的返回数据与get回来的数据是一样的，即post失败。

这个问题我首先是试着从token方面来找问题，修改了Headers以及Header写法都没有改变这个情况。紧接着，我试着将@Field改成@RequestBody来装载post过去的数据，并通过log来查看post的json内容，结果都是正确无误。这使我一度陷入怀疑人生的状态，在这个debug过程学会了多种post的方法却无一成功。

debug不成，就开始从头开始重构代码，结果被我反复查看，发现了我的**api基地址填写的是http://而不是https://开头**.

这个错误导致了一直post不成功，甚至返回get正确的结果。这样的错误难以发现，但是经历这次错误后，以后使用api都会再三比对网址的准确性。



#### b.token的读取错误

这是一个比较搞笑的错误，但还是要记录一下，避免下次的犯错。本来是应该Authorization，我却将其拼写成了Authentication。这两个单词傻傻分不清楚，下次不会再犯，这样的错误是会有错误提示，比较好找，第一个错误就没有这么幸运了。

```java
@Headers("Authorization: 
                 token xxxx34ddbcb0xxxxxx1ce6xxxx0d9xxxx2fbxxxx")
```



#### c.RecyclerView显示丢失

在通过api获取完仓库列表后，我直接将获取后的list复制给adapter的list，结果UI显示不出来，输出list的数据却存在。

这是因为改变了list的地址，使到adapter所传入的list直接丢失掉。正确的做法是将新数据一个个从列表中拿出来，放置在初始化adapter的list中，这样就可以正常显示了。

```java
// 错误
list = issues;
// 正确
for (int i = 0; i < issues.size(); i++){
	Log.i("list",issues.get(i).getTitle());
	list.add(issues.get(i));
}
```



------

## 四、实验思考及感想

### a.加分项：实现POST一个评论

惯例，先写出接口函数，由于POST需要用户的一些权限。所以这里通过Headers传入了权限的token，该token可以从github的设置中获取。

POST参数，所使用的地址与GET一样，但是需要一个变量存放传入的Json。

```java
        @Headers("Authorization: 
                 token xxxx34ddbcb0xxxxxx1ce6xxxx0d9xxxx2fbxxxx")
        @POST("/repos/{user_name}/{repo_name}/issues")
        Observable<Issue> postIssue(@Path("user_name") String user_name,
     @Path("repo_name") String repo_name, @Body RequestBody requestBody);
```

创建OkHttpClient与Retrofit跟GET一样，不重复放代码。这里讲述新建一个JSONObject，将传入的数据按照键值对的形式put进去。然后RequestBody转换成json。接着我们就可以利用之前定义的post接口来获取单个Issue皆可。

```java
		JSONObject root = new JSONObject();
        try {
            root.put("title", title);
            root.put("body", body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create
        		(MediaType.parse("application/json"), root.toString());
        Log.i("requestBody",root.toString());

        IssueService service = retrofit.create(IssueService.class);
        Observable<Issue> IssueObservable2 = service.postIssue
        		(userName,repoName,requestBody);
        
```

获取了Issue后，将它加入队列显示就完成了。



### b.感想

这次Retrofit实验是在之前HttpConnection的基础下做的，其实除了用了原有的界面也没有太多的可复用性。这次实验还是加强了RecyclerView的使用技巧，复习了RxJava的多线程处理，学习到了新的访问网络获取数据的方式。相比较下来，使用Retrofit来获取网络的数据更加简便，而且接口简单，可读性强，配合RxJava更是完美解决获取数据与更新UI的矛盾。这次实验遇到了不少bug，网址写不正确使我学习到了更多的Retrofit参数使用，更学会使用了postman先测试使用接口，再来编写代码。