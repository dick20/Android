# 中山大学数据科学与计算机学院本科生实验报告

## （2018年秋季学期）

| 课程名称 | 手机平台应用开发 |   任课老师   |         郑贵锋          |
| :------: | :--------------: | :----------: | :---------------------: |
|   年级   |       2016       | 专业（方向） | 软件工程（计算机应用）  |
|   学号   |     16340133     |     姓名     |         梁育诚          |
|   电话   |   18565043306    |    Email     | jacky14.liang@gmail.com |
| 开始日期 |    2018.12.28    |   完成日期   |        2019.1.19        |

------

## 一、实验题目

期末项目——Tarlesh学习资源分享平台

## 二、负责内容

&emsp;&emsp;我负责的是文件详情页面的实现以及问答页面的实现，以及UI设计和编写小组报告。

&emsp;&emsp;文件详情页面包括`FileDetail.java`和`file_detail.xml`，还有`assets`文件夹的内容，问答页面包括`QuizActivity.java`和`quiz_activity.xml`。

&emsp;&emsp;文件详情页面向用户展示文件的详细信息，提供给用户评分、下载、预览等功能。

&emsp;&emsp;问答页面提供给用户发表问题、回答问题等功能。

## 三、实验过程

1. 先将我负责部分的所有API访问列出，在`ApiService.java`中。

   ```java
   	@POST("/api/qa/getQuestionList")
       Observable<QuizResponseBody> getQuizList(@Header("x-access-token") String token);
   
       @POST("/api/qa/createQuestion")
       Observable<QuizPostResponseBody> postQuiz(@Header("x-access-token") String token, @Body RequestBody requestBody);
   
       @POST("/api/file/download")
       Observable<FileDownloadResponse> downloadFile(@Header("x-access-token") String token, @Body RequestBody requestBody);
   
   	@POST("api/file/comment")
       Observable<CommentResponseBody> postComment(@Header("x-access-token") String token, @Body RequestBody requestBody);
   ```

