package pl.advancedsoftware.manga;

import java.util.ArrayList;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ImagesSestionPagerAdapter extends FragmentPagerAdapter {

	ArrayList<String> URLs;
	
	public ImagesSestionPagerAdapter(FragmentManager fragmentManager,ArrayList<String> URLs) {
		super(fragmentManager);
		this.URLs = URLs;
	}


	
	@Override
	public Fragment getItem(int i) {
		return ImageViewFragment.newInstance(URLs.get(i)); //new ImageViewFragment(URLs.get(i));
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


