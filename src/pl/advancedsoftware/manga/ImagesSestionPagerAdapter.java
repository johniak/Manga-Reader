package pl.advancedsoftware.manga;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

public class ImagesSestionPagerAdapter extends FragmentPagerAdapter {

	ArrayList<String> URLs;
	
	public ImagesSestionPagerAdapter(FragmentManager fragmentManager,ArrayList<String> URLs) {
		super(fragmentManager);
		this.URLs = URLs;
	}


	
	@Override
	public Fragment getItem(int i) {
		Log.v("JRf", "lololo::"+i);
		return new ImageViewFragment(URLs.get(i));
	}
@Override
public void destroyItem(ViewGroup container, int position, Object object) {
	Log.v("JRf", "papapa::"+position);
	super.destroyItem(container, position, object);
}
	@Override
	public int getCount() {
		return URLs.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		
		return Integer.toString(position);
	}
}


