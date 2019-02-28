package group15.android.tarlesh;

import java.util.List;

public class QuizResponseBody {
    boolean sign;
    List<Quiz> question;

    public QuizResponseBody(boolean sign, List<Quiz> question) {
        this.sign = sign;
        this.question = question;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public List<Quiz> getQuestion() {
        return question;
    }
}
