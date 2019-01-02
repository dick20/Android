# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 |   任课老师   |         郑贵锋         |
| :------: | :--------------: | :----------: | :--------------------: |
|   年级   |        16        | 专业（方向） | 软件工程（计算机应用） |
|   学号   |     16340132     |     姓名     |         梁颖霖         |
|   电话   |   13680473185    |    Email     |      dic0k@qq.com      |
| 开始日期 |      12/24       |   完成日期   |          1/3           |

---

## 一、实验题目

### 第十六周任务

### 传感器

------

### 第十六周实验目的

1. 学会使用加速度传感器
2. 学会使用地磁传感器
3. 学会获取经纬度
4. 学习动画效果

---

## 二、实现内容

#### 实现一个简单的传感器应用

| ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img1.png)打开程序主页面 | ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img2.png)摇一摇晃动手机展示动画与震动与弹Toast提示并跳转。其中展示图片与动画属于加分项可不做 |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img3.png)页面2，展示指南针和经纬度，可以点击跳转返回上一界面 | ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img4.png)其中数值和图片需要动态变化，指南针的图片的指向需要大致正确 |

+ 该项目属于选作项目
+ 页面2的数值和图片需要动态变化
+ 整体验收流程如上，需要保证跳转→返回→再跳转后应用不会崩溃且显示正确（如经纬度朝向发生改变后再次跳转显示的是改变后的值）
+ 加分项为摇一摇动画效果，可不做，不做的主界面居中展示“摇一摇跳转”即可
+ 跳转的动画可以自由发挥，有能力增加音效的也可以加入

---

## 三、实验结果
### (1)实验截图

1. 手机初始页面显示

![1](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\sensor\1.png)



2. 摇动手机后，展示动画以及Toast，并实现了音效



![2](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\sensor\2.png)

3. 进入地图页面初始情况

![3](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\sensor\3.png)



4. 改变手机的位置，显示经纬度的实时改变

![4](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\sensor\4.png)



5. 改变手机的方向，改变图标以及旋转角度

![5](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\sensor\5.png)



### (2)实验步骤以及关键代码

#### a.设置传感器

先获取传感器的管理者，再通过这个管理者获取加速度传感器，这里直接用getDefaultSensor获取缺省即可，里面的参数表示传感器的类型是加速度传感器

```java
//获得传感器的管理器
sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//获得加速度传感器
sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

```

#### b.注册与注销监听器

这里在页面切换出去的时候，注销监听，减少内存的消耗，释放资源。当切换回来再重新注册这一个监听器，分别在onPause与onResume函数中实现。

```java
@Override
protected void onPause() {
    sensorManager.unregisterListener(sensorEventListener);
    super.onPause();
}

@Override
protected void onResume() {
    //参数一传感器监听  参数二:监听的传感器对象
    //注册摇一摇事件
sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    super.onResume();
}
```

#### c.设置事件监听器

这里重载onSensorChanged函数，当传感器接受到改变时就会触发这个函数。这里实现摇一摇的事件，不是一有轻微的变动就触发，而是需要超过一定的变化的范围。

这里通过事件的返回值来设置变化的范围，当x,y,z三轴的变化有一个大于15的时候，触发摇一摇事件，播放动画，产生震动，显示Toast等事件。

动画与音乐的部分待加分项处详述。

```java
/*传感器事件监听器*/
sensorEventListener  =  new SensorEventListener() {
    //当值发生改变的时候调用
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        //获取控件的值,设置触发条件
        float x = values[0];
        float y = values[1];
        float z = values[2];
        if(x > 15 || y > 15 || z > 15){//表示摇一摇
            if(flag) {//正在执行动画的同时不能再次触发
                //播放动画
                imageUp.startAnimation(upAnimationSet);
                imageDown.startAnimation(downAnimationSet);
                soundPool.play(soundId,1.0f,1.0f,1,1,1.0f);
                //震动
                //long[] pattern 1,第一次震动延迟的时间 2,第一次震动的持续时间
                //int repeat震动的重复次数 -1表示不重复
                // 仅一次震动，不延迟，震动3s即可。
                vibrator.vibrate(new long[]{0,300},-1);
                // 显示TOAST
                Toast.makeText(MainActivity.this,
                               "摇一摇",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
};
```

#### d.实现方向传感器，注册与注销监听器

与上面类似，先获取管理者，再获取传感器。这里需要地磁传感器和加速度传感器这两个。

