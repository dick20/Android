package APIData;

import com.google.gson.annotations.SerializedName;

public class API_Response_user {
    @SerializedName("sign")
    public boolean sign;

    @SerializedName("msg")
    public String msg;

    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("uid")
        public String uid;

        public String uname;
        public String email;
        public String phone;
        public int points;
    }
}
