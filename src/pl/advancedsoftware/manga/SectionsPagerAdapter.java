package pl.advancedsoftware.manga;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	public SectionsPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}


	
	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			return new MangaListFragment();
		case 1:
			return new FavoritesFragment();
		}
		return null; 
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Manga books";
		case 1:
			return "Favorite";
		}
		return null;
	}
}