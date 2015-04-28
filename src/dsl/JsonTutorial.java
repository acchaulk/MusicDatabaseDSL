package dsl;

import org.boon.HTTP;
import org.boon.IO;
import org.boon.Lists;
import org.boon.core.Dates;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import json.templates.*;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.boon.Boon.puts;

/**
 * Created by rick on 1/4/14.
 */
public class JsonTutorial {
	


	public static class MyBean {
		String name = "Rick";

		@Override
		public String toString() {
			return "MyBean{" +
					"name='" + name + '\'' +
					'}';
		}
	}


	/*
	 * {
	    "id": "5b11f4ce-a62d-471e-81fc-a69a8278c7da",
	    "name": "Nirvana",
	    "sort-name": "Nirvana"
	    "type": "Group",
	    "country": "US",
	    "disambiguation": "90s US grunge band",
	    "life-span": {
	        "ended": true,
	        "begin": "1988-02",
	        "end": "1994-04-05"
	    },
	    "aliases": [ { "name": "Nirvana US", "sort-name": "Nirvana US" } ]]
		}
	 * */
	//    public static class Artist {
	//        String name = "Rick";
	//
	//        @Override
	//        public String toString() {
	//            return "MyBean{" +
	//                    "name='" + name + '\'' +
	//                    '}';
	//        }
	//    }


	public static class Artists {
		
		public static class Artist {
			String id;
			String name;

			@Override
			public String toString() {
				return "Artists{" +
						"id: " + id + " name: " + name +
						'}';
			}
		}
		
		List<Artist> artists = new ArrayList<Artist>();
	}



	public static class User {
		public enum Gender { MALE, FEMALE };

		public static class Name {
			private String first, last;

			public Name( String _first, String _last ) {
				this.first = _first;
				this.last = _last;
			}

			public String getFirst() { return first; }
			public String getLast() { return last; }

			public void setFirst(String s) { first = s; }
			public void setLast(String s) { last = s; }

			@Override
			public String toString() {
				return "Name{" +
						"first='" + first + '\'' +
						", last='" + last + '\'' +
						'}';
			}
		}

		private Gender gender;
		private Name name;
		private boolean verified;
		private Date birthDate;

		public Name getName() { return name; }
		public boolean isVerified() { return verified; }
		public Gender getGender() { return gender; }

		public void setName(Name n) { name = n; }
		public void setVerified(boolean b) { verified = b; }
		public void setGender(Gender g) { gender = g; }

		public Date getBirthDate() { return birthDate; }
		public void setBirthDate( Date birthDate ) { this.birthDate = birthDate; }

		@Override
		public String toString() {
			return "User{" +
					"gender=" + gender +
					", name=" + name +
					", isVerified=" + verified +
					'}';
		}
	}


	public static void part1 () throws Exception {


		MyBean myBean = new MyBean();
		File dst = File.createTempFile("emp", ".json");

		ObjectMapper mapper =  JsonFactory.create();

		puts ("json string", mapper.writeValueAsString( myBean ));

		mapper.writeValue( dst, myBean ); // where 'dst' can be File, OutputStream or Writer


		File src = dst;
		MyBean value = mapper.readValue(src, MyBean.class); // 'src' can be File, InputStream, Reader, String

		puts ("mybean", value);



		Object root = mapper.readValue(src, Object.class);
		Map<String,Object> rootAsMap =  mapper.readValue(src, Map.class);

		puts ("root", root);
		puts ("rootAsMap", rootAsMap);



		MyBean myBean1 = new MyBean(); myBean1.name = "Diana";
		MyBean myBean2 = new MyBean(); myBean2.name = "Rick";

		dst = File.createTempFile("empList", ".json");

		final List<MyBean> list = Lists.list( myBean1, myBean2 );

		puts ("json string", mapper.writeValueAsString( list ));

		mapper.writeValue( dst, list );

		src = dst;

		List<MyBean> beans = mapper.readValue(src, List.class, MyBean.class);

		puts ("mybeans", beans);


	}

