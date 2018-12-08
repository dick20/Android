# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）

| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 16级  | 专业（方向） | 软件工程 |
| 学号 | 16340132 | 姓名 | 梁颖霖 |
| 电话 | 13680473185 | Email | 806179953@qq.com |
| 开始日期 | 11.23 | 完成日期 | 11.26

## 一、实验题目

**个人项目4  简单音乐播放器**

---

## 二、实现内容

### 实验目的
1. 学会使用MediaPlayer
2. 学会简单的多线程编程，使用Handler更新UI
3. 学会使用Service进行后台工作
4. 学会使用Service与Activity进行通信

---
### 实验内容
实现一个简单的播放器，要求功能有：  
<table>
    <tr>
        <td ><img src="https://gitee.com/code_sysu/PersonalProject4/raw/master/manual/images/fig1.jpg" >打开程序主页面</td>
        <td ><img src="https://gitee.com/code_sysu/PersonalProject4/raw/master/manual/images/fig2.jpg" >开始播放</td>
    </tr>
    <tr>
        <td ><img src="https://gitee.com/code_sysu/PersonalProject4/raw/master/manual/images/fig3.jpg" >暂停</td>
        <td ><img src="https://gitee.com/code_sysu/PersonalProject4/raw/master/manual/images/fig1.jpg" >停止</td>
    </tr>
</table>

1. 播放、暂停、停止、退出功能，按停止键会重置封面转角，进度条和播放按钮；按退出键将停止播放并退出程序
2. 后台播放功能，按手机的返回键和home键都不会停止播放，而是转入后台进行播放
3. 进度条显示播放进度、拖动进度条改变进度功能
4. 播放时图片旋转，显示当前播放时间功能，圆形图片的实现使用的是一个开源控件CircleImageView


**附加内容（加分项，加分项每项占10分）**

1.选歌

用户可以点击选歌按钮自己选择歌曲进行播放，要求换歌后不仅能正常实现上述的全部功能，还要求选歌成功后不自动播放，重置播放按钮，重置进度条，重置歌曲封面转动角度，最重要的一点：需要解析mp3文件，并更新封面图片。

---

## 三、实验结果
### (1)实验截图

1.打开程序主页面

