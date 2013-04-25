package pl.advancedsoftware.manga;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.advancedsoftware.manga.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MangaListFragment extends ListFragment {
	Activity parent;
	ListView listView;
	ArrayAdapter<Manga> arrayAdapter;
	TextView textView; 
	SearchView searchView;
	ArrayList<Manga> mangaList= new ArrayList<Manga>();;
	
	@Override
    public void onAttach(Activity activity) {
    	parent = activity;
    	super.onAttach(activity);
    }
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		setupSearchView();
		getMangList();
		
    	
    	super.onActivityCreated(savedInstanceState);
    }
	@Override
	public void onResume() {
		searchView.setQuery("", true);
		getMangList();
		super.onResume();
	}
	protected void getMangList() {
		searchView.setVisibility(View.GONE);
		parent.setProgressBarIndeterminateVisibility(true);
		JSOUPAsyncParser parser= new JSOUPAsyncParser(){
			@Override
			protected void onPostExecute(Elements result) {
				if(result==null){
					Toast.makeText(parent, "Internet connection problem", Toast.LENGTH_LONG).show();
				}
				mangaList.clear();
				for(Element element:result){
					mangaList.add(new Manga(element.html(), element.attr("href")));

					
				}
				arrayAdapter.notifyDataSetChanged();
				Log.v("JRe", "Parsed!");
				parent.setProgressBarIndeterminateVisibility(false);
				super.onPostExecute(result);
				searchView.setVisibility(View.VISIBLE);
			}
		};
		parser.execute("http://www.manga-lib.pl/index.php/manga/directory", "a[href^=http://www.manga-lib.pl/index.php/manga/info/");
		
		
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Context c = getActivity().getApplicationContext();
    	View view = inflater.inflate(R.layout.list_view, container, false);
    	
    	searchView = (SearchView) view.findViewById(R.id.searchView);
    	listView = (ListView) view.findViewById(android.R.id.list);
    	textView = (TextView) view.findViewById(android.R.id.empty);
    	listView.setOnItemClickListener(onMangaItemClickListener);
    	listView.setAdapter(arrayAdapter = new ArrayAdapter<Manga>(c,
        	R.layout.listview_item,
        	mangaList));
    	listView.setTextFilterEnabled(true);
    	textView.setText("Donwnloading data..."); 
        return view;
    }
    private void setupSearchView() {
		//searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(onQueryTextListener);
		searchView.setSubmitButtonEnabled(false);
		searchView.setQueryHint("Enter Manga title...");
    }
    
	SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String query) {
			searchView.setIconifiedByDefault(false);
			searchView.setOnQueryTextListener(this);
			searchView.setSubmitButtonEnabled(false);
			searchView.setQueryHint("Enter Manga title...");
			return false;
		}
		
		@Override
		public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
            	listView.clearTextFilter();
            } else {
            	listView.setFilterText(newText.toString());
            }
            return true;
		}
	};
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		Manga selectedManga=(Manga)getListView().getItemAtPosition(position);
    	
    	Intent intent = new Intent(parent, BookActivity.class);
    	intent.putExtra("url", selectedManga.getUrl());
    	intent.putExtra("title", selectedManga.getTitle());
    	startActivity(intent);
	}
	OnItemClickListener onMangaItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int index,
				long id) {
			
			
		}
	};
}
