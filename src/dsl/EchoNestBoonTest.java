package dsl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import json.templates.Artist;
import json.templates.ResponseHolder;

import org.boon.HTTP;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.junit.Test;

public class EchoNestBoonTest {
	

    @Test
    public void testReadArtists() {
        ObjectMapper mapper = JsonFactory.create();
        // String artistStr =
        // "{\"artists\":[{\"id\":\"AR6XPWV1187B9ADAEB\",\"name\":\"Foo Fighters\"},{\"id\":\"ARR4HE8119B866873B\",\"name\":\"Nirvana 2002\"}]}";
       
        String artistJson = HTTP
                .get("http://developer.echonest.com/api/v4/artist/similar?api_key=DGRSTO8KKQIAWYCPY&name=Of+Monsters+And+Men&format=json");
        System.out.println(artistJson);
        ResponseHolder responseHolder = mapper.readValue(artistJson, ResponseHolder.class);
        System.out.println(responseHolder.response.status.message);
        
        for(Artist s : responseHolder.response.artists) {
        	System.out.println(s.name.toString());
        }
//        System.out.println(responseHolder.response.artists.get(0).name);
    }

//    public static class ResponseHolder {
//        Response response;
//
//
//        public ResponseHolder(Response response) {
//            super();
//            this.response = response;
//        }
//
//
//        @Override
//        public String toString() {
//            return "ResponseHolder [response=" + response + "]";
//        }
//
//    }
//
//    public static class Response {
//        Status status;
//        List<Artist> artists;
//
//
//        public Response(Status status, List<Artist> artists) {
//            super();
//            this.status = status;
//            this.artists = artists;
//        }
//
//
//        @Override
//        public String toString() {
//            return "Response [status=" + status + ", artists=" + artists + "]";
//        }
//
//    }
//
//    public static class Artist {
//        String id;
//        String name;
//
//
//        public Artist(String id, String name) {
//            super();
//            this.id = id;
//            this.name = name;
//        }
//
//
//        @Override
//        public String toString() {
//            return "Artist{" + "id: " + id + " name: " + name + '}';
//        }
//    }
//
//    public static class Status {
//        String code;
//        String message;
//        String version;
//
//
//        public Status(String code, String message, String version) {
//            super();
//            this.code = code;
//            this.version = version;
//            this.message = message;
//        }
//    }
    

}