	public static void part2 () throws Exception {

		ObjectMapper mapper =  JsonFactory.create();

		User user = new User();
		user.setGender( User.Gender.MALE );
		user.setName(new User.Name("Richard", "Hightower"));
		user.setVerified( true );
		user.setBirthDate( Dates.getUSDate( 5, 25, 1980 ) );


		puts (mapper.writeValueAsString( user ));



		//Now to write and then read this as a file.

		File file = File.createTempFile( "user", ".json" );

		mapper.writeValue( file, user );

		User userFromFile = mapper.readValue( file, User.class );

		puts ( "userFromFile", userFromFile );


		Path path = Paths.get(file.toString());
		InputStream inputStream = Files.newInputStream(path);

		User userFromInput = mapper.readValue( inputStream, User.class );
		puts ( "userFromInput", userFromInput );


		Reader reader = Files.newBufferedReader( path, StandardCharsets.UTF_8 );
		User userFromReader = mapper.readValue( reader, User.class );

		puts ( "userFromReader", userFromReader );


	}

	public static void artistTest () throws Exception {
		ObjectMapper mapper =  JsonFactory.create();
		//    	 Artist userFromURL = mapper.readValue( IO.read(
		//    			 "http://musicbrainz.org/ws/2/artist/?query=artist:Nirvana&fmt=json"), Artist.class);
		//    	  puts ( "userFromReader", userFromURL );
		//    	IO.read("http://musicbrainz.org/ws/2/artist/?query=artist:Nirvana&fmt=json");
		//    	HTTP.getJSON("http://musicbrainz.org/ws/2/artist/?query=artist:Nirvana&fmt=json", mapper);


		//    	Artist userList = mapper.readValue( HTTP.getJSON(
		//    			"http://musicbrainz.org/ws/2/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da?inc=aliases&fmt=json", null),  
		//    			 Artist.class );

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		System.out.println(HTTP.getJSON("http://musicbrainz.org/ws/2/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da?inc=aliases&fmt=json",
				headers));

		//		Artists artist = mapper.readValue(HTTP.getJSON("http://musicbrainz.org/ws/2/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da?inc=aliases&fmt=json",
		//				headers), Artists.class);
		//		puts("artist", artist);

		//		Artist art = mapper.readValue("{\"response\": {\"status\": {\"version\": \"4.2\",\"code\": 0, \"message\": \"Success\"},\"artists\": [{\"id\": \"AR6XPWV1187B9ADAEB\", \"name\": \"Foo Fighters\"}\"]}}", Artist.class);
		//		puts("art:", art);


		Artists a = mapper.readValue(HTTP.getJSON("http://developer.echonest.com/api/v4/artist/similar?api_key=DGRSTO8KKQIAWYCPY&name=Nirvana&format=json",
				headers), Artists.class);
		puts("Artists:", a);

		System.out.println(HTTP.getJSON("http://developer.echonest.com/api/v4/artist/similar?api_key=DGRSTO8KKQIAWYCPY&name=Nirvana", headers));
	}

	public static void main (String... args) throws Exception {

		//        part1();
		//        System.out.println("-------------");
		//        part2();
		//        System.out.println("-------------");
//		artistTest();
		artistSimilarTo("Foo Fighters");
//		EchoNestBoonTest a = new EchoNestBoonTest();
//		a.testReadArtists();

	}
	
	public static void artistSimilarTo(String artist) {
		
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		try {
			artist = URLEncoder.encode(artist, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String URL = "http://developer.echonest.com/api/v4/artist/similar?api_key=DGRSTO8KKQIAWYCPY&format=json&name=" + artist;
		System.out.println(URL);
		
		
		 ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
	        System.out.println(responseHolder.response.status.message);
	        
	        for(Artist s : responseHolder.response.artists) {
	        	System.out.println(s.name.toString());
	        }
		
//		Response a = mapper.readValue(HTTP.getJSON(URL, headers), Response.class);
//		puts("Artists:", a);
//		System.out.println(HTTP.getJSON(URL, headers));
		
	}
}