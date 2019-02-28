package group15.android.tarlesh;

import java.util.List;

public class ResponseBody {
    Boolean sign;
    List<File> data;
    String msg;

    public ResponseBody(Boolean sign, List<File> data, String msg) {
        this.sign = sign;
        this.data = data;
        this.msg = msg;
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public List<File> getData() {
        return data;
    }

    public void setData(List<File> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
