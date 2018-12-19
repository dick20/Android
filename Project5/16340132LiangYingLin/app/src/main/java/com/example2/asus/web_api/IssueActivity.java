package com.example2.asus.web_api;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IssueActivity extends AppCompatActivity {
    List<Issue> list;
    RecyclerView recyclerView;
    IssueRecyclerViewAdapter adapter;
    String userName;
    String repoName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        Bundle bundle=this.getIntent().getExtras();
        repoName = bundle.getString("repoName");
        userName = bundle.getString("userName");
        Log.i("jump",repoName + " " + userName);

        // 显示recyclerView
        recyclerView = findViewById(R.id.recyclerView_issue);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(IssueActivity.this));
        adapter = new IssueRecyclerViewAdapter(IssueActivity.this,R.layout.issue_item,list);
        recyclerView.setAdapter(adapter);

        // 获取issue
        request_issue(userName,repoName);
        // 加分项，绑定按键监听器，post一个issue
        bind_post_issue();
    }

    private void bind_post_issue(){
        final EditText input_title = findViewById(R.id.input_title);
        final EditText input_body = findViewById(R.id.input_body);
        Button add_issue = findViewById(R.id.add_issue);
        add_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = input_title.getText().toString();
                String body = input_body.getText().toString();
                post_issue(userName, repoName, title,body);
            }
        });
    }

    private void post_issue(String userName, String repoName, String title, String body){
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

        Log.i("post",title + " " + body + " " + userName +" " + repoName + " ");
        JSONObject root = new JSONObject();
        try {
            root.put("title", title);
            root.put("body", body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
        Log.i("requestBody",root.toString());

        IssueService service = retrofit.create(IssueService.class);
        Observable<Issue> IssueObservable2 = service.postIssue(userName,repoName,requestBody);
        IssueObservable2
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Issue>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(IssueActivity.this,"无法上传该issue",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Issue issue) {
                        list.add(0,issue);
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    private void request_issue(String userName,String repoName){
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

        IssueActivity.IssueService service = retrofit.create(IssueActivity.IssueService.class);
        Observable<List<Issue>> IssueObservable = service.getIssue(userName,repoName);

        IssueObservable
                .observeOn(AndroidSchedulers.mainThread())  // 指定 Subscriber 的回调发生在主线程
                .subscribeOn(Schedulers.io())               // 指定 subscribe() 发生在 IO 线程
                .subscribe(new Observer<List<Issue>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Issue> issues) {
                        for (int i = 0; i < issues.size(); i++){
                            Log.i("list",issues.get(i).getTitle());
                            list.add(issues.get(i));
                        }
                        if(list.isEmpty()){
                            Toast.makeText(IssueActivity.this,"该repo不存在issue",Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    // https://api.github.com/repos/用户名/仓库名/issues
    public interface IssueService {
        @GET("/repos/{user_name}/{repo_name}/issues")
            // 这里的List<Repo>即为最终返回的类型，需要保持一致才可解析
            // 之所以使用一个List包裹是因为该接口返回的最外层是一个数组
            // Call<List<Repo>> getRepo(@Path("user_name") String user_name);
            // 特别地，使用rxJava时为
        Observable<List<Issue>> getIssue(@Path("user_name") String user_name, @Path("repo_name") String repo_name);

        // 绑定个人Token用于测试,拼写错误authentication
        @Headers("Authorization: token e6115a109795c8a934ba54fd274f895fa47021c5")
        @POST("/repos/{user_name}/{repo_name}/issues")
        Observable<Issue> postIssue(@Path("user_name") String user_name,
                                          @Path("repo_name") String repo_name, @Body RequestBody requestBody);
    }

}