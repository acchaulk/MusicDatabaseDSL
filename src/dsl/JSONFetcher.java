package dsl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.boon.HTTP;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import json.templates.*;

public class JSONFetcher {

	// LAST FM 		1d5bb175305c97b01c4316f972dc3bd9
	// echonest		DGRSTO8KKQIAWYCPY
	// musicgraph 	473468b3be7f6bfd4ecf7f8f576a799c


	// album.similarTo	
	// http://api.musicgraph.com/api/v2/album/search?api_key=473468b3be7f6bfd4ecf7f8f576a799c&top_rated=true&similar_to=Good+Kid+Mad+City
	// alternative/indie, blues, cast recordings/cabaret, christian/gospel, children's, 
	//	classical/opera, comedy/spoken word, country, electronica/dance, folk, instrumental, jazz, 
	//	latin, new age, pop, rap/hip hop, reggae/ska, rock, seasonal, soul/r&b, soundtracks, vocals, 
	//	world, show music, contemporary christian, drum n' bass, techno, latin jazz, swing, latin pop, 
	//	latin rock, latin urban, mpb, regional mexican, salsa, tango, pop ballad, pop rock, 90's rock, 
	//	adult contemporary, metal, television, tropical
	
	
	
	public static ResponseHolder songSimilarTo(String title, String artist) {
		
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");
		artist = convertStringToUTF(artist);
		title = convertStringToUTF(title);
		//		System.out.println(artist);
		String URL = "http://developer.echonest.com/api/v4/song/search?api_key=DGRSTO8KKQIAWYCPY&format=json&results=1&artist=" + artist + "&title=" + title;
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);

		String id = "";
		if(responseHolder.response.songs[0].id != null) {
			id = responseHolder.response.songs[0].id;
		}
		System.out.println("SONG ID: " + id);
		