```java
// 传感器管理者
sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
// 地磁传感器和加速度传感器
accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
```

注册与注销也是类似在onResume与onPause函数中实现

```java
@Override
protected void onResume() {
    sensorManager.registerListener(sensorEventListener, accelerometer, 
                                   SensorManager.SENSOR_DELAY_NORMAL);
    sensorManager.registerListener(sensorEventListener, magnetic, 
                                   SensorManager.SENSOR_DELAY_NORMAL);
    super.onResume();
}

@Override
protected void onPause() {
    sensorManager.unregisterListener(sensorEventListener);
    super.onPause();
}
```

#### e.设置事件的监听器

这里两个传感器但是共有同一个事件监听器，所有在触发onSensorChanged的时候要判断是哪一种传感器的事件。这里先**用if语句来判断类型**，然后将响应的values值clone过去，一定要使用clone函数来赋值，不然**accelerometerValues 和 magneticValues 将会指向同一个引用。**

接着就是将这些值用getOrientation来获取方向，然后将这个值转化为角度，改变TextView的相应值。

至于图片角度的改变这里需要用到动画RotateAnimation，**第一个参数表示开始旋转的角度，第二个参数表示到达的角度。后面四个参数是用来确定旋转中心。**

**setFillAfter表示动画结束，维持在最后的一帧**

```java
// 传感器事件监听器
sensorEventListener = new SensorEventListener() {
    //当值发生改变的时候调用
    float[] accelerometerValues = new float[3];
    float[] maneticValues = new float[3];
    private float lastDegree;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerometerValues = sensorEvent.values.clone();
        }
        else if (sensorEvent.sensor.getType() == 
                 Sensor.TYPE_MAGNETIC_FIELD){
            maneticValues = sensorEvent.values.clone();
        }
        float[] R = new float[9];
        float[] values = new float[3];
        SensorManager.getRotationMatrix(R,null,accelerometerValues,maneticValues);
        SensorManager.getOrientation(R,values);
        float rotateDegree = -(float) Math.toDegrees(values[0]);
        rotate.setText(Float.toString(rotateDegree));
        if (Math.abs(rotateDegree - lastDegree) > 1){
            RotateAnimation animation = new 
                RotateAnimation(lastDegree,rotateDegree, 
                                Animation.RELATIVE_TO_SELF,0.5f,
                                Animation.RELATIVE_TO_SELF,0.5f);
            animation.setFillAfter(true);
            arrow.startAnimation(animation);
            lastDegree = rotateDegree;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
};
```

#### f.位置的获取

先获取位置的管理者，获得LOCATION_SERVICE，然后我使用的是网络定位，这里要**注意权限的设置以及开启手机的GPS, 且允许该应用打开位置服务**

```java
// 位置管理者
locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
// 利用网络定位
final String provider = LocationManager.GPS_PROVIDER;
// 设置时间变化频率
// 产生位置改变事件的条件设定为距离改变10米，时间间隔为2秒
locationManager.requestLocationUpdates
        				(provider, 2000, 10, locationListener);
Location location = locationManager.getLastKnownLocation
        				(LocationManager.GPS_PROVIDER);
// 更新经纬度
updateLocation(location);
```

Manifest权限设置

```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.VIBRATE"/>

<uses-feature android:name="android.hardware.location.gps"/>
```

#### g.位置变化监听函数

这里仅仅需要在onLocationChanged变化的时候来更新位置即可，这里直接调用我之前设置的函数，更改相应位置的TextView.

```java
// 定义位置的监听函数
locationListener = new LocationListener() {
    @Override
    public void onLocationChanged(Location location) {
        // 更新位置
        updateLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
};
```



### (3)实验遇到的困难以及解决思路

#### a.监听器获取为null

我在获取管理者后，注册监听器，里面需要用到传感器，但是由于函数的先后顺序，没有进行赋值。故必须在注册监听器前给传感器与管理者赋值。

**逻辑顺序如下所示**

sensorManager -> sensor -> registerListener -> new SensorEventListener

```java
sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
```

#### b. 摇一摇后动画未结束，直接跳转

起初，我处理跳转事件是在传感器的监听函数中处理的，当传感器收到信号就进行跳转事件，这样**导致动画仍未播放完毕。**

正确的处理是在动画AnimationListener中的重构onAnimationEnd来处理跳转的逻辑。