![1](https://gitee.com/dic0k/PersonalProject4/raw/master/report/Wednesday/16340132LiangYingLin/image/1.png)

2.开始播放

![2](https://gitee.com/dic0k/PersonalProject4/raw/master/report/Wednesday/16340132LiangYingLin/image/2.png)

3.暂停

![3](https://gitee.com/dic0k/PersonalProject4/raw/master/report/Wednesday/16340132LiangYingLin/image/3.png)

4.停止

![4](https://gitee.com/dic0k/PersonalProject4/raw/master/report/Wednesday/16340132LiangYingLin/image/4.png)

5.打开选歌

![5](https://gitee.com/dic0k/PersonalProject4/raw/master/report/Wednesday/16340132LiangYingLin/image/5.png)

6.更换歌曲播放

![6](https://gitee.com/dic0k/PersonalProject4/raw/master/report/Wednesday/16340132LiangYingLin/image/6.png)

### (2)实验步骤以及关键代码

#### a.音乐播放页面的UI设计
这次应用重点在服务端以及多线程之间的内容，所以只有一个页面的设计。基本的要素包括一个circleImageView，用来作为歌曲的封面图，**使用前要先在依赖上引入包。**

其次TextView包括四个，分别是歌曲名字，歌手名字，歌曲当前时间，歌曲总长度。

这次使用seekBar来表示歌曲的进度，可以设置max，position等参数来控制滑条的长度

剩下的按钮我采取的是ImageButton来实现。

#### b.关于MediaPlayer的编写
这次的音乐播放器离不开MediaPlayer这一个类，它是由状态机来实现的，具有多个状态，必须在使用前切换到相应的状态使用。

音乐的播放应该放在服务部分，因为当应用返回的时候，还应该在后台播放着音乐。

首先创建mediaPlayer对象，设置相应的音乐路径，这个路径是我一开始加载进入手机里的，通过绝对路径读取即可。
```java
	@Override
    public void onCreate() {
        super.onCreate();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource("/data/data/com.example2.asus.musicplayer/cache/data/山高水长.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

当服务端被销毁的时候，该mediaPlayer也应该删掉，这里使用的是release函数。

```java
	@Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
```

其他的情况如下：包括暂停，开始，滑动seekbar时候歌曲的播放情况变化。这属于主页前端与服务的交互，这里使用的onTransact来进行信息的交互。

首先是播放暂停事件，根据mediaPlayer是否在播放的状态来决定
```java
		@Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 播放or暂停按钮，service
            if (code == 101) {
                Log.i("播放", "101");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                else {
                    mediaPlayer.start();
                }
            }
```
然后是停止事件，则先stop，然后滑条移动到0，在进入准备的状态。
```java     
            // 停止按钮,service
            else if (code == 102) {
                Log.i("停止", "102");
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.seekTo(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
```
按退出音乐的时候
```java
            // 退出按钮，service
            else if (code == 103) {
                Log.i("退出", "103");
                System.exit(0);
            }
```
拖动进度条的mediaPlayer要根据传入的参数来改变播放的进度。使用的函数是seekTo.
```java
            // 停止拖动进度条
            else if (code == 104) {
                data.setDataPosition(0);
                int process = data.readInt();
                Log.i("process_service", process + "");
                mediaPlayer.seekTo(process);
            }
```

上面是前端要求服务所提供的音乐控制功能，而服务也需要返回一些音乐播放的参数来帮助前端主页更改UI的界面，例如进度条的实时变化，获取歌曲的总长度。

```java
	//获取歌曲的状态
    public Boolean getIsPlaying() {
        return mediaPlayer.isPlaying();
    }
	//获取歌曲总长度
    public int getDuration() {
        return mediaPlayer.getDuration();
    }
	//获取歌曲的播放进度
    public int getPosition() {
        return mediaPlayer.getCurrentPosition();
    }
```


**后面将getservice()改进为通过onTransact的函数来执行activity与service的信息数据交互，详情见改进部分叙述**



当音乐播放完毕后，我们不能持续让UI的circleImage持续变化，或按键失效，于是需要重构  onCompletion来实现音乐播放完成的事件。

这里，我不对UI进行处理，而是告诉handler一个消息，让它来处理。

```java
	public void onComplete(final Handler handler) {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                handler.obtainMessage(102).sendToTarget();//传递给handler
            }
        });
    }
```

#### c. 利用服务Service的IBinder与主页构建链接
服务能与Activity交互，得益于IBinder，故在MyService中要实现MyBinder类。

```java
    public final IBinder binder = new MyBinder();
    
	public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
```
}
```

而在主页上，通过ServiceConnection来进行连接，闭关对其中的一些组件进行设置。intent的设置以及bindService的调用，则是开启了服务。
​```java
	sc = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName arg0, IBinder binder) {
                myBinder = (MyService.MyBinder)binder;
                service = ((MyService.MyBinder)binder).getService();
                seekBar.setMax( myBinder.getService().getDuration());
                endtime.setText(time.format(myBinder.getService().getDuration()));
                myBinder.getService().onComplete(handler);
            }
            @Override
            public void onServiceDisconnected(ComponentName arg0) {
            }
        };
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
```

#### d.对于相应的按钮进行设置监听器
1.播放暂停按键
这里主要是对circleImage来进行动画设置，这里利用了animator，设置它的转动周期，转动重复次数，设置角度后开启动画。并且改变按键上的图片。暂停的时候要保存转动的角度，方便下一次直接进行继续转动，而不是从头开始。

**myBinder.onTransact(101,Parcel.obtain(), Parcel.obtain(),0);** 这一句是与服务进行交互，利用101这一code通知服务打开或暂停音乐。

```java
		start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    myBinder.onTransact(101,Parcel.obtain(), Parcel.obtain(),0);
                } catch (RemoteException e){
                    e.printStackTrace();
                }
                // 开始事件
                if(!is_playing){
                    animator= ObjectAnimator.ofFloat(imageView,"rotation",degree,360+degree);
                    animator.setDuration(15000);
                    animator.setRepeatCount(-1);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.start();
                    start.setImageResource(R.drawable.pause);
                    is_playing = true;
                }
                // 暂停事件
                else{
                    degree=(Float) animator.getAnimatedValue();
                    animator.cancel();
                    start.setImageResource(R.drawable.play);
                    is_playing = false;
                }
            }
        });
```

2.停止键
停止按键事件包括对于动画的重置，设置图片，seekBar归零等等
```java
stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree=0;
                animator.cancel();
                animator=ObjectAnimator.ofFloat(imageView,"rotation",degree,360+degree);
                animator.start();
                animator.cancel();
                is_playing = false;
                starttime.setText("00:00");
                seekBar.setProgress(0);
                start.setImageResource(R.drawable.play);
                try{
                    myBinder.onTransact(102,Parcel.obtain(),Parcel.obtain(),0);
                } catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
```
3.退出键
退出比较简单，直接结束当前活动，并且调用unbindService将服务也解绑，这时候服务就会被销毁。
```java
	exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(sc);
                sc=null;
                MainActivity.this.finish();
                System.exit(0);
            }
        });
```
4.seekBar的拖动
seekBar的改变监听函数必须重构三个特定函数，这里我只需要用到第一个，将改变后的process值传入到服务中来处理即可。

**if(!fromUser) return;** 十分关键，判断是否是用户进行拖动，因为在音乐播放途中，seekBar也会改变，但是这不是人为拖动，如果这样还传递回服务处理，这样会导致程序卡死。
```java
seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(!fromUser) return;
                isProcessChange = true;
                Parcel parcel=Parcel.obtain();
                parcel.writeInt(progress);
                Log.i("process",progress+"");
                starttime.setText(time.format(progress));
                try {
                    myBinder.onTransact(104,parcel,Parcel.obtain(),0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isProcessChange = false;
            }
        });
```
5.file按键暂不处理，留待加分项处详述

#### e.线程与Handler的处理
线程必须sleep，避免执行过于频繁，由于改变UI只能在UI线程不然会出现报错，所以这里只是根据播放器的状态传递handler一个消息，这里的code为101，handler可以在UI线程来处理UI的变化。

```java
 private Thread thread = new Thread(){
        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(is_playing&&!isProcessChange){
                    Log.i("thread",is_playing+""+isProcessChange);
                    handler.obtainMessage(101).sendToTarget();
                }
            }
        }
    };
```

Handler与UI是同一线程，这里可以通过Handler更新UI上的组件状态，Handler有很多方法，这里使用比较简便的post和postDelayed方法。 使用Seekbar显示播放进度，设置当前值与最大值。与此同时，设置播放时间与mediaPlayer一致，显示正确。
```java
	final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            // 播放or暂停
            if(msg.what==101){
                Log.i("seekBar",myBinder.getService().getPosition()+"");
                seekBar.setProgress(myBinder.getService().getPosition());
                seekBar.setMax(myBinder.getService().getDuration());
                starttime.setText(time.format(myBinder.getService().getPosition()));
            }
        }
    };
```

**后面将handler改进为axjava的方式处理，详情见改进部分叙述**



### (3)实验遇到的困难以及解决思路

#### a.设置线程与handler后，无法更新UI界面
按照教程与课件对线程与handler进行编写后，但是无法修改到UI界面，并且应用出现卡死情况。首先，我利用log的信息来进行debug，发现并没有进入到handler的处理函数，但是线程的函数却是可以进入。这时发现，线程仅仅只是被新建new了，并没有被我开启。

所以必须在onCreate函数中开启这一线程，这样才能正确修改UI界面。

#### b.mediaPlayer屡次报错，getDuration执行错误
这一问题困扰了我很久，一开始我在实验的时候是没有问题，但增加了file功能后，原来就会出现报错。当我还怀疑是新功能影响到了前面的create，但是在我查看服务的生命周期，发现它的确是进入了create函数，再加载出file，故不可能存在后者影响前者的情况。

于是，我又对mediaPlayer的各个状态切换进行查看，阅读Android的文档，并搜索报错信息相关的内容，都指出我的mediaPlayer是处于idle的状态，没有prepare，但是我确实编写了prepare。我陷入了死循环，这时我再次阅读繁琐的报错信息发现，原来是setDataSource的错误，导致mediaPlayer错误。我查看手机的储存，的确发现少了这个音乐的储存，可能是因为我重新卸载并安装程序来测试新功能的时候，将这首歌的储存也移除了，没有留意到。

---

## 四、实验思考及感想
### a.加分项
1.实现通过外部得到音乐的路径
参考链接:[从外部读入音乐文件](https://blog.csdn.net/qq_38552744/article/details/78713381)
这里由于代码众多就不全部放出。这里只针对onActivityResult来进行讨论。我通过得到的这个文件，返回该文件的路径存到了path里面，要将该path传入服务，必须利用parcel.writeString(path);

然后，重新加载图片的旋转动画，重置开始按钮等，进入初始的播放状态。
```java
// 读取外部音乐
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();
                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                Toast.makeText(MainActivity.this, path+"222222", Toast.LENGTH_SHORT).show();
            }
            if(path!=null){
                Parcel parcel=Parcel.obtain();
                parcel.writeString(path);
                Log.i("customer",path);
                try {
                    myBinder.onTransact(105,parcel,Parcel.obtain(),0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                degree=0;
                animator.cancel();
                animator=ObjectAnimator.ofFloat(imageView,"rotation",degree,360+degree);
                animator.start();
                animator.cancel();
                is_playing = false;
                starttime.setText("00:00");
                seekBar.setProgress(0);
                start.setImageResource(R.drawable.play);
                // 根据MP3改变相应的UI
                setSongDetail(path);
            }
        }
    }
```


这里将得到的音乐路径传入service，service来对该路径进行处理。首先释放mediaPlayer，然后再重新加载该音乐的路径，保持就绪的状态，随时准备开始播放。
```java
            // 外部加载音乐
            else if (code == 105) {
                data.setDataPosition(0);
                String path = data.readString();
                if (path!=null) {
                    Log.i("process_service", path+"");
                    try {
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (code==106){
                mediaPlayer.pause();
            }
        }
    }
```

2.解析音乐，获得音乐的名字，歌手，专辑图
参考链接：[解析MP3](https://www.jianshu.com/p/e38178f008ab)

这里要用到MediaMetadataRetriever类来获取MP3中的信息。分别得到歌曲的名字，歌手，专辑图，设置相应的UI组件即可。最后记得释放MediaMetadataRetriever。
```java
private void setSongDetail(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String song_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String singer_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
		 // 改变专辑图
        byte[] data = mmr.getEmbeddedPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        imageView.setImageBitmap(bitmap);
        
        name.setText(song_name);
        singer.setText(singer_name);
        
        mmr.release();
    }
```

3.添加功能，切换音乐的时候暂停，切换成功的时候重置
file按键的监听函数，当点击的时候，当前播放歌曲暂停，保存状态。如果加载新路径失败则该状态保留，若加载成功，则进入新歌曲的播放状态.
**注意：读取音乐必须开启用户的权限。在manifest上加入权限的声明。**
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

```java
 file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animator.getAnimatedValue() != null){
                    degree=(Float) animator.getAnimatedValue();
                    animator.cancel();
                }
                start.setImageResource(R.drawable.play);
                is_playing = false;
                try {
                    myBinder.onTransact(106,Parcel.obtain(),Parcel.obtain(),0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*"); //选择音频
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });
```

### b.感想
这次实验使用到了多线程以及服务这两个全新的内容，这是我之前从来没有接触的java范畴，所以上手起来还是比较难的。我起初先对课件的例子进行了测试理解，才开始了这次的作业。对于音乐的处理mediaPlayer也理解了一点，对于不同状态的转换，如何利用该组件进行多媒体的播放。

而Service与Activity的交互，利用了IBinder，通过传递的code来决定交互的功能，而之间的内容传输则需要parcel的加入，这个的使用我也查阅了相当多的资料，一开始单纯的write，read读出来的全部是null值，后来根据网上博客的指点才能正确的使用。

线程之间的交互则要用到handler，同样是利用code来处理不同功能，注意只能在UI线程处理UI的动态变化，而其他耗时的操作可以在其他线程进行处理。通过这次实验，对于java的多线程有了更深刻的理解与认识。

---



## 改进音乐播放器（Week 14）

### 实验目的

1. 学习rxJava，使用rxJava更新UI
2. 通过Binder来保持Activity和Service的通信，而不是直接返回service.this

### 实验结果

#### 1.使用Transact函数来更换直接获取函数

之前直接通过getService()函数来获取Service对象是不规范的做法，而应该用Transact与OnTransact来进行通信，交换信息。

```java
 		MyService getService() {
            return MyService.this;
        }
```



根据之前的代码，这里新增了两个OnTransact的code操作。这样子通过reply这一Parcel来传递服务想要传递的音乐播放进度，而不需要在activity之中调用service的函数。

```java
			// 获取歌曲的总长度
            else if (code==201){
                int dur = mediaPlayer.getDuration();
                Log.i("歌曲总长度2",dur+"");
                reply.writeInt(dur);
            }
            // 获取歌曲当前的播放位置
            else if (code==202){
                int pos = mediaPlayer.getCurrentPosition();
                reply.writeInt(pos);
            }
```



在activity的调用则改为读取Parcel的数据来改变UI界面。

```java
				// 获取歌曲的总长度
                Parcel reply = Parcel.obtain();
                try {
                    myBinder.onTransact(201,Parcel.obtain(),reply,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                reply.setDataPosition(0);
                int dur = reply.readInt();
                seekBar.setMax(dur);
                endtime.setText(time.format(dur));
                Log.i("歌曲总长度",dur+"");
				
			//  以下是之前错误的作法，已改变为通过onTransact通信
			//  seekBar.setMax( myBinder.getService().getDuration());
            //  endtime.setText(time.format(myBinder.getService().getDuration()));
```



#### 2.使用动态广播来解决服务器主动通知客户端问题

对于歌曲播放完毕的监听器设置，由于我不能在service直接更改UI，所以之前我是通过service的函数来直接把handler传入service。

现在没有getService这一函数，自然也要修改歌曲播放完毕监听的逻辑，这里我利用到之前学到过的动态广播来解决这一问题。当service的歌曲播放完毕，并且监听到这一消息后发送一个动态广播到activity，又UI线程来改变这一界面的变化。

注册广播，注销广播，发送广播过程不再叙述，主要展示这个广播所需要做的动作。

```java
public class DynamicReceiver extends BroadcastReceiver {
        private static final String DYNAMICACTION = "com.example2.asus.musicplayer";
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DYNAMICACTION)) {    //动作检测
                Bundle bundle = intent.getExtras();
                int num = bundle.getInt("complete");
                if(num == 1){
                    // 停止图片的旋转，恢复开始按钮
                    degree=0;
                    animator.cancel();
           			animator=ObjectAnimator.ofFloat
                        (imageView,"rotation",degree,360+degree);
                    animator.start();
                    animator.cancel();
                    seekBar.setProgress(0);
                    starttime.setText("00:00");
                    start.setImageResource(R.drawable.play);
                    is_playing=false;
                }
            }
        }
    }
```



#### 3.修改handler为rxJava

我的handler函数主要处理的是获取service中的歌曲播放情况动态改变seekBar的进度与播放时间进度。所以修改为rxjava，仅仅需要实现这一功能。主要的逻辑是**在Observable对象中查询歌曲的播放时间，用onNext方法传递给Observer。Observer对象观察到Observable发送的播放时间后，完成UI的更新。**



首先，调用create函数创建Observable对象，在里面需要重构call函数，并通过onTransact函数来向service获取歌曲播放的时间。

```java
Observable operationObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
        @Override
        public void call(Subscriber<? super Integer> subscriber) {
            Parcel reply2 = Parcel.obtain();
            try {
                myBinder.onTransact(202,Parcel.obtain(),reply2,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            reply2.setDataPosition(0);
            int pos = reply2.readInt();
			// pos为歌曲的播放时间，传递到observer
            subscriber.onNext(pos);
            subscriber.onCompleted();
        }
    });
```

然后，在我之前新建的线程中将给handler传递消息变化为，为Observable处理订阅事件，重构onNext函数，在这个函数中处理seekBar的进度与Starttime的时间展示。

```java
private Thread thread = new Thread(){
        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(is_playing&&!isProcessChange){
                    // 这是之前处理handler消息的函数
                    // handler.obtainMessage(101).sendToTarget();
                    
                    operationObservable.subscribe(new Observer<Integer>() {
                        @Override
                        public void onNext(Integer pos) {
                            Log.i("播放位置",pos+"");
                            seekBar.setProgress(pos);
                            starttime.setText(time.format(pos));
                        }
                        
                        @Override
                        public void onCompleted() {}
                        
                        @Override
                        public void onError(Throwable e) {}
                    });
                }
            }
        }
    };
```



修改完成后，应用程序能正常运行，截图与第十三周实验结果一致，这里就不再重复放置。我对于音乐播放器的改进过程到此结束。



### 感想

之前对于service与activity的交互信息不太清楚，随便定义函数，并通过getservice获取service的数据到activity中处理，这样的做法是不规范的。我们应该采用transact与onTransact的方法来进行交互，将要传递的信息放在Parcel中，这样交互更加安全，符合规范。除此之外，我还学习到了别的交互方式，例如这次我就**使用了动态广播来处理一些Parcel无法处理的逻辑问题**，利用之前的知识来学以致用，感觉很棒。

最后，关于多线程编程的rxjava，一开始接触这些概念是有点不解，但在了解其中的奥秘与看了许多样例，对于观察者与订阅者的处理模式也有了一点理解，这样的处理方式比其handler更加直观，不需要传递一个特定的code，当需要多种情况处理的时候，使用code的方式会显得混乱。rxjava可以处理多种订阅方式，可以传递的参数也是没有限制。