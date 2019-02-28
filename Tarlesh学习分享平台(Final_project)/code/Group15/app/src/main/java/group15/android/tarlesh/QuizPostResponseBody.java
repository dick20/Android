package group15.android.tarlesh;

public class QuizPostResponseBody {
    boolean sign;
    String msg;
    Quiz data;
    public QuizPostResponseBody(boolean sign, String msg, Quiz data) {
        this.sign = sign;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSign() {
        return sign;
    }

    public String getMsg() { return msg; }

    public Quiz getData() { return data; }
}