```java
@Override
public void onAnimationEnd(Animation animation) {
    flag = true;
    // 设置跳转
    Intent intent = new Intent();
    intent.setClass(MainActivity.this,MapActivity.class);
    startActivity(intent);
}
```

#### c.跳转后摇一摇页面的传感器仍工作

起初我在onDestroy函数中来注销传感器监听器，却忽略了页面跳转后，起初的页面仍存在活动栈中仍未被destroy，故该传感器的监听函数一直在工作，导致出现我在第二个页面摇动手机也会触发事件。

解决方法：分析活动的进程，**在onPause函数中来注销，在onResume函数中来注册**

```java
@Override
protected void onPause() {
    sensorManager.unregisterListener(sensorEventListener);
    super.onPause();
}
```

---



## 四、实验思考及感想

### 1.加分项

#### a. 摇一摇产生动画

这里用到了AnimationSet，TranslateAnimation两个类。

然后分别定义**上部分图片的向上移动以及向下移动，下部分图片的向下移动以及向上移动**这四个动画效果，注意设置顺序与设置动画时间，以及设置动画的延迟时间。

```java
//初始化动画()  两个图片同时进行不能共用,
//图片最终需要回到原点,因此使用补间动画
//上面图片动画集合
// true 表示 几个动画共用一个插值器
upAnimationSet = new AnimationSet(true);
//上面图片动画
//1.先上移
TranslateAnimation upUptranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                                                                   Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.5f);
//设置时间
upUptranslateAnimation.setDuration(500);
//upUptranslateAnimation.setDuration(0);
//1.后下移
TranslateAnimation upDowntranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                                                                     Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0);

upDowntranslateAnimation.setDuration(500);
//upDowntranslateAnimation.setDuration(0);
//设置启动延迟,300ms后开始启动
upDowntranslateAnimation.setStartOffset(300);
upAnimationSet.addAnimation(upUptranslateAnimation);
upAnimationSet.addAnimation(upDowntranslateAnimation);
upAnimationSet.setDuration(1200);
upAnimationSet.setStartOffset(200);

downAnimationSet = new AnimationSet(true);
//下面图片的动画
//1.先上移
TranslateAnimation downUptranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                                                                     Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
downUptranslateAnimation.setDuration(500);
downUptranslateAnimation.setStartOffset(300);
//1.后下移
TranslateAnimation downDowntranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                                                                       Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
downDowntranslateAnimation.setDuration(500);
downAnimationSet.addAnimation(downUptranslateAnimation);
downAnimationSet.addAnimation(downDowntranslateAnimation);
downAnimationSet.setDuration(1200);
downAnimationSet.setStartOffset(200);
```

动画的播放，两张图片imageView分别利用startAnimation函数执行对应的动画集即可

```java
if(flag) {//正在执行动画的同时不能再次触发
    //播放动画
    imageUp.startAnimation(upAnimationSet);
    imageDown.startAnimation(downAnimationSet);
    ···
}
```



#### b.摇一摇产生音乐效果

这里没有用到我们之前学过的MediaPlayer，而是采用soundPool，soundPool的主要的优点是开销少，反应迅速，一般用于手机的一些短音效。

这里先new一个soundpool然后运用load函数来加载音乐，这里的音乐我存放在raw文件夹中，后面的1表示该音乐只播放一次。

而播放就使用play函数，将这个音乐id资源播放，第二个参数与第三个参数表示左右声道，**这里需要设成1.0f，不然在某些机型无法实现播放**，第四个参数表示优先级，第五个参数表示循环播放次数，第六个参数表示播放的速度

```java
//int maxStreams参数一:表示音乐池数量
//int streamType 参数二:类型
// int srcQuality参数三:资源的质量
soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
//将音乐加载到soundPool
soundId = soundPool.load(this, R.raw.dingdong, 1);

···
// 播放
soundPool.play(soundId,1.0f,1.0f,1,1,1.0f);
```

### 2.感想

虽然这次实验是选做，但是本人对安卓开发方向也颇有兴趣，且传感器方面的内容在日常生活也是经常用到，所以就完成这一次的实验。实验过程并不难，主要也只是注册传感器，判断传感器返回数据，监听事件处理等等。遇到的困难多在动画与音效方面，以及一些权限的设置。这次不仅学习到传感器的用法，还对平移动画，旋转动画等有了一定的了解，更加清楚的了解活动生命周期。





## 第十七周任务

## 一、实验题目

### 地图

