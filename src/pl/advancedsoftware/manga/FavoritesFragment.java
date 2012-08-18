package pl.advancedsoftware.manga;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class FavoritesFragment extends ListFragment {
	Activity parent;
	ListView listView;
	ArrayAdapter<Manga> arrayAdapter;
	TextView textView; 
	SearchView searchView;
	ArrayList<Manga> mangaList= new ArrayList<Manga>();
	
	@Override
    public void onAttach(Activity activity) {
    	parent = activity;
    	super.onAttach(activity);
    }
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	
    	mangaList.add(new Manga("Cos 2", "Cos innego 2"));
    	
    	super.onActivityCreated(savedInstanceState);
    }
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Context c = getActivity().getApplicationContext();
    	View view = inflater.inflate(R.layout.list_view, container, false);
    	
    	textView = (TextView) view.findViewById(android.R.id.empty);
    	searchView = (SearchView) view.findViewById(R.id.searchView);
    	listView = (ListView) view.findViewById(android.R.id.list);
    	listView.setAdapter(arrayAdapter = new ArrayAdapter<Manga>(c,
        	R.layout.listview_item,
        	mangaList));
    	listView.setTextFilterEnabled(true);

        return view;
    }
	
}
