package json.templates.song;

import json.templates.albums.Data;

public class Song {
	public Song(String artist_id, String id, String artist_name, String title) {
		super();
		this.artist_id = artist_id;
		this.id = id;
		this.artist_name = artist_name;
		this.title = title;
	}
	public String artist_id;
	public String id;
	public String artist_name;
	public String title;
	
	public boolean equals(Song s) {
		if(this.id.equals(s.id)) {
			return true;
		}
		return false;
	}
}
