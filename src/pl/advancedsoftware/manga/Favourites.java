package pl.advancedsoftware.manga;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.style.UpdateAppearance;
import android.util.Log;

public class Favourites {
	private static ArrayList<Manga> mangaArrayList;
	private static final String FAVORITE_LIST_PREFS_KEY = "FAVORITE_LIST_PREFS";
	private static final String MANGA_PREFS = "MangaReaderPrefs";
	public static Context context;

	private static String serialize(Manga m) {
		return m.getTitle() + "~" + m.getUrl();
	}

	private static Manga deserialize(String ss) {
		String[] ds = ss.split("~");
		if(ds.length<=1)
			return null;
		return new Manga(ds[0], ds[1]);
	}

	private static String seralizeList(List<Manga> list) {
		String ss = "";
		for (Manga manga : list) {
			ss += serialize(manga) + "|";
		}
		return ss;
	}

	private static ArrayList<Manga> deserializeList(String ss) {
		String[] ds = ss.split("\\|");
		ArrayList<Manga> mangas = new ArrayList<Manga>();
		for (String s : ds) {
			Log.v("JRe", s);
			if(deserialize(s)!=null)
				mangas.add(deserialize(s));
		}
		return mangas;
	}
	public static boolean isFavourite(Manga m){
		if(getFavoritesList().indexOf(m)>=0){
			return true;
		}
		return false;
	}
	public static void toggleFavourite(Manga m){
		if(isFavourite(m)){
			mangaArrayList.remove(m);
		}else {
			mangaArrayList.add(m);
		}
		update();
	}
	private static void update(){
		insertMangaList(mangaArrayList);
	}
	private static void insertMangaList(ArrayList<Manga> list) {
		String ss = seralizeList(list);
		SharedPreferences settings = context.getSharedPreferences(MANGA_PREFS,
				0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(FAVORITE_LIST_PREFS_KEY, ss);
		editor.commit();
	}

	public static ArrayList<Manga> getFavoritesList() {
		if (mangaArrayList != null)
			return mangaArrayList;
		SharedPreferences settings = context.getSharedPreferences(MANGA_PREFS,0);
		return mangaArrayList = deserializeList(settings.getString(
				FAVORITE_LIST_PREFS_KEY, ""));
	}
}
