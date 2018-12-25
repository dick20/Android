package com.example2.asus.sensorgraph;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity{
    private ImageView arrow;
    private TextView rotate;
    private TextView longtitude;
    private TextView latitude;
    private Sensor accelerometer;
    private Sensor magnetic;
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private SensorEventListener sensorEventListener;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initView();
        initEvent();
        initData();
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
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // 更新经纬度
        updateLocation(location);
    }

    private void updateLocation(Location location){
        Log.i("location","update location");
        if (location != null){
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            latitude.setText(Double.toString(lat));
            longtitude.setText(Double.toString(lon));
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
                rotate.setText(Float.toString(rotateDegree));
                if (Math.abs(rotateDegree - lastDegree) > 1){
                    RotateAnimation animation = new RotateAnimation(lastDegree,rotateDegree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    animation.setFillAfter(true);
                    arrow.startAnimation(animation);
                    lastDegree = rotateDegree;
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
                updateLocation(location);
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
    }

    void initView() {
        arrow = findViewById(R.id.arrow);
        rotate = findViewById(R.id.rotate);
        longtitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }
}
