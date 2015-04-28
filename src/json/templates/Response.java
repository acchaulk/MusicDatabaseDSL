package json.templates;

import java.util.List;

public class Response {
    public Status status;
    public List<Artist> artists;


    public Response(Status status, List<Artist> artists) {
        super();
        this.status = status;
        this.artists = artists;
    }


    @Override
    public String toString() {
        return "Response [status=" + status + ", artists=" + artists + "]";
    }

}