2. 首先是文件详情页面的设计。总体布局是一个ConstrainLayout，文件详情页面的设计主要包含以下及部分：

   + 文件信息展示部分：该部分主要使用TextView显示文件信的基本信息
   + 用户评分部分：使用了RatingBar这个新控件，以及结合一个Button进行提交评分
   + 下载部分：提供一个Button作为下载功能。
   + 文件预览部分：使用了Webview这个新控件，配合html来预览显示。

   `file_detail.xml`页面设计：

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:layout_marginLeft="15dp"
       android:layout_marginRight="15dp"
       xmlns:app="http://schemas.android.com/apk/res-auto">
   
       <TextView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:id="@+id/fileName"
           android:text="文件名"
           android:textSize="20dp"
           android:textColor="@color/md_black_1000"
           app:layout_constraintTop_toTopOf="parent"
           app:layout_constraintLeft_toLeftOf="parent"
           android:layout_margin="10dp"/>
       <TextView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:id="@+id/description"
           android:text="介绍"
           android:textColor="@color/md_black_1000"
           app:layout_constraintTop_toBottomOf="@id/fileName"
           android:textSize="20dp"
           app:layout_constraintLeft_toLeftOf="parent"
           android:layout_margin="10dp"/>
       <TextView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:id="@+id/rating"
           android:text="评分"
           android:textColor="@color/dialog_black"
           app:layout_constraintTop_toBottomOf="@id/description"
           app:layout_constraintLeft_toLeftOf="parent"
           android:textSize="20dp"
           android:layout_margin="10dp"/>
       <TextView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:id="@+id/owner"
           android:text="创建人"
           android:textColor="@color/dialog_black"
           app:layout_constraintTop_toBottomOf="@id/rating"
           android:textSize="20dp"
           android:layout_margin="10dp"/>
       <TextView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:id="@+id/downloads"
           android:text="下载量"
           android:textColor="@color/md_black_1000"
           app:layout_constraintTop_toBottomOf="@id/owner"
           android:textSize="20dp"
           android:layout_margin="10dp"/>
       <View
           android:layout_width="0dp"
           android:layout_height="1dp"
           android:background="@color/greyLine"
           app:layout_constraintTop_toBottomOf="@id/downloads"
           android:layout_marginTop="15dp"/>
       <Button
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:id="@+id/downloadBtn"
           android:text="下载"
           android:textColor="@color/white"
           android:textSize="20sp"
           android:background="@drawable/mybutton"
           app:layout_constraintBottom_toBottomOf="parent"
           android:layout_margin="8dp"/>
       <RatingBar
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_marginTop="25dp"
           app:layout_constraintLeft_toLeftOf="parent"
           android:id="@+id/ratingBar"
           android:stepSize="1"
           app:layout_constraintTop_toBottomOf="@id/downloads"
           android:progressTint="@color/lightBlue"/>
       <WebView
           android:layout_width="match_parent"
           android:layout_height="0dp"
           app:layout_constraintBottom_toTopOf="@id/downloadBtn"
           android:id="@+id/webView"
           app:layout_constraintTop_toBottomOf="@id/ratingBar"
           android:layout_marginBottom="10dp">
       </WebView>
       <ProgressBar
           android:layout_width="match_parent"
           android:layout_height="80dp"
           android:id="@+id/progressBar"
           android:visibility="invisible"
           app:layout_constraintTop_toTopOf="@id/webView"
           app:layout_constraintBottom_toBottomOf="@id/webView"/>
       <Button
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:id="@+id/ratingBtn"
           android:text="提交"
           android:textSize="18sp"
           android:textColor="@color/white"
           android:background="@drawable/mybutton"
           app:layout_constraintLeft_toRightOf="@id/ratingBar"
           app:layout_constraintTop_toTopOf="@id/ratingBar"
           android:layout_marginLeft="25dp"/>
   </android.support.constraint.ConstraintLayout>
   ```

3. 渲染文件信息。为了减少网络请求带来的延迟影响，这里的文件数据是通过Intent从主页传过来，这里就可以减少不必要的网络请求，直接展示数据。特别注意，这里需要对数据是否为null进行判断。尤其是当评分为null时，我们默认这个文件的评分是“3/5"。

   `FileDetail.java`显示文件信息：

   ```java
   // 列表点击后传一个Intent
           Intent intent =  getIntent();
           Bundle bundle = intent.getExtras();
           fid = bundle.getString("fid");
           starCount = bundle.getString("starCount");
           filenameStr = bundle.getString("fileName");
           createTime = bundle.getString("createTime");
           descriptionStr = bundle.getString("description");
           downloadCount = bundle.getString("downloadCount");
           ownerStr = bundle.getString("owner");
   
           // 渲染当前文件数据
           filename.setText("文件名： " + filenameStr);
           description.setText("介绍： " + descriptionStr);
           if (starCount == null)
               rating.setText("评分：3/5");
           else
               rating.setText("评分：" + starCount + "/5");
           owner.setText("创建人： " + ownerStr);
           if (downloadCount == null)
               downloads.setText("下载量：0");
           else
               downloads.setText("下载量：" + downloadCount);
   ```

4. 评分功能。因为评分中数据库仅支持整数，因此需要设置stepSize为1。配合一个Button作为提交，在提交后将设置isIndicator为true，即用户不能修改。然后根据服务器返回的信息判断是否评分成功：

   `FileDetail.java`评分部分代码：

   ```java
   ratingBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int rate = (int) ratingBar.getRating();
                   System.out.println("Rate: " + rate);
                   // 提交评分
                   ratingBar.setIsIndicator(true);
   
                   OkHttpClient build = new OkHttpClient.Builder()
                           .connectTimeout(2, TimeUnit.SECONDS)
                           .readTimeout(2, TimeUnit.SECONDS)
                           .writeTimeout(2, TimeUnit.SECONDS)
                           .build();
   
                   // API Retrofit
                   Retrofit retrofit = new Retrofit.Builder()
                           .baseUrl("http://139.199.166.124:8088")
                           .addConverterFactory(GsonConverterFactory.create())
                           .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                           .client(build)
                           .build();
   
                   ApiService service = retrofit.create(ApiService.class);
                   // 生成请求的Body
                   JSONObject root = new JSONObject();
                   try {
                       root.put("fid", fid);
                       root.put("msg", "");
                       root.put("points", rate);
                   }
                   catch (Exception ex) {
                       ex.printStackTrace();
                   }
   
                   RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),root.toString());
                   String token = CommonSPUtil.getInstance(FileDetail.this).getToken();
                   Observable<CommentResponseBody> postCommentBody = service.postComment(token, requestBody);
   
                   postCommentBody.subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe(new Observer<CommentResponseBody>() {
                               @Override
                               public void onCompleted() { }
   
                               @Override
                               public void onError(Throwable e) {
                                   Toast.makeText(FileDetail.this, "评分提交失败",Toast.LENGTH_SHORT).show();
                                   e.printStackTrace();
                               }
   
                               @Override
                               public void onNext(CommentResponseBody commentResponseBody) {
                                   if (commentResponseBody.sign == false) {
                                       Toast.makeText(FileDetail.this, "您已经评过分了", Toast.LENGTH_SHORT).show();
                                       return;
                                   }
                                   List<Comment> list = commentResponseBody.getComments();
                                   double average_rate = 0;
                                   for (int i = 0; i < list.size(); i++) {
                                       average_rate += list.get(i).getPoints();
                                   }
                                   average_rate /= list.size();
                                   rating.setText("评分：" + String.valueOf(average_rate) + "/5");
                                   Toast.makeText(view.getContext(), "评分成功", Toast.LENGTH_SHORT).show();
                               }
                           });
               }
           });
   ```

5. 下载功能。首先定义一个类`FileDownloadResponse.java`来接受下载数据。为了便于数据库的存储，我们约定当下载时数据库返回的是Buffer类型。通过查阅官方文档，Buffer类型可以理解为bytes[]，这样我们就可以通过接受一个bytes[]的数据去获取这个文件。上网找了很多方法，最后确定使用OutputStream写入一个系统原声的File类型文件，然后将这个文件放在SD卡的download中。下载完后，判断文件类型，如果是pdf则支持在线预览，否则就不支持预览。（实际上在国外可以支持doc,ppt等文件的预览）。在下载开始前显示ProgressBar，下载完毕后隐藏，这样就有进度加载的用户提示。

   `FileDonwloadResponse.java`代码：

   ```java
   package group15.android.tarlesh;
   
   import java.nio.Buffer;
   
   public class FileDownloadResponse {
       boolean sign;
       String fname;
       DownloadFile file;
   
       class DownloadFile{
           String type;
           byte[] data;
           DownloadFile(String type, byte[] data) {
               this.type = type;
               this.data = data;
           }
   
           public byte[] getData() { return data; }
   
           public String getType() { return type; }
       }
   
       FileDownloadResponse(boolean sign, String type, DownloadFile file) {
           this.sign = sign;
           this.file = file;
       }
   
       public boolean isSign() { return sign; }
   
       public String getFname() { return fname; }
   
       public DownloadFile getFile() { return file; }
   }
   ```

   `FileDetail.java`下载功能代码：

   ```java
   // 下载按钮的事件函数
           downloadBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   OkHttpClient build = new OkHttpClient.Builder()
                           .connectTimeout(2, TimeUnit.SECONDS)
                           .readTimeout(2, TimeUnit.SECONDS)
                           .writeTimeout(2, TimeUnit.SECONDS)
                           .build();
   
                   // API Retrofit
                   Retrofit retrofit = new Retrofit.Builder()
                           .baseUrl("http://139.199.166.124:8088")
                           .addConverterFactory(GsonConverterFactory.create())
                           .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                           .client(build)
                           .build();
   
                   ApiService service = retrofit.create(ApiService.class);
                   // 生成请求的Body
                   JSONObject root = new JSONObject();
                   try {
                       root.put("fid", fid);
                   }
                   catch (Exception ex) {
                       ex.printStackTrace();
                   }
   
                   RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
                   String token = CommonSPUtil.getInstance(FileDetail.this).getToken();
                   Observable<FileDownloadResponse> fileDownlownResponseObservable = service.downloadFile(token, requestBody);
                   progressBar.setVisibility(View.VISIBLE);
                   fileDownlownResponseObservable.subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe(new Observer<FileDownloadResponse>() {
                               @Override
                               public void onCompleted() { }
   
                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   Toast.makeText(FileDetail.this, "下载文件失败", Toast.LENGTH_SHORT).show();
                               }
   
                               @Override
                               public void onNext(FileDownloadResponse fileDownlownResponse) {
                                   System.out.println("DownLoad Success");
                                   System.out.println("FileName:" + fileDownlownResponse.getFname());
                                   byte[] buffer = fileDownlownResponse.getFile().getData();
   
                                   // 保存文件到本地
                                   String fileName = fileDownlownResponse.getFname();
                                   String dir = Environment.getExternalStorageDirectory() + "/Download/";
                                   java.io.File data = new File(dir,fileName);
                                   try {
                                       OutputStream op = new FileOutputStream(data);
                                       op.write(buffer);
                                   }
                                   catch (Exception ex) {
                                       ex.printStackTrace();
                                   }
                                   // 预览
                                   String postfix = fileName.substring(fileName.length() - 3, fileName.length());
                                   System.out.println("Post:" + postfix);
                                   String pdfUrl = Environment.getExternalStorageDirectory() + "/Download/" + fileName;
                                   if (postfix.equals("pdf")) {
                                       mWebView.loadUrl("file:///android_asset/viewer.html?" + pdfUrl);
                                       progressBar.setVisibility(View.INVISIBLE);
                                   }
                                   else {
                                       mWebView.loadUrl("file:///android_asset/index.html");
                                       //mWebView.loadUrl("https://view.officeapps.live.com/op/view.aspx?src="+ "file:///android_asset/t.doc");
                                       progressBar.setVisibility(View.INVISIBLE);
                                   }
                               }
                           });
               }
           });
   ```

6. PDF页面在线预览。首先Android对于这个pdf预览时很不友好的，它不像IOS有直接提供预览的控件，因此就需要使用到别人的一些网络服务。在应用内显示网页可以使用Webview这个控件，用起来难度不大，但是需要设置很多网络属性，其中一个很关键的就是`setAllowFileAccessFromFileURLs(true)`，这个是支持跨域访问的设置，如果不设置就会一直报错。Google和微软的文档服务在国内基本都用不了，于是我打算使用Mozilla提供的pdf渲染服务。下载官方的pdfjs发现很大，如果直接放进应用里面会增加应用的大小。花了一点理解了代码，发现其实原理就是使用一个html文件来加载pdf.js。这样的话我自己直接写就更加简洁了。抛弃多余的网页元素，然后在html里面调用pdf.js即可。将一个`viewer.html`和一个`index.js`放入`assets`文件夹中（这个文件夹在AS中需要手动创建，放在`main/`中），最终实现了页面内的pdf预览！因为暂不支持其他类型的文件预览，因此还需要多写一个页面。

   `FileDetail.java`中的websetting：

   ```java
   // WebView设置
           WebSettings webSettings = mWebView.getSettings();
           webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
           webSettings.setLoadsImagesAutomatically(true);
           webSettings.setLoadWithOverviewMode(true);
           webSettings.setJavaScriptEnabled(true);
           webSettings.setUseWideViewPort(true);
           webSettings.setSupportZoom(false);
           webSettings.setBuiltInZoomControls(false);
           webSettings.setDomStorageEnabled(true);
           webSettings.setAllowFileAccessFromFileURLs(true);
   ```

   `viewer.html`代码：

   ```html
   <!DOCTYPE html>
   <html lang="en">
   <head>
       <meta charset="UTF-8">
       <meta name="viewport"
             content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
       <title>PDF Preview</title>
       <style type="text/css">
           canvas {
               width: 100%;
               height: 100%;
               border: 1px solid black;
           }
       </style>
       <script src="https://unpkg.com/pdfjs-dist@1.9.426/build/pdf.min.js"></script>
       <script type="text/javascript" src="index.js"></script>
   </head>
   <body>
   </body>
   </html>
   ```

   `index.js`代码：

   ```javascript
   var url = location.search.substring(1);
   
   PDFJS.cMapUrl = 'https://unpkg.com/pdfjs-dist@1.9.426/cmaps/';
   PDFJS.cMapPacked = true;
   
   var pdfDoc = null;
   
   function createPage() {
       var div = document.createElement("canvas");
       document.body.appendChild(div);
       return div;
   }
   
   function renderPage(num) {
       pdfDoc.getPage(num).then(function (page) {
           var viewport = page.getViewport(2.0);
           var canvas = createPage();
           var ctx = canvas.getContext('2d');
   
           canvas.height = viewport.height;
           canvas.width = viewport.width;
   
           page.render({
               canvasContext: ctx,
               viewport: viewport
           });
       });
   }
   
   PDFJS.getDocument(url).then(function (pdf) {
       pdfDoc = pdf;
       for (var i = 1; i <= pdfDoc.numPages; i++) {
           renderPage(i)
       }
   });
   ```

   `index.html`代码：

   ```html
   <!DOCTYPE html>
   <html>
   <head>
       <title></title>
   </head>
   <body>
       <span style="font-size: 50px">
           Sorry! This kind of type is no supported!
       </span>
       <br>
       <span style="font-size: 50px">
           预览类型不支持！
       </span>
   </body>
   </html>
   ```

7. 问答页面布局。问答页面的布局可以分为提交部分和显示部分。上方是提供给用户输入的部分，下方是一个RecyclerList搭配自定义的Adapter来显示问题列表，这种布局已经做过很多次了，做起来是比较熟练的。

   `activity_quiz.xml`代码：

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       xmlns:app="http://schemas.android.com/apk/res-auto">
       <TextView
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:id="@+id/quizTitle"
           android:text="Title"
           app:layout_constraintLeft_toLeftOf="parent"
           android:layout_marginLeft="8dp"
           android:textSize="20dp"
           app:layout_constraintTop_toTopOf="parent"
           android:layout_marginTop="15dp"/>
       <EditText
           android:layout_width="0dp"
           android:layout_height="wrap_content"
           android:id="@+id/titleInput"
           android:hint="Please Input Title"
           android:gravity="center"
           app:layout_constraintLeft_toLeftOf="@id/contentInput"
           app:layout_constraintRight_toRightOf="parent"
           app:layout_constraintTop_toTopOf="@id/quizTitle"
           android:layout_marginRight="5dp"
           android:theme="@style/MyEditText"/>
       <TextView
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:id="@+id/quizContent"
           android:text="Content"
           android:textSize="20dp"
           app:layout_constraintLeft_toLeftOf="parent"
           app:layout_constraintTop_toBottomOf="@id/quizTitle"
           android:layout_marginTop="20dp"
           android:layout_marginLeft="5dp"/>
       <EditText
           android:layout_width="0dp"
           android:layout_height="wrap_content"
           android:id="@+id/contentInput"
           android:hint="Please Input Content"
           android:gravity="center"
           app:layout_constraintLeft_toRightOf="@id/quizContent"
           app:layout_constraintRight_toRightOf="parent"
           app:layout_constraintTop_toTopOf="@id/quizContent"
           android:layout_marginLeft="10dp"
           android:layout_marginRight="5dp"
           android:theme="@style/MyEditText"/>
       <Button
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:id="@+id/submitBtn"
           android:text="Submit"
           android:textSize="17dp"
           android:textColor="@color/white"
           android:background="@drawable/mybutton"
           app:layout_constraintRight_toRightOf="parent"
           app:layout_constraintTop_toBottomOf="@id/contentInput"
           android:layout_marginTop="8dp"
           android:layout_marginRight="10dp"/>
       <View
           android:layout_width="match_parent"
           android:layout_height="1dp"
           app:layout_constraintTop_toBottomOf="@id/submitBtn"
           android:id="@+id/greyLine"
           android:background="@color/greyLine"
           android:layout_marginTop="20dp"
           android:layout_marginLeft="10dp"
           android:layout_marginRight="10dp"/>
       <android.support.v7.widget.RecyclerView
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:id="@+id/quizRecyclerView"
           app:layout_constraintTop_toBottomOf="@id/greyLine"
           app:layout_constraintBottom_toTopOf="@id/navigation3"
           android:layout_marginBottom="60dp"
           android:layout_marginTop="210dp">
       </android.support.v7.widget.RecyclerView>
   
       <android.support.design.widget.BottomNavigationView
           android:id="@+id/navigation3"
           android:layout_width="0dp"
           android:layout_height="wrap_content"
           android:layout_marginEnd="0dp"
           android:layout_marginStart="0dp"
           android:background="?android:attr/windowBackground"
           app:layout_constraintBottom_toBottomOf="parent"
           app:layout_constraintLeft_toLeftOf="parent"
           app:layout_constraintRight_toRightOf="parent"
           app:menu="@menu/navigation" />
   </android.support.constraint.ConstraintLayout>
   ```

   `Quiz.java`基类：

   ```java
   package group15.android.tarlesh;
   
   public class Quiz {
       private String qid;
       private String uname;
       private String title;
       private String content;
       private String createTime;
       private String answers_counts;
   
       public Quiz(String qid, String uname, String title, String content, String createTime, String answers_counts) {
           this.qid = qid;
           this.uname = uname;
           this.title = title;
           this.content = content;
           this.createTime = createTime;
           this.answers_counts = answers_counts;
       }
   
       public String getQid() { return qid; }
   
       public String getUname() { return uname; }
   
       public String getTitle() { return title; }
   
       public String getContent() { return content; }
   
       public String getCreateTime() { return createTime; }
   
       public String getAnswers_counts() { return answers_counts; }
   }
   ```

   `quiz_item.xml`布局代码：

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:layout_margin="10dp"
       xmlns:app="http://schemas.android.com/apk/res-auto">
       <android.support.v7.widget.CardView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           app:cardCornerRadius="8dp"
           android:layout_margin="10dp"
           app:contentPadding="8dp"
           app:cardBackgroundColor="@color/greyLine">
           <android.support.constraint.ConstraintLayout
               android:layout_width="match_parent"
               android:layout_height="wrap_content">
               <TextView
                   android:layout_width="match_parent"
                   android:layout_height="wrap_content"
                   android:id="@+id/title"
                   android:text="Title:"
                   android:textSize="18dp"
                   app:layout_constraintTop_toTopOf="parent"
                   app:layout_constraintLeft_toLeftOf="parent"
                   android:layout_marginLeft="8dp"/>
               <TextView
                   android:layout_width="match_parent"
                   android:layout_height="wrap_content"
                   android:id="@+id/createTime"
                   android:text="Create Time:"
                   android:textSize="18dp"
                   app:layout_constraintTop_toBottomOf="@id/title"
                   android:layout_marginTop="10dp"
                   app:layout_constraintLeft_toLeftOf="parent"
                   android:layout_marginLeft="8dp"/>
               <TextView
                   android:layout_width="match_parent"
                   android:layout_height="wrap_content"
                   android:id="@+id/content"
                   android:text="Content"
                   app:layout_constraintTop_toBottomOf="@id/createTime"
                   android:textSize="18dp"
                   android:layout_marginLeft="8dp"
                   android:layout_marginTop="10dp"/>
           </android.support.constraint.ConstraintLayout>
   
       </android.support.v7.widget.CardView>
   
   </android.support.constraint.ConstraintLayout>
   ```

   `MyQuizAdapter.java`自定义适配器：

   ```java
   package group15.android.tarlesh;
   
   import android.support.annotation.NonNull;
   import android.support.v7.widget.RecyclerView;
   import android.view.LayoutInflater;
   import android.view.View;
   import android.view.ViewGroup;
   import android.widget.TextView;
   
   import java.util.List;
   
   public class MyQuizAdapter extends RecyclerView.Adapter<MyQuizAdapter.ViewHolder>{
       private List<Quiz> list;
   
       public MyQuizAdapter(List<Quiz> list) {
           this.list = list;
       }
   
       @Override
       public int getItemCount() {
           if (list == null) {
               return 0;
           }
           return list.size();
       }
   
       @Override
       public long getItemId(int position) {
           return position;
       }
   
       @NonNull
       @Override
       public MyQuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
           View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quiz_item, viewGroup,false);
           return new ViewHolder(view);
       }
   
       @Override
       public void onBindViewHolder(@NonNull MyQuizAdapter.ViewHolder viewHolder, int i) {
           System.out.println("In Quiz Adapter " + i);
           final Quiz quiz = list.get(i);
           // 渲染每个Item的页面数据
           viewHolder.title.setText("Title: " + quiz.getTitle());
           viewHolder.content.setText("Content: " + quiz.getContent());
           viewHolder.createTime.setText("Create Time: " + quiz.getCreateTime());
       }
   
       public class ViewHolder extends RecyclerView.ViewHolder {
           private TextView title;
           private TextView content;
           private TextView createTime;
   
           public ViewHolder(View itemView) {
               super(itemView);
               title = itemView.findViewById(R.id.title);
               content = itemView.findViewById(R.id.content);
               createTime = itemView.findViewById(R.id.createTime);
           }
       }
   }
   ```

8. 获取问题列表功能。在打开页面时，就应该加载所有的问题，通过API来获取列表。新建一个类`QuizResponseBody.java`来接受API的返回数据。

   `QuizResponseBody.java`代码：

   ```java
   package group15.android.tarlesh;
   
   import java.util.List;
   
   public class QuizResponseBody {
       boolean sign;
       List<Quiz> question;
   
       public QuizResponseBody(boolean sign, List<Quiz> question) {
           this.sign = sign;
           this.question = question;
       }
   
       public boolean isSign() {
           return sign;
       }
   
       public void setSign(boolean sign) {
           this.sign = sign;
       }
   
       public List<Quiz> getQuestion() {
           return question;
       }
   }
   
   ```

   `QuizActivity.java`获取问题列表部分代码：

   ```java
   private void request_quiz() {
           OkHttpClient build = new OkHttpClient.Builder()
                   .connectTimeout(2, TimeUnit.SECONDS)
                   .readTimeout(2, TimeUnit.SECONDS)
                   .writeTimeout(2, TimeUnit.SECONDS)
                   .build();
   
           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl("http://139.199.166.124:8088")
                   .addConverterFactory(GsonConverterFactory.create())
                   .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                   .client(build)
                   .build();
   
           ApiService service = retrofit.create(ApiService.class);
           String token = CommonSPUtil.getInstance(this).getToken();
           Observable<QuizResponseBody> repoObservable = service.getQuizList(token);
   
           repoObservable.observeOn(AndroidSchedulers.mainThread())
                   .subscribeOn(Schedulers.io())
                   .subscribe(new Observer<QuizResponseBody>() {
                       @Override
                       public void onCompleted() { }
   
                       @Override
                       public void onError(Throwable e) {
                           e.printStackTrace();
                           Toast.makeText(QuizActivity.this, "获取Quiz列表失败", Toast.LENGTH_SHORT).show();
                       }
   
                       @Override
                       public void onNext(QuizResponseBody quizResponseBody) {
                           List<Quiz> quizs = new ArrayList<>();
                           quizs = quizResponseBody.getQuestion();
                           if (quizs == null)
                               return;
                           list.clear();
                           for (int i = 0; i < quizs.size(); i++) {
                               list.add(quizs.get(i));
                           }
                           myQuizAdapter.notifyDataSetChanged();
                       }
                   });
       }
   ```

9. 提问功能。提供的功能主要是读取用户输入，将其打包成一个json数据，然后POST到服务器中，如果返回正确，则将该提问加入到下方的问题列表中。需要新建一个类`QuizPostResponseBody.java`来接受API的返回数据。

   `QuizPostResponseBody.java`代码：

   ```java
   package group15.android.tarlesh;
   
   public class QuizPostResponseBody {
       boolean sign;
       String msg;
       Quiz data;
       public QuizPostResponseBody(boolean sign, String msg, Quiz data) {
           this.sign = sign;
           this.msg = msg;
           this.data = data;
       }
   
       public boolean isSign() {
           return sign;
       }
   
       public String getMsg() { return msg; }
   
       public Quiz getData() { return data; }
   }
   
   ```



   `QuizActivity.java`提问部分代码：

   ```java
   // 提交Quiz事件
           submitBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String title = titleInput.getText().toString();
                   String content = contentInput.getText().toString();
                   // 清空输入框
                   titleInput.setText("");
                   contentInput.setText("");
   
                   if (title.equals("")) {
                       Toast.makeText(QuizActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
                   }
                   else if (content.equals("")) {
                       Toast.makeText(QuizActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                   }
                   else {
                       OkHttpClient build = new OkHttpClient.Builder()
                               .connectTimeout(2, TimeUnit.SECONDS)
                               .readTimeout(2, TimeUnit.SECONDS)
                               .writeTimeout(2, TimeUnit.SECONDS)
                               .build();
   
                       // API Retrofit
                       Retrofit retrofit = new Retrofit.Builder()
                               .baseUrl("http://139.199.166.124:8088")
                               .addConverterFactory(GsonConverterFactory.create())
                               .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                               .client(build)
                               .build();
   
                       ApiService service = retrofit.create(ApiService.class);
                       // 生成请求的Body
                       JSONObject root = new JSONObject();
                       try {
                           root.put("title", title);
                           root.put("content", content);
                       }
                       catch (Exception ex) {
                           ex.printStackTrace();
                       }
                       RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
                       String token = CommonSPUtil.getInstance(QuizActivity.this).getToken();
                       Observable<QuizPostResponseBody> postQuizObservable = service.postQuiz(token, requestBody);
   
                       postQuizObservable.subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(new Observer<QuizPostResponseBody>() {
                                   @Override
                                   public void onCompleted() { }
   
                                   @Override
                                   public void onError(Throwable e) {
                                       e.printStackTrace();
                                       Toast.makeText(QuizActivity.this, "无法提交Quiz", Toast.LENGTH_SHORT).show();
                                   }
   
                                   @Override
                                   public void onNext(QuizPostResponseBody quizResponseBody) {
                                       Quiz quiz = quizResponseBody.getData();
                                       list.add(quiz);
                                       myQuizAdapter.notifyDataSetChanged();
                                       Toast.makeText(QuizActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                   }
                               });
                   }
               }
           });
   ```

10. 统一应用内控件风格。对用的比较多的控件如Button、EditText的样式进行自定义，统一颜色，使得整个应用比较好看。

    MyEditText样式：

    ```xml
    <style name="MyEditText" parent="Theme.AppCompat.Light">
            <item name="colorControlNormal">@color/md_black_1000</item>
            <item name="colorControlActivated">@color/lightBlue</item>
        </style>
    ```

    MyButton样式：

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <shape xmlns:android="http://schemas.android.com/apk/res/android">
    
        android:shape="rectangle">
        <solid android:color="@color/lightBlue"></solid>
        <corners
            android:radius="10dp"/>
    </shape>
    ```


