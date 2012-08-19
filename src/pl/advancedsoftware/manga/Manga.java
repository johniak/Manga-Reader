package pl.advancedsoftware.manga;

import java.io.Serializable;

public class Manga implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String url;
	private boolean read=false;
	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Manga(String title,String url){
		this.title=title;
		this.url=url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return this.title;
	}
	@Override
	public boolean equals(Object o) {
		Manga manga= (Manga)o;
		if(manga.title.compareTo(title)==0&&manga.url.compareTo(url)==0)
			return true;
		return false;
	}

	
}
