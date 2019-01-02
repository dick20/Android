package com.example2.asus.sensorgraph;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

public class MapActivity extends AppCompatActivity{
    private Sensor accelerometer;
    private Sensor magnetic;
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private SensorEventListener sensorEventListener;
    private LocationListener locationListener;
    private BaiduMap.OnMapStatusChangeListener onMapStatusChangeListener;
    private Location loc;
    private float degree;

    private MapView mMapView = null;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map2);
        LocationClient mLocationClient = new LocationClient(getApplicationContext());
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
        // 获取sensor管理者
        //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        initView();
        initEvent();
        initData();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, magnetic, SensorManager.SENSOR_DELAY_NORMAL);


        // 位置管理者
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // 利用网络定位
        final String provider = LocationManager.GPS_PROVIDER;
        // 设置时间变化频率
        // 产生位置改变事件的条件设定为距离改变10米，时间间隔为2秒
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i("location",loc.getLatitude()+" "+ loc.getLongitude());
        // 转换坐标
        //获取地图控件引用
        BaiduMap mBaiduMap = mMapView.getMap();
        // 设置监听器
        mBaiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(new LatLng(loc.getLatitude(), loc.getLongitude()));
        LatLng desLatLng = converter.convert();

        degree = 90.0f;
        mMapView.getMap().setMyLocationEnabled(true);
        MyLocationData data = new MyLocationData.Builder()
                .latitude(desLatLng.latitude)
                .longitude(desLatLng.longitude)
                .direction(degree).build();
        mMapView.getMap().setMyLocationData(data);

        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pointer), 100, 100, true);
        BitmapDescriptor bitmapD = BitmapDescriptorFactory.fromBitmap(bitmap);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapD);
        mMapView.getMap().setMyLocationConfiguration(config);

        // 设置地图居中
        MapStatus mapStatus = new MapStatus.Builder().target(desLatLng).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mMapView.getMap().setMapStatus(mapStatusUpdate);
    }

    private void setMarker(Location location,float degree){
        // 转换坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(new LatLng(location.getLatitude(), location.getLongitude()));
        LatLng desLatLng = converter.convert();

        mMapView.getMap().setMyLocationEnabled(true);
        MyLocationData data = new MyLocationData.Builder()
                .latitude(desLatLng.latitude)
                .longitude(desLatLng.longitude)
                .direction(degree).build();
        mMapView.getMap().setMyLocationData(data);

        // 设置marker的图标
        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pointer), 100, 100, true);
        BitmapDescriptor bitmapD = BitmapDescriptorFactory.fromBitmap(bitmap);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapD);
        mMapView.getMap().setMyLocationConfiguration(config);
        Log.i("hel",""+desLatLng.latitude+ " " +desLatLng.longitude);
    }

    private void initData(){
        // 传感器管理者
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 地磁传感器和加速度传感器
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // 位置管理者
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // 利用网络定位
        final String provider = LocationManager.GPS_PROVIDER;
        // 设置时间变化频率
        // 产生位置改变事件的条件设定为距离改变10米，时间间隔为2秒
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i("setMarker",""+loc.getLatitude());
        // 设置marker位置
        if (loc != null){
            Log.i("setMarker",""+loc.getLatitude());
            setMarker(loc,degree);
        }
    }

    private void initEvent() {
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
                }else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                    maneticValues = sensorEvent.values.clone();
                }
                float[] R = new float[9];
                float[] values = new float[3];
                SensorManager.getRotationMatrix(R,null,accelerometerValues,maneticValues);
                SensorManager.getOrientation(R,values);
                float rotateDegree = -(float) Math.toDegrees(values[0]);
                if (Math.abs(rotateDegree - lastDegree) > 1){
                    // RotateAnimation animation = new RotateAnimation(lastDegree,rotateDegree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    // animation.setFillAfter(true);
                    // arrow.startAnimation(animation);
                    lastDegree = rotateDegree;
                    degree = rotateDegree;
                    Log.i("sensor","setMarker");
                    if (loc!=null){
                        setMarker(loc,degree);
                        //Log.i("sensor","setMarker");
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        // 定义位置的监听函数
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 设置marker位置
                loc = location;
                setMarker(loc,degree);
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
    }

    void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        imageButton = findViewById(R.id.imaage_button);
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        sensorManager.unregisterListener(sensorEventListener);
    }
}
