package group15.android.tarlesh;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import APIData.API_Response_user;
import Service.DataService;
import group15.android.tarlesh.activity.LoginActivity;
import group15.android.tarlesh.api.Api;
import group15.android.tarlesh.callback.LogoutCallBack;
import group15.android.tarlesh.model.LogoutModel;
import group15.android.tarlesh.util.CommonSPUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static group15.android.tarlesh.api.Api.SERVER_URL;
import static group15.android.tarlesh.api.Api.UPLOAD_URL;

public class HomeActivity extends AppCompatActivity {

    private List<File> mFileList;
    private RecyclerView recyclerView;
    private RecommendAdapter adapter;
    private ImageView imageView;
    private Uri uri;
    private Button button;
    private Drawer drawer;
    private EditText editText;
    String name = "user";
    DataService dataService;
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

    private void startQuizActivity() {
        startActivity(new Intent(HomeActivity.this,QuizActivity.class));
        //关闭当前页面
        finish();
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            // 上传成功
            if(msg.what==101){
                Toast.makeText(HomeActivity.this,"完成上传！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    //动态获取内存存储权限
    public void verifyStoragePermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        verifyStoragePermissions(this);

        //先声明OkHttpClient，因为retrofit时基于okhttp的，在这可以设置一些超时参数等
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        String baseURL= SERVER_URL;
        Retrofit retrofit = new Retrofit.Builder()
                // baseURL即为请求前缀，本次作业为showTop之前的字符
                .baseUrl(baseURL)
                // 设置json数据解析器
                .addConverterFactory(GsonConverterFactory.create())

                // RxJava封装OkHttp的Call函数，本质还是利用OkHttp请求数据
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(build)
                .build();

        dataService = retrofit.create(DataService.class);

        initDrawer();

        imageView = findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("InlinedApi")
            @Override
            public void onClick(View v) {
                // 选择文件
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                //intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/*");
                startActivityForResult(intent, 0);
            }
        });

        // 显示recyclerView
        mFileList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        adapter = new RecommendAdapter(HomeActivity.this,R.layout.file_item,mFileList);
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
                intent.setClass(HomeActivity.this,FileDetail.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // 设置搜索按钮
        button = findViewById(R.id.but1);
        editText = findViewById(R.id.search_content);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_file_by_filename(editText.getText().toString());
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(navigation.getMenu().getItem(0).getItemId());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // 获取推荐
        request_file();
    }
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

    // 侧栏的设置
    private void initDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        Call<API_Response_user> call = dataService.getUserDetail(CommonSPUtil.getInstance(getApplicationContext()).getToken());
        //进行网络请求
        call.enqueue(new Callback<API_Response_user>() {
            @Override
            public void onResponse(Call<API_Response_user> call, Response<API_Response_user> response) {
                Log.i("res",response.headers().toString());
                if(response.isSuccessful()) {
                    name = response.body().data.uname;

                    // Create the AccountHeader
                    AccountHeader headerResult = new AccountHeaderBuilder()
                            .withActivity(HomeActivity.this)
                            .withHeaderBackground(R.color.md_dark_background)
                            .addProfiles(
                                    new ProfileDrawerItem()
                                            .withTextColor(getColor(R.color.colorPrimaryDark))
                                            .withName(name)
                                            .withEmail(response.body().data.email)
                                            .withIcon(getResources().getDrawable(R.drawable.sysu))
                            )
                            .build();

                    PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("主页");
                    // .withIcon(getDrawable(R.mipmap.ic_launcher))
                    PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(1).withName("我的文件");
                    PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(1).withName("历史记录");
                    PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(1).withName("Q&A");
                    SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(1).withName("注销");

                    //create the drawer and remember the `Drawer` result object
                    drawer = new DrawerBuilder()
                            .withAccountHeader(headerResult)
                            .withActivity(HomeActivity.this)
                            .withToolbar(toolbar)
                            .addDrawerItems(
                                    item1,
                                    item2,
                                    item3,
                                    item5,
                                    new DividerDrawerItem(),
                                    item4
                            )
                            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                @Override
                                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                    // do something with the clicked item :D
                                    switch (position){
                                        case 1:
                                            drawer.closeDrawer();
                                            break;
                                        case 2:
//                                viewpager.setCurrentItem(0);
                                            drawer.closeDrawer();
                                            break;
                                        case 3:
                                            drawer.closeDrawer();
                                            break;
                                        case 4:
                                            startQuizActivity();
                                            break;
                                        default:
                                            logout();
                                            return true;
                                    }
                                    return true;
                                }
                            })
                            .build();
                } else{
                    Toast.makeText(getApplicationContext(), "Error with code "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<API_Response_user> call, Throwable t) {
                t.printStackTrace();
                if(t.getClass().toString().equals("class java.net.UnknownHostException"))
                {
                    Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "查无此项", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // 退出登陆
    private void logout(){
        //清除本地token
        CommonSPUtil commonSPUtil = CommonSPUtil.getInstance(HomeActivity.this);
        String token = commonSPUtil.getToken();
        commonSPUtil.setToken(null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        //退出请求
        OkHttpUtils.postString().url(Api.SERVER_URL + Api.LOGOUT_URL)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new LogoutCallBack() {

                    @Override
                    public void onResponse(LogoutModel response, int id) {
                        System.out.println("msg是:" + response.msg);
                    }
                });
        Intent intent = new Intent();
        Toast.makeText(HomeActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
        intent.setClass(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // 获取推荐文件
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
        org.json.JSONObject root = new org.json.JSONObject();
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
                        Toast.makeText(HomeActivity.this,"获取列表失败",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.i("list",responseBody.sign+" "+responseBody.msg);
                        List<File> files = new ArrayList<>();
                        files = responseBody.data;
                        if (files.size() > 0){
                            mFileList.clear();
                            mFileList.add(files.get(0));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}