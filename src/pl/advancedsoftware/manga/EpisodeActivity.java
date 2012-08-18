package pl.advancedsoftware.manga;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

public class EpisodeActivity extends FragmentActivity {
	Manga currentEpisode;
	String imageDirURL;
	ArrayList<String> imageURLs;
	ViewPager viewPager;
	EpisodeActivity This;

	@Override
	protected void onCreate(Bundle savedInstancebBundle) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		try {
			

			super.onCreate(savedInstancebBundle);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Bundle extras = getIntent().getExtras();
		currentEpisode = new Manga((String) extras.get("title"),
				(String) extras.get("url"));
		String episodeNr = currentEpisode.getUrl().split(",")[1];
		if (episodeNr.length() == 1) {
			episodeNr = "00" + episodeNr;
		} else if (episodeNr.length() == 2) {
			episodeNr = "0" + episodeNr;
		}

		imageURLs = new ArrayList<String>();
		String title = currentEpisode.getUrl().substring(
				currentEpisode.getUrl().lastIndexOf("/"),
				currentEpisode.getUrl().indexOf(","));
		imageDirURL = "http://img.manga-lib.pl" + title + "/" + episodeNr + "/";
		Log.v("JRe", imageDirURL);
		getImagesURLs();
	}

	protected void getImagesURLs() {
		JSOUPAsyncParser jsoupAsyncParser = new JSOUPAsyncParser() {
			@Override
			protected void onPostExecute(Elements result) {
				for (Element e : result) {
					String a=e.attr("href");
					if(a.compareTo("000_origin_info.jpg")!=0&&a.compareTo("../")!=0&&a.compareTo("origin_info.txt")!=0)
						imageURLs.add(imageDirURL+e.attr("href"));
					Log.v("JRf", e.attr("href"));
				}
				initialize();
				super.onPostExecute(result);
			}
		};
		jsoupAsyncParser.execute(imageDirURL, "a");
	}

	protected void initialize() {
		setContentView(R.layout.episode_activity);
		This = this;

		ImagesSestionPagerAdapter sectionsPagerAdapter = new ImagesSestionPagerAdapter(
				getSupportFragmentManager(),imageURLs);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(sectionsPagerAdapter);
	}
}
