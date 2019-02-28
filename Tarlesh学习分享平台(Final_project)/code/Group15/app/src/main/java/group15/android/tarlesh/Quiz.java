package group15.android.tarlesh;

public class Quiz {
    private String qid;
    private String uname;
    private String title;
    private String content;
    private String createTime;
    private String answers_counts;

    public Quiz(String qid, String uname, String title, String content, String createTime, String answers_counts) {
        this.qid = qid;
        this.uname = uname;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.answers_counts = answers_counts;
    }

    public String getQid() { return qid; }

    public String getUname() { return uname; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

    public String getCreateTime() { return createTime; }

    public String getAnswers_counts() { return answers_counts; }
}
