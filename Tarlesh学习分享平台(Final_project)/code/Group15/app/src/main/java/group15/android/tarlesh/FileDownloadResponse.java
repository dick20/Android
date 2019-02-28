package group15.android.tarlesh;

import java.nio.Buffer;

public class FileDownloadResponse {
    boolean sign;
    String fname;
    DownloadFile file;

    class DownloadFile{
        String type;
        byte[] data;
        DownloadFile(String type, byte[] data) {
            this.type = type;
            this.data = data;
        }

        public byte[] getData() { return data; }

        public String getType() { return type; }
    }

    FileDownloadResponse(boolean sign, String type, DownloadFile file) {
        this.sign = sign;
        this.file = file;
    }

    public boolean isSign() { return sign; }

    public String getFname() { return fname; }

    public DownloadFile getFile() { return file; }
}
