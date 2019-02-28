package group15.android.tarlesh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import group15.android.tarlesh.util.CommonSPUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

// 文档详情页面
public class FileDetail extends AppCompatActivity {
    private TextView filename;
    private TextView description;
    private TextView rating;
    private TextView owner;
    private TextView downloads;
    private Button downloadBtn;
    private RatingBar ratingBar;
    private Button ratingBtn;
    private WebView mWebView;
    private ProgressBar progressBar;

    // 页面文件信息
    private String fid;
    private String starCount;
    private String filenameStr;
    private String createTime;
    private String descriptionStr;
    private String downloadCount;
    private String ownerStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_detail);

        // 初始化变量
        filename = (TextView)findViewById(R.id.fileName);
        description = (TextView)findViewById(R.id.description);
        rating = (TextView)findViewById(R.id.rating);
        owner = (TextView)findViewById(R.id.owner);
        downloads = (TextView)findViewById(R.id.downloads);
        downloadBtn = (Button)findViewById(R.id.downloadBtn);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingBtn = (Button)findViewById(R.id.ratingBtn);
        mWebView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

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

        // 评分条事件处理
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) { }
        });

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
        //String path = Environment.getExternalStorageDirectory().getPath() + ;
        //String path = "file:///android_asset/hw1.pdf";
        //System.out.println("Path: " + path);
        //mWebView.loadUrl("file:///android_asset/viewer.html?" + path);
    }
}