### 第十七周实验目的

1. 接入百度地图API
2. 掌握少量的百度地图API接口

------

## 二、实现内容

#### 基于之前的应用，初始界面仍为摇一摇

| ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img5.png)跳转后的界面为百度地图 | ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img6.png)地图定位在目前的经纬度，需要可以动态改变 |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img7.png)界面中心为箭头，指向是目前的朝向，可以利用上周代码得到的朝向，也需要动态改变 | ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img8.png)左下角是一个按钮，当移动地图时需要变为空心，可以点击，点击后变为实心同时回到定位到目前位置 |
| ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img9.png)当拖动地图时，左下角会变为空心 | ![img](https://gitee.com/dic0k/PersonalProject6/raw/master/manual/images/img6.png)点击左下角按钮，回到目前位置 |

+ 该项目属于选作项目
+ 需要理解一定的百度地图API
+ 基础内容是显示地图，定位到目前位置，显示目前朝向共计三项，其中后两项可以利用之前的代码
+ 加分项即为左下角的按钮以及相应的事件监听处理。对流程还不清晰的可以查看demo
+ 需要的图片资源在manual里

------

## 三、课堂实验结果

### (1)实验截图

1.地图初始页面

![1](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\graph\1.png)

2.改变经纬度

![2](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\graph\2.png)

3.移动地图，左下方图标变化

![3](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\graph\3.png)

4.点击左下方图标，显示回原位置

![4](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\graph\4.png)

5.摇动手机，marker改变方向

![5](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\graph\5.png)



![6](C:\Users\asus\Desktop\PersonalProject6\report\Wednesday\16340132LiangYingLin\image\graph\6.png)



### (2)实验步骤以及关键代码

#### a.引入百度地图的资源包

网上教程一大把，这里也不再叙述。

**主要步骤**为

+ 下载开发包
+ 复制so文件至src/main/jniLibs目录
+ 复制jar包至libs目录
+ 增加sourceSets
+ 增加依赖
+ 添加密钥
+ 添加权限
+ 使用地图

#### b.添加地图到xml布局

这里的页面仅需要显示一个地图的组件加上自己的一张图片即可，后面通过点击这张图片来与地图进行交互。

```xml
	<com.baidu.mapapi.map.MapView
        android:id="@+id/bmapView"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:clickable="true" />

    <ImageButton
        android:id="@+id/imaage_button"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:scaleType="fitXY"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:src="@drawable/centerdirection"
        />
```

#### C.根据经纬度显示地图，并设置marker

这里的经纬度利用的是上周的LocationManager所获得的地址，但是**由于百度地图的api的地址计算并不与location的直接一致，故需要进行转换**，这一点在百度的api手册也有说明。以下是设置我的当前位置信息，需要设置角度，经度，纬度。

```java
//获取地图控件引用
BaiduMap mBaiduMap = mMapView.getMap();

mBaiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);
//普通地图
mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
mBaiduMap.setMyLocationEnabled(true);
// 转换坐标
CoordinateConverter converter = new CoordinateConverter();
converter.from(CoordinateConverter.CoordType.GPS);
converter.coord(new LatLng(loc.getLatitude(), loc.getLongitude()));
LatLng desLatLng = converter.convert();
// LatLng就是当前的坐标
degree = 90.0f;
mMapView.getMap().setMyLocationEnabled(true);
MyLocationData data = new MyLocationData.Builder()
    .latitude(desLatLng.latitude)
    .longitude(desLatLng.longitude)
    .direction(degree).build();
mMapView.getMap().setMyLocationData(data);
```

然后，设置marker的图片与位置

```java
Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pointer), 100, 100, true);
BitmapDescriptor bitmapD = BitmapDescriptorFactory.fromBitmap(bitmap);
MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapD);
mMapView.getMap().setMyLocationConfiguration(config);
```

**设置地图居中显示**

```java
// 设置地图居中
MapStatus mapStatus = new MapStatus.Builder().target(desLatLng).build();
MapStatusUpdate mapStatusUpdate = 	 
    MapStatusUpdateFactory.newMapStatus(mapStatus);
mMapView.getMap().setMapStatus(mapStatusUpdate);
```

这一点很关键，没有设置居中显示，则地图默认显示中心在首都，这一点会导致我的marker看不到，误以为程序出问题，且不符合要求。

#### d.判断手机转向时，改变marker的转向

这一点与上一周的指针旋转类似，传感器不变，但是需要做的是改变marker的角度，即重新设置direction。

```java
MyLocationData data = new MyLocationData.Builder()
                .latitude(desLatLng.latitude)
                .longitude(desLatLng.longitude)
                .direction(degree).build();
```

里面的degree就是我通过之前传感器获取的转向角度。

#### e.判断位置改变时，改变地图和marker所处位置

与改变角度一样，只不过这次改变的是经纬度。同样经纬度也是通过上周传感器的改变监听函数中获取即可。

```java
MyLocationData data = new MyLocationData.Builder()
                .latitude(desLatLng.latitude)
                .longitude(desLatLng.longitude)
                .direction(degree).build();
```

#### f.判断地图的改变状态，以此改变左下角的图片

这里利用的是百度地图的地图状态改变监听器。

我设置当状态改变完毕后，若当前状态与经纬度的状态不一致，则证明偏离了中心，所以改变图片的样式。同样，当点击图片，仅需返回到原来的状态即可，并将图片设置回实心。

```java
// 设置imageButton的点击函数
imageButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // 转换坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(new LatLng(loc.getLatitude(), loc.getLongitude()));
        LatLng desLatLng = converter.convert();
        // 设置地图居中
        MapStatus mapStatus = new MapStatus.Builder().target(desLatLng).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mMapView.getMap().setMapStatus(mapStatusUpdate);

        // 更改图片
        imageButton.setImageResource(R.drawable.centerdirection);
    }
});
```

以下是百度地图的状态监听器：

```java
// 地图改变的监听函数
onMapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
    /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
             * @param status 地图状态改变开始时的地图状态
             */
    public void onMapStatusChangeStart(MapStatus status){
    }

    /** 因某种操作导致地图状态开始改变。
             * @param status 地图状态改变开始时的地图状态
             * @param reason表示地图状态改变的原因，取值有：
             * 1：用户手势触发导致的地图状态改变,比如双击、拖拽、滑动底图
             * 2：SDK导致的地图状态改变, 比如点击缩放控件、指南针图标
             * 3：开发者调用,导致的地图状态改变
             */
    public void onMapStatusChangeStart(MapStatus status, int reason){
    }

    /**
         * 地图状态变化中
         * @param status 当前地图状态
         */
    public void onMapStatusChange(MapStatus status){
    }

    /**
         * 地图状态改变结束
         * @param status 地图状态改变结束后的地图状态
         */
    public void onMapStatusChangeFinish(MapStatus status){
        // 转换坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(new LatLng(loc.getLatitude(), loc.getLongitude()));
        LatLng desLatLng = converter.convert();
        // 判断两个状态是否一致
        MapStatus mapStatus = new MapStatus.Builder().target(desLatLng).build();
        Log.i("change","map has changed");
        if(!status.equals(mapStatus)){
            imageButton.setImageResource(R.drawable.definelocation);
        }
    }
};
```

在初始化地图的时候记得设置监听器即可。

```java
mBaiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);
```



### (3)实验遇到的困难以及解决思路

#### a.地图加载出来后，仅显示在首都，并未根据经纬度改变

这一点是由于我只设置了MyLocationData，并没有设置地图的Status导致的。地图的位置信息已经在经纬度了，但是并没有居中显示。

```java
// 设置地图居中
MapStatus mapStatus = new MapStatus.Builder().target(desLatLng).build();
MapStatusUpdate mapStatusUpdate = 
    MapStatusUpdateFactory.newMapStatus(mapStatus);
mMapView.getMap().setMapStatus(mapStatusUpdate);
```



------

## 四、实验思考及感想

这次的实验也是在之前传感器的基础上添加百度地图功能，传感器部分的功能可以继续使用，只是需要学习一些百度地图的SDK。个人感觉地图与传感器这两个功能在日常的android开发都是离不开的，而想用好别人的地图SDK就要先看懂[官方的文档](http://lbsyun.baidu.com/index.php?title=androidsdk/guide/interaction/event)，这一点很重要。我在做实验过程一遇到缺少的功能或者函数，一般官方文档上也会有教程或者方法提供。

最后一次安卓作业也做完了，虽然要求是选做，但是趁着自己有时间空余还是完成一下吧。安卓的学习并没有因此而终止，反而是另一种学习的开始，到此我已经基本掌握android的开发知识，要趁着寒假时间也加强一下自己的水平，期待在安卓开发能有更远的发展。Fighting!