## 四、难点与解决方案

1. 同一用户重复评分的问题：评分机制是这个应用一个很核心的机制。我们采用了RatingBar的方式来给用户评分，但是如何解决重复评分的问题呢？从前端的角度，利用RatingBar的isIndicator属性，用户在一次评分后就不能再次修改评分条。从后端的角度，数据库中记录评分的条目，重复评分将会返回报错信息。
2. 文件下载解决。文件有很多种存储形式，之前也没有试过将文件存放在数据库中，最后我们决定的是使用Buffer来作为数据类型传输文件。如果将Buffer类型解码成为相应的文件呢？在查看了Buffer的官方文档后，对其几个子类有了一定了解，发现可以直接转为byte[]来存储。使用bytes[]就比较容易了，只需要通过OutputStream和File结合就能够保存文件到本地。
3. pdf预览。应用内在线预览使用的是网络服务的形式。在充分了解了预览的原理后，出于对应用效率的考虑，我觉得自己写前端的渲染代码，参考了网上的一些例子，大致写了一个界面是支持pdf预览的。使用webview将这个html显示即可。注意解决跨域问题。

## 五、实验截图

点击某个文件，进入文件详情页面：

![fig1](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig1.png)

点击评分条，看到评分在变：

![fig2](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig2.png)

