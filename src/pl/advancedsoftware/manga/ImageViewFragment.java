package pl.advancedsoftware.manga;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.polites.android.GestureImageView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImageViewFragment extends Fragment {

	Activity parent;
	String url;
	GestureImageView imageView;
	static ImageViewFragment instance;
	public ImageViewFragment(String url){
		this.url=url;
	}
	public static ImageViewFragment newInstance(String url){
		if( instance==null){
			instance= new ImageViewFragment(url);
		}else {
			//instance.url=url;
		}
		return new ImageViewFragment(url);
	}
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
    public void onAttach(Activity activity) {
    	parent = activity;
    	super.onAttach(activity);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.image_view_layout, container, false);
    	imageView=(GestureImageView)view.findViewById(R.id.imageView);
    	DownloadImageAsyncTask getImageAsyncTask= new DownloadImageAsyncTask(){
    		@Override
    		protected void onPostExecute(Bitmap[] result) {
    			imageView.setImageBitmap(result[0]);
    			
    			result=null;
    			System.gc();
    			super.onPostExecute(result);
    		}
    	};
    	getImageAsyncTask.execute(url);
    	
    	super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
	
}