		URL = "http://developer.echonest.com/api/v4/playlist/static?api_key=DGRSTO8KKQIAWYCPY&song_id=" + id +
				"&format=json&results=20&type=song-radio";
		responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		return responseHolder;
	}
	
	public static ResponseHolder songSameGenre(String genre) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		genre = genre.replaceAll("^\"|\"$", "");
		genre = convertStringToUTF(genre);

		String URL = "http://developer.echonest.com/api/v4/playlist/static?api_key=DGRSTO8KKQIAWYCPY&format=json&results=100&type=genre-radio&genre=" + genre;
		//		System.out.println(URL);
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		return responseHolder;
	}
	


	public static ResponseHolder albumCombinedQueries(String genre, String similarTo, String decade) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		genre = genre.replaceAll("^\"|\"$", "");
		genre = convertStringToUTF(genre);

		similarTo = similarTo.replaceAll("^\"|\"$", "");
		similarTo = convertStringToUTF(similarTo);

		decade = decade.replaceAll("^\"|\"$", "");
		decade = convertStringToDecade(decade);
		decade = convertStringToUTF(decade);

		String URL = "http://api.musicgraph.com/api/v2/album/search?api_key=473468b3be7f6bfd4ecf7f8f576a799c"
				+ "&top_rated=true&limit=100&genre=" + genre + "&similar_to=" + similarTo + "&decade=" + decade;
		System.out.println(URL);
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		return responseHolder;

	}

	public static ResponseHolder albumSameGenre(String genre) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		genre = genre.replaceAll("^\"|\"$", "");
		genre = convertStringToUTF(genre);

		String URL = "http://api.musicgraph.com/api/v2/album/search?api_key=473468b3be7f6bfd4ecf7f8f576a799c&top_rated=true&limit=100&genre=" + genre;
		//		System.out.println(URL);
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		return responseHolder;
	}

	public static ResponseHolder albumSimilarTo(String album) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		album = album.replaceAll("^\"|\"$", "");
		album = convertStringToUTF(album);

		String URL = "http://api.musicgraph.com/api/v2/album/search?api_key=473468b3be7f6bfd4ecf7f8f576a799c&limit=100&similar_to=" + album;
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		return responseHolder;
	}

	public static ResponseHolder albumSameDecade(String decade) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		decade = decade.replaceAll("^\"|\"$", "");
		decade = convertStringToDecade(decade);
		decade = convertStringToUTF(decade);
		//		System.out.println(decade);

		String URL = "http://api.musicgraph.com/api/v2/album/search?api_key=473468b3be7f6bfd4ecf7f8f576a799c&top_rated=true&limit=100&decade=" + decade;
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);
		//		System.out.println(URL);
		return responseHolder;
	}





	public static ResponseHolder genreSimilarTo(String genre) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");

		genre = genre.replaceAll("^\"|\"$", "");
		genre = convertStringToUTF(genre);
		genre = genre.toLowerCase();
		//		System.out.println(genre);
		String URL = "http://developer.echonest.com/api/v4/genre/similar?api_key=DGRSTO8KKQIAWYCPY&bucket=description&name=" + genre;
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);

		return responseHolder;
	}

	public static ResponseHolder artistSimilarTo(String artist) {

		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");
		artist = convertStringToUTF(artist);
		//		System.out.println(artist);
		String URL = "http://developer.echonest.com/api/v4/artist/similar?api_key=DGRSTO8KKQIAWYCPY&format=json&results=100&min_familiarity=.7&name=" + artist;
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);

		return responseHolder;

	}

	public static ResponseHolder artistSameGenre(String genre) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");
		genre = genre.replaceAll("^\"|\"$", "");
		genre = convertStringToUTF(genre);
		genre = genre.toLowerCase();
		//		System.out.println(genre);

		String URL = "http://developer.echonest.com/api/v4/artist/search?api_key=DGRSTO8KKQIAWYCPY&format=json&results=100&min_familiarity=.7&genre=" + genre;
		//		System.out.println(URL);
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);

		return responseHolder;
	}

	public static ResponseHolder artistSameDecade(String decade) {
		ObjectMapper mapper =  JsonFactory.create();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Musicbrainz School Projcet/1.0 ( acchaulk@wpi.edu )");
		decade = decade.replaceAll("^\"|\"$", "");
		//		decade = decade.toLowerCase();
		//		System.out.println(decade);

		int year = Integer.parseInt(decade);

		int mod = year % 10;
		String startYear = Integer.toString(year - mod - 1);
		String endYear = Integer.toString(year - mod + 10);
		startYear = convertStringToUTF(startYear);
		endYear = convertStringToUTF(endYear);


		String URL = "http://developer.echonest.com/api/v4/artist/search?api_key=DGRSTO8KKQIAWYCPY&format=json&results=100&min_familiarity=.7&artist_start_year_before=" + endYear + "&artist_start_year_after=" + startYear;
		//		System.out.println(URL);
		ResponseHolder responseHolder = mapper.readValue(HTTP.get(URL), ResponseHolder.class);

		return responseHolder;
	}





	/* Methods called directly by DSLCodeGenerator.java */
	public static ResponseHolder similarTo(String queryString, String queryType) {
		ResponseHolder response = null;
		switch(queryType) {
		case "ARTIST":
			response = artistSimilarTo(queryString);
			break;
		case "ALBUM":
			response = albumSimilarTo(queryString);
			break;
		case "GENRE":
			response = genreSimilarTo(queryString);
			break;
		}
		return response;
	}

	public static ResponseHolder sameGenre(String queryString, String queryType) {
		ResponseHolder response = null;
		switch(queryType) {
		case "ARTIST":
			response = artistSameGenre(queryString);
			break;
		case "ALBUM":
			response = albumSameGenre(queryString);
			break;
		case "GENRE":
			break;
		}
		return response;
	}

	public static ResponseHolder sameDecade(String queryString, String queryType) {
		ResponseHolder response = null;
		switch(queryType) {
		case "ARTIST":
			response = artistSameDecade(queryString);
			break;
		case "ALBUM":
			response = albumSameDecade(queryString);
			break;
		case "GENRE":
			break;
		}
		return response;
	}

	public static List<String> artistsToString(List<Artist> artists) {
		List<String> results = new ArrayList<String>();
		for(Artist s : artists) {
			results.add(s.name);
		}
		return results;
	}

	public static List<Artist> stringsToArtists(List<String> strings) {
		List<Artist> results = new ArrayList<Artist>();
		Artist a;
		for(String s : strings) {
			a = new Artist("recreated", s);
			results.add(a);
		}
		return results;
	}

	public static List<String> genresToString(List<Genre> genres) {
		List<String> results = new ArrayList<String>();
		for(Genre s : genres) {
			results.add(s.name);
		}
		return results;
	}

	public static List<Genre> stringsToGenres(List<String> strings) {
		List<Genre> results = new ArrayList<Genre>();
		Genre a;
		for(String s : strings) {
			a = new Genre("recreated", s, "recreated");
			results.add(a);
		}
		return results;
	}

	public static <T> List<T> andListOp(List<T> list1, List<T> list2) {
		List<T> results = new ArrayList<T>();

		for (T t : list1) {
			if(list2.contains(t)) {
				results.add(t);
			}
		}

		return results;
	}

	public static <T> List<T>orListOp(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();

		set.addAll(list1);
		set.addAll(list2);
		List<T> list = new ArrayList<T>(set);

		return list;
		//		return new ArrayList<T>(set);	
	}

	public static String convertStringToUTF(String s) {
		String result = null;
		try {
			result = URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String convertStringToDecade(String s) {
		if(!s.isEmpty()) {
			int year = Integer.parseInt(s);
			int mod = year % 10;
			int decade = year - mod;
			String result =  Integer.toString(decade);
			result += "s";
			return result;
		}
		return "";
	}

}
