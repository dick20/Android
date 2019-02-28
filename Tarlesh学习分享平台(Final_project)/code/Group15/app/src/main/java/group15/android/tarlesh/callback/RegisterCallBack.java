package group15.android.tarlesh.callback;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import group15.android.tarlesh.model.LoginModel;
import group15.android.tarlesh.model.RegisterModel;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 注册回调处理
 */
public abstract class RegisterCallBack extends Callback<RegisterModel> {

    @Override
    public RegisterModel parseNetworkResponse(Response response, int id) throws Exception {
        String str = response.body().string();
        JSONObject jsonObject = new JSONObject(str);
        RegisterModel registerModel = new RegisterModel();
        if (!jsonObject.optBoolean("sign"))
            return registerModel;
        registerModel.token = jsonObject.getString("token");
        return registerModel;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public abstract void onResponse(RegisterModel response, int id);
}
