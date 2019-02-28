package group15.android.tarlesh;

public class File {
    private String fid;
    private String fname;
    private String description;
    private String createTime;
    private String uname;
    private String points;
    private String download_count;

    public File(String fid, String filename, String description, String createTime, String owner, String star_count, String download_count) {
        this.fid = fid;
        this.fname = filename;
        this.description = description;
        this.createTime = createTime;
        this.uname = owner;
        this.points = star_count;
        this.download_count = download_count;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setFilename(String filename) {
        this.fname = filename;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setOwner(String owner) {
        this.uname = owner;
    }

    public void setStar_count(String star_count) {
        this.points = star_count;
    }

    public void setDownload_count(String download_count) {
        this.download_count = download_count;
    }

    public String getStar_count() {
        return points;
    }

    public String getDownload_count() {
        return download_count;
    }

    public String getFid() { return fid; }

    public String getFilename() { return fname; }

    public String getDescription() { return description; }

    public String getCreateTime() { return createTime; }

    public String getOwner() { return uname; }
}
