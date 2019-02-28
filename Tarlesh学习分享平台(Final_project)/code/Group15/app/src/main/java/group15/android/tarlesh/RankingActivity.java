package group15.android.tarlesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

public class RankingActivity extends AppCompatActivity {

    private List<File> mFileList;
    private RecyclerView recyclerView;
    private RecommendAdapter adapter;

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
                    return true;
                case R.id.navigation_quiz:
                    // 跳转至quiz页面
                    startQuizActivity();
                    return true;
            }
            return false;
        }
    };

    private void startMainActivity() {
        startActivity(new Intent(RankingActivity.this,HomeActivity.class));
        //关闭当前页面
        finish();
    }

    private void startQuizActivity() {
        startActivity(new Intent(RankingActivity.this,QuizActivity.class));
        //关闭当前页面
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

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
        request_file();
        Log.i("sss",mFileList.size()+" size");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setSelectedItemId(navigation.getMenu().getItem(1).getItemId());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
                    request_file_by_para(0);
                    str = select1.getText().toString();
                }
                else if(select2.getId() == checkedID){
                    request_file_by_para(0);
                    str = select2.getText().toString();
                }
                else if(select3.getId() == checkedID){
                    request_file_by_para(2);
                    str = select3.getText().toString();
                }
                else if(select4.getId() == checkedID){
                    request_file_by_para(1);
                    str = select4.getText().toString();
                }
                Toast.makeText(RankingActivity.this, str + "被选中", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void request_file_by_para(int para){
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
        JSONObject root = new JSONObject();
        try {
            root.put("filename", "");
            root.put("rankType", para);
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
                        Toast.makeText(RankingActivity.this,"获取列表失败",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.i("list",responseBody.sign+" "+responseBody.msg);
                        List<File> files = new ArrayList<>();
                        files = responseBody.data;
                        Log.i("files",files.size()+"");
                        mFileList.clear();
                        for (int i = 0; i < files.size(); i++){
                            Log.i("list",files.get(i).getFilename());
                            mFileList.add(files.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void request_file(){
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
        String token = CommonSPUtil.getInstance(this).getToken();
        // 生成请求的Body
        JSONObject root = new JSONObject();
        try {
            root.put("filename", "");
            root.put("rankType", 0);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
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
                        Toast.makeText(RankingActivity.this,"获取列表失败",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.i("list",responseBody.sign+" "+responseBody.msg);
                        List<File> files = new ArrayList<>();
                        files = responseBody.data;
                        Log.i("files",files.size()+"");
                        mFileList.clear();
                        for (int i = 0; i < files.size(); i++){
                            mFileList.add(files.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}