点击提交，弹出Toast显示评分成功，上面的评分也有可能会变：

![fig3](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig3.png)

再次评分，弹出Toast显示已经评过分了：

![fig4](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig4.png)

点击下方下载按钮，看到webview有进度条：

![fig5](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig5.png)

下载完成后，如果是pdf文件则有预览：

![fig6](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig6.png)

进入问答页面，显示所有问题：

![fig7](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig7.png)

输入问题标题及内容：

![fig8](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig8.png)

提交，弹出Toast显示提交成功:

![fig9](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig9.png)

拉到最下方看到刚刚提交的问题：

![fig10](https://gitee.com/leungyukshing/final_project/raw/master/report/Group15/16340133LiangYucheng/images/fig10.png)

## 六、总结与心得

&emsp;&emsp;我完成部分主要是前后端交互，有一些是比较常规的，比如问答页面的实现等，这部分遇到的问题不多，主要就是api的数据格式问题，与组员及时沟通基本上都能很快解决。

&emsp;&emsp;这次比较难的是文件的下载与预览。以前没有试过在服务器上存放文件，因此在上传和下载时都踩了很多坑。试过了很多种方法后，最后决定使用Buffer作为数据传输的类型。网上对Buffer的介绍少之又少，官方的文档也没有具体的用法。我自己尝试了几次之后，发现直接使用bytes数组反而更加直接方便，通过一个OutputStream就能够保存为本地文件了。

&emsp;&emsp;PDF的预览花费了我大量的时间，不断地在各种方式之间变换，最后还是使用了自定义的实现方式。虽然自己写的代码量多了，但是对于整个应用的稳定性是非常关键的，同时也能够为应用”瘦身“，减少不必要的代码。这一块也牵涉到了跨域的问题，也是通过大量查找网上资料解决的。

&emsp;&emsp;我认为我完成的部分是非常满意的，预期的功能基本都实现了，代码方便也比较简洁。还可以改进的地方是可以继续完善其他类型的文件预览。经过了期中项目的锻炼，我认为我们小组成员之间的配合已经变得默契。虽然有时候还是会在API接口的问题上有冲突，但是已经比之前的项目有了很大进步，比较多的时间都花在了自己的部分，而不是对接的部分，效率大大地提升了。

&emsp;&emsp;总的来说这个项目是比较实用的，也是比较有技术含量的，感谢小组成员一个学期下来的合作，也感谢老师和TA一学期的教学。

## 七、参考资料

1. bytes数组转pdf文件：https://stackoverflow.com/questions/8644459/how-to-convert-byte-array-to-pdf-file-in-android
2. webview跨域访问文件解决：https://blog.csdn.net/yclfdn2004/article/details/51364660
3. webview的基本使用：https://blog.csdn.net/lowprofile_coding/article/details/77928614
4. 修改RatingBar样式：https://blog.csdn.net/yaochangliang159/article/details/67637185
5. PDF预览：https://blog.csdn.net/DeMonliuhui/article/details/81185611