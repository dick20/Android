package method;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class imageDownloader extends AsyncTask<String,Integer,Bitmap> {
    private ImageView view;

    public imageDownloader(ImageView v) {
        view= v;
    }

    @Override
    protected Bitmap doInBackground(String...param) {
        String url = param[0];
        Bitmap bitmap = null;
        try {
            //加载一个网络图片
            InputStream is = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        view.setImageBitmap(bitmap);
    }
}
