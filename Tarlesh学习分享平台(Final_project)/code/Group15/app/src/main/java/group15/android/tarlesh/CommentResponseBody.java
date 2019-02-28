package group15.android.tarlesh;

import java.util.List;

public class CommentResponseBody {
    boolean sign;
    String msg;
    List<Comment> comments;

    public boolean isSign() { return sign; }

    public String getMsg() { return msg; }

    public List<Comment> getComments() { return comments; }
}
