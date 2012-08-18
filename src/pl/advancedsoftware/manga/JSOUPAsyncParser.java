package pl.advancedsoftware.manga;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.select.*;


import android.R.integer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class JSOUPAsyncParser extends AsyncTask<String, integer, Elements> {
	int counter;
	@Override
	protected Elements doInBackground(String... params) {
		counter=0;
		return doParse(params);
	}
	private Elements doParse(String... params){
		
		if(counter>10){
			return null;
		}
		counter++;
		try {
			Log.v("JRe", params[0]);
			return Jsoup.connect(params[0]).get().select(params[1]);
		} catch (IOException e) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return doParse(params);
		}
	}
}
