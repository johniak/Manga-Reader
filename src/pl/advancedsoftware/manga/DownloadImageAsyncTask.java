package pl.advancedsoftware.manga;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImageAsyncTask extends AsyncTask<String, Integer, Bitmap[]> {

	@Override
	protected Bitmap[] doInBackground(String... params) {
		Bitmap[] bitmaps = new Bitmap[params.length];
		for(int i=0;i<params.length;i++){
			bitmaps[i]=getImageBitmap(params[i]);
		}
		return bitmaps;
	}
	private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
        	Log.v("JRe","Dadas:"+url);
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=1;
          
            bm = BitmapFactory.decodeStream(bis,null,options);
            
            bis.close();
            is.close();
       } catch (Exception e) {
           Log.w("JRe",  e.toString());
       }
       return bm;
    } 
	
}
