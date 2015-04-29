package json.templates;

import java.util.List;

public class Response {
    public Status status;
    public List<Artist> artists;
    public List<Genre> genres;


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
 
    @Override
    public String toString() {
        return "Response [status=" + status + ", artists=" + artists + "]";
    }

}
