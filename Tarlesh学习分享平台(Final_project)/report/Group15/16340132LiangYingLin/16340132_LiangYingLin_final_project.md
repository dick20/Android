# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 16 | 专业（方向） | 软件工程（计算机应用） |
| 学号 | 16340132 | 姓名 | 梁颖霖 |
| 电话 | 13680473185 | Email | dic0k@qq.com |
| 开始日期 | 2019/12/25 | 完成日期 |2019/01/19|

---

## 一、实验题目

**期末项目——Tarlesh学习分享平台**

---

## 二、实现内容

**个人贡献**

1. 完成底部导航栏的设置

2. 文件上传核心功能（包括选中文件，上传到服务器）
3. 排行榜实现
4. 随机推荐功能
5. 打开APP页面的广告跳转
6. 小组PPT与演示视频展示
7. 完成需求分析与设计文档

---

## 三、实验结果
### (1)实验截图

1. 打开APP页面的广告跳转

![1](https://gitee.com/group_15/final_project/raw/master/report/Group15/16340132LiangYingLin/images/1.png)

2. APP主页展示，底部是导航栏

![2](https://gitee.com/group_15/final_project/raw/master/report/Group15/16340132LiangYingLin/images/2.png)



3. 上传文件

   ![3](https://gitee.com/group_15/final_project/raw/master/report/Group15/16340132LiangYingLin/images/3.png)



![4](https://gitee.com/group_15/final_project/raw/master/report/Group15/16340132LiangYingLin/images/4.png)

![5](https://gitee.com/group_15/final_project/raw/master/report/Group15/16340132LiangYingLin/images/5.png)

4. 排行榜——按时间排序

![6](https://gitee.com/group_15/final_project/raw/master/report/Group15/16340132LiangYingLin/images/6.png)



5. 排行榜——按下载次数排序

![7](https://gitee.com/group_15/final_project/raw/master/report/Group15/16340132LiangYingLin/images/7.png)

6. 推荐功能

![8](https://gitee.com/group_15/final_project/raw/master/report/Group15/16340132LiangYingLin/images/8.png)

### (2)实验步骤以及关键代码

#### a.设置广告页面

这里是模拟应用市场大多数应用的广告跳转页面，点击右上角的跳过可以直接进入应用，否则倒计时三秒后进入应用。

这里利用的是Handler，每隔一秒发送消息，如果三秒后则直接进入。

```java
tv= (Button) findViewById(R.id.Welcome_tv);
//每隔一秒发送消息
handler.sendMessageDelayed(handler.obtainMessage(1), 1000);

//延迟3秒后进入主界面
handler.postDelayed(new Runnable() {
    @Override
    public void run() {
        //执行在主线程
        //启动页面
        startMainActivity();
    }
},3000);

tv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //启动页面
        startMainActivity();
    }
});
}
```

Handler则是用于修改页面的UI，提示用户剩余多少秒进入

```java
@SuppressLint("HandlerLeak")
final Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case 1:
                time--;
                Log.e("TAG",time+"");
                if (time>0){
                    tv.setText("跳过 "+time+"s");
                    handler.sendMessageDelayed
                        (handler.obtainMessage(1),1000);
                }
                break;
        }
        super.handleMessage(msg);
    }
};
```



#### b.设置底部导航栏

我们应用是通过点击底部导航栏来切换活动的，所以要先构建好这个导航栏。

导航栏是利用Android Studio自带的导航功能，我上网搜索一些适合的icon进行修改，主要添加了跳转逻辑以及将活动生命周期进行调整。

```java
private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // 由于就在home页面，故不需要做跳转
                    return true;
                case R.id.navigation_ranking:
                    // 跳转至ranking页面
                    startRankingActivity();
                    return true;
                case R.id.navigation_quiz:
                    // 跳转至quiz页面
                    startQuizActivity();
                    return true;
            }
            return false;
        }
    };

private void startRankingActivity() {
    startActivity(new Intent(HomeActivity.this, RankingActivity.class));
    //关闭当前页面
    finish();
}
```

在每次点击后，要跳转到不同页面，但是要把当前页面关闭，否则会导致应用崩溃。

而页面的设计，简单修改文字以及icon即可。

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/navigation_home"
        android:icon="@drawable/ic_home_black_24dp"
        android:title="@string/title_home" />

    <item
        android:id="@+id/navigation_ranking"
        android:icon="@drawable/ranking"
        android:title="Ranking" />

    <item
        android:id="@+id/navigation_quiz"
        android:icon="@drawable/quiz"
        android:title="Quiz" />

</menu>
```



#### c.设置主页推荐，主页搜索

这两个都是使用同一个API，查询文件。

```java
@POST("/api/file/fileList")
    Observable<ResponseBody> getFileList(@Header("x-access-token") String token, @Body RequestBody requestBody);
```

其中requestBody有两个参数

+ filename，可选，搜索内容或搜索关键字
+ rankType，可选，返回结果的排序情况，是按照时间排序，评分排序还是下载量排序。



搜索文件使用该api，并将输入内容放入filename中，若搜索成功则跳转到该文件的详情页面。

否则弹出Toast找不到该文件。

每日推荐功能，则随机返回三种排序中第一的文件，filename不填，rankType随机数即可。



这里我使用的是Retrofit2+Rxjava的异步操作进行网络API访问。**每次访问都需要用户的token,该token是登陆后保存下来的**

```java
// 搜索文件功能
private void search_file_by_filename(String filename){
    OkHttpClient build = new OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://139.199.166.124:8088") // 设置网络请求的Url地址
        .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
        .client(build)
        .build();
    // 生成请求的Body
    org.json.JSONObject root = new org.json.JSONObject();
    try {
        root.put("filename", filename);
        root.put("rankType", 0);
    }
    catch (Exception ex) {
        ex.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

    ApiService service = retrofit.create(ApiService.class);
    String token = CommonSPUtil.getInstance(this).getToken();
    Observable<ResponseBody> repoObservable = service.getFileList(token,requestBody);

    repoObservable
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("sss",e.getMessage());
                Toast.makeText(HomeActivity.this,"搜索失败",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                // 跳转到详情页面
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if(responseBody.getData().isEmpty()){
                    Toast.makeText(HomeActivity.this,"未找到该文件",Toast.LENGTH_SHORT).show();
                    return;
                }
                bundle.putString("fid",responseBody.getData().get(0).getFid());
                bundle.putString("starCount",responseBody.getData().get(0).getStar_count());
                bundle.putString("fileName",responseBody.getData().get(0).getFilename());
                bundle.putString("createTime",responseBody.getData().get(0).getCreateTime());
                bundle.putString("description",responseBody.getData().get(0).getDescription());
                bundle.putString("downloadCount",responseBody.getData().get(0).getDownload_count());
                bundle.putString("owner",responseBody.getData().get(0).getOwner());
                intent.putExtras(bundle);
                intent.setClass(HomeActivity.this,FileDetail.class);
                startActivity(intent);
            }
        });
}
```



#### d.实现核心文件选择上传功能

选择文件部分，选择文件是利用intent的功能，然后开启该活动。类似之前做过的加载用户头像功能。

```java
imageView = findViewById(R.id.image);
imageView.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("InlinedApi")
    @Override
    public void onClick(View v) {
        // 选择文件
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/*");
        startActivityForResult(intent, 0);
    }
});
```

根据上述获得的文件，获取其中的uri。

然后弹出一个输入框，输入框填入文件的简述。该输入框使用的是github的一个开源Dialog，更加美观。

点击确认按钮则正式开始传送。

```java
// 获取文件
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data != null) {
        // 得到文件的全路径
        uri = data.getData();
        System.out.println(uri);
        java.io.File file = uri2File(uri);
        System.out.println(file.getAbsoluteFile());
        // 弹出输入框
        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(this).builder()
            .setTitle("请输入描述")
            .setEditText("");
        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(myAlertInputDialog.getResult());
                upload_file(file, String.valueOf(myAlertInputDialog.getResult()));
                Toast.makeText(HomeActivity.this, "开始上传",Toast.LENGTH_SHORT).show();
                myAlertInputDialog.dismiss();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "取消上传",Toast.LENGTH_SHORT).show();
                myAlertInputDialog.dismiss();
            }
        });
        myAlertInputDialog.show();
    }
    super.onActivityResult(requestCode, resultCode, data);
}
```



调用api上传文件部分，这里用到Multipart来传送文件。

```java
@Multipart
@POST("/api/file/upload")
Observable<UploadResponseBody> uploadFile
        (@Header("x-access-token") String token,
         @Part("description") RequestBody description,
         @Part MultipartBody.Part file);
```



Retrofit2+Rxjava调用api部分

```java
// 上传文件
private void upload_file(java.io.File file, String description){
    OkHttpClient build = new OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://139.199.166.124:8088") // 设置网络请求的Url地址
        .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
        .client(build)
        .build();

    ApiService service = retrofit.create(ApiService.class);
    String token = CommonSPUtil.getInstance(HomeActivity.this).getToken();
    Map<String, RequestBody> paramMap = new HashMap<>();
    RequestBody requestFile =
        RequestBody.create(MediaType.parse("multipart/form-data"), file);
    // MultipartBody.Part  和后端约定好Key，这里的partName是用image
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    // 添加描述
    RequestBody description2 =
        RequestBody.create(MediaType.parse("multipart/form-data"), description);

    Observable<UploadResponseBody> uploadResponseBodyObservable =
        service.uploadFile(token,description2,body);

    uploadResponseBodyObservable
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<UploadResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("sss",e.getMessage());
                Toast.makeText(HomeActivity.this,"上传文件失败",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onNext(UploadResponseBody responseBody) {
                Log.i("list", responseBody.sign + " " + responseBody.msg);
                Toast.makeText(HomeActivity.this,"上传文件成功",Toast.LENGTH_SHORT).show();
            }
        });
}
```



#### e.排行榜功能

排行榜功能主要利用的是RadioGroup的切换，通过这个参数的切换来传递不同的参数获取list。显示排行榜则是用RecyclerView，每一次获取后都使用notifyDataSetChanged。并且点击其中的item会显示文件的详情，预览情况，这里需要重载点击函数。

api的调用与上面类似，不再重述。这里说一下recyclerView的显示以及设置，重载函数进行跳转的情况。

```java
// 显示recyclerView
mFileList = new ArrayList<>();
recyclerView = findViewById(R.id.ranking_recyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(RankingActivity.this));
adapter = new RecommendAdapter(RankingActivity.this,R.layout.file_item,mFileList);
adapter.setOnItemClickListener(new RecommendAdapter.OnItemClickListener() {
    @Override
    public void onClick(int position) {
        // 跳转到详情页面
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("fid",mFileList.get(position).getFid());
        bundle.putString("starCount",mFileList.get(position).getStar_count());
        bundle.putString("fileName",mFileList.get(position).getFilename());
        bundle.putString("createTime",mFileList.get(position).getCreateTime());
        bundle.putString("description",mFileList.get(position).getDescription());
        bundle.putString("downloadCount",mFileList.get(position).getDownload_count());
        bundle.putString("owner",mFileList.get(position).getOwner());
        intent.putExtras(bundle);
        intent.setClass(RankingActivity.this,FileDetail.class);
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {
    }
});
recyclerView.setAdapter(adapter);
```



### (3)实验遇到的困难以及解决思路

#### a.文件uri转文件真实路径

根据之前作业的做法，选择文件后，我只能获得文件的uri。而我传递到api需要是一个java的File类，所以这里的难题在于如何通过uri转化成文件的真实路径。这里使用到了actualimagecursor来获取，该方法参考了网上的做法。

[Android Uri，Path与File、Bitmap的相互转换](https://blog.csdn.net/weixin_37577039/article/details/79242455)

[Android Uri获取真实路径以及文件名的方法](https://www.cnblogs.com/jooy/p/9134020.html)

解决这个问题尝试了很多种方法，也了解到了更多关于android访问文件的知识，关于文件路径之间转化的关系。

```java
private java.io.File uri2File(Uri uri) {
    String img_path;
    String[] proj = {MediaStore.Images.Media.DATA};
    Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
    if (actualimagecursor == null) {
        img_path = uri.getPath();
    }
    else {
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        img_path = actualimagecursor .getString(actual_image_column_index);
    }
    return new java.io.File(img_path);
}
```



#### b.如何使用Retrofit2上传文件

首先，我是先使用了postman测试了该api，传递的参数包括一个file类型一个text类型的描述。但是到了android里面并没有找到相应的方法。首先我尝试的还是使用requestbody简单的将file与描述封装成json传送到服务器，结果服务器无解析。然后，又试着使用@Field来解决这个问题，结果还是一样，无法解释传送的数据。

接着查询了许多资料，如何使用retrofit上传，功夫不负有心人。这里使用的是其中的一个方法，利用@MultiPart来进行传输。将文件与描述分成不同的part，使用不同的码转换传输。

[Retrofit 2.0 超能实践（三），轻松实现多文件/图片上传/Json字符串/表单](https://www.jianshu.com/p/acfefb0a204f)

```java
// 创建 RequestBody，用于封装构建RequestBody
RequestBody requestFile =
    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part  和后端约定好Key，这里的partName是用image
MultipartBody.Part body =
    MultipartBody.Part.createFormData("image", file.getName(), requestFile);

// 添加描述
String descriptionString = "hello, 这是文件描述";
RequestBody description =
    RequestBody.create(
    MediaType.parse("multipart/form-data"), descriptionString);

// 执行请求
Call<ResponseBody> call = service.upload(description, body);
call.enqueue(new Callback<ResponseBody>() {
    @Override
    public void onResponse(Call<ResponseBody> call,
                           Response<ResponseBody> response) {
        Log.v("Upload", "success");
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("Upload error:", t.getMessage());
    }
});
}
```



#### c.关于调用api返回参数的问题

在使用一些api的时候，由于与后台沟通失误，有时返回的数据为数组，而我在接受的时候仅以变量接受，这样导致上传错误等一系列问题。经过查询很多网上博客都没有解决办法，最终是通过仔细查看postman返回结果才找到问题所在。

这里我通过retrofit接受的返回值都是其api返回参数所一一对应的，这样才能保证传输的正确性。



---

## 四、实验思考及感想

### 个人总结、个人贡献评分、思想感悟

整个项目从构思到最后完工做下来，遇到了不少的困难，也修改了很多次方案，作为小组长工作量还是有点大的。关于功能方面，需要与负责后台的同学沟通api的传递方式。而自己也要实现android的两个页面，核心的文件上传功能等。不仅需要自己把工作做好，还需要协调组员之间的工作，向他们分配任务，解决提交的冲突等等。我们小组之间的合作也非常顺利高效，有问题都能在一定的时间内顺利解决，不会一直拖着。

最后在app收尾的阶段，我也作为这个应用的测试员，去测试每一个功能，告诉他们哪些地方需要再改进下，哪些功能有bug，UI还可以再优化下。最后给这款应用录制视频演示，完善PPT展示并准备演讲稿。

如果要给自己打分，我还是认为小组的每一个成员都应该共享这份功劳，平均分配，我应该也是占25%。应用还并不是很完善，需要改进的地方还是挺多的，接下来的寒假也会找机会继续完善一下。在这次的团队合作学到了如何与别的同学配合，互相体谅，一起解决问题，这比一个人埋头苦干更为重要！从期中项目走到期末项目，虽然安卓项目开发这门课程到一段落了，但是对于移动端开发的学习还是远远不够的，要学习的东西还是很多，因此日后仍需继续努力！

---

