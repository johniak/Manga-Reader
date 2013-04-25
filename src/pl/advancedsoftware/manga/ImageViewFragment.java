package pl.advancedsoftware.manga;

import pl.advancedsoftware.manga.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.polites.android.GestureImageView;

public class ImageViewFragment extends Fragment {

	Activity parent;
	String url;
	GestureImageView imageView;
	static int counter=0;
	static float scale=1;
	
	
	
	public ImageViewFragment(String url){
		this.url=url;
		
	}
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		parent.setProgressBarIndeterminateVisibility(true);
		DownloadImageAsyncTask getImageAsyncTask= new DownloadImageAsyncTask(){
    		@Override
    		protected void onPostExecute(Bitmap[] result) {
    			imageView.setImageBitmap(result[0]);
    			result=null;
    			System.gc();
    			parent.setProgressBarIndeterminateVisibility(false);
    			super.onPostExecute(result);
    		}
    	};
    	getImageAsyncTask.execute(url);
	}
	
	@Override
    public void onAttach(Activity activity) {
    	parent = activity;
    	super.onAttach(activity);
    }
	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		
		imageView.setImageBitmap(null);
		
		MemoryInfo mInfo= new MemoryInfo();
		Debug.getMemoryInfo(mInfo);
		Log.v("JRf", "papapa::"+mInfo.getTotalPrivateDirty());
		super.onDestroyView();
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.image_view_layout, container, false);
    	imageView=(GestureImageView)view.findViewById(R.id.imageView);
    	
    	
    	super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
	
}
