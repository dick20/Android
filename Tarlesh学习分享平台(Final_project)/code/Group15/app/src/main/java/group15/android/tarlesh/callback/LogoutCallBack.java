package group15.android.tarlesh.callback;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import group15.android.tarlesh.model.LoginModel;
import group15.android.tarlesh.model.LogoutModel;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 退出回调处理
 */
public abstract class LogoutCallBack extends Callback<LogoutModel> {

    @Override
    public LogoutModel parseNetworkResponse(Response response, int id) throws Exception {
        String str = response.body().string();
        JSONObject jsonObject = new JSONObject(str);
        LogoutModel logoutModel = new LogoutModel();
        if (!jsonObject.optBoolean("sign"))
            return logoutModel;
        logoutModel.msg = jsonObject.getString("msg");
        return logoutModel;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public abstract void onResponse(LogoutModel response, int id);
}
