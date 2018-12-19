package com.example2.asus.web_api;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GithubActivity extends AppCompatActivity {

    List<Repo> list;
    RecyclerView recyclerView;
    GithubRecyclerViewAdapter adapter;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        // 显示recyclerView
        recyclerView = findViewById(R.id.recyclerView_github);
        list = new ArrayList<>();
        // list.add(new Repo("hello","sss","11",Boolean.TRUE,3));
        recyclerView.setLayoutManager(new LinearLayoutManager(GithubActivity.this));
        adapter = new GithubRecyclerViewAdapter(GithubActivity.this,R.layout.repo_item,list);
        recyclerView.setAdapter(adapter);

        final EditText inputUserName = findViewById(R.id.input_github);
        Button search = findViewById(R.id.search_github);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = inputUserName.getText().toString();
                list.clear();
                Log.i("GITHUB",userName);
                // 获取repo
                request_repo(userName);
            }
        });

        // 设置监听器
        adapter.setOnItemClickListener(new GithubRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("repoName",list.get(position).getName());
                bundle.putString("userName",userName);

                intent.setClass(GithubActivity.this,IssueActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

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
                        Log.i("sss",e.getMessage());
                        if (e.getMessage().equals("Unable to resolve host \"api.github.com\": No address associated with hostname"))
                            Toast.makeText(GithubActivity.this,"HTTP 404:网络连接异常",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(GithubActivity.this,"无法找到该用户",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        for (int i = 0; i < repos.size(); i++){
                            Log.i("list",repos.get(i).getName());
                            if(repos.get(i).getHas_issues())
                                list.add(repos.get(i));
                        }
                        if (repos.isEmpty()){
                            Toast.makeText(GithubActivity.this,"该用户没有任何Repo",Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    public interface GitHubService {
        @GET("/users/{user_name}/repos")
        Observable<List<Repo>> getRepo(@Path("user_name") String user_name);
    }

}