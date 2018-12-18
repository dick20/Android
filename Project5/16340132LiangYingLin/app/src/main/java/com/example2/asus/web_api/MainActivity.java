package com.example2.asus.web_api;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.PatternMatcher;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private int user_id;

    public static final int GET_DATA_SUCCESS = 1;
    public static final int GET_DATA_EMPTY = 2;
    public static final int NETWORK_ERROR = 3;
    public static final int SERVER_ERROR = 4;
    public static final int GET_IMAGE_SUCCESS = 5;
    public static final int GET_IMAGEPEACES_SUCCESS = 6;

    ArrayList<RecyclerObj> data;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter myAdapter;
    ProgressBar progressBar;


    // HTTP获取信息
    //子线程不能操作UI，通过Handler设置图片
    @SuppressLint("HandlerLeak")
     Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    myAdapter.notifyDataSetChanged();
                    RecyclerObj recyclerObj = (RecyclerObj)msg.obj;
                    setImageURL(recyclerObj);
                    getImagePeacesByAid(recyclerObj);
                    break;
                case GET_DATA_EMPTY:
                    Toast.makeText(MainActivity.this,"数据库不存在记录",Toast.LENGTH_SHORT).show();
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(MainActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(MainActivity.this,"服务器发生错误",Toast.LENGTH_SHORT).show();
                    break;
                case GET_IMAGE_SUCCESS:
                    // 去除缓冲的圆圈
                    myAdapter.notifyDataSetChanged();
                    Log.i("handler","设置照片成功");
                    break;
                case GET_IMAGEPEACES_SUCCESS:
                    Log.i("handler","获取预览图成功");
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private void getImagePeacesByAid(final RecyclerObj recyclerObj) {
        //开启一个线程用于联网
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    //把传过来的路径转成URL
                    String aid = recyclerObj.getData().getAid();
                    URL url = new URL("https://api.bilibili.com/pvideo?aid=" + aid);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为10秒
                    connection.setConnectTimeout(10000);
                    //获取返回码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String result = new BufferedReader(new InputStreamReader(inputStream))
                                .lines().collect(Collectors.joining(System.lineSeparator()));
                        Log.i("connection", result);

                        inputStream.close();
                        // 测试获取图片的url字符串
                        JSONObject obj = new JSONObject(result);
                        String image_url = obj.getJSONObject("data").getString("image");
                        String index = obj.getJSONObject("data").getString("index");
                        index = index.substring(1,index.length()-1);
                        String[] indexArray = index.split(",");
                        String[] imageUrlArray = new String[2];
                        // 两张缩略图
                        if (indexArray.length > 100){
                            imageUrlArray = image_url.split(",");
                            imageUrlArray[0] = imageUrlArray[0].substring(2,imageUrlArray[0].length()-1);
                            imageUrlArray[1] = imageUrlArray[1].substring(1,imageUrlArray[1].length()-2);
                        }
                        // 一张缩略图
                        else{
                            image_url = image_url.substring(2,image_url.length()-2);
                            imageUrlArray[0] = image_url;
                        }
                        // 获取图片
                        URL url2 = new URL(imageUrlArray[0]);
                        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                        connection2.setRequestMethod("GET");
                        connection2.setConnectTimeout(10000);
                        // 处理第二张图片
                        Bitmap bitmap2 = null;
                        if (indexArray.length > 100){
                            URL url3 = new URL(imageUrlArray[1]);
                            HttpURLConnection connection3 = (HttpURLConnection) url3.openConnection();
                            connection3.setRequestMethod("GET");
                            connection3.setConnectTimeout(10000);
                            int code3 = connection3.getResponseCode();
                            if (code3 == 200) {
                                InputStream inputStream3 = connection3.getInputStream();
                                bitmap2 = BitmapFactory.decodeStream(inputStream3);
                            }
                        }
                        //获取返回码
                        int code2 = connection2.getResponseCode();
                        if (code2 == 200) {
                            InputStream inputStream2 = connection2.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream2);
                            // 切割bitmap
                            ArrayList<RecyclerObj.ImagePiece> imagePieces = new ArrayList<>();
                            ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
                            bitmapArrayList.add(bitmap);
                            if (bitmap2 != null) {
                                bitmapArrayList.add(bitmap2);
                            }
                            imagePieces = spiltBitmap(indexArray,bitmapArrayList);
                            data.remove(recyclerObj);
                            recyclerObj.setPeaces(imagePieces);
                            data.add(recyclerObj);

                            Message msg = Message.obtain();
                            msg.what = GET_IMAGEPEACES_SUCCESS;
                            msg.obj = imagePieces;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private ArrayList<RecyclerObj.ImagePiece> spiltBitmap(String[] indexArray,ArrayList<Bitmap> bitmapArrayList){
        int width = 160;
        int height = 90;
        int xValue = 0;
        int yValue = 0;
        int size = indexArray.length;
        Bitmap bitmap = bitmapArrayList.get(0);
        Bitmap bitmap2 = null;
        if (size > 100) {
            bitmap2 = bitmapArrayList.get(1);
        }
        ArrayList<RecyclerObj.ImagePiece> imagePieces = new ArrayList<>();
        // 对于id为32的进行处理
        if (bitmap == null) return null;
        // 仅对一张缩略图
        if (size < 100){
            for (int i = 1; i <= size; i++){
                Bitmap piece_bitmap = Bitmap.createBitmap(bitmap,xValue,yValue,width,height);
                xValue += width;
                if(i%10==0){
                    yValue += height;
                    xValue = 0;
                }
                RecyclerObj.ImagePiece piece = new RecyclerObj.ImagePiece(piece_bitmap,Integer.valueOf(indexArray[i-1]));
                imagePieces.add(piece);
            }
        }
        // 对于两张缩略图
        else {
            for (int i = 1; i <= 100; i++){
                Bitmap piece_bitmap = Bitmap.createBitmap(bitmap,xValue,yValue,width,height);
                xValue += width;
                if(i%10==0){
                    yValue += height;
                    xValue = 0;
                }
                RecyclerObj.ImagePiece piece = new RecyclerObj.ImagePiece(piece_bitmap,Integer.valueOf(indexArray[i-1]));
                imagePieces.add(piece);
            }
            xValue = 0;
            yValue = 0;
            for (int i = 101; i <= size; i++){
                Bitmap piece_bitmap = Bitmap.createBitmap(bitmap,xValue,yValue,width,height);
                xValue += width;
                if((i-100)%10==0){
                    yValue += height;
                    xValue = 0;
                }
                RecyclerObj.ImagePiece piece = new RecyclerObj.ImagePiece(piece_bitmap,Integer.valueOf(indexArray[i-1]));
                imagePieces.add(piece);
            }
        }
        return imagePieces;
    }

    private Thread thread = new Thread(){
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run(){
            URL url = null;
            try {
                url = new URL("https://space.bilibili.com/ajax/top/showTop?mid="+user_id);
            } catch (MalformedURLException e) {
                //网络连接错误
                handler.sendEmptyMessage(NETWORK_ERROR);
                e.printStackTrace();
            }
            try {
                //获取连接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //使用GET方法访问网络
                connection.setRequestMethod("GET");
                //超时时间为10秒
                connection.setConnectTimeout(10000);
                //获取返回码
                int code = connection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = connection.getInputStream();
                    String result = new BufferedReader(new InputStreamReader(inputStream))
                            .lines().collect(Collectors.joining(System.lineSeparator()));
                    Message msg = Message.obtain();
                    Log.i("connection", "获取成功");
                    Log.i("connection", result);
                    // 处理字符串放入列表中，用于显示UI
                    RecyclerObj recyclerObj;
                    try {
                        recyclerObj = new Gson().fromJson(result, RecyclerObj.class);
                        // 获取预览图

                        data.add(recyclerObj);
                        msg.obj = recyclerObj;
                        msg.what = GET_DATA_SUCCESS;
                    }
                    catch (Exception e){
                        msg.obj = null;
                        msg.what = GET_DATA_EMPTY;
                    }
                    handler.sendMessage(msg);
                    inputStream.close();
                }else {
                    //服务启发生错误
                    handler.sendEmptyMessage(SERVER_ERROR);
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                //网络连接错误
                handler.sendEmptyMessage(NETWORK_ERROR);
                e.printStackTrace();
            }
        }
    };

    public void setImageURL(final RecyclerObj recyclerObj) {
        //开启一个线程用于联网
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    //把传过来的路径转成URL
                    String path = recyclerObj.getData().getCover();
                    URL url = new URL(path);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为10秒
                    connection.setConnectTimeout(10000);
                    //获取返回码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Log.i("adapter","获取照片成功");
                        //利用Message把图片发给Handler
                        Message msg = Message.obtain();
                        data.remove(recyclerObj);
                        recyclerObj.getData().setCover_image(bitmap);
                        msg.what = GET_IMAGE_SUCCESS;
                        data.add(recyclerObj);
                        handler.sendMessage(msg);
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.processBar);

        // 判断输入框,处理user的id
        final EditText editText = findViewById(R.id.input);
        Button button = findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher matcher = pattern.matcher(s);
                if (!matcher.matches()){
                    Toast.makeText(MainActivity.this,"搜索框只允许正整数int类型，请重新输入！",Toast.LENGTH_SHORT).show();
                }
                else {
                    user_id = Integer.parseInt(s);
                    if (user_id > 40 || user_id < 0){
                        Toast.makeText(MainActivity.this,"user的id查询只允许小于等于40且大于0",Toast.LENGTH_SHORT).show();
                    }
                }
                // 获取该id的信息
                thread.start();
            }
        });

        // RecyclerView的显示
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyRecyclerViewAdapter(this,R.layout.item,data);
        recyclerView.setAdapter(myAdapter);
    }
}