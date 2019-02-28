package group15.android.tarlesh.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sp保存工具类
 */
public class CommonSPUtil {
    /**
     * 配置文件名
     */
    public final static String SP_FILE_NAME = "configuration";

    /**
     * 辅助对象，用于单例模式中
     */
    private final static Object syncObj = new Object();
    /**
     * token
     */
    private static final String KEY_TOKEN = "token";
    /**
     * CommonSPUtil实例
     */
    private static CommonSPUtil instance;
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 共享参数对象
     */
    private SharedPreferences sp;

    private CommonSPUtil(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 采用单例模式获取CommonSPUtil实例
     *
     * @param context 上下文对象
     * @return instance 单例对象
     */
    public static CommonSPUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (syncObj) {
                if (instance == null) {
                    instance = new CommonSPUtil(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取token
     */
    public String getToken() {
        return sp.getString(KEY_TOKEN, "");
    }

    /**
     * 设置token
     */
    public void setToken(String token) {
        final SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }
}
