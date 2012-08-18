package pl.advancedsoftware.manga;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
		BookActivity This=this;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			setContentView(R.layout.activity_book);
			episodesListView= (ListView)findViewById(android.R.id.list);
			episodes=new ArrayList<Manga>();
			episodesArrayAdapter= new ArrayAdapter<Manga>(this, R.layout.listview_item,episodes);
			episodesListView.setAdapter(episodesArrayAdapter);
			Bundle extras = getIntent().getExtras();
			this.currentManga = new Manga((String)extras.get("title"),(String) extras.get("url"));
			TextView titleTextView=((TextView)findViewById(R.id.bookTitle));
			titleTextView.setText(currentManga.getTitle());
			getMangList();
			
		}
		
		protected void getMangList() {
			This.setProgressBarIndeterminateVisibility(true);
			JSOUPAsyncParser parser= new JSOUPAsyncParser(){
				@Override
				protected void onPostExecute(Elements result) {
					for(Element element:result){
						Log.v("JRe", element.html());
						if(element.html().compareTo("Online")!=0)
							episodes.add(new Manga(element.html(), element.attr("href")));
					}
					episodesArrayAdapter.notifyDataSetChanged();
					Log.v("JRe", "Parsed!");
					This.setProgressBarIndeterminateVisibility(false);
					super.onPostExecute(result);
				}
			};
			parser.execute(currentManga.getUrl(), "td > a[href^=http://www.manga-lib.pl/index.php/manga/online/");
		}
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			Manga selectedManga=(Manga)getListView().getItemAtPosition(position);
	    	
	    	Intent intent = new Intent(this, EpisodeActivity.class);
	    	intent.putExtra("url", selectedManga.getUrl());
	    	intent.putExtra("title", selectedManga.getTitle());
	    	startActivity(intent);
	    	super.onListItemClick(l, v, position, id);
		}
}
