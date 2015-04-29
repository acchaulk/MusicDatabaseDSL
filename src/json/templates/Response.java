package json.templates;

import java.util.List;

import json.templates.song.Song;

public class Response {
    public Status status;
    public List<Artist> artists;
    public List<Genre> genres;
    public Song[] songs;


    public Response(Status status, List<Artist> artists) {
        super();
        this.status = status;
        this.artists = artists;
    }

    public Response(List<Genre> genres, Status status) {
    	super();
        this.status = status;
        this.genres = genres;
    }
    
    public Response(Song[] songs, Status status) {
    	super();
        this.status = status;
        this.songs = songs;
    }
 
    @Override
    public String toString() {
        return "Response [status=" + status + ", artists=" + artists + "]";
    }

}
