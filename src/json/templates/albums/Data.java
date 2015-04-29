package json.templates.albums;

public class Data {
	public String release_year;
	public String title;
	public String entity_type;
	public String album_artist_id;
	public String id;
	public String number_of_tracks;
	public String label_name;
	public String album_ref_id;
	public String performer_name;
	public String main_genre;
	public String product_form;

	public Data(String release_year, String title, String entity_type,
			String album_artist_id, String id, String number_of_tracks,
			String label_name, String album_ref_id, String performer_name,
			String main_genre, String product_form) {
		super();
		this.release_year = release_year;
		this.title = title;
		this.entity_type = entity_type;
		this.album_artist_id = album_artist_id;
		this.id = id;
		this.number_of_tracks = number_of_tracks;
		this.label_name = label_name;
		this.album_ref_id = album_ref_id;
		this.performer_name = performer_name;
		this.main_genre = main_genre;
		this.product_form = product_form;
	}
	
	public boolean equals(Data d) {
		if(this.title.equals(d.title) && this.performer_name.equals(d.performer_name)) {
			return true;
		}
		return false;
	}
}

