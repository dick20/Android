package group15.android.tarlesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
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

public class QuizActivity extends AppCompatActivity {
    private TextView quizTitle;
    private TextView quizContent;
    private EditText titleInput;
    private EditText contentInput;
    private Button submitBtn;
    private RecyclerView quizRecyclerView;
    private List<Quiz> list;
    private MyQuizAdapter myQuizAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // 跳转至home页面
                    startMainActivity();
                    return true;
                case R.id.navigation_ranking:
                    startRankingActivity();
                    return true;
                case R.id.navigation_quiz:
                    return true;
            }
            return false;
        }
    };

    private void startMainActivity() {
        startActivity(new Intent(QuizActivity.this,HomeActivity.class));
        //关闭当前页面
        finish();
    }

    private void startRankingActivity() {
        startActivity(new Intent(QuizActivity.this,RankingActivity.class));
        //关闭当前页面
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // 初始化navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation3);
        navigation.setSelectedItemId(navigation.getMenu().getItem(2).getItemId());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // 初始化变量
        quizTitle = (TextView)findViewById(R.id.quizTitle);
        quizContent = (TextView)findViewById(R.id.quizContent);
        titleInput = (EditText)findViewById(R.id.titleInput);
        contentInput = (EditText)findViewById(R.id.contentInput);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        list = new ArrayList<>();
        quizRecyclerView = (RecyclerView)findViewById(R.id.quizRecyclerView);
        myQuizAdapter = new MyQuizAdapter(list);

        // 渲染RecyclerView
        quizRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        quizRecyclerView.setAdapter(myQuizAdapter);

        // 获取所有Quiz，渲染页面
        request_quiz();

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
    }

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
}
