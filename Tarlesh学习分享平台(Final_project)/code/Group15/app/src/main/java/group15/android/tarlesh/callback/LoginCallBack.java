package group15.android.tarlesh.callback;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import group15.android.tarlesh.model.LoginModel;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 登录回调处理
 */
public abstract class LoginCallBack extends Callback<LoginModel> {

    @Override
    public LoginModel parseNetworkResponse(Response response, int id) throws Exception {
        String str = response.body().string();
        JSONObject jsonObject = new JSONObject(str);
        LoginModel loginModel = new LoginModel();
        if (!jsonObject.optBoolean("sign"))
            return loginModel;
        loginModel.token = jsonObject.getString("token");
        return loginModel;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public abstract void onResponse(LoginModel response, int id);
}
