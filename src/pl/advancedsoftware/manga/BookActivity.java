package pl.advancedsoftware.manga;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BookActivity extends ListActivity {
	ArrayAdapter<Manga> episodesArrayAdapter;
	ArrayList<Manga> episodes;
	ListView episodesListView;
	Manga currentManga;
	BookActivity This = this;
	MangaDatabaseManager databaseManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_book);
		episodesListView = (ListView) findViewById(android.R.id.list);
		episodes = new ArrayList<Manga>();
		episodesArrayAdapter = new ArrayAdapter<Manga>(this,
				R.layout.listview_item, episodes) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				TextView t = (TextView) v.findViewById(android.R.id.text1);
				t.setTextColor(episodes.get(position).isRead() ? Color
						.parseColor("#AA0000") : Color.BLACK);
				return v;
			}
		};
		episodesListView.setAdapter(episodesArrayAdapter);
		Bundle extras = getIntent().getExtras();
		this.currentManga = new Manga((String) extras.get("title"),
				(String) extras.get("url"));
		TextView titleTextView = ((TextView) findViewById(R.id.bookTitle));
		TextView textView = (TextView) findViewById(android.R.id.empty);
		textView.setText("Donwnloading data...");
		titleTextView.setText(currentManga.getTitle());
		getMangList();
	}

	protected void getMangList() {
		This.setProgressBarIndeterminateVisibility(true);
		JSOUPAsyncParser parser = new JSOUPAsyncParser() {
			@Override
			protected void onPostExecute(Elements result) {
				SharedPreferences settings = getSharedPreferences(
						"MangaReaderPrefs", 0);
				int book = settings.getInt("book" + currentManga.getUrl(), -1);
				for (Element element : result) {
					if (element.html().compareTo("Online") != 0)
						episodes.add(new Manga(element.html(), element
								.attr("href")));
				}
				if (book >= 0)
					episodes.get(book).setRead(true);
				episodesArrayAdapter.notifyDataSetChanged();
				This.setProgressBarIndeterminateVisibility(false);

				episodesListView.setSelection(book);
				super.onPostExecute(result);
			}
		};
		parser.execute(currentManga.getUrl(),
				"td > a[href^=http://www.manga-lib.pl/index.php/manga/online/");
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Manga selectedManga = (Manga) getListView().getItemAtPosition(position);
		SharedPreferences settings = getSharedPreferences("MangaReaderPrefs", 0);
		SharedPreferences.Editor editor = settings.edit();

		int book = settings.getInt("book" + currentManga.getUrl(), -1);
		if (book >= 0)
			episodes.get(book).setRead(false);
		episodes.get(position).setRead(true);
		episodesArrayAdapter.notifyDataSetChanged();
		editor.putInt("book" + currentManga.getUrl(), position);
		editor.commit();
		Intent intent = new Intent(this, EpisodeActivity.class);
		intent.putExtra("url", selectedManga.getUrl());
		intent.putExtra("title", selectedManga.getTitle());
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_book, menu);
		if (Favourites.isFavourite(currentManga)) {
			menu.getItem(0).setIcon(android.R.drawable.star_on);
		}
		menu.getItem(0).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Favourites.toggleFavourite(currentManga);
						if (Favourites.isFavourite(currentManga)) {
							item.setIcon(android.R.drawable.star_on);
						} else {
							item.setIcon(android.R.drawable.star_off);
						}
						return false;
					}
				});

		return true;
	}

}
