package group15.android.tarlesh;

public class UploadResponseBody {
    boolean sign;
    MSG msg[];


    public UploadResponseBody(boolean sign, MSG[] msg) {
        this.sign = sign;
        this.msg = msg;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public MSG[] getMsg() {
        return msg;
    }

    public void setMsg(MSG[] msg) {
        this.msg = msg;
    }

    private class MSG{
        String fid;
        String points;
        String fname;
        String uid;
        String uname;
        String description;
        String createTime;

        public MSG(String fid, String points, String fname, String uid, String uname, String description, String createTime) {
            this.fid = fid;
            this.points = points;
            this.fname = fname;
            this.uid = uid;
            this.uname = uname;
            this.description = description;
            this.createTime = createTime;